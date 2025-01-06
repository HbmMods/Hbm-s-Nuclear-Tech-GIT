package com.hbm.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Deprecated slots can hold items from previous versions, but are otherwise entirely uninteractable
 */
public class SlotDeprecated extends Slot {

	public SlotDeprecated(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_111238_b() {
		return false;
	}
}
