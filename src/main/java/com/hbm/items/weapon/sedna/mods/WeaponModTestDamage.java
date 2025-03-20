package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModTestDamage extends WeaponModBase {
	
	public WeaponModTestDamage(int id, String... slots) {
		super(id, slots);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(parent instanceof Receiver && key == Receiver.F_BASEDAMAGE && base instanceof Float) {
			return fagSlop((Float) base * 1.5F, base);
		}
		
		return base;
	}
}
