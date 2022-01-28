package com.hbm.render.block;

import com.hbm.blocks.IBlockMultiPass;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderBlockMultipass implements ISimpleBlockRenderingHandler {

	/**
	 * First I wanted to pass the current render pass in the methods for getting color and icon, but the later one would have to work with
	 * texture overrides which would either break sided textures or force me to implement renderStandardBlock myself, so that's a big nono.
	 * So I made a static variable for the current render pass so that Block.getIcon could use it while still minding sides. Great, I put it
	 * into IBlockMultiPass because that's the only logical place I could put it since I intend to use IBlockMultiPass for more rendering
	 * handlers than just this one and BOOM, primitive fields in interfaces are implicitly final. Why? Because "functionality in interfaces
	 * bad", or so the HeckOverflow people are trying to tell me. Mh-hm, holding a single static value is "functionality" now, and functionality
	 * in interfaces is very very bad in a language that allows interfaces to define a default behavior that is LITERALLY FULLY FUNCTIONAL
	 * METHODS. Statistically speaking I, the individual, should be wrong and many people who - supposedly - know what they're talking about
	 * should be right, but if you ask me there's something off about this whole ordeal. I don't know.
	 */
	public static int currentPass = 0;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z);

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		
		if(!(block instanceof IBlockMultiPass)) {
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		}
		
		IBlockMultiPass multi = (IBlockMultiPass) block;
		renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
		
		int passes = multi.getPasses();
		
		for(int i = 0; i < passes; i++) {
			currentPass = i;
			System.out.println(multi.getColorFromPass(world, x, y, z, false));
			tessellator.setColorOpaque_I(multi.getColorFromPass(world, x, y, z, false));
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		currentPass = 0;

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return IBlockMultiPass.getRenderType();
	}
}
