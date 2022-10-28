package com.hbm.explosion.vanillant.interfaces;

import com.hbm.explosion.vanillant.ExplosionVNT;

public interface IBlockMutator {

	public int mutateAtPosition(ExplosionVNT explosion, int x, int y, int z);
}
