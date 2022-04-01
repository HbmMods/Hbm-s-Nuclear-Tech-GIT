package com.hbm.render.block;

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
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

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

		boolean pX = Library.canConnect(world, x + 1, y, z, Library.NEG_X);
		boolean nX = Library.canConnect(world, x - 1, y, z, Library.POS_X);
		boolean pY = Library.canConnect(world, x, y + 1, z, Library.NEG_Y);
		boolean nY = Library.canConnect(world, x, y - 1, z, Library.POS_Y);
		boolean pZ = Library.canConnect(world, x, y, z + 1, Library.NEG_Z);
		boolean nZ = Library.canConnect(world, x, y, z - 1, Library.POS_Z);

		double spanU = iicon.getMaxU() - iicon.getMinU();
		double spanV = iicon.getMaxV() - iicon.getMinV();
		double px = 0.0625D;
		
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
		double pos_min = px * 5.5D;
		double pos_max = px * 10.5D;

		float topColor = 1.0F;
		float brightColor = 0.8F;
		float darkColor = 0.6F;
		float bottomColor = 0.5F;
		
		//TODO: all that manual tessellator crap
		
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
		}
		
		if(!nX) {
			tessellator.setColorOpaque_F(darkColor, darkColor, darkColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_cR, uv_cB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_cL, uv_cB);
		}
		
		if(!pZ) {
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_max, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_max, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_max, uv_cR, uv_cB);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_max, uv_cL, uv_cB);
		}
		
		if(!nZ) {
			tessellator.setColorOpaque_F(brightColor, brightColor, brightColor);
			tessellator.addVertexWithUV(x + pos_min, y + pos_max, z + pos_min, uv_cR, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_max, z + pos_min, uv_cL, uv_cT);
			tessellator.addVertexWithUV(x + pos_max, y + pos_min, z + pos_min, uv_cL, uv_cB);
			tessellator.addVertexWithUV(x + pos_min, y + pos_min, z + pos_min, uv_cR, uv_cB);
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockCable.renderIDClassic;
	}

}
