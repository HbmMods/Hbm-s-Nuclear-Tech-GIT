package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.DecoBlock;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderSteelWall implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.9375F);
		
		renderer.setRenderBounds(0D, 0D, 0.875D, 1D, 1D, 1D);
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		int meta = world.getBlockMetadata(x, y, z);
		
		switch(meta) {
		case 2: renderer.setRenderBounds(0D, 0D, 0.875D, 1D, 1D, 1D); break;
		case 3: renderer.setRenderBounds(0D, 0D, 0D, 1D, 1D, 0.125D); break;
		case 4: renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 1D, 1D); break;
		case 5: renderer.setRenderBounds(0D, 0D, 0D, 0.125D, 1D, 1D); break;
		}

		renderer.renderStandardBlock(block, x, y, z);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return DecoBlock.renderIDWall;
	}

}
