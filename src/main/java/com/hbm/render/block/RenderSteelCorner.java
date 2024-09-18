package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.DecoBlock;
import com.hbm.render.util.RenderBlocksNT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderSteelCorner implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		tessellator.startDrawingQuads();

		
		renderer.setRenderBounds(0D, 0D, 0.875D, 0.75D, 1D, 1D);
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));

		
		renderer.setRenderBounds(0.75D, 0D, 0.75D, 1D, 1D, 1D);
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));
		
		renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 1D, 0.75D);
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));
		
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		int meta = world.getBlockMetadata(x, y, z);
		
		renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		switch(meta) {
		case 2:
			renderer.setRenderBounds(0.25D, 0D, 0.875D, 1D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0D, 0.75D, 0.25D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0D, 0D, 0.125D, 1D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
			break;
		case 3:
			renderer.setRenderBounds(0D, 0D, 0D, 0.75D, 1D, 0.125D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.75D, 0D, 0D, 1D, 1D, 0.25D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.875D, 0D, 0.25D, 1D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			break;
		case 4:
			renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 1D, 0.75D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.75D, 0D, 0.75D, 1D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0D, 0.875D, 0.75D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			break;
		case 5:
			renderer.setRenderBounds(0D, 0D, 0.25D, 0.125D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0D, 0D, 0D, 0.25D, 1D, 0.25D); renderer.renderStandardBlock(block, x, y, z);
			renderer.setRenderBounds(0.25D, 0D, 0D, 1D, 1D, 0.125D); renderer.renderStandardBlock(block, x, y, z);
			break;
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return DecoBlock.renderIDCorner;
	}
}
