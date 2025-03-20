package com.hbm.items.weapon.sedna.mods;

import java.util.function.BiConsumer;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;

import net.minecraft.item.ItemStack;

public class WeaponModPolymerFurniture extends WeaponModBase {

	public WeaponModPolymerFurniture(int id) {
		super(id, "FURNITURE");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == Receiver.CON_ONRECOIL) return (T) LAMBDA_RECOIL_G3;
		return base;
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_G3 = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 0.125), (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.125));
	};

}
