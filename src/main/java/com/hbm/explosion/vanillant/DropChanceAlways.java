package com.hbm.explosion.vanillant;

import net.minecraft.block.Block;

/**
 * Now it's getting ridiculously over-engineered
 * @author hbm
 *
 */
public class DropChanceAlways implements IDropChanceMutator {

	@Override
	public float mutateDropChance(ExplosionVNT explosion, Block block, int x, int y, int z, float chance) {
		return 1F;
	}
}
