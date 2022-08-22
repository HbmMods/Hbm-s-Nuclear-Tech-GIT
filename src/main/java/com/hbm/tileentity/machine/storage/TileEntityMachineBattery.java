package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.machine.MachineBattery;
import com.hbm.lib.Library;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyUser;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityMachineBattery extends TileEntityMachineBase implements IEnergyUser, IPersistentNBT, SimpleComponent {
	
	public long[] log = new long[20];
	public long delta = 0;
	public long power = 0;
	
	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public static final int mode_input = 0;
	public static final int mode_buffer = 1;
	public static final int mode_output = 2;
	public static final int mode_none = 3;
	public short redLow = 0;
	public short redHigh = 2;
	public ConnectionPriority priority = ConnectionPriority.LOW;
	
	//public boolean conducts = false;
	public byte lastRedstone = 0;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0, 1};
	private static final int[] slots_side = new int[] {1};
	
	private String customName;
	
	public TileEntityMachineBattery() {
		super(2);
		slots = new ItemStack[2];
	}

	@Override
	public String getName() {
		return "container.battery";
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : getName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		switch(i) {
		case 0:
		case 1:
			if(stack.getItem() instanceof IBatteryItem)
				return true;
			break;
		}
		
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.lastRedstone = nbt.getByte("lastRedstone");
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setShort("redLow", redLow);
		nbt.setShort("redHigh", redHigh);
		nbt.setByte("lastRedstone", lastRedstone);
		nbt.setByte("priority", (byte)this.priority.ordinal());
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		
		if(itemStack.getItem() instanceof IBatteryItem) {
			if(i == 0 && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == 0) {
				return true;
			}
			if(i == 1 && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge()) {
				return true;
			}
		}
			
		return false;
	}

	public long getPowerRemainingScaled(long i) {
		return (power * i) / this.getMaxPower();
	}
	
	public byte getComparatorPower() {
		if(power == 0) return 0;
		double frac = (double) this.power / (double) this.getMaxPower() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15)); //to combat eventual rounding errors with the FEnSU's stupid maxPower
	}
	
	@Override
	public void updateEntity() {
		
		if(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof MachineBattery && !worldObj.isRemote) {
			
			long prevPower = this.power;
			
			//////////////////////////////////////////////////////////////////////
			this.transmitPower();
			//////////////////////////////////////////////////////////////////////
			
			byte comp = this.getComparatorPower();
			if(comp != this.lastRedstone)
				this.markDirty();
			this.lastRedstone = comp;
			
			power = Library.chargeTEFromItems(slots, 0, power, getMaxPower());
			power = Library.chargeItemsFromTE(slots, 1, power, getMaxPower());

			this.delta = this.power - this.log[0];
			
			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}
			
			this.log[19] = this.power;
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("power", (power + prevPower) / 2);
			nbt.setLong("delta", delta);
			nbt.setShort("redLow", redLow);
			nbt.setShort("redHigh", redHigh);
			nbt.setByte("priority", (byte) this.priority.ordinal());
			this.networkPack(nbt, 20);
		}
	}
	
	protected void transmitPower() {
		
		short mode = (short) this.getRelevantMode();
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			// first we make sure we're not subscribed to the network that we'll be supplying
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				
				if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
					con.getPowerNet().unsubscribe(this);
			}
			
			//then we add energy
			if(mode == mode_buffer || mode == mode_output) {
				if(te instanceof IEnergyConnector) {
					IEnergyConnector con = (IEnergyConnector) te;
					long oldPower = this.power;
					long transfer = this.power - con.transferPower(this.power);
					this.power = oldPower - transfer;
				}
			}
			
			//then we subscribe if possible
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				
				if(con.getPowerNet() != null) {
					if(mode == mode_output || mode == mode_none) {
						if(con.getPowerNet().isSubscribed(this)) {
							con.getPowerNet().unsubscribe(this);
						}
					} else if(!con.getPowerNet().isSubscribed(this)) {
						con.getPowerNet().subscribe(this);
					}
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) { 

		this.power = nbt.getLong("power");
		this.delta = nbt.getLong("delta");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];
	}

	@Override
	public long getPower() {
		return power;
	}
	
	public short getRelevantMode() {
		
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			return this.redHigh;
		} else {
			return this.redLow;
		}
	}
	
	private long bufferedMax;

	@Override
	public long getMaxPower() {
		
		if(bufferedMax == 0) {
			bufferedMax = ((MachineBattery)worldObj.getBlock(xCoord, yCoord, zCoord)).maxPower;
		}
		
		return bufferedMax;
	}
	
	/*
	 * SATAN - TECH
	 */
	@Override
	public long transferPower(long power) {

		int mode = this.getRelevantMode();
		
		if(mode == mode_output || mode == mode_none) {
			return power;
		}
		
		this.power += power;
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		
		if(this.power > this.getMaxPower()) {
			
			long overshoot = this.power - this.getMaxPower();
			this.power = this.getMaxPower();
			return overshoot;
		}
		
		return 0;
	}
	
	@Override
	public long getTransferWeight() {

		int mode = this.getRelevantMode();
		
		if(mode == mode_output || mode == mode_none) {
			return 0;
		}
		
		return Math.max(getMaxPower() - getPower(), 0);
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return true;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public ConnectionPriority getPriority() {
		return this.priority;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "ntm_energy_storage"; // need a way to somehow detect the first word of the energy storage block so people wont get confused when it comes to multiple energy storage blocks
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyStored(Context context, Arguments args) {
		return new Object[] {getPower()};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxEnergy(Context context, Arguments args) {
		return new Object[] {getMaxPower()};
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setShort("redLow", redLow);
		data.setShort("redHigh", redHigh);
		data.setInteger("priority", this.priority.ordinal());
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.power = data.getLong("power");
		this.redLow = data.getShort("redLow");
		this.redHigh = data.getShort("redHigh");
		this.priority = ConnectionPriority.values()[data.getInteger("priority")];
	}
}
