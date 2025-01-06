package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.FoundryMold;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderFoundryMold implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		FoundryMold basin = (FoundryMold) block;
		double x = 0;
		double y = 0;
		double z = 0;
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 1F, 0F);
		renderer.renderFaceYPos(block, x, y, z, basin.iconTop);
		renderer.renderFaceYPos(block, x, y - 0.375D, z, basin.iconBottom);
		tessellator.setNormal(0F, -1F, 0F);
		renderer.renderFaceYNeg(block, x, y, z, basin.iconBottom);
		tessellator.setNormal(1F, 0F, 0F);
		renderer.renderFaceXPos(block, x, y, z, basin.iconSide);
		renderer.renderFaceXPos(block, x - 0.875D, y, z, basin.iconInner);
		tessellator.setNormal(-1F, 0F, 0F);
		renderer.renderFaceXNeg(block, x, y, z, basin.iconSide);
		renderer.renderFaceXNeg(block, x + 0.875D, y, z, basin.iconInner);
		tessellator.setNormal(0F, 0F, 1F);
		renderer.renderFaceZPos(block, x, y, z, basin.iconSide);
		renderer.renderFaceZPos(block, x, y, z - 0.875D, basin.iconInner);
		tessellator.setNormal(0F, 0F, -1F);
		renderer.renderFaceZNeg(block, x, y, z, basin.iconSide);
		renderer.renderFaceZNeg(block, x, y, z + 0.875D, basin.iconInner);
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
		int colorMult = block.colorMultiplier(world, x, y, z);
		float r = (float) (colorMult >> 16 & 255) / 255.0F;
		float g = (float) (colorMult >> 8 & 255) / 255.0F;
		float b = (float) (colorMult & 255) / 255.0F;
		
		float mulBottom = 0.5F;
		float mulTop = 1.0F;
		float mulZ = 0.8F;
		float mulX = 0.6F;

		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		tessellator.setBrightness(brightness);

		if(EntityRenderer.anaglyphEnable) {
			float aR = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			float aG = (r * 30.0F + g * 70.0F) / 100.0F;
			float aB = (r * 30.0F + b * 70.0F) / 100.0F;
			r = aR;
			g = aG;
			b = aB;
		}
		
		FoundryMold mold = (FoundryMold) block;

		tessellator.setColorOpaque_F(r * mulTop, g * mulTop, b * mulTop);
		renderer.renderFaceYPos(block, x, y, z, mold.iconTop);
		renderer.renderFaceYPos(block, x, y - 0.375D, z, mold.iconBottom);
		tessellator.setColorOpaque_F(r * mulBottom, g * mulBottom, b * mulBottom);
		renderer.renderFaceYNeg(block, x, y, z, mold.iconBottom);
		tessellator.setColorOpaque_F(r * mulX, g * mulX, b * mulX);
		renderer.renderFaceXPos(block, x, y, z, mold.iconSide);
		renderer.renderFaceXNeg(block, x, y, z, mold.iconSide);
		renderer.renderFaceXPos(block, x - 0.875D, y, z, mold.iconInner);
		renderer.renderFaceXNeg(block, x + 0.875D, y, z, mold.iconInner);
		tessellator.setColorOpaque_F(r * mulZ, g * mulZ, b * mulZ);
		renderer.renderFaceZPos(block, x, y, z, mold.iconSide);
		renderer.renderFaceZNeg(block, x, y, z, mold.iconSide);
		renderer.renderFaceZPos(block, x, y, z - 0.875D, mold.iconInner);
		renderer.renderFaceZNeg(block, x, y, z + 0.875D, mold.iconInner);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return FoundryMold.renderID;
	}
}
