package com.hbm.explosion.vanillant;

import java.util.HashSet;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public interface IBlockProcessor {

	public void process(ExplosionVNT explosion, World world, double x, double y, double z, HashSet<ChunkPosition> affectedBlocks);
}
