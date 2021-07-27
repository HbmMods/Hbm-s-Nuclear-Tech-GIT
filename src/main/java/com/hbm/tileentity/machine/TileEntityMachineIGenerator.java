package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineIGenerator extends TileEntityMachineBase implements ISource, IFluidAcceptor {
	
	public long power;
	public static final long maxPower = 1000000;
	public int lastBurnTime;
	public int burnTime;
	public int temperature;
	public static final int maxTemperature = 1000;
	public int torque;
	public static final int maxTorque = 10000;
	public float limiter = 0.0F; /// 0 - 1 ///
	
	public static final int animSpeed = 50;

	@SideOnly(Side.CLIENT)
	public float rotation;
	@SideOnly(Side.CLIENT)
	public float prevRotation;
	
	public IGenRTG[] pellets = new IGenRTG[12];
	public FluidTank[] tanks;
	
	public int age = 0;
	public List<IConsumer> list = new ArrayList();

	public TileEntityMachineIGenerator() {
		super(15);
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidType.WATER, 8000, 0);
		tanks[1] = new FluidTank(FluidType.HEATINGOIL, 16000, 1);
		tanks[2] = new FluidTank(FluidType.LUBRICANT, 2000, 2);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				ffgeuaInit();

			tanks[0].loadTank(7, 8, slots);
			tanks[1].loadTank(9, 10, slots);
			tanks[1].setType(11, 12, slots);
			tanks[2].loadTank(13, 14, slots);
			
			loadFuel();
			pelletAction();
			
			if(burnTime > 0) {
				burnTime --;
				temperature += 100;
			}
			
			fuelAction();
			
			if(temperature > maxTemperature)
				temperature = maxTemperature;
			
			int displayHeat = temperature;
			
			rtgAction();
			
			rotorAction();
			generatorAction();
			
			this.power = Library.chargeItemsFromTE(slots, 6, power, maxPower);
			
			NBTTagCompound data = new NBTTagCompound();
			int[] rtgs = new int[pellets.length];
			
			for(int i = 0; i < pellets.length; i++) {
				if(pellets[i] != null)
					rtgs[i] = pellets[i].ordinal();
				else
					rtgs[i] = -1;
			}
			
			data.setIntArray("rtgs", rtgs);
			data.setInteger("temp", displayHeat);
			data.setInteger("torque", torque);
			data.setInteger("power", (int)power);
			data.setShort("burn", (short) burnTime);
			data.setShort("lastBurn", (short) lastBurnTime);
			data.setFloat("dial", limiter);
			this.networkPack(data, 250);
			
			for(int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
		} else {
			
			this.prevRotation = this.rotation;
			
			this.rotation += this.torque * animSpeed / maxTorque;
			
			if(this.rotation >= 360) {
				this.rotation -= 360;
				this.prevRotation -= 360;
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {

		int[] rtgs = nbt.getIntArray("rtgs");
		
		if(rtgs != null) {
			for(int i = 0; i < pellets.length; i++) {
				
				int pellet = rtgs[i];
				if(pellet >= 0 && pellet < IGenRTG.values().length) {
					pellets[i] = IGenRTG.values()[pellet];
				} else {
					pellets[i] = null;
				}
			}
		}

		this.temperature = nbt.getInteger("temp");
		this.torque = nbt.getInteger("torque");
		this.power = nbt.getInteger("power");
		this.burnTime = nbt.getShort("burn");
		this.lastBurnTime = nbt.getShort("lastBurn");
		
		if(ignoreNext <= 0) {
			this.limiter = nbt.getFloat("dial");
		} else {
			ignoreNext--;
		}
	}

	@Override
	public void handleButtonPacket(int value, int meta) {

		if(meta == 0)
			pushPellet();
		if(meta == 1)
			popPellet();
		if(meta == 2)
			setDialByAngle(value);
	}
	
	/**
	 * Checks for solid fuel and burns it
	 */
	private void loadFuel() {
		
		if(this.burnTime <= 0 && slots[0] != null) {
			
			int time = TileEntityFurnace.getItemBurnTime(slots[0]) / 2;
			
			if(time > 0) {
				
				if(slots[0].getItem().hasContainerItem(slots[0]) && slots[0].stackSize == 1) {
					slots[0] = slots[0].getItem().getContainerItem(slots[0]);
				} else {
					this.decrStackSize(0, 1);
				}
				
				this.burnTime = time;
				this.lastBurnTime = time;
				
				this.markDirty();
			}
		}
	}
	
	/**
	 * Creates heat from RTG pellets
	 */
	private void pelletAction() {
		
		for(int i = 0; i < pellets.length; i++) {
			if(pellets[i] != null)
				this.temperature += pellets[i].heat;
		}
	}
	
	/**
	 * Burns liquid fuel
	 */
	private void fuelAction() {
		
		int heat = getHeatFromFuel(tanks[1].getTankType());
		
		int maxBurn = 2;
		
		if(tanks[1].getFill() > 0) {
			
			int burn = Math.min(maxBurn, tanks[1].getFill());
			
			tanks[1].setFill(tanks[1].getFill() - burn);
			temperature += heat * burn;
		}
	}
	
	public int getHeatFromFuel(FluidType type) {
		
		switch(type) {
		case SMEAR: 		return 75;
		case HEATINGOIL: 	return 150;
		case DIESEL: 		return 225;
		case KEROSENE: 		return 300;
		case RECLAIMED: 	return 100;
		case PETROIL: 		return 125;
		case BIOFUEL: 		return 200;
		case NITAN: 		return 2500;
		default: 			return 0;
		}
	}
	
	/**
	 * does the thing with the thermo elements
	 */
	private void rtgAction() {
		
		int rtg = 0;
		
		for(int i = 3; i <= 5; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.thermo_element)
				rtg += 15;
		}
		
		int pow = Math.min(this.temperature, rtg);
		
		this.temperature -= pow;
		this.power += pow;
		
		if(power > maxPower)
			power = maxPower;
	}

	/**
	 * Turns heat into rotational energy
	 */
	private void rotorAction() {
		
		int conversion = getConversion();
		
		if(temperature > 10 && tanks[0].getFill() > 0)
			tanks[0].setFill(tanks[0].getFill() - 1);
		
		if(torque > 10 && tanks[2].getFill() > 0 && worldObj.rand.nextInt(2) == 0)
			tanks[2].setFill(tanks[2].getFill() - 1);
		
		this.torque += conversion * (tanks[0].getFill() > 0 ? 1.5 : 1);
		this.temperature -= conversion;
		
		if(torque > maxTorque)
			worldObj.createExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true);
	}
	
	/**
	 * Do the power stuff
	 */
	private void generatorAction() {
		
		double balanceFactor = 0.025D;
		
		this.power += this.torque * balanceFactor;
		torque -= getBrake();
		
		if(power > maxPower)
			power = maxPower;
	}
	
	public int getBrake() {
		return (int) Math.ceil(torque * 0.1 * (tanks[2].getFill() > 0 ? 0.5 : 1));
	}
	
	public int getConversion() {
		return (int) (temperature * limiter);
	}
	
	/**
	 * Adds a pellet onto the pile
	 */
	private void pushPellet() {
		
		if(pellets[11] != null)
			return;
		
		if(slots[1] != null) {
			
			IGenRTG pellet = IGenRTG.getPellet(slots[1].getItem());
			
			if(pellet != null) {
				
				for(int i = 0; i < pellets.length; i++) {
					
					if(pellets[i] == null) {
						pellets[i] = pellet;
						this.decrStackSize(1, 1);
						
						this.markDirty();
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Removes a pellet from the bottom of the pile
	 */
	private void popPellet() {
		
		if(slots[2] != null)
			return;
		
		if(pellets[0] == null)
			return;
		
		//i don't feel like adding null checks because they won't trigger anyway
		slots[2] = new ItemStack(this.rtgPellets.inverse().get(pellets[0]));
		
		for(int i = 0; i < pellets.length - 1; i++) {
			pellets[i] = pellets[i + 1];
		}
		
		pellets[pellets.length - 1] = null;
		
		this.markDirty();
	}
	
	public double getSolidGauge() {
		return (double) burnTime / (double) lastBurnTime;
	}
	
	public double getPowerGauge() {
		return (double) power / (double) maxPower;
	}
	
	public double getTempGauge() {
		return (double) temperature / (double) maxTemperature;
	}
	
	public double getTorqueGauge() {
		return (double) torque / (double) maxTorque;
	}
	
	public float getAngleFromDial() {
		return (45F + limiter * 270F) % 360F;
	}
	
	int ignoreNext = 0;
	public void setDialByAngle(float angle) {
		this.limiter = (angle - 45F) / 270F;
		ignoreNext = 5;
	}

	@Override
	public void ffgeuaInit() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		
		int[] rot = MultiblockHandlerXR.rotate(new int [] {1,0,2,2,8,8}, dir);
		
		boolean tact = this.getTact();
		
		for(int iy = 0; iy <= 1; iy++) {
			for(int ix = -rot[4]; ix <= rot[5]; ix++) {
				for(int iz = -rot[2]; iz <= rot[3]; iz++) {
					
					if(ix == -rot[4] || ix == rot[5] || iz == -rot[2] || iz == rot[3]) {
						
						ffgeua(xCoord + dir.offsetX * 2 + ix, yCoord + iy, zCoord + dir.offsetZ * 2 + iz, tact);
					}
				}
			}
		}
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public long getSPower() {
		return this.power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return this.list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public void setFillstate(int fill, int index) {
		tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(type == FluidType.WATER)
			tanks[0].setFill(fill);
		else if(type == FluidType.LUBRICANT)
			tanks[2].setFill(fill);
		else if(tanks[1].getTankType() == type)
			tanks[1].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return Arrays.asList(tanks);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getMaxFill();
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < pellets.length; i++) {
			
			short s = nbt.getShort("pellet" + i);
			
			if(s >= 0 && s < IGenRTG.values().length) {
				pellets[i] = IGenRTG.values()[s];
			} else {
				pellets[i] = null;
			}
		}

		this.burnTime = nbt.getInteger("burn");
		this.lastBurnTime = nbt.getInteger("lastBurn");
		this.limiter = nbt.getFloat("limiter");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < pellets.length; i++) {
			
			if(pellets[i] != null) {
				nbt.setShort("pellet" + i, (short) pellets[i].ordinal());
			} else {
				nbt.setShort("pellet" + i, (short)-1);
			}
		}

		nbt.setInteger("burn", burnTime);
		nbt.setInteger("lastBurn", lastBurnTime);
		nbt.setFloat("limiter", limiter);
	}
	
	private static HashBiMap<Item, IGenRTG> rtgPellets = HashBiMap.create();
	
	public static enum IGenRTG {
		PLUTONIUM(ModItems.pellet_rtg, 0, 5),
		URANIUM(ModItems.pellet_rtg_weak, 9, 3),
		POLONIUM(ModItems.pellet_rtg_polonium, 18, 25);
		
		public int offset;
		public int heat;
		
		private IGenRTG(Item item, int offset, int heat) {
			rtgPellets.put(item, this);
			this.offset = offset;
			this.heat = heat;
		}
		
		public static IGenRTG getPellet(Item item) {
			return rtgPellets.get(item);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
