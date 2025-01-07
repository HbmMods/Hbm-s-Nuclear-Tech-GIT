package com.hbm.render.block;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.generic.BlockHangingVine;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderHangingVine implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		//TextureUtil.func_152777_a(false, false, 1.0F);

		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(30.0F, -1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(-1.0F, -0.5F, -1.0F);
		
		/*GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(1.0F, 1.0F, -1.0F);
		GL11.glTranslatef(1.0F, 0.5F, 1.0F);
		GL11.glScalef(10.0F, 10.0F, 10.0F);*/
		

		IIcon iicon0 = ((BlockHangingVine) block).iconHang;
		IIcon iicon1 = ((BlockHangingVine) block).iconHangGlow;
		renderStandard2D(iicon0);
		renderStandard2D(iicon1);
		
	}
	
	public void renderStandard2D(IIcon iicon) {
		Tessellator tessellator = Tessellator.instance;
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		float f4 = 0.0F;
		float f5 = 0.3F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		//GL11.glTranslatef(-f4, -f5, 0.0F);
		float f6 = 1.5F;
		//GL11.glScalef(f6, f6, f6);
		//GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		//GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		//ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)f1, (double)f3);
		tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)f, (double)f3);
		tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)f, (double)f2);
		tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)f1, (double)f2);
		tessellator.draw();
	}
	
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
