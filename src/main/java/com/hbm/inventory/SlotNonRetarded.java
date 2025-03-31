package com.hbm.inventory;

import com.hbm.interfaces.NotableComments;

import com.hbm.inventory.container.ContainerCrateBase;
import com.hbm.items.block.ItemBlockStorageCrate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Because vanilla slots have severe mental disabilities that prevent them from working as expected.
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

	/**
	 * This prevents the player from moving containers that are being held *at all*, fixing a decently big dupe.
	 * I hate that this has to be here but... It is what it is.
	 */
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		if(player.inventory.currentItem == this.getSlotIndex() && // If this slot is the current held slot.
			this.getStack() != null && this.getStack().getItem() instanceof ItemBlockStorageCrate && // If the slot contains a storage crate.
			player.openContainer instanceof ContainerCrateBase) // If the player is currently inside a crate container.
			return false;
		return super.canTakeStack(player);
	}
}
