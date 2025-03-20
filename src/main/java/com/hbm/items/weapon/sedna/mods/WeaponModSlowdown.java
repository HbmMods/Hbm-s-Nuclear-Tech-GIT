package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModSlowdown extends WeaponModBase {

	public WeaponModSlowdown(int id) {
		super(id, "SPEED");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.I_DELAYAFTERFIRE) return cast((Integer) base * 2, base);
		if(key == Receiver.F_SPREADINNATE) return cast(0F, base);
		return base;
	}
}
