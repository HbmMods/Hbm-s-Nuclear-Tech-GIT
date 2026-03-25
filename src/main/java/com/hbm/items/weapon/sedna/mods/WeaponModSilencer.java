package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.main.NTMSounds;

import net.minecraft.item.ItemStack;

public class WeaponModSilencer extends WeaponModBase {

	public WeaponModSilencer(int id) {
		super(id, "SILENCER");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(key == Receiver.S_FIRESOUND) {
			if(gun.getItem() == ModItems.gun_amat) return (T) NTMSounds.GUN_AMAT_SILENCER;
			return (T) NTMSounds.GUN_RIFLE_SILENCER;
		}
		
		return base;
	}
}
