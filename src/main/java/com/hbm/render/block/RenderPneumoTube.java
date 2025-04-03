package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.PneumoTube;
import com.hbm.lib.Library;
import com.hbm.render.util.RenderBlocksNT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderPneumoTube implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		PneumoTube duct = (PneumoTube) block;

		double lower = 0.3125D;
		double upper = 0.6875D;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		renderer.setRenderBounds(lower, lower, 0, upper, upper, 1);
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, duct.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, duct.getIcon(0, 0));
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, duct.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, duct.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, duct.iconConnector);
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, duct.iconConnector);
		tessellator.draw();

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		PneumoTube duct = (PneumoTube) block;

		boolean pX = duct.canConnectTo(world, x, y, z, Library.POS_X);
		boolean nX = duct.canConnectTo(world, x, y, z, Library.NEG_X);
		boolean pY = duct.canConnectTo(world, x, y, z, Library.POS_Y);
		boolean nY = duct.canConnectTo(world, x, y, z, Library.NEG_Y);
		boolean pZ = duct.canConnectTo(world, x, y, z, Library.POS_Z);
		boolean nZ = duct.canConnectTo(world, x, y, z, Library.NEG_Z);

		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		
		double lower = 0.3125D;
		double upper = 0.6875D;
		
		//Straight along X
		if(mask == 0b110000) {
			renderer.setRenderBounds(0.0D, lower, lower, 1.0D, upper, upper);
			duct.renderSides[4] = false;
			duct.renderSides[5] = false;
			renderer.renderStandardBlock(block, x, y, z);
			duct.resetRenderSides();

		// Straight along Z
		} else if(mask == 0b000011) {
			renderer.setRenderBounds(lower, lower, 0.0D, upper, upper, 1.0D);
			duct.renderSides[2] = false;
			duct.renderSides[3] = false;
			renderer.renderStandardBlock(block, x, y, z);
			duct.resetRenderSides();
			
		//Straight along Y
		} else if(mask == 0b001100) {
			renderer.setRenderBounds(lower, 0.0D, lower, upper, 1.0D, upper);
			duct.renderSides[0] = false;
			duct.renderSides[1] = false;
			renderer.renderStandardBlock(block, x, y, z);
			duct.resetRenderSides();
		} else {
			renderer.setRenderBounds(lower, lower, lower, upper, upper, upper);
			renderer.renderStandardBlock(block, x, y, z);
			if(pX) { duct.renderSides[5] = false; renderer.setRenderBounds(upper, lower, lower, 1, upper, upper); renderer.renderStandardBlock(block, x, y, z); duct.resetRenderSides(); }
			if(nX) { duct.renderSides[4] = false; renderer.setRenderBounds(0, lower, lower, lower, upper, upper); renderer.renderStandardBlock(block, x, y, z); duct.resetRenderSides(); }
			if(pY) { duct.renderSides[1] = false; renderer.setRenderBounds(lower, upper, lower, upper, 1, upper); renderer.renderStandardBlock(block, x, y, z); duct.resetRenderSides(); }
			if(nY) { duct.renderSides[0] = false; renderer.setRenderBounds(lower, 0, lower, upper, lower, upper); renderer.renderStandardBlock(block, x, y, z); duct.resetRenderSides(); }
			if(pZ) { duct.renderSides[3] = false; renderer.setRenderBounds(lower, lower, upper, upper, upper, 1); renderer.renderStandardBlock(block, x, y, z); duct.resetRenderSides(); }
			if(nZ) { duct.renderSides[2] = false; renderer.setRenderBounds(lower, lower, 0, upper, upper, lower); renderer.renderStandardBlock(block, x, y, z); duct.resetRenderSides(); }
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return PneumoTube.renderID;
	}
}
