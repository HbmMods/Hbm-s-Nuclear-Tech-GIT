package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.entity.Entity;

public interface ICustomDamageHandler {

	public void handleAttack(ExplosionVNT explosion, Entity entity, double distanceScaled);
}
