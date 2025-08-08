package com.hbm.items.weapon.sedna.mods;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.Receiver;

import net.minecraft.item.ItemStack;

public class WeaponModLasAuto extends WeaponModBase {

	public WeaponModLasAuto(int id) {
		super(id, "RECEIVER");
		this.setPriority(PRIORITY_SET);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.F_BASEDAMAGE) return cast((Float) base * 0.66F, base);
		if(key == Receiver.B_REFIREONHOLD) return cast(true, base);
		if(key == Receiver.I_DELAYAFTERFIRE) return cast(5, base);
		if(key == GunConfig.O_SCOPETEXTURE) return cast(null, base);
		return base;
	}
}
