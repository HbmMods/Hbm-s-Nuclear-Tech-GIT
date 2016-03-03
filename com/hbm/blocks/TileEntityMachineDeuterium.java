package com.hbm.blocks;

import com.hbm.interfaces.IConductor;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineDeuterium extends TileEntity implements ISidedInventory, IConductor {

	private ItemStack slots[];
	
	public int power = 0;
	public int water = 0;
	public int sulfur = 0;
	public int process = 0;
	public static final int maxFill = 1000;
	public static final int maxPower = 10000;
	public static final int processSpeed = 200;

	private static final int[] slots_top = new int[] {3};
	private static final int[] slots_bottom = new int[] {4};
	private static final int[] slots_side = new int[] {0, 1, 2};
	
	private String customName;
	
	public TileEntityMachineDeuterium() {
		slots = new ItemStack[5];
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machine_deuterium";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		power = nbt.getShort("power");
		water = nbt.getShort("water");
		sulfur = nbt.getShort("sulfur");
		process = nbt.getShort("process");
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
		nbt.setShort("power", (short) power);
		nbt.setShort("water", (short) water);
		nbt.setShort("sulfur", (short) sulfur);
		nbt.setShort("process", (short) process);
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
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getWaterScaled(int i) {
		return (water * i) / maxFill;
	}
	
	public int getSulfurScaled(int i) {
		return (sulfur * i) / maxFill;
	}
	
	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}
	
	public boolean canProcess() {
		if(water != 0 && power != 0 && sulfur != 0 && slots[3] != null && slots[3].getItem() == ModItems.cell_empty && (slots[4] == null || (slots[4] != null && slots[4].stackSize < 64)))
		{
			return true;
		}
		return false;
	}
	
	public boolean isProcessing() {
		return process > 0;
	}
	
	public void process() {
		water -= 2;
		sulfur -= 1;
		power -= 5;
		
		process++;
		
		if(process >= processSpeed) {
			
			slots[3].stackSize--;
			
			if(slots[3].stackSize == 0)
			{
				slots[3] = null;
			}
			
			if(slots[4] == null)
			{
				slots[4] = new ItemStack(ModItems.cell_deuterium);
			} else {
				
				slots[4].stackSize++;
			}
			
			process = 0;
		}
	}
	
	@Override
	public void updateEntity() {
		if(slots[2] != null && slots[2].getItem() == ModItems.sulfur && sulfur + 125 <= maxFill)
		{
			sulfur += 125;
			slots[2].stackSize--;
			if(slots[2].stackSize == 0)
			{
				slots[2] = null;
			}
		}
		
		if(slots[1] != null && slots[1].getItem() == Items.water_bucket && water + 250 <= maxFill)
		{
			water += 250;
			slots[1].stackSize--;
			if(slots[1].stackSize == 0)
			{
				this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}
		
		if(slots[1] != null && slots[1].getItem() == ModItems.rod_water && water + 250 <= maxFill)
		{
			water += 250;
			slots[1].stackSize--;
			if(slots[1].stackSize == 0)
			{
				this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}
		
		if(slots[1] != null && slots[1].getItem() == ModItems.rod_dual_water && water + 500 <= maxFill)
		{
			water += 500;
			slots[1].stackSize--;
			if(slots[1].stackSize == 0)
			{
				this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}
		
		if(slots[1] != null && slots[1].getItem() == ModItems.rod_quad_water && water + 1000 <= maxFill)
		{
			water += 1000;
			slots[1].stackSize--;
			if(slots[1].stackSize == 0)
			{
				this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
			}
		}
		
		if(/*power + 100 <= maxPower && */slots[0] != null && slots[0].getItem() == ModItems.battery_creative)
		{
			power = maxPower;
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_generic && slots[0].getItemDamage() < 50)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_advanced && slots[0].getItemDamage() < 200)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.battery_schrabidium && slots[0].getItemDamage() < 1000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.fusion_core && slots[0].getItemDamage() < 5000)
		{
			power += 100;
			slots[0].setItemDamage(slots[0].getItemDamage() + 1);
		}
		
		if(canProcess())
		{
			process();
		} else {
			process = 0;
		}
	}
}
