package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModGenericDamage extends WeaponModBase {
	
	public WeaponModGenericDamage(int id) {
		super(id, "GENERIC_DAMAGE");
		this.setPriority(PRIORITY_MULTIPLICATIVE);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(parent instanceof Receiver && key == Receiver.F_BASEDAMAGE && base instanceof Float) {
			return cast((Float) base * 1.33F, base);
		}
		
		return base;
	}
}
