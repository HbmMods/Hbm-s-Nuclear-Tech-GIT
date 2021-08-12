package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.machine.MachineBattery;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineBattery extends TileEntityMachineBase implements IConsumer, ISource, IEnergyConnector {
	
	public long power = 0;
	public long maxPower = 1000000;
	
	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public short redLow = 0;
	public short redHigh = 2;
	
	public boolean conducts = false;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0, 1};
	private static final int[] slots_side = new int[] {1};
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	
	private String customName;
	
	public TileEntityMachineBattery() {
		super(2);
		slots = new ItemStack[2];
	}
	
	public TileEntityMachineBattery(long maxPower) {
		super(2);
		slots = new ItemStack[2];
		this.maxPower = maxPower;
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
		switch(i)
		{
		case 0:
			if(stack.getItem() instanceof IBatteryItem)
				return true;
			break;
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
		NBTTagList list = nbt.getTagList("items", 10);

		this.power = nbt.getLong("power");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setShort("redLow", redLow);
		nbt.setShort("redHigh", redHigh);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
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
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		if(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof MachineBattery && !worldObj.isRemote) {
		
			short mode = (short) this.getRelevantMode();
			
			//////////////////////////////////////////////////////////////////////
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				
				// first we make sure we're not subscribed to the network that we'll be supplying
				if(te instanceof IEnergyConductor) {
					IEnergyConductor con = (IEnergyConductor) te;
					
					if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
						con.getPowerNet().unsubscribe(this);
				}
				
				//then we add energy
				if(mode == 1 || mode == 2) {
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
					
					if(con.getPowerNet() != null && !con.getPowerNet().isSubscribed(this))
						con.getPowerNet().subscribe(this);
				}
			}
			//////////////////////////////////////////////////////////////////////
			
			this.maxPower = ((MachineBattery)worldObj.getBlock(xCoord, yCoord, zCoord)).maxPower;
			
			if(mode == 1 || mode == 2)
			{
				age++;
				if(age >= 20)
				{
					age = 0;
				}
				
				if(age == 9 || age == 19)
					ffgeuaInit();
			}
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			power = Library.chargeItemsFromTE(slots, 1, power, maxPower);
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("power", power);
			nbt.setLong("maxPower", maxPower);
			nbt.setShort("redLow", redLow);
			nbt.setShort("redHigh", redHigh);
			this.networkPack(nbt, 20);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) { 

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}
	
	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10)
		{
			return true;
		}
		
		return false;
	}
	
	public short getRelevantMode() {
		
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			return this.redHigh;
		} else {
			return this.redLow;
		}
	}

	@Override
	public long getMaxPower() {
		
		if(!worldObj.isRemote && getRelevantMode() >= 2)
			return this.getPower();
		
		return maxPower;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}
	
	/*
	 * SATAN - TECH
	 */
	@Override
	public long transferPower(long power) {
		
		this.power += power;
		
		if(this.power > this.getMaxPower()) {
			
			long overshoot = this.power - this.getMaxPower();
			this.power = this.getMaxPower();
			return overshoot;
		}
		
		return 0;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return true;
	}

}
