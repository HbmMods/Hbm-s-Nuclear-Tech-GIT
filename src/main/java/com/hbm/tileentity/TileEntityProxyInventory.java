package com.hbm.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

@Deprecated
public class TileEntityProxyInventory extends TileEntityProxyBase implements ISidedInventory {

	@Override
	public int getSizeInventory() {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.getSizeInventory();
		
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.getStackInSlot(slot);
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.decrStackSize(slot, count);
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.getStackInSlotOnClosing(slot);
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			inv.setInventorySlotContents(slot, stack);
	}

	@Override
	public String getInventoryName() {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.getInventoryName();
		
		return "";
	}

	@Override
	public boolean hasCustomInventoryName() {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.hasCustomInventoryName();
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.getInventoryStackLimit();
		
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false; //never usable, it's just a proxy
	}

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.isItemValidForSlot(slot, stack);
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.getAccessibleSlotsFromSide(side);
		
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.canInsertItem(slot, stack, side);
		
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		
		ISidedInventory inv = this.getBase();
		
		if(inv != null)
			return inv.canExtractItem(slot, stack, side);
		
		return false;
	}
	
	private ISidedInventory getBase() {
		
		TileEntity te = this.getTE();
		
		if(te instanceof ISidedInventory)
			return (ISidedInventory)te;
		
		return null;
	}

}
