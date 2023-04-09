package com.hbm.blocks.generic;

import com.hbm.blocks.IRadResistantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockRadResistant extends Block implements IRadResistantBlock {

	public BlockRadResistant(Material mat) {
		super(mat);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		//ChunkRadiationHandlerNT.markChunkForRebuild(world, x, y, z);
		super.onBlockAdded(world, x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta) {
		//ChunkRadiationHandlerNT.markChunkForRebuild(world, x, y, z);
		super.breakBlock(world, x, y, z, b, meta);
	}

	@Override
	public int getResistance() {
		return 1;
	}
}
