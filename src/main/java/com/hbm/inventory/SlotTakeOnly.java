package com.hbm.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTakeOnly extends Slot {

	public SlotTakeOnly(IInventory inventory, int i, int j, int k) {
		super(inventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
}
