package com.hbm.items.weapon.sedna.factory;

import com.hbm.particle.helper.AshesCreator;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class ConfettiUtil {
	
	public static void decideConfetti(EntityLivingBase entity, DamageSource source) {
		if(source.damageType.equals(DamageClass.LASER.name())) pulverize(entity);
	}

	public static void pulverize(EntityLivingBase entity) {
		if(entity.isEntityAlive()) return;
		int amount = MathHelper.clamp_int((int) (entity.width * entity.height * entity.width * 25), 5, 50);
		AshesCreator.composeEffect(entity.worldObj, entity, amount, 0.125F);
	}
}
