package com.hbm.render.block;

import com.hbm.blocks.test.TestCT;
import com.hbm.render.block.ct.CTContext;
import com.hbm.render.block.ct.RenderBlocksCT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RenderBlockCT implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		renderer.renderBlockAsItem(block, 1, 1.0F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		RenderBlocksCT rbct = RenderBlocksCT.instance;
		
		CTContext.loadContext((World)world, x, y, z, block);
		rbct.renderStandardBlock(block, x, y, z);
		CTContext.dropContext();
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return TestCT.renderID;
	}

}
