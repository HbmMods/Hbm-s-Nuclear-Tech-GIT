package com.hbm.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTransporterRocket extends Container {

	IInventory transporterInventory;

	public ContainerTransporterRocket(InventoryPlayer invPlayer, IInventory invTransporter) {
		transporterInventory = invTransporter;

		addSlots(invTransporter, 0, 8, 34, 2, 4); // Input slots
		addSlots(invTransporter, 8, 98, 34, 2, 4); // Output slots

		addSlots(invTransporter, 16, 8, 106, 1, 4); // Input Fluid ID slots
		addSlots(invTransporter, 20, 188, 90, 1, 2); // Fuel Fluid ID slots

		addSlots(invPlayer, 9, 8, 154, 3, 9); // Player inventory
		addSlots(invPlayer, 0, 8, 212, 1, 9); // Player hotbar
	}

	// I'm gonna make a farken helper function for this shit, why was it done the old way for 9 whole ass years?
	private void addSlots(IInventory inv, int from, int x, int y, int rows, int cols) {
		int slotSize = 18;
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				this.addSlotToContainer(new Slot(inv, col + row * cols + from, x + col * slotSize, y + row * slotSize));
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= transporterInventory.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, transporterInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!this.mergeItemStack(var5, 0, transporterInventory.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	
}
