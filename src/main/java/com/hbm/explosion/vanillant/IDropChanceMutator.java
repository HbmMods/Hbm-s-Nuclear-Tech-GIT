package com.hbm.explosion.vanillant;

import net.minecraft.block.Block;

public interface IDropChanceMutator {

	public float mutateDropChance(ExplosionVNT explosion, Block block, int x, int y, int z, float chance);
}