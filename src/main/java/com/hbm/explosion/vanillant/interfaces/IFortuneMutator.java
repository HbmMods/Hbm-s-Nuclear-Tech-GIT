package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.block.Block;

public interface IFortuneMutator {

	public int mutateFortune(ExplosionVNT explosion, Block block, int x, int y, int z);
}
