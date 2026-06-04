package com.hbm.inventory.recipes;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class BlastFurnaceRecipe extends GenericRecipe {

	public BlastFurnaceRecipe(String name) {
		super(name);
	}
	
	@Override
	public void printNEIExtras() {

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		String duration = BobMathUtil.getShortNumber(this.duration) + " ticks";
		
		int side = 164;
		fontRenderer.drawString(duration, side - fontRenderer.getStringWidth(duration), 45, 0x404040);
	}
}
