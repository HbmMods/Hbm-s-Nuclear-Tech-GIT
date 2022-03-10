package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.block.Block;

public interface IDropChanceMutator {

	public float mutateDropChance(ExplosionVNT explosion, Block block, int x, int y, int z, float chance);
}
