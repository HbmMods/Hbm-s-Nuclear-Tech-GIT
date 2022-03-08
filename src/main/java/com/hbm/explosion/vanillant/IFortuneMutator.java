package com.hbm.explosion.vanillant;

import net.minecraft.block.Block;

public interface IFortuneMutator {

	public int mutateFortune(ExplosionVNT explosion, Block block, int x, int y, int z);
}
