package com.hbm.items.weapon.sedna.mods;

import net.minecraft.item.ItemStack;

public class WeaponModNickel extends WeaponModBase {

	public WeaponModNickel(int id, String name) {
		super(id, name);
		this.setPriority(PRIORITY_SET);
	}

	@Override public <T> T eval(T base, ItemStack gun, String key, Object parent) { return base; }
}
