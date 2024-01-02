package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.rail.IRenderBlock;
import com.hbm.blocks.rail.RailStandardStraight;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderRail implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		IRenderBlock rail = (IRenderBlock) block;
		rail.renderInventory(tessellator, block, metadata);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);
		int meta = world.getBlockMetadata(x, y, z);
		IRenderBlock rail = (IRenderBlock) block;
		rail.renderWorld(tessellator, block, meta, world, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RailStandardStraight.renderID;
	}
}
