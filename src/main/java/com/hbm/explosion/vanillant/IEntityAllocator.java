package com.hbm.explosion.vanillant;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IEntityAllocator {

	public HashMap<Entity, Vec3> allocate(ExplosionVNT explosion, World world, double x, double y, double z, float size);
}
