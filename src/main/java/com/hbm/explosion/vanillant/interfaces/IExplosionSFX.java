package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.world.World;

public interface IExplosionSFX {

	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size);
}
