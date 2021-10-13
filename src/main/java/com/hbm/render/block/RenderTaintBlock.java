package com.hbm.render.block;

import com.hbm.blocks.bomb.BlockTaint;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderTaintBlock implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(0, world.getBlockMetadata(x, y, z));

        if (renderer.hasOverrideBlockTexture())
        {
            iicon = renderer.overrideBlockTexture;
        }

		boolean ceil = world.getBlock(x, y + 1, z).isNormalCube();
		boolean floor = world.getBlock(x, y - 1, z).isNormalCube();
		boolean side1 = world.getBlock(x, y, z + 1).isNormalCube();
		boolean side2 = world.getBlock(x - 1, y, z).isNormalCube();
		boolean side3 = world.getBlock(x, y, z - 1).isNormalCube();
		boolean side4 = world.getBlock(x + 1, y, z).isNormalCube();

        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float f = (float)(l >> 16 & 255) / 255.0F;
        float f1 = (float)(l >> 8 & 255) / 255.0F;
        float f2 = (float)(l & 255) / 255.0F;
        tessellator.setColorOpaque_F(f, f1, f2);
        double d3 = (double)iicon.getMinU();
        double d4 = (double)iicon.getMinV();
        double d0 = (double)iicon.getMaxU();
        double d1 = (double)iicon.getMaxV();
        double d2 = 0.05D;
        renderer.blockAccess.getBlockMetadata(x, y, z);

        if (side2)
        {
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 1), (double)(z + 1), d3, d4);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 0), (double)(z + 1), d3, d1);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 0), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 1), (double)(z + 0), d0, d4);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 1), (double)(z + 0), d0, d4);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 0), (double)(z + 0), d0, d1);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 0), (double)(z + 1), d3, d1);
            tessellator.addVertexWithUV((double)x + d2, (double)(y + 1), (double)(z + 1), d3, d4);
        }

        if (side4)
        {
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 0), (double)(z + 1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 1), (double)(z + 1), d0, d4);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 1), (double)(z + 0), d3, d4);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 0), (double)(z + 0), d3, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 0), (double)(z + 0), d3, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 1), (double)(z + 0), d3, d4);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 1), (double)(z + 1), d0, d4);
            tessellator.addVertexWithUV((double)(x + 1) - d2, (double)(y + 0), (double)(z + 1), d0, d1);
        }

        if (side3)
        {
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), (double)z + d2, d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1), (double)z + d2, d0, d4);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1), (double)z + d2, d3, d4);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)z + d2, d3, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)z + d2, d3, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1), (double)z + d2, d3, d4);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1), (double)z + d2, d0, d4);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), (double)z + d2, d0, d1);
        }

        if (side1)
        {
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1), (double)(z + 1) - d2, d3, d4);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), (double)(z + 1) - d2, d3, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)(z + 1) - d2, d0, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1), (double)(z + 1) - d2, d0, d4);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1), (double)(z + 1) - d2, d0, d4);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)(z + 1) - d2, d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0), (double)(z + 1) - d2, d3, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1), (double)(z + 1) - d2, d3, d4);
        }

        if (ceil)
        {
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1) - d2, (double)(z + 0), d3, d4);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 1) - d2, (double)(z + 1), d3, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1) - d2, (double)(z + 1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 1) - d2, (double)(z + 0), d0, d4);
        }

        if (floor)
        {
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0) + d2, (double)(z + 0), d0, d4);
            tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0) + d2, (double)(z + 1), d0, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0) + d2, (double)(z + 1), d3, d1);
            tessellator.addVertexWithUV((double)(x + 1), (double)(y + 0) + d2, (double)(z + 0), d3, d4);
        }
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockTaint.renderID;
	}

}
