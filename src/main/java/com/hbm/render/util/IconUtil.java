package com.hbm.render.util;

import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class IconUtil {
	
	public static ResourceLocation getTextureFromBlock(Block b) {
		return getTextureFromBlockAndSide(b, 1);
	}
	
	public static ResourceLocation getTextureFromBlockAndSide(Block b, int side) {

		
		RenderBlocks rb = RenderBlocks.getInstance();

        IIcon icon = rb.getBlockIconFromSide(b, side);
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + icon.getIconName().substring(4, icon.getIconName().length()) + ".png");
		
		return loc;
	}

}
