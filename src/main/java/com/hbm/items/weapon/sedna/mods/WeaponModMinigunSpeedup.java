package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModMinigunSpeedup extends WeaponModBase {

	public WeaponModMinigunSpeedup(int id) {
		super(id, "SPEED");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.I_ROUNDSPERCYCLE) return cast((Integer) base * 3, base);
		if(key == Receiver.F_SPREADINNATE) return cast((Float) base * 1.5F, base);
		return base;
	}
}
