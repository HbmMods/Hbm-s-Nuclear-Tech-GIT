package com.hbm.render.block;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public interface ISBRHUniversal {
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	public void renderInventoryBlock(Block block, int metadata, int modelId, Object renderBlocks);
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks);
}
