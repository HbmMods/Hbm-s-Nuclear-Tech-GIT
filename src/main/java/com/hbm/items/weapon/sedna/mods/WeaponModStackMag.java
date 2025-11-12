package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.items.weapon.sedna.mags.MagazineSingleReload;

import net.minecraft.item.ItemStack;

public class WeaponModStackMag extends WeaponModBase {

	// we can get away with reusing and re-adjusting these single instances because magazines
	// aren't permanent objects, they are not cached anywhere, a magazine is only relevant
	// for accessing the itemstack's ammo info and for the state machine's reload operation
	protected static MagazineSingleReload DUMMY_SINGLE = new MagazineSingleReload(0, 0);
	protected static MagazineFullReload DUMMY_FULL = new MagazineFullReload(0, 0);

	public WeaponModStackMag(int id) {
		super(id, "MAG");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.O_MAGAZINE) {
			if(base instanceof MagazineSingleReload) {
				MagazineSingleReload original = (MagazineSingleReload) base;
				DUMMY_SINGLE.acceptedBullets = original.acceptedBullets;
				DUMMY_SINGLE.capacity = original.capacity * 3 / 2;
				DUMMY_SINGLE.index = original.index;
				return (T) DUMMY_SINGLE;
			}
			if(base instanceof MagazineFullReload) {
				MagazineFullReload original = (MagazineFullReload) base;
				DUMMY_FULL.acceptedBullets = original.acceptedBullets;
				DUMMY_FULL.capacity = original.capacity * 3 / 2;
				DUMMY_FULL.index = original.index;
				return (T) DUMMY_FULL;
			}
		}
		return base;
	}
}
