package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.BlockConveyor;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderConveyor implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);

		GL11.glTranslatef(-0.5F, -0.25F, -0.5F);
		renderer.setRenderBounds( 0D, 0D, 0D, 1D, 0.25D, 1D);
		
		meta = 2;
		
		renderer.uvRotateTop = 3;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateWest = 3;
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();
		
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;

		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		
		boolean bent = meta > 5;
		
		if(meta > 9) meta -= 8;
		if(meta > 5) meta -= 4;

		if(meta == 2) {
			renderer.uvRotateTop = 3;
			renderer.uvRotateBottom = 0;
			if(!bent) renderer.uvRotateWest = 3;
		}
		if(meta == 3) {
			renderer.uvRotateTop = 0;
			renderer.uvRotateBottom = 3;
			if(!bent) renderer.uvRotateEast = 3;
		}
		if(meta == 4) {
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
			if(!bent) renderer.uvRotateSouth = 3;
		}
		if(meta == 5) {
			renderer.uvRotateTop = 2;
			renderer.uvRotateBottom = 2;
			if(!bent) renderer.uvRotateNorth = 3;
		}

		renderer.setRenderBounds((double) 0, 0.0D, (double) 0, (double) 1, 0.25D, (double) 1);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockConveyor.renderID;
	}

}
