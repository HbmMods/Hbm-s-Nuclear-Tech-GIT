package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IBlockMutator;
import com.hbm.inventory.RecipesCommon.MetaBlock;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMutatorDebris implements IBlockMutator {
	
	protected MetaBlock metaBlock;
	
	public BlockMutatorDebris(Block block) {
		this(block, 0);
	}
	
	public BlockMutatorDebris(Block block, int meta) {
		this.metaBlock = new MetaBlock(block, meta);
	}

	@Override public void mutatePre(ExplosionVNT explosion, Block block, int meta, int x, int y, int z) { }

	@Override public void mutatePost(ExplosionVNT explosion, int x, int y, int z) {

		World world = explosion.world;
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if(b.isNormalCube() && (b != metaBlock.block || world.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) != metaBlock.meta)) {
				world.setBlock(x, y, z, metaBlock.block, metaBlock.meta, 3);
				return;
			}
		}
	}
}
