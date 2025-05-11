package com.hbm.explosion.vanillant.standard;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IBlockMutator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMutatorBalefire implements IBlockMutator {

	@Override public void mutatePre(ExplosionVNT explosion, Block block, int meta, int x, int y, int z) { }

	@Override
	public void mutatePost(ExplosionVNT explosion, int x, int y, int z) {

		Block block = explosion.world.getBlock(x, y, z);
		Block block1 = explosion.world.getBlock(x, y - 1, z);
		if(block.getMaterial() == Material.air && block1.func_149730_j() && explosion.world.rand.nextInt(3) == 0) {
			explosion.world.setBlock(x, y, z, ModBlocks.balefire);
		}
	}
}
