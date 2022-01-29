package com.hbm.inventory;

import com.hbm.items.machine.ItemMachineUpgrade;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrade extends Slot {

	public SlotUpgrade(IInventory inventory, int i, int j, int k) {
		super(inventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemMachineUpgrade;
	}

	@Override
	public void onSlotChange(ItemStack sta1, ItemStack sta2) {
		super.onSlotChange(sta1, sta2);
	}
}
