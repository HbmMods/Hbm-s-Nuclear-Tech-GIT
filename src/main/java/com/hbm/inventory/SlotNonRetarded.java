package com.hbm.inventory;

import com.hbm.interfaces.NotableComments;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Because vanilla slots have shit idiot brain fungus that prevent them from working as expected.
 * @author hbm
 */
@NotableComments
public class SlotNonRetarded extends Slot {

	public SlotNonRetarded(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	/**
	 * Dear mojang: Why wasn't that the standard to begin with? What do IInventories have isItemValidForSlot for when by default nothing fucking uses it?
	 */
	@Override
	public boolean isItemValid(ItemStack stack) {
		return inventory.isItemValidForSlot(this.slotNumber, stack);
	}

	/**
	 * Because if slots have higher stacksizes than the maximum allowed by the tile, the display just stops working.
	 * Why was that necessary? Sure it's not intended but falsifying information isn't very cool.
	 */
	@Override
	public int getSlotStackLimit() {
		return Math.max(this.inventory.getInventoryStackLimit(), this.getHasStack() ? this.getStack().stackSize : 1);
	}
}
