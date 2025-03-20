package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModTestFirerate extends WeaponModBase {
	
	public WeaponModTestFirerate(int id, String... slots) {
		super(id, slots);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {

		if(parent instanceof Receiver && key == Receiver.I_DELAYAFTERFIRE && base instanceof Integer) {
			return fagSlop(Math.max((Integer) base / 2, 1), base);
		}
		
		return base;
	}
}
