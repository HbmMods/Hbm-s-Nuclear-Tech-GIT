package com.hbm.items.weapon.sedna.mags;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Uses individual bullets which are loaded one by one */
public class MagazineSingleReload extends MagazineSingleTypeBase {

	public MagazineSingleReload(int index, int capacity) {
		super(index, capacity);
	}

	/** Reloads all rounds at once. If the mag is empty, the mag's type will change to the first valid ammo type */
	@Override
	public void reloadAction(ItemStack stack, IInventory inventory) {
		standardReload(stack, inventory, 1);
	}
}
