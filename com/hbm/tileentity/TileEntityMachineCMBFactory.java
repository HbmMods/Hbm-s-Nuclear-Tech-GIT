package com.hbm.tileentity;

import com.hbm.interfaces.IConsumer;
import com.hbm.items.ItemBattery;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineCMBFactory extends TileEntity implements ISidedInventory, IConsumer {

	private ItemStack slots[];
	
	public int power = 0;
	public int waste = 0;
	public int process = 0;
	public static final int maxFill = 1000;
	public static final int maxPower = 10000;
	public static final int processSpeed = 200;

	private static final int[] slots_top = new int[] {3};
	private static final int[] slots_bottom = new int[] {4, 0, 1};
	private static final int[] slots_side = new int[] {0, 1, 2};
	
	private String customName;
	
	public TileEntityMachineCMBFactory() {
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
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		switch(i)
		{
		case 0:
			if(stack.getItem() instanceof ItemBattery)
				return true;
			break;
		case 1:
			if(stack.getItem() == ModItems.rod_water || stack.getItem() == ModItems.rod_dual_water || stack.getItem() == ModItems.rod_quad_water || stack.getItem() == Items.water_bucket)
				return true;
			break;
		case 2:
			if(stack.getItem() == ModItems.sulfur)
				return true;
			break;
		case 3:
			if(stack.getItem() == ModItems.cell_empty)
				return true;
			break;
		}
		
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
		waste = nbt.getShort("waste");
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
		nbt.setShort("waste", (short) waste);
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
		if(i == 4)
			return true;
		if(i == 0 && itemStack.getItem() instanceof ItemBattery)
			if(itemStack.getItemDamage() == itemStack.getMaxDamage())
				return true;
		if(i == 1)
			if(itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
				return true;
		
		return false;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getWasteScaled(int i) {
		return (waste * i) / maxFill;
	}
	
	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}
	
	public boolean canProcess() {
		return false;
	}
	
	public boolean isProcessing() {
		return process > 0;
	}
	
	public void process() {
		waste -= 1;
		power -= 25;
		
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

		if (!worldObj.isRemote) {

			if (power + 100 <= maxPower && slots[0] != null && slots[0].getItem() == ModItems.energy_core
					&& slots[0].getItemDamage() < 5000) {
				power += 100;
				slots[0].setItemDamage(slots[0].getItemDamage() + 1);
			}

			if (canProcess()) {
				process();
			} else {
				process = 0;
			}
		}
	}

	@Override
	public void setPower(int i) {
		power = i;
		
	}

	@Override
	public int getPower() {
		return power;
		
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}
}
