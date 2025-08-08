package com.hbm.items.weapon.sedna.mods;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.factory.Lego;
import com.hbm.items.weapon.sedna.factory.XFactoryRocket;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.item.ItemStack;

public class WeaponModPanzerschreckSawedOff extends WeaponModBase {

	public WeaponModPanzerschreckSawedOff(int id) {
		super(id, "SHIELD");
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == GunConfig.I_DRAWDURATION) return cast(5, base);
		if(key == Receiver.CON_ONFIRE) { return (T) LAMBDA_FIRE; }
		return base;
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_PANZERSCHRECK_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation().addBus("EQUIP", new BusAnimationSequence().addPos(60, 0, 0, 0).addPos(0, 0, 0, 250, IType.SIN_DOWN));
		}
		return XFactoryRocket.LAMBDA_PANZERSCHRECK_ANIMS.apply(stack, type);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_FIRE = (stack, ctx) -> {
		Lego.LAMBDA_STANDARD_FIRE.accept(stack, ctx);
		if(ctx.entity != null) {
			HbmLivingProps.getData(ctx.entity).fire += 100;
			EntityDamageUtil.attackEntityFromNT(ctx.entity, BulletConfig.getDamage(ctx.entity, ctx.entity, DamageClass.FIRE), 4F, true, false, 0F, 0F, 0F);
		}
	};
}
