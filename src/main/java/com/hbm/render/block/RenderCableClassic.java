package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.network.BlockCable;
import com.hbm.lib.Library;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderCableClassic implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		GL11.glScaled(1.25D, 1.25D, 1.25D);
		GL11.glTranslated(-0.5, -0.5, -0.5);
		IIcon iicon = block.getIcon(0, 0);
		
		double uv_cL = iicon.getMinU();
		double uv_cR = iicon.getInterpolatedU(5);
		double uv_cT = iicon.getMinV();
		double uv_cB = iicon.getInterpolatedV(5);
		
		double uv_sL = iicon.getInterpolatedU(5);
		double uv_sR = iicon.getInterpolatedU(10);
		double uv_sT = iicon.getMinV();
		double uv_sB = iicon.getInterpolatedV(5);
		
		double pos_nil = 0D;
		double pos_one = 1D;
		double pos_min = 0.34375D;
		double pos_max = 0.65625D;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(1F, 1F, 1F);
		tessellator.setNormal(0F, 1F, 0F);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_min, uv_cR, uv_cT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_min, uv_cL, uv_cT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_max, uv_cL, uv_cB);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_max, uv_cR, uv_cB);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_max, pos_max, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_max, pos_min, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_max, pos_min, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_max, pos_max, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_one, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_one, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_nil, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_nil, uv_sR, uv_sT);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, -1F, 0F);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_min, uv_cL, uv_cT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_min, uv_cR, uv_cT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_max, uv_cR, uv_cB);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_max, uv_cL, uv_cB);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_min, pos_min, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_min, pos_max, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_min, pos_max, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_min, pos_min, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_one, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_one, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_nil, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_nil, uv_sR, uv_sT);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1F, 0F, 0F);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_one, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_one, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_nil, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_nil, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_one, pos_max, pos_min, uv_cR, uv_cT);
		tessellator.addVertexWithUV(pos_one, pos_max, pos_max, uv_cL, uv_cT);
		tessellator.addVertexWithUV(pos_one, pos_min, pos_max, uv_cL, uv_cB);
		tessellator.addVertexWithUV(pos_one, pos_min, pos_min, uv_cR, uv_cB);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0F, 0F);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_one, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_one, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_nil, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_nil, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_nil, pos_max, pos_max, uv_cR, uv_cT);
		tessellator.addVertexWithUV(pos_nil, pos_max, pos_min, uv_cL, uv_cT);
		tessellator.addVertexWithUV(pos_nil, pos_min, pos_min, uv_cL, uv_cB);
		tessellator.addVertexWithUV(pos_nil, pos_min, pos_max, uv_cR, uv_cB);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 0F, 1F);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_min, pos_max, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_max, pos_max, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_max, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_max, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_max, pos_max, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_min, pos_max, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_one, uv_cR, uv_cT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_one, uv_cL, uv_cT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_one, uv_cL, uv_cB);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_one, uv_cR, uv_cB);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0F, 0F, -1F);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_max, pos_min, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_one, pos_min, pos_min, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_min, uv_sL, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_min, uv_sL, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_min, pos_min, uv_sR, uv_sB);
		tessellator.addVertexWithUV(pos_nil, pos_max, pos_min, uv_sR, uv_sT);
		tessellator.addVertexWithUV(pos_min, pos_max, pos_nil, uv_cR, uv_cT);
		tessellator.addVertexWithUV(pos_max, pos_max, pos_nil, uv_cL, uv_cT);
		tessellator.addVertexWithUV(pos_max, pos_min, pos_nil, uv_cL, uv_cB);
		tessellator.addVertexWithUV(pos_min, pos_min, pos_nil, uv_cR, uv_cB);
		tessellator.draw();
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(0, 0);
		tessellator.setColorOpaque_F(1, 1, 1);

		if(renderer.hasOverrideBlockTexture()) {
			iicon = renderer.overrideBlockTexture;
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		boolean pX = Library.canConnect(world, x + 1, y, z, Library.POS_X);
		boolean nX = Library.canConnect(world, x - 1, y, z, Library.NEG_X);
		boolean pY = Library.canConnect(world, x, y + 1, z, Library.POS_Y);
		boolean nY = Library.canConnect(world, x, y - 1, z, Library.NEG_Y);
		boolean pZ = Library.canConnect(world, x, y, z + 1, Library.POS_Z);
		boolean nZ = Library.canConnect(world, x, y, z - 1, Library.NEG_Z);

		double uv_cL = iicon.getMinU();
		double uv_cR = iicon.getInterpolatedU(5);
		double uv_cT = iicon.getMinV();
		double uv_cB = iicon.getInterpolatedV(5);
		
		double uv_sL = iicon.getInterpolatedU(5);
		double uv_sR = iicon.getInterpolatedU(10);
		double uv_sT = iicon.getMinV();
		double uv_sB = iicon.getInterpolatedV(5);
		
		double pos_nil = 0D;
		double pos_one = 1D;
		double pos_min = 0.34375D;
		double pos_max = 0.65625D;

		float topColor = 1.0F;
		float brightColor = 0.8F;
		float darkColor = 0.6F;
		float bottomColor = 0.5F;
		
		//this is a lot less tedious than it looks when you draw a 3D cube to take the vertex positions from
		if(!pY) {
			tessellator.setColorOpaque_F(topColor, topColor, topColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_cL, uv_cB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_cR, uv_cB);
		} else {
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_one, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_one, z + pos_min, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_one, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_one, z + pos_max, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_one, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_one, z + pos_max, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_one, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_one, z + pos_min, uv_sR, uv_sT);
		}
		
		if(!nY) {
			tessellator.setColorOpaque_F(bottomColor, bottomColor, bottomColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_cR, uv_cB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_cL, uv_cB);
		} else {
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_nil, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_nil, z + pos_min, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_nil, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_nil, z + pos_min, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_nil, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_nil, z + pos_max, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_nil, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_nil, z + pos_max, uv_sR, uv_sT);
		}
		
		if(!pX) {
			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_cL, uv_cB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_cR, uv_cB);
		} else {
			tessellator.setColorOpaque_F(topColor, topColor, topColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_max, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_max, z + pos_min, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_max, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_min, z + pos_min, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(bottomColor, bottomColor, bottomColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_min, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_min, z + pos_max, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_min, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_one, y + pos_max, z + pos_max, uv_sR, uv_sT);
		}
		
		if(!nX) {
			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_cL, uv_cB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_cR, uv_cB);
		} else {
			tessellator.setColorOpaque_F(topColor, topColor, topColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_max, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_max, z + pos_max, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_min, z + pos_min, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_max, z + pos_min, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(bottomColor, bottomColor, bottomColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_min, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_min, z + pos_min, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_max, z + pos_max, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_nil, y + pos_min, z + pos_max, uv_sR, uv_sT);
		}
		
		if(!pZ) {
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_cL, uv_cB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_cR, uv_cB);
		} else {
			tessellator.setColorOpaque_F(topColor, topColor, topColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_one, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_one, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_one, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_one, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(bottomColor, bottomColor, bottomColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_one, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_one, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_one, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_one, uv_sR, uv_sT);
		}
		
		if(!nZ) {
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_cL, uv_cB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_cR, uv_cB);
		} else {
			tessellator.setColorOpaque_F(topColor, topColor, topColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_nil, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_nil, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_nil, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_nil, uv_sR, uv_sT);
			
			tessellator.setColorOpaque_F(bottomColor, bottomColor, bottomColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_nil, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_nil, uv_sR, uv_sT);

			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_sL, uv_sT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_sL, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_nil, uv_sR, uv_sB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_nil, uv_sR, uv_sT);
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockCable.renderIDClassic;
	}

}
