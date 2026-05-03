package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageAccess;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerPneumoStorageAccess extends Container {
	
	protected TileEntityPneumoStorageAccess access;
	protected InventoryPneumoStorageAccess inventory;

	public ContainerPneumoStorageAccess(InventoryPlayer invPlayer, TileEntityPneumoStorageAccess access) {
		this.access = access;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new SlotNonRetarded(invPlayer, j + i * 9 + 9, 8 + j * 18, 169 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotNonRetarded(invPlayer, i, 8 + i * 18, 227));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return access.getDistanceFrom(player.posX, player.posY, player.posZ) <= 15 * 15;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
	
	public static class InventoryPneumoStorageAccess implements IInventory {
		
		public ItemStack[] slots;
		
		public InventoryPneumoStorageAccess() {
			slots = new ItemStack[getSizeInventory()];
		}

		@Override public int getSizeInventory() { return 6 * 9; }
		@Override public ItemStack getStackInSlot(int slot) { return slots[slot]; }

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			if(slots[slot] != null) {
				ItemStack stack = slots[slot];
				slots[slot] = null;
				return stack;
			}
			return null;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			this.slots[slot] = stack;
		}

		@Override public String getInventoryName() { return "null"; }
		@Override public boolean hasCustomInventoryName() { return false; }

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public void markDirty() {
			
		}

		@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }

		@Override public void openInventory() { }
		@Override public void closeInventory() { }

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			return false;
		}
	}
}
