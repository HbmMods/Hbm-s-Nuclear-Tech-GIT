package com.hbm.render.block;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderFence implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		BlockFence fence = (BlockFence) ModBlocks.fence_metal;
		
        float f = 0.375F;
        float f1 = 0.625F;
        renderer.setRenderBounds((double)f, 0.0D, (double)f, (double)f1, 1.0D, (double)f1);
        renderer.renderStandardBlock(fence, x, y, z);
        boolean flag1 = false;
        boolean flag2 = false;

        if (fence.canConnectFenceTo(world, x - 1, y, z) || fence.canConnectFenceTo(world, x + 1, y, z))
        {
            flag1 = true;
        }

        if (fence.canConnectFenceTo(world, x, y, z - 1) || fence.canConnectFenceTo(world, x, y, z + 1))
        {
            flag2 = true;
        }

        boolean flag3 = fence.canConnectFenceTo(world, x - 1, y, z);
        boolean flag4 = fence.canConnectFenceTo(world, x + 1, y, z);
        boolean flag5 = fence.canConnectFenceTo(world, x, y, z - 1);
        boolean flag6 = fence.canConnectFenceTo(world, x, y, z + 1);

        if (!flag1 && !flag2)
        {
            flag1 = true;
        }

        f = 0.4375F;
        f1 = 0.5625F;
        float f4 = flag3 ? 0.0F : f;
        float f5 = flag4 ? 1.0F : f1;
        float f6 = flag5 ? 0.0F : f;
        float f7 = flag6 ? 1.0F : f1;
        renderer.field_152631_f = true;

        if (flag1)
        {
        	renderer.setRenderBounds((double)f4, (double)0, (double)0.5, (double)f5, (double)1, (double)0.5);
        	renderer.renderStandardBlock(fence, x, y, z);
        }

        if (flag2)
        {
        	renderer.setRenderBounds((double)0.5, (double)0, (double)f6, (double)0.5, (double)1, (double)f7);
        	renderer.renderStandardBlock(fence, x, y, z);
        }

        renderer.field_152631_f = false;
        fence.setBlockBoundsBasedOnState(world, x, y, z);
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return 334082;
	}

}
