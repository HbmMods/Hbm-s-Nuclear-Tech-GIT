package com.hbm.items.weapon.sedna.mags;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Uses individual bullets which are loaded all at once */
public class MagazineFullReload extends MagazineSingleTypeBase {

	public MagazineFullReload(int index, int capacity) {
		super(index, capacity);
	}

	/** Reloads all rounds at once. If the mag is empty, the mag's type will change to the first valid ammo type */
	@Override
	public void reloadAction(ItemStack stack, IInventory inventory) {
		standardReload(stack, inventory, this.capacity);
	}
}
