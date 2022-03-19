package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IDropChanceMutator;

import net.minecraft.block.Block;

public class DropChanceMutatorStandard implements IDropChanceMutator {
	
	private float chance;
	
	public DropChanceMutatorStandard(float chance)  {
		this.chance = chance;
	}

	@Override
	public float mutateDropChance(ExplosionVNT explosion, Block block, int x, int y, int z, float chance) {
		return this.chance;
	}
}
