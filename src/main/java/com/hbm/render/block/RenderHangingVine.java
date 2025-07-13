package com.hbm.render.block;

import com.hbm.blocks.generic.BlockHangingVine;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderHangingVine implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tess = Tessellator.instance;
		int colorMult = block.colorMultiplier(world, x, y, z);
		float r = (float) (colorMult >> 16 & 255) / 255.0F;
		float g = (float) (colorMult >> 8 & 255) / 255.0F;
		float b = (float) (colorMult & 255) / 255.0F;
		
		tess.setColorOpaque_F(r, g, b);
		
		BlockHangingVine vine = (BlockHangingVine) block;
		
		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		tess.setBrightness(brightness);
		
		IIcon icon = vine.getIcon(world, x, y, z, false);
		renderCrossedSquares(icon, x, y, z, 1.0D);
		
		tess.setBrightness(240);
		
		icon = vine.getIcon(world, x, y, z, true); //glow pass
		renderCrossedSquares(icon, x, y, z, 1.0D);
		
		return true;
	}
	
	//fixed implementation of drawCrossedSquares
	public void renderCrossedSquares(IIcon icon, double x, double y, double z, double height) {
		Tessellator tess = Tessellator.instance;
		
		double minU = icon.getMinU();
		double minV = icon.getMinV();
		double maxU = icon.getMaxU();
		double maxV = icon.getMaxV();
		
		double factor = 0.45D * height;
		double minX = x + 0.5D - factor;
		double maxX = x + 0.5D + factor;
		double minZ = z + 0.5D - factor;
		double maxZ = z + 0.5D + factor;
		
		tess.addVertexWithUV(minX, y, minZ, maxU, maxV); 
		tess.addVertexWithUV(minX, y + height, minZ, maxU, minV);
		tess.addVertexWithUV(maxX, y + height, maxZ, minU, minV);
		tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
		
		tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
		tess.addVertexWithUV(maxX, y + height, maxZ, maxU, minV);
		tess.addVertexWithUV(minX, y + height, minZ, minU, minV);
		tess.addVertexWithUV(minX, y, minZ, minU, maxV);
		
		tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
		tess.addVertexWithUV(maxX, y + height, minZ, maxU, minV);
		tess.addVertexWithUV(minX, y + height, maxZ, minU, minV);
		tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
		
		tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
		tess.addVertexWithUV(minX, y + height, maxZ, maxU, minV);
		tess.addVertexWithUV(maxX, y + height, minZ, minU, minV);
		tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}
	
	@Override
	public int getRenderId() {
		return BlockHangingVine.renderID;
	}
}
