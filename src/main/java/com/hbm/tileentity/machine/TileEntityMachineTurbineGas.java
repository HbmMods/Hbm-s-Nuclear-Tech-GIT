package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.gui.GUIMachineTurbineGas;
import com.hbm.inventory.container.ContainerMachineTurbineGas;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTurbineGas extends TileEntityMachinePolluting implements IFluidStandardTransceiver, IEnergyGenerator, IControlReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1000000L;
	
	public int rpm; //0-100, crescent moon gauge, used for calculating the amount of power generated, starts past 10%
	public int temp; //0-800, used for figuring out how much water to boil, starts boiling at 300°C
	public int rpmIdle = 10;
	public int tempIdle = 300;
	
	public int powerSliderPos; //goes from 0 to 60, 0 is idle, 60 is max power
	public int throttle; //the same thing, but goes from 0 to 100
	
	public boolean autoMode;
	public int state = 0; //0 is offline, -1 is startup, 1 is online
	
	public int counter = 0; //used to startup and shutdown
	public int instantPowerOutput;
	
	public FluidTank[] tanks;
	
	private AudioWrapper audio;
	
	public static HashMap<FluidType, Double> fuelMaxCons = new HashMap(); //fuel consumption per tick at max power
	
	static {
		fuelMaxCons.put(Fluids.GAS, 50D);			// natgas doesn't burn well so it burns faster to compensate
		fuelMaxCons.put(Fluids.SYNGAS, 10D);		// syngas just fucks
		fuelMaxCons.put(Fluids.OXYHYDROGEN, 100D);	// oxyhydrogen is terrible so it needs to burn a ton for the bare minimum
		fuelMaxCons.put(Fluids.REFORMGAS, 2.5D);	// halved because it's too powerful
		// default to 5 if not in list
	}
	
	//TODO particles from heat exchanger maybe? maybe in a future
	
	public TileEntityMachineTurbineGas() {
		super(2, 200);
		this.tanks = new FluidTank[4];
		tanks[0] = new FluidTank(Fluids.GAS, 100000);
		tanks[1] = new FluidTank(Fluids.LUBRICANT, 16000);
		tanks[2] = new FluidTank(Fluids.WATER, 16000);
		tanks[3] = new FluidTank(Fluids.HOTSTEAM, 160000);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			throttle = powerSliderPos * 100 / 60;
			
			if(slots[1] != null && slots[1].getItem() instanceof IItemFluidIdentifier) {
				FluidType fluid = ((IItemFluidIdentifier) slots[1].getItem()).getType(worldObj, xCoord, yCoord, zCoord, slots[1]);
				if(fluid.hasTrait(FT_Combustible.class) && fluid.getTrait(FT_Combustible.class).getGrade() == FuelGrade.GAS) {
					tanks[0].setTankType(fluid);
				}
			}
			
			switch(state) { //what to do when turbine offline, starting up and online			
			case 0:
				shutdown();	
				break;
			case -1:
				stopIfNotReady();
				startup();
				break;
			case 1:			
				stopIfNotReady();
				run();
				break;
			default:
				break;
			}
			
			if(autoMode) { //power production depending on power requirement
				
				//scales the slider proportionally to the power gauge
				int powerSliderTarget = 60 - (int) (60 * power / maxPower);
				
				if(powerSliderTarget > powerSliderPos) { //makes the auto slider slide instead of snapping into position
					powerSliderPos++;
				}
				else if(powerSliderTarget < powerSliderPos) {
					powerSliderPos--;
				}
			}
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", Math.min(this.power, this.maxPower)); //set first to get an unmodified view of how much power was generated before deductions from the net
			
			//do net/battery deductions first...
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);
			this.sendPower(worldObj, xCoord - dir.offsetZ * 5, yCoord + 1, zCoord + dir.offsetX * 5, rot); //sends out power
			
			//...and then cap it. Prevents potential future cases where power would be limited due to the fuel being too strong and the buffer too small.
			if(this.power > this.maxPower)
				this.power = this.maxPower;
			
			for(int i = 0; i < 2; i++) { //fuel and lube
				this.trySubscribe(tanks[i].getTankType(), worldObj, xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite());
				this.trySubscribe(tanks[i].getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ, dir);

				this.sendSmoke(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite());
				this.sendSmoke(xCoord + dir.offsetX * 2 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ, dir);
			}
			//water
			this.trySubscribe(tanks[2].getTankType(), worldObj, xCoord - dir.offsetX * 2 + rot.offsetX * -4, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * -4, dir.getOpposite());
			this.trySubscribe(tanks[2].getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * -4, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * -4, dir);
			//steam
			this.sendFluid(tanks[3], worldObj, xCoord + dir.offsetZ * 6, yCoord + 1, zCoord - dir.offsetX * 6, rot.getOpposite());
			
			data.setInteger("rpm",  this.rpm);
			data.setInteger("temp",  this.temp);
			data.setInteger("state", this.state);
			data.setBoolean("automode", this.autoMode);
			data.setInteger("throttle",  this.throttle);
			data.setInteger("slidpos",  this.powerSliderPos);
			
			if(state != 1) {
				data.setInteger("counter", this.counter); //sent during startup and shutdown
			} else {
				data.setInteger("instantPow", this.instantPowerOutput); //sent while running
			}
			
			tanks[0].writeToNBT(data, "fuel");
			tanks[1].writeToNBT(data, "lube");
			tanks[2].writeToNBT(data, "water");
			tanks[3].writeToNBT(data, "steam");
				
			this.networkPack(data, 150);
			
		} else { //client side, for sounds n shit
			
			if(rpm >= 10 && state != -1) { //if conditions are right, play the sound
				
				if(audio == null) { //if there is no sound playing, start it
					
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", xCoord, yCoord, zCoord, 1.0F, 20F, 1.0F);
					audio.startSound();
					
				} else if(!audio.isPlaying()) {
					audio.stopSound();
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", xCoord, yCoord, zCoord, 1.0F, 20F, 1.0F);
					audio.startSound();
				}
				
				audio.updatePitch((float) (0.55 + 0.1 * rpm / 10)); //dynamic pitch update based on rpm
				audio.updateVolume(100F); //yeah i need this
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	private void stopIfNotReady() {
		
		if(tanks[0].getFill() == 0 || tanks[1].getFill() == 0) {
			state = 0;
		}
		if(!hasAcceptableFuel()) {
			state = 0;
		}
	}
	
	public boolean hasAcceptableFuel() {
		
		if(tanks[0].getTankType().hasTrait(FT_Combustible.class)) {
			return tanks[0].getTankType().getTrait(FT_Combustible.class).getGrade() == FuelGrade.GAS;
		}
		
		return false;
	}
	
	private void startup() {
		
		counter++;
		
		if(counter <= 20) //rpm gauge 0-100-0
			rpm = 5 * counter;
		else if (counter > 20 && counter <= 40)
			rpm = 100 - 5 * (counter - 20);
		else if (counter > 50) {
			rpm = (int) (rpmIdle * (counter - 50) / 530); //slowly ramps up temp and RPM
			temp = (int) (tempIdle * (counter - 50) / 530);
		}
		
		if(counter == 50) {
			worldObj.playSoundEffect(xCoord, yCoord + 2, zCoord, "hbm:block.turbinegasStartup", 1F, 1.0F);
		}
			
		if(counter == 580) {
			state = 1;
		}
	}
	
	
	int rpmLast; //used to progressively slow down and cool the turbine without immediatly setting rpm and temp to 0
	int tempLast;
	
	private void shutdown() {
		
		autoMode = false;
		instantPowerOutput = 0;
		
		if(powerSliderPos > 0)
			powerSliderPos--;
		
		if(rpm <= 10 && counter > 0) {
			
			if(counter == 225) {
				
				worldObj.playSoundEffect(xCoord, yCoord + 2, zCoord, "hbm:block.turbinegasShutdown", 1F, 1.0F);
				
				rpmLast = rpm;
				tempLast = temp;
			}
			
			counter--;
			
			rpm = (int) (rpmLast * (counter) / 225);
			temp = (int) (tempLast * (counter) / 225);
			
		} else if(rpm > 11) { //quickly slows down the turbine to idle before shutdown
			counter = 42069; //absolutely necessary to avoid fuckeries on shutdown
			rpm--;
		} else if(rpm == 11) {
			counter = 225;
			rpm--;
		}
	}
	
	/** Dynamically calculates a (hopefully) sensible burn heat from the combustion energy, scales from 300°C - 800°C */
	protected int getFluidBurnTemp(FluidType type) {
		double dFuel = type.hasTrait(FT_Combustible.class) ? type.getTrait(FT_Combustible.class).getCombustionEnergy() : 0;
		return (int) Math.floor(800D - (Math.pow(Math.E, -dFuel / 100_000D)) * 300D);
	}
	
	private void run() {
		
		if((int) (throttle * 0.9) > rpm - rpmIdle) { //simulates the rotor's moment of inertia
			if(worldObj.getTotalWorldTime() % 5 == 0) {
				rpm++;
			}
		} else if((int) (throttle * 0.9) < rpm - rpmIdle) {
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				rpm--;
			}
		}
		
		int maxTemp = getFluidBurnTemp(tanks[0].getTankType()); // fuelMaxTemp.get(tanks[0].getTankType())
		
		if(throttle * 5 * (maxTemp - tempIdle) / 500 > temp - tempIdle) { //simulates the heat exchanger's resistance to temperature variation
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				temp++;
			}
		} else if(throttle * 5 * (maxTemp - tempIdle) / 500 < temp - tempIdle) {
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				temp--;
			}
		}
		
		double consumption = fuelMaxCons.containsKey(tanks[0].getTankType()) ? fuelMaxCons.get(tanks[0].getTankType()) : 5D;
		if(worldObj.getTotalWorldTime() % 20 == 0 && tanks[0].getTankType() != Fluids.OXYHYDROGEN) this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * (float) consumption * 0.25F);
		makePower(consumption, throttle);
	}
	
	
	double fuelToConsume; //used to consume 1 mb of fuel at a time when consumption is <1 mb/tick
	double waterToBoil;
	double waterPerTick = 0;
	
	private void makePower(double consMax, int throttle) {
		
		double idleConsumption = consMax * 0.05D;
		double consumption = idleConsumption + consMax * throttle / 100;
		
		fuelToConsume += consumption;
		
		tanks[0].setFill(tanks[0].getFill() - (int) Math.floor(fuelToConsume));
		fuelToConsume -= (int) Math.floor(fuelToConsume);
		
		if(worldObj.getTotalWorldTime() % 10 == 0) //lube consumption 
			tanks[1].setFill(tanks[1].getFill() - 1);
		
		if(tanks[0].getFill() < 0) { //avoids negative amounts of fluid
			tanks[0].setFill(0);
			state = 0;
		}
		if(tanks[1].getFill() < 0) {
			tanks[1].setFill(0);
			state = 0;
		}
		
		
		long energy = 0; //energy per mb of fuel
		
		if(tanks[0].getTankType().hasTrait(FT_Combustible.class)) {
			energy = tanks[0].getTankType().getTrait(FT_Combustible.class).getCombustionEnergy() / 1000L;
		}
		
		int rpmEff = rpm - rpmIdle; // RPM above idle level, 0-90
		
		//consMax*energy is equivalent to power production at 100%
		if(instantPowerOutput < (consMax * energy * rpmEff / 90)) { //this shit avoids power rising in steps of 2000 or so HE at a time, instead it does it smoothly
			instantPowerOutput += Math.random() * 0.005 * consMax * energy;
			if(instantPowerOutput > (consMax * energy * rpmEff / 90))
				instantPowerOutput = (int) (consMax * energy * rpmEff / 90);
		}
		else if(instantPowerOutput > (consMax * energy * rpmEff / 90)) {
			instantPowerOutput -= Math.random() * 0.011 * consMax * energy;
			if(instantPowerOutput < (consMax * energy * rpmEff / 90))
				instantPowerOutput = (int) (consMax * energy * rpmEff / 90);
		}
		this.power += instantPowerOutput;
		
		waterPerTick = (consMax * energy * (temp - tempIdle) / 220000); //it just works fuck you
		
		if(tanks[2].getFill() >= Math.ceil(waterPerTick)) { //checks if there's enough water to boil
			
			waterToBoil += waterPerTick;
			
			if(tanks[3].getFill() <= 160000 - waterToBoil * 10) { //checks if there's room for steam in the tank
				
				tanks[2].setFill(tanks[2].getFill() - (int) Math.floor(waterToBoil));
				tanks[3].setFill(tanks[3].getFill() + 10 * (int) Math.floor(waterToBoil));
				waterToBoil -= (int) Math.floor(waterToBoil);
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.power = nbt.getLong("power");
		this.rpm = nbt.getInteger("rpm");
		this.temp = nbt.getInteger("temp");
		this.state = nbt.getInteger("state");
		this.autoMode = nbt.getBoolean("automode");
		this.powerSliderPos = nbt.getInteger("slidpos");
		this.throttle = nbt.getInteger("throttle");			
		
		if(nbt.hasKey("counter"))
			this.counter = nbt.getInteger("counter"); //state 0 and -1
		else
			this.instantPowerOutput = nbt.getInteger("instantPow"); //state 1
		
		this.tanks[0].readFromNBT(nbt, "fuel");
		this.tanks[1].readFromNBT(nbt, "lube");
		this.tanks[2].readFromNBT(nbt, "water");
		this.tanks[3].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "gas");
		this.tanks[1].readFromNBT(nbt, "lube");
		this.tanks[2].readFromNBT(nbt, "water");
		this.tanks[3].readFromNBT(nbt, "densesteam");
		this.autoMode = nbt.getBoolean("automode");
		this.power = nbt.getLong("power");
		this.state = nbt.getInteger("state");
		this.rpm = nbt.getInteger("rpm");
		this.temp = nbt.getInteger("temperature");
		this.powerSliderPos = nbt.getInteger("slidPos");
		this.instantPowerOutput = nbt.getInteger("instPwr");
		this.counter = nbt.getInteger("counter");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		tanks[0].writeToNBT(nbt, "gas");
		tanks[1].writeToNBT(nbt, "lube");
		tanks[2].writeToNBT(nbt, "water");
		tanks[3].writeToNBT(nbt, "densesteam");
		nbt.setBoolean("automode", autoMode);
		nbt.setLong("power", power);
		if(state == 1) {
			nbt.setInteger("state", this.state);
			nbt.setInteger("rpm", this.rpm);
			nbt.setInteger("temperature", this.temp);
			nbt.setInteger("slidPos", this.powerSliderPos);
			nbt.setInteger("instPwr", instantPowerOutput);
			nbt.setInteger("counter", 225);
		} else {
			nbt.setInteger("state", 0);
			nbt.setInteger("rpm", 0);
			nbt.setInteger("temperature", 20);
			nbt.setInteger("slidPos", 0);
			nbt.setInteger("instpwr", 0);
			nbt.setInteger("counter", 0);
		}
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("slidPos"))
			powerSliderPos = data.getInteger("slidPos");
		
		if(data.hasKey("autoMode"))
			autoMode = data.getBoolean("autoMode");
		
		if(data.hasKey("state"))
			state = data.getInteger("state");

		this.markDirty();
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 25;
	}
	
	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return this.power;
	}
	
	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 6,
					yCoord + 3,
					zCoord + 6
					);
		}
		
		return bb;
	}
		
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public String getName() {
		return "container.turbinegas";
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0], tanks[1], tanks[2] };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[3], smoke, smoke_leaded, smoke_poison };
	}
	
	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineTurbineGas(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineTurbineGas(player.inventory, this);
	}
}