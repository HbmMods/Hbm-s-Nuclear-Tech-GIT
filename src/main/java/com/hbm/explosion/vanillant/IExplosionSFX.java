package com.hbm.explosion.vanillant;

import net.minecraft.world.World;

public interface IExplosionSFX {

	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size);
}