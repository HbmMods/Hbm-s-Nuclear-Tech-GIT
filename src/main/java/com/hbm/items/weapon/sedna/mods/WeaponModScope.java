package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.factory.XFactory44;

import net.minecraft.item.ItemStack;

public class WeaponModScope extends WeaponModBase {

	public WeaponModScope(int id) {
		super(id, "SCOPE");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		
		if(key == GunConfig.O_SCOPETEXTURE) {
			return (T) XFactory44.scope_lilmac;
		}
		
		return base;
	}
}
