package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModTestMulti extends WeaponModBase {
	
	public WeaponModTestMulti(int id, String... slots) {
		super(id, slots);
		this.setPriority(PRIORITY_MULT_FINAL);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(parent instanceof Receiver && key == Receiver.I_ROUNDSPERCYCLE && base instanceof Integer) {
			return cast((Integer) base * 3, base);
		}
		
		return base;
	}
}
