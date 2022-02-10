package com.hbm.explosion.vanillant;

import java.util.HashSet;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public interface IExplosionSFX {

	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size);
}
