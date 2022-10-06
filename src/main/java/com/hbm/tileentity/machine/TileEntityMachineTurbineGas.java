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
	
	public int powerSliderPos = 0; //goes from 0 to 60, 0 is idle, 60 is max power
	int throttle;
	
	public boolean autoMode;
	public int state = 0; //0 is offline, -1 is startup, 1 is online
	
	public int counter = 0; //used to startup and shutdown
	public float instantPowerOutput;
	
	public List<IFluidAcceptor> list = new ArrayList();
	
	public FluidTank[] tanks;
	
	private AudioWrapper audio;
	private ISoundSourceTE ssTE;
	
	public static HashMap<FluidType, Integer> fuelPwrProduction = new HashMap(); //max power production is 20000 * value, for example gas will produce 20000 * 4 = 80000 HE/s
	
	static {
		fuelPwrProduction.put(Fluids.GAS, 4); 
		fuelPwrProduction.put(Fluids.PETROLEUM, 1);
		fuelPwrProduction.put(Fluids.LPG, 1);
		fuelPwrProduction.put(Fluids.BIOGAS, 1);
	}
	
	public static HashMap<FluidType, Integer> fuelStmProduction = new HashMap(); //the system will produce steam equivalent to 4200 * value per tick, for example gas will produce 4200 * 10 = 42000 HE/s equivalent in dense steam
	
	static {
		fuelStmProduction.put(Fluids.GAS, 10);
		fuelStmProduction.put(Fluids.PETROLEUM, 1);
		fuelStmProduction.put(Fluids.LPG, 1);
		fuelStmProduction.put(Fluids.BIOGAS, 1);
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
				if (fuel == Fluids.GAS || fuel == Fluids.PETROLEUM || fuel == Fluids.LPG || fuel == Fluids.BIOGAS)
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
				data.setLong("power", power);
				data.setInteger("rpm",  rpm);
				data.setInteger("temp",  temp);
				data.setInteger("state", state);
				data.setBoolean("automode", autoMode);
				data.setInteger("slidpos",  powerSliderPos);
				if(state != 1)
					data.setInteger("counter", counter); //sent during startup and shutdown
				else
					data.setFloat("instantPow", instantPowerOutput); //sent while running
					
				this.networkPack(data, 150);
				
		} else { //client side, for sounds n shit
			
			float volume = 10.0F; //TODO this is too low
			
			if(rpm >= 10 && state != -1 && volume > 0) { //if conditions are right, play thy sound
				
				if(audio == null) { //if there is no sound playing, start it
					
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", xCoord, yCoord, zCoord, volume, 1.0F);
					audio.startSound();
					
				} else if(!audio.isPlaying()) {
					audio.stopSound();
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", xCoord, yCoord, zCoord, volume, 1.0F);
					audio.startSound();
				}
				
				audio.updatePitch((float) (0.55 + 0.1 * rpm / 10)); //dynamic pitch update based on rpm
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	private void isReady() { //checks if the turbine can make power, if not shutdown
		
		if(tanks[0].getFill() == 0 || tanks[1].getFill() == 0) {
			state = 0;
		}
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
			worldObj.playSoundEffect(xCoord, yCoord + 2, zCoord, "hbm:block.turbinegasStartup", 10.0F, 1.0F);
		}
			
		if(counter >= 580) {
			state = 1;
			counter = 225;
		}
	}
	
	private void run() { //TODO maybe make a sound if gas/lube levels are too low
		
		if(tanks[0].getFill() <= 0) { //shuts down if there is no gas or lube
			state = 0;
			tanks[0].setFill(0);
		}
		if(tanks[1].getFill() <= 0) {
			state = 0;
			tanks[1].setFill(0);
		}
		
		if((int) (throttle * 0.9) > rpm - rpmIdle) { //simulates the rotor's moment of inertia
			if(worldObj.getTotalWorldTime() % 5 == 0) {
				rpm++;
			}
		} else if((int) (throttle * 0.9) < rpm - rpmIdle) {
			if(worldObj.getTotalWorldTime() % 5 == 0) {
				rpm--;
			}
		}
		
		if(throttle * 5 > temp - tempIdle) { //simulates the heat exchanger's resistance to temperature variation
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				temp++;
			}
		} else if(throttle * 5 < temp - tempIdle) {
			if(worldObj.getTotalWorldTime() % 2 == 0) {
				temp--;
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// POWER PRODUCTION
		
		double consMax = 20D; //fuel consumption per tick in mb while running at 100% power
		
		tanks[0].setFill(tanks[0].getFill() - (int) Math.ceil(consMax * throttle / 100)); //fuel consumption based on throttle position
		
		if(throttle == 0 && worldObj.getTotalWorldTime() % 5 == 0) //idle gas consumption
			tanks[0].setFill(tanks[0].getFill() - 1);
		
		if(worldObj.getTotalWorldTime() % 10 == 0) //lube consumption is constant, hydrostatic bearings go brrrr
			tanks[1].setFill(tanks[1].getFill() - 1);
		
		
		int a = fuelPwrProduction.get(tanks[0].getTankType()); //power production depending on fuel
		
		if(instantPowerOutput < (10 * throttle * a)) { //this shit avoids power rising in steps of 2000 or so HE at a time, instead it does it smoothly
			instantPowerOutput += Math.random() * 4.5 * a;
			if(instantPowerOutput > (10 * throttle * a))
				instantPowerOutput = (10 * throttle * a);
		}
		else if(instantPowerOutput > (10 * throttle * a)) {
			instantPowerOutput -= Math.random() * 4.5 * a;
			if(instantPowerOutput < (10 * throttle * a))
				instantPowerOutput = (10 * throttle * a);
		}
		
		this.power += instantPowerOutput;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// STEAM PRODUCTION
		
		int waterPerTick = fuelStmProduction.get(tanks[0].getTankType()) * (temp - 300) / 500;
		
		if(tanks[2].getFill() >= waterPerTick && tanks[3].getFill() <= 160000 - waterPerTick * 10) { //21 HE produced every 1mb of dense steam
			
				tanks[2].setFill(tanks[2].getFill() - waterPerTick);
				tanks[3].setFill(tanks[3].getFill() + waterPerTick * 10);
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
				
				worldObj.playSoundEffect(xCoord, yCoord + 2, zCoord, "hbm:block.turbinegasShutdown", 10.0F, 1.0F);
				
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.power = nbt.getLong("power");
		this.rpm = nbt.getInteger("rpm");
		this.temp = nbt.getInteger("temp");
		this.state = nbt.getInteger("state");
		this.powerSliderPos = nbt.getInteger("slidpos");
		this.autoMode = nbt.getBoolean("automode");
		if(nbt.hasKey("counter"))
			this.counter = nbt.getInteger("counter"); //state 0 and -1
		else
			this.instantPowerOutput = nbt.getFloat("instantPow"); //state 1
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "gas");
		tanks[1].readFromNBT(nbt, "lube");
		tanks[2].readFromNBT(nbt, "water");
		tanks[3].readFromNBT(nbt, "densesteam");
		autoMode = nbt.getBoolean("automode");
		power = nbt.getLong("power");
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
		//TODO temp
		//TODO throttle
		//TODO counter
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("slidPos")) 
			this.powerSliderPos = data.getInteger("slidPos");
		
		if(data.hasKey("autoMode"))
			this.autoMode = data.getBoolean("autoMode");
		
		if(data.hasKey("state") && (counter == 0 || counter == 225))
			this.state = data.getInteger("state");

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
