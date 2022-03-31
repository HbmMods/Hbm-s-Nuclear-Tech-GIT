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
		double uv_cR = iicon.getMinU() + spanU * 5 / px;
		double uv_cT = iicon.getMaxV();
		double uv_cB = iicon.getMaxV() - spanV * 5 / px;
		
		double pos_min = px * 5.5D;
		double pos_max = px * 10.5D;
		
		//TODO: all that manual tessellator crap
		
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
