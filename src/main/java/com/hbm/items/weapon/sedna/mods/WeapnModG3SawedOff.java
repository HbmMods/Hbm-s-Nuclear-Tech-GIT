package com.hbm.items.weapon.sedna.mods;

import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.factory.XFactory556mm;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

public class WeapnModG3SawedOff extends WeaponModBase {

	public WeapnModG3SawedOff(int id) {
		super(id, "SHIELD");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == GunConfig.I_DRAWDURATION) return cast(5, base);
		if(key == GunConfig.FUN_ANIMNATIONS) return (T) LAMBDA_G3_ANIMS;
		return base;
	}
	
	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_G3_ANIMS = (stack, type) -> {
		switch(type) {
			case EQUIP: return new BusAnimation().addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 250, IType.SIN_FULL));
		}
		return XFactory556mm.LAMBDA_G3_ANIMS.apply(stack, type);
	};
}
