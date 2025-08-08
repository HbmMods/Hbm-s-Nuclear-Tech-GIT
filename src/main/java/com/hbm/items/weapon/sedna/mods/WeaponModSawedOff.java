package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.factory.XFactory12ga;

import net.minecraft.item.ItemStack;

public class WeaponModSawedOff extends WeaponModBase {

	public WeaponModSawedOff(int id) {
		super(id, "BARREL");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {

		if(key == Receiver.F_SPREADINNATE) { return cast(Math.max(0.025F, (Float) base), base); }
		if(key == Receiver.F_SPREADAMMO) { return cast((Float) base * 1.5F, base); }
		if(key == Receiver.F_BASEDAMAGE) { return cast((Float) base * 1.35F, base); }
		
		if(gun.getItem() == ModItems.gun_maresleg) {
			if(key == GunConfig.FUN_ANIMNATIONS) return (T) XFactory12ga.LAMBDA_MARESLEG_SHORT_ANIMS;
			if(key == GunConfig.I_DRAWDURATION) return cast(5, base);
		}
		
		return base;
	}
}
