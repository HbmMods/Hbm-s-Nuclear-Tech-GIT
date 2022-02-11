package com.hbm.explosion.vanillant;

import net.minecraft.block.Block;

public class DropChanceNever implements IDropChanceMutator {

	@Override
	public float mutateDropChance(ExplosionVNT explosion, Block block, int x, int y, int z, float chance) {
		return 0;
	}
}
