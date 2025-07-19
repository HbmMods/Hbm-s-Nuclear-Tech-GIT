package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModSilencer extends WeaponModBase {

	public WeaponModSilencer(int id) {
		super(id, "SILENCER");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(key == Receiver.S_FIRESOUND) {
			return (T) "hbm:weapon.fire.silenced";
		}
		
		return base;
	}
}
