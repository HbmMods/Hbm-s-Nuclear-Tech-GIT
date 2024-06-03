package com.hbm.render.block;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMetalFence;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderFence implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		BlockMetalFence fence = (BlockMetalFence) ModBlocks.fence_metal;

		boolean xNeg = fence.canConnectFenceTo(world, x - 1, y, z);
		boolean xPos = fence.canConnectFenceTo(world, x + 1, y, z);
		boolean zNeg = fence.canConnectFenceTo(world, x, y, z - 1);
		boolean zPos = fence.canConnectFenceTo(world, x, y, z + 1);

		boolean flag1 = xNeg || xPos;
		boolean flag2 = zNeg || zPos;
		
		boolean hidePost = (xNeg && xPos) || (zNeg && zPos);

		if (!flag1 && !flag2) {
			flag1 = true;
		}

		float f = 0.4375F;
		float f1 = 0.5625F;
		float f4 = xNeg ? 0.0F : f;
		float f5 = xPos ? 1.0F : f1;
		float f6 = zNeg ? 0.0F : f;
		float f7 = zPos ? 1.0F : f1;
		renderer.field_152631_f = true;

		if (flag1) {
			renderer.setRenderBounds((double)f4, (double)0, (double)0.5, (double)f5, (double)1, (double)0.5);
			renderer.renderStandardBlock(fence, x, y, z);
		}

		if (flag2) {
			renderer.setRenderBounds((double)0.5, (double)0, (double)f6, (double)0.5, (double)1, (double)f7);
			renderer.renderStandardBlock(fence, x, y, z);
		}

		if(!hidePost) {
			f = 0.375F;
			f1 = 0.625F;
			renderer.setOverrideBlockTexture(fence.postIcon);
			renderer.setRenderBounds((double)f, 0.0D, (double)f, (double)f1, 1.0D, (double)f1);
			renderer.renderStandardBlock(fence, x, y, z);
			renderer.clearOverrideBlockTexture();
		}

		renderer.field_152631_f = false;
		fence.setBlockBoundsBasedOnState(world, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockMetalFence.renderID;
	}

}
