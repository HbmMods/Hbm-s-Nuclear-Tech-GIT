package com.hbm.explosion.vanillant;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public interface IEntityProcessor {

	public void process(ExplosionVNT explosion, World world, double x, double y, double z, HashMap<Entity, Vec3> affectedBlocks);
}
