package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockPedestal;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderPedestal implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		
		for(int i = 0; i < 3; i++) {
			if(i == 0) renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
			if(i == 1) renderer.setRenderBounds(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
			if(i == 2) renderer.setRenderBounds(0.125, 0.25, 0.125, 0.875, 0.75, 0.875);
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		}
		
		tessellator.draw();
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.125, 0.25, 0.125, 0.875, 0.75, 0.875);
		renderer.renderStandardBlock(block, x, y, z);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockPedestal.renderID;
	}
}
