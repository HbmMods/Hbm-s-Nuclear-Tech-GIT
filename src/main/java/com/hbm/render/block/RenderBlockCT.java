package com.hbm.render.block;

import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTContext;
import com.hbm.render.block.ct.RenderBlocksCT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockCT implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		RenderBlocksCT.instance.renderBlockAsItem(block, metadata, 1F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		RenderBlocksCT rbct = RenderBlocksCT.instance;
		rbct.prepWorld(world);
		
		CTContext.loadContext(world, x, y, z, block);
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
		return CT.renderID;
	}
}
