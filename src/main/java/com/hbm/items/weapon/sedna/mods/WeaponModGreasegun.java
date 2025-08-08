package com.hbm.items.weapon.sedna.mods;

import java.util.function.BiConsumer;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.factory.Orchestras;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.helper.CasingCreator;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class WeaponModGreasegun extends WeaponModBase {

	public WeaponModGreasegun(int id) {
		super(id, "FURNITURE");
		this.setPriority(PRIORITY_ADDITIVE);
	}

	@Override
	public <T> T eval(T base, ItemStack gun, String key, Object parent) {
		if(key == GunConfig.F_DURABILITY) return cast((Float) base * 3F, base);
		if(key == Receiver.F_BASEDAMAGE) return cast((Float) base + 2F, base);
		if(key == Receiver.F_SPREADINNATE) return cast(0F, base);
		if(key == Receiver.I_DELAYAFTERFIRE) return cast((Integer) base / 2, base);
		if(key == GunConfig.CON_ORCHESTRA) return (T) ORCHESTRA_GREASEGUN;
		return base;
	}
	
	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA_GREASEGUN = (stack, ctx) -> {
		EntityLivingBase entity = ctx.entity;
		if(entity.worldObj.isRemote) return;
		AnimType type = ItemGunBaseNT.getLastAnim(stack, ctx.configIndex);
		int timer = ItemGunBaseNT.getAnimTimer(stack, ctx.configIndex);
		boolean aiming = ItemGunBaseNT.getIsAiming(stack);

		if(type == AnimType.CYCLE) {
			if(timer == 1) {
				SpentCasing casing = ctx.config.getReceivers(stack)[0].getMagazine(stack).getCasing(stack, ctx.inventory);
				if(casing != null) CasingCreator.composeEffect(entity.worldObj, entity, 0.55, aiming ? 0 : -0.125, aiming ? 0 : -0.25D, 0, 0.18, -0.12, 0.01, -7.5F + (float)entity.getRNG().nextGaussian() * 5F, 12F + (float)entity.getRNG().nextGaussian() * 5F, casing.getName());
			}
			return;
		}
		Orchestras.ORCHESTRA_GREASEGUN.accept(stack, ctx);
	};
}
