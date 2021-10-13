package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWasteDrum extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	
	private String customName;
	
	public TileEntityWasteDrum() {
		slots = new ItemStack[12];
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
		return this.hasCustomInventoryName() ? this.customName : "container.wasteDrum";
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
		return 1;
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
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		Item item = itemStack.getItem();
		
		if(item == ModItems.waste_mox_hot || 
				item == ModItems.waste_plutonium_hot || 
				item == ModItems.waste_schrabidium_hot || 
				item == ModItems.waste_thorium_hot || 
				item == ModItems.waste_uranium_hot)
			return true;
		
		if(item instanceof ItemRBMKRod)
			return true;
		
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
        return slots_arr;
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {

		Item item = itemStack.getItem();
		
		if(item == ModItems.waste_mox || 
				item == ModItems.waste_plutonium || 
				item == ModItems.waste_schrabidium || 
				item == ModItems.waste_thorium || 
				item == ModItems.waste_uranium)
			return true;
		
		if(item instanceof ItemRBMKRod) {
			return ItemRBMKRod.getCoreHeat(itemStack) < 50 && ItemRBMKRod.getHullHeat(itemStack) < 50;
		}
		
		return false;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			int water = 0;

			if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) == Blocks.water || worldObj.getBlock(xCoord + 1, yCoord, zCoord) == Blocks.flowing_water)
				water++;
			if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) == Blocks.water || worldObj.getBlock(xCoord - 1, yCoord, zCoord) == Blocks.flowing_water)
				water++;
			if(worldObj.getBlock(xCoord, yCoord + 1, zCoord) == Blocks.water || worldObj.getBlock(xCoord, yCoord + 1, zCoord) == Blocks.flowing_water)
				water++;
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.water || worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.flowing_water)
				water++;
			if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) == Blocks.water || worldObj.getBlock(xCoord, yCoord, zCoord + 1) == Blocks.flowing_water)
				water++;
			if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) == Blocks.water || worldObj.getBlock(xCoord, yCoord, zCoord - 1) == Blocks.flowing_water)
				water++;
			
			if(water > 0) {
				
				int r = 60 * 60 * 20 / water;
				
				for(int i = 0; i < 12; i++) {
					
					if(slots[i] != null) {
						
						if(slots[i].getItem() instanceof ItemRBMKRod) {
							
							ItemRBMKRod rod = (ItemRBMKRod) slots[i].getItem();
							rod.updateHeat(worldObj, slots[i], 0.025D);
							rod.provideHeat(worldObj, slots[i], 20D, 0.025D);
							
						} else if(worldObj.rand.nextInt(r) == 0) {

							if(slots[i].getItem() == ModItems.waste_uranium_hot)
								slots[i] = new ItemStack(ModItems.waste_uranium);
							else if(slots[i].getItem() == ModItems.waste_plutonium_hot)
								slots[i] = new ItemStack(ModItems.waste_plutonium);
							else if(slots[i].getItem() == ModItems.waste_thorium_hot)
								slots[i] = new ItemStack(ModItems.waste_thorium);
							else if(slots[i].getItem() == ModItems.waste_mox_hot)
								slots[i] = new ItemStack(ModItems.waste_mox);
							else if(slots[i].getItem() == ModItems.waste_schrabidium_hot)
								slots[i] = new ItemStack(ModItems.waste_schrabidium);
						}
					}
				}
			}
		}
	}
}
