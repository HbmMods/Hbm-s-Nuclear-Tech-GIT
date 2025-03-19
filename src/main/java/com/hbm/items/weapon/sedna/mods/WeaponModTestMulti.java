package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModTestMulti extends WeaponModBase {
	
	public WeaponModTestMulti(int id, String... slots) {
		super(id, slots);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(parent instanceof Receiver && key == Receiver.I_ROUNDSPERCYCLE && base instanceof Integer) {
			return fagSlop((Integer) base * 3, base);
		}
		
		return base;
	}
}
