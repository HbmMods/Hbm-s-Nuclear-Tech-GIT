package com.hbm.render.block;

import com.hbm.blocks.generic.BlockReeds;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderReeds implements ISimpleBlockRenderingHandler {

	@Override public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		Tessellator tessellator = Tessellator.instance;
		int colorMult = block.colorMultiplier(world, x, y, z);
		float r = (float) (colorMult >> 16 & 255) / 255.0F;
		float g = (float) (colorMult >> 8 & 255) / 255.0F;
		float b = (float) (colorMult & 255) / 255.0F;

		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		tessellator.setBrightness(brightness);
		
		int depth = 0;
		for(int i = 1; i < 4; i++) {
			Block water = world.getBlock(x, y - i, z);
			depth = i;
			if(water != Blocks.water && water != Blocks.flowing_water) break;
		}
		
		BlockReeds reeds = (BlockReeds) block;
		
		for(int i = 0; i < depth; i++) {
			IIcon icon = reeds.getIcon(i == 0 ? 0 : i == depth - 1 ? 2 : 1);
			renderer.drawCrossedSquares(icon, x, y, z, 1.0F);
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockReeds.renderID;
	}
}
