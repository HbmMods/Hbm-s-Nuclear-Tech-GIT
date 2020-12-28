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
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(world, x, y, z, 0);

        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        double d0 = (double)iicon.getMinU();
        double d1 = (double)iicon.getMinV();
        double d2 = (double)iicon.getMaxU();
        double d3 = (double)iicon.getMaxV();
        int l = world.getBlockMetadata(x, y, z);
        double d4 = 0.0D;
        double d5 = 0.05D;
        
        if(l == 0) {

            double minU = (double)iicon.getMinU();
            double minV = (double)iicon.getMinV();
            double maxU = (double)iicon.getMaxU();
            double maxV = (double)iicon.getMaxV();
        	double d8 = x;
        	double d9 = x + 1;
        	double p_147765_4_ = y;
        	double p_147765_8_ = 1;
        	double d10 = z + 0;
        	double d11 = z + 1;
            tessellator.addVertexWithUV(d8, p_147765_4_ + (double)p_147765_8_, d10, minU, minV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0D, d10, minU, maxV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0D, d11, maxU, maxV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + (double)p_147765_8_, d11, maxU, minV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + (double)p_147765_8_, d11, minU, minV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0D, d11, minU, maxV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0D, d10, maxU, maxV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + (double)p_147765_8_, d10, maxU, minV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + (double)p_147765_8_, d11, minU, minV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0D, d11, minU, maxV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0D, d10, maxU, maxV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + (double)p_147765_8_, d10, maxU, minV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + (double)p_147765_8_, d10, minU, minV);
            tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0D, d10, minU, maxV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0D, d11, maxU, maxV);
            tessellator.addVertexWithUV(d8, p_147765_4_ + (double)p_147765_8_, d11, maxU, minV);
        }

        if (l == 5)
        {
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d0, d1);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d0, d3);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d2, d3);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d2, d1);

            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d0, d3);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d0, d1);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d2, d1);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d2, d3);
        }

        if (l == 4)
        {
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d2, d3);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d2, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d0, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d0, d3);
            
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d2, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d2, d3);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d0, d3);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d0, d1);
        }

        if (l == 3)
        {
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)z + d5, d2, d3);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)z + d5, d2, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)z + d5, d0, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)z + d5, d0, d3);
            
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)z + d5, d2, d1);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)z + d5, d2, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)z + d5, d0, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)z + d5, d0, d1);
        }

        if (l == 2)
        {
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d0, d1);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d0, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d2, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d2, d1);

            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d0, d3);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d0, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d2, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d2, d3);
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
