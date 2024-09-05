package com.hbm.items.weapon.sedna.mags;

import com.hbm.items.weapon.sedna.BulletConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Uses individual bullets which are loaded all at once */
public class MagazineRevolverDrum extends MagazineStandardBase {

	public MagazineRevolverDrum(int index, int capacity) {
		super(index, capacity);
	}

	@Override
	public void reloadAction(ItemStack stack, EntityPlayer player) {
		
	}

	@Override
	public ItemStack getIcon(ItemStack stack) {
		Object o = this.getType(stack);
		if(o instanceof BulletConfig) {
			return ((BulletConfig) o).ammo.toStack();
		}
		return null;
	}
}
