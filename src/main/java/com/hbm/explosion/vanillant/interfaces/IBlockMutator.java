package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

import net.minecraft.block.Block;

public interface IBlockMutator {

	public void mutatePre(ExplosionVNT explosion, Block block, int meta, int x, int y, int z);
	public void mutatePost(ExplosionVNT explosion, int x, int y, int z);
}
