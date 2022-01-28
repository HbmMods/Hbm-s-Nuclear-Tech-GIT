package com.hbm.render.block;

import com.hbm.blocks.generic.BlockChain;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderChain implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon = block.getIcon(world, x, y, z, 0);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double d0 = (double) iicon.getMinU();
		double d1 = (double) iicon.getMinV();
		double d2 = (double) iicon.getMaxU();
		double d3 = (double) iicon.getMaxV();
		int l = world.getBlockMetadata(x, y, z);
		double scale = 0.0D;
		double wallOffset = 0.05D;

		if(l == 0) {

			double minU = (double) iicon.getMinU();
			double minV = (double) iicon.getMinV();
			double maxU = (double) iicon.getMaxU();
			double maxV = (double) iicon.getMaxV();
			double minX = x;
			double maxX = x + 1;
			double minY = y;
			double ySize = 1;
			double minZ = z + 0;
			double maxZ = z + 1;
			tessellator.addVertexWithUV(minX, minY + (double) ySize, minZ, minU, minV);
			tessellator.addVertexWithUV(minX, minY + 0.0D, minZ, minU, maxV);
			tessellator.addVertexWithUV(maxX, minY + 0.0D, maxZ, maxU, maxV);
			tessellator.addVertexWithUV(maxX, minY + (double) ySize, maxZ, maxU, minV);
			tessellator.addVertexWithUV(maxX, minY + (double) ySize, maxZ, minU, minV);
			tessellator.addVertexWithUV(maxX, minY + 0.0D, maxZ, minU, maxV);
			tessellator.addVertexWithUV(minX, minY + 0.0D, minZ, maxU, maxV);
			tessellator.addVertexWithUV(minX, minY + (double) ySize, minZ, maxU, minV);
			tessellator.addVertexWithUV(minX, minY + (double) ySize, maxZ, minU, minV);
			tessellator.addVertexWithUV(minX, minY + 0.0D, maxZ, minU, maxV);
			tessellator.addVertexWithUV(maxX, minY + 0.0D, minZ, maxU, maxV);
			tessellator.addVertexWithUV(maxX, minY + (double) ySize, minZ, maxU, minV);
			tessellator.addVertexWithUV(maxX, minY + (double) ySize, minZ, minU, minV);
			tessellator.addVertexWithUV(maxX, minY + 0.0D, minZ, minU, maxV);
			tessellator.addVertexWithUV(minX, minY + 0.0D, maxZ, maxU, maxV);
			tessellator.addVertexWithUV(minX, minY + (double) ySize, maxZ, maxU, minV);
		}

		if(l == 5) {
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 1) + scale, (double) (z + 1) + scale, d0, d1);
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 0) - scale, (double) (z + 1) + scale, d0, d3);
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 0) - scale, (double) (z + 0) - scale, d2, d3);
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 1) + scale, (double) (z + 0) - scale, d2, d1);

			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 0) - scale, (double) (z + 1) + scale, d0, d3);
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 1) + scale, (double) (z + 1) + scale, d0, d1);
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 1) + scale, (double) (z + 0) - scale, d2, d1);
			tessellator.addVertexWithUV((double) x + wallOffset, (double) (y + 0) - scale, (double) (z + 0) - scale, d2, d3);
		}

		if(l == 4) {
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 0) - scale, (double) (z + 1) + scale, d2, d3);
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 1) + scale, (double) (z + 1) + scale, d2, d1);
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 1) + scale, (double) (z + 0) - scale, d0, d1);
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 0) - scale, (double) (z + 0) - scale, d0, d3);

			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 1) + scale, (double) (z + 1) + scale, d2, d1);
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 0) - scale, (double) (z + 1) + scale, d2, d3);
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 0) - scale, (double) (z + 0) - scale, d0, d3);
			tessellator.addVertexWithUV((double) (x + 1) - wallOffset, (double) (y + 1) + scale, (double) (z + 0) - scale, d0, d1);
		}

		if(l == 3) {
			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 0) - scale, (double) z + wallOffset, d2, d3);
			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 1) + scale, (double) z + wallOffset, d2, d1);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 1) + scale, (double) z + wallOffset, d0, d1);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 0) - scale, (double) z + wallOffset, d0, d3);

			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 1) + scale, (double) z + wallOffset, d2, d1);
			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 0) - scale, (double) z + wallOffset, d2, d3);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 0) - scale, (double) z + wallOffset, d0, d3);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 1) + scale, (double) z + wallOffset, d0, d1);
		}

		if(l == 2) {
			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 1) + scale, (double) (z + 1) - wallOffset, d0, d1);
			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 0) - scale, (double) (z + 1) - wallOffset, d0, d3);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 0) - scale, (double) (z + 1) - wallOffset, d2, d3);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 1) + scale, (double) (z + 1) - wallOffset, d2, d1);

			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 0) - scale, (double) (z + 1) - wallOffset, d0, d3);
			tessellator.addVertexWithUV((double) (x + 1) + scale, (double) (y + 1) + scale, (double) (z + 1) - wallOffset, d0, d1);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 1) + scale, (double) (z + 1) - wallOffset, d2, d1);
			tessellator.addVertexWithUV((double) (x + 0) - scale, (double) (y + 0) - scale, (double) (z + 1) - wallOffset, d2, d3);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockChain.renderID;
	}
}
