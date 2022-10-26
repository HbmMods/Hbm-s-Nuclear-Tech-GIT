package com.hbm.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Because vanilla slots have severe mental disabilities that prevent them from working as expected.
 * @author hbm
 */
public class SlotNonRetarded extends Slot {

	public SlotNonRetarded(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	/**
	 * Dear mojang: Why wasn't that the standard to begin with? What do IInventories have isItemValidForSlot when by default nothing fucking uses it?
	 */
	@Override
	public boolean isItemValid(ItemStack stack) {
		return inventory.isItemValidForSlot(this.slotNumber, stack);
	}
}
