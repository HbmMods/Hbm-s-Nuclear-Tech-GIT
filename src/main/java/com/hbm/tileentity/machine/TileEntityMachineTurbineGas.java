package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.sound.AudioWrapper;
import com.hbm.sound.nt.ISoundSourceTE;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTurbineGas extends TileEntityMachineBase implements IFluidAcceptor, IFluidSource, IEnergyGenerator, IControlReceiver{
	
	public long power;
	public static final long maxPower = 1000000L;
	
	public int rpm; //0-100
	public int temp; //0-800
	public int rpmIdle = 10;
	public int tempIdle = 300;
	
	public int powerSliderPos; //goes from 0 to 60, 0 is idle, 60 is max power
	public int throttle; //the same thing, but goes from0 to 100
	
	public boolean autoMode;
	public int state = 0; //0 is offline, -1 is startup, 1 is online
	
	public int counter = 0; //used to startup and shutdown
	public int instantPowerOutput;
	
	public List<IFluidAcceptor> list = new ArrayList(); //TODO when ran on dummy blocks emit funny particles
	//TODO use normal steam
	
	public FluidTank[] tanks;
	
	private AudioWrapper audio;
	private ISoundSourceTE ssTE;
	
	public static HashMap<FluidType, Integer> fuelPwrProduction = new HashMap(); //max power production is 20000 * value, for example gas will produce 20000 * 4 = 80000 HE/s
	
	static {
		fuelPwrProduction.put(Fluids.GAS, 4); //80k
		fuelPwrProduction.put(Fluids.PETROLEUM, 8); //160k
		fuelPwrProduction.put(Fluids.LPG, 12); //240k
		fuelPwrProduction.put(Fluids.BIOGAS, 3); //60k
	}
	
	public static HashMap<FluidType, Double> fuelMaxCons = new HashMap(); //fuel consumption per tick at max power
	
	static {
		fuelMaxCons.put(Fluids.GAS, 1.5D);
		fuelMaxCons.put(Fluids.PETROLEUM, 16D); //TODO finish balancing
		fuelMaxCons.put(Fluids.LPG, 10D);
		fuelMaxCons.put(Fluids.BIOGAS, 4.5D);
	}
	
	public static HashMap<FluidType, Integer> fuelMaxTemp = new HashMap(); //the system will produce steam equivalent to 4200 * value per second, for example gas will produce 4200 * 10 = 42000 HE/s equivalent in dense steam
	
	static {
		fuelMaxTemp.put(Fluids.GAS, 600); //42k TODO balance!
		fuelMaxTemp.put(Fluids.PETROLEUM, 800); //84k
		fuelMaxTemp.put(Fluids.LPG, 400); //21k
		fuelMaxTemp.put(Fluids.BIOGAS, 500); //21k
	}
	
	public TileEntityMachineTurbineGas() {
		super(2);
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(Fluids.GAS, 100000, 0);
		tanks[1] = new FluidTank(Fluids.LUBRICANT, 16000, 1);		
		tanks[2] = new FluidTank(Fluids.WATER, 16000, 2);	
		tanks[3] = new FluidTank(Fluids.HOTSTEAM, 160000, 3);
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			throttle = powerSliderPos * 100 / 60;
			
			if(slots[1] != null && slots[1].getItem() instanceof ItemFluidIdentifier) {
				FluidType fuel = ItemFluidIdentifier.getType(slots[1]);
				if (fuelPwrProduction.get(ItemFluidIdentifier.getType(slots[1])) != null)
					tanks[0].setTankType(fuel);
			}
			
			switch(state) { //what to do when turbine offline, starting up and online			
			case 0:
				shutdown();	
				break;
			case -1:
				isReady();
				startup();
				break;
			case 1:			
				isReady();
				run();
				break;
			default:
				break;
			}
			
			if(autoMode) { //power production depending on power requirement
				
				int powerSliderTarget = 60 - (int) (60 * power / maxPower);
				
				if(powerSliderTarget > powerSliderPos) { //makes the auto slider slide instead of snapping into position
					powerSliderPos++;
				}
				else if(powerSliderTarget < powerSliderPos) {
					powerSliderPos--;
				}
			}			
			
			if(this.power > this.maxPower)
				this.power = this.maxPower;
			
			for(int i = 0; i < 4; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			
			if(audio != null)
				audio.updatePitch((float) (0.45 + 0.05 * rpm / 10));
			
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);

			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset); 
			
			this.sendPower(worldObj, xCoord - dir.offsetZ * 5, yCoord + 1, zCoord + dir.offsetX * 5, dir); //sends out power
			fillFluidInit(tanks[3].getTankType());
			
			NBTTagCompound data = new NBTTagCompound();
				data.setLong("power", this.power);
				data.setInteger("rpm",  this.rpm);
				data.setInteger("temp",  this.temp);
				data.setInteger("state", this.state);
				data.setBoolean("automode", this.autoMode);
				data.setInteger("slidpos",  this.powerSliderPos);
				if(state != 1)
					data.setInteger("counter", this.counter); //sent during startup and shutdown
				else
					data.setInteger("instantPow", this.instantPowerOutput); //sent while running
				
				data.setInteger("throttle",  this.throttle);
					
				this.networkPack(data, 150);
				
		} else { //client side, for sounds n shit
			
			if(rpm >= 10 && state != -1) { //if conditions are right, play thy sound
				
				if(audio == null) { //if there is no sound playing, start it
					
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", xCoord, yCoord, zCoord, 1.0F, 1.0F);
					audio.startSound();
					
				} else if(!audio.isPlaying()) {
					audio.stopSound();
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", xCoord, yCoord, zCoord, 1.0F, 1.0F);
					audio.startSound();
				}
				
				audio.updatePitch((float) (0.55 + 0.1 * rpm / 10)); //dynamic pitch update based on rpm
				audio.updateVolume(100F); //yeah i need this
				
			} else {
				
				if(audio != null) { //TODO stop sound when turbine broken
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	private void isReady() { //checks if the turbine can make power, if not shutdown TODO make this a bool maybe?
		
		if(tanks[0].getFill() == 0 || tanks[1].getFill() == 0) {
			state = 0;
		}
		if (!hasAcceptableFuel())
			state = 0;
	}
	
	public boolean hasAcceptableFuel() {
		
		if (fuelPwrProduction.get(tanks[0].getTankType()) != null)
			return true;
		return false;
	}
	
	private void startup() {
		
		counter++;
		
		if(counter <= 20) //rpm gauge 0-100-0
			rpm = 5 * counter;
		else if (counter > 20 && counter <= 40)
			rpm = 100 - 5 * (counter - 20);
		else if (counter > 50 ) {
			rpm = (int) (rpmIdle * (counter - 50) / 530); //slowly ramps up temp and RPM
			temp = (int) (tempIdle * (counter - 50) / 530);
		}
		
		if(counter == 50) {
			worldObj.playSoundEffect(xCoord, yCoord + 2, zCoord, "hbm:block.turbinegasStartup", 1F, 1.0F);
		}
			
		if(counter >= 580) {
			state = 1;
			counter = 225;
		}
	}
	
	
	int rpmLast; //used to progressively slow down and cool the turbine without immediatly setting rpm and temp to 0
	int tempLast; //TODO temp fucks up after shutdown
	
	private void shutdown() { //TODO weird shit happens on shutdown after re-entering a world with an active turbine
		
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
		}
		else if(rpm > 10) { //quickly slows down the turbine to idle before shutdown
			rpm--;
			return;
		}
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
		
		if(throttle * 5 * (fuelMaxTemp.get(tanks[0].getTankType()) - tempIdle) / 500 > temp - tempIdle) { //simulates the heat exchanger's resistance to temperature variation
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				temp++;
			}
		} else if(throttle * 5 * (fuelMaxTemp.get(tanks[0].getTankType()) - tempIdle) / 500 < temp - tempIdle) { //TODO dis not work
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				temp--;
			}
		}
		
		makePower(fuelMaxCons.get(tanks[0].getTankType()), fuelPwrProduction.get(tanks[0].getTankType()), throttle);
		makeSteam(); //TODO no steam???
	}
	
	double fuelToConsume; //used to consume 1 mb of fuel at a time when the average consumption is <1 mb/tick
	double idleConsumption = 0.1D;
	
	private void makePower(double consMax, int prodMax, int throttle) {
		
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
		
		
		int a = fuelPwrProduction.get(tanks[0].getTankType()); //power production depending on fuel
		
		if(instantPowerOutput < (10 * throttle * a)) { //this shit avoids power rising in steps of 2000 or so HE at a time, instead it does it smoothly
			instantPowerOutput += Math.random() * 4.7 * a;
			if(instantPowerOutput > (10 * throttle * a))
				instantPowerOutput = (10 * throttle * a);
		}
		else if(instantPowerOutput > (10 * throttle * a)) {
			instantPowerOutput -= Math.random() * 11 * a;
			if(instantPowerOutput < (10 * throttle * a))
				instantPowerOutput = (10 * throttle * a);
		}
		
		this.power += instantPowerOutput;
	}
	
	double waterToBoil;
	
	private void makeSteam() {
		
		double waterPerTick = 190.5D * (temp - tempIdle) * 0.002D; //238 dense steam per tick is 100kHE/s
		
		waterToBoil += waterPerTick;
		
		if(tanks[2].getFill() >= waterToBoil && tanks[3].getFill() <= 160000 - waterToBoil * 10) { //21 HE produced every 1mb of dense steam 
			
			tanks[2].setFill(tanks[2].getFill() - (int) Math.floor(waterToBoil));
			tanks[3].setFill(tanks[3].getFill() + 10 * (int) Math.floor(waterToBoil));
			waterToBoil -= (int) Math.floor(waterToBoil);
		}
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.power = nbt.getLong("power");
		this.rpm = nbt.getInteger("rpm");
		this.temp = nbt.getInteger("temp");
		this.state = nbt.getInteger("state");
		if(nbt.hasKey("slidpos"))
			this.powerSliderPos = nbt.getInteger("slidpos");
			
		this.autoMode = nbt.getBoolean("automode");
		if(nbt.hasKey("counter"))
			this.counter = nbt.getInteger("counter"); //state 0 and -1
		else
			this.instantPowerOutput = nbt.getInteger("instantPow"); //state 1
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
		
		if(data.hasKey("state") && (counter == 0 || counter == 225))
			state = data.getInteger("state");

		this.markDirty();
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(type == tanks[0].getTankType())
			tanks[0].setFill(fill);
		else if(type == Fluids.LUBRICANT)
			tanks[1].setFill(fill);
		else if(type == Fluids.WATER)
			tanks[2].setFill(fill);
		else if(type == Fluids.HOTSTEAM)
			tanks[3].setFill(fill);
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
	public void setTypeForSync(FluidType type, int index) {
		tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(int i = 0; i < 4; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getFill();
		
		return 0;
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

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getMaxFill();
		
		return 0;
	}
	
	AxisAlignedBB bb = null;
		
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
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
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset); 
		
		fillFluid(xCoord + dir.offsetZ * 4 + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 - dir.offsetX * 4, getTact(), type); //steam out 1
		fillFluid(xCoord + dir.offsetZ * 4 - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 - dir.offsetX * 4, getTact(), type); //steam out 2
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if(worldObj.getTotalWorldTime() % 5 == 0)
			return true;
		return false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
}
