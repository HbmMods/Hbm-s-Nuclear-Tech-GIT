package com.hbm.inventory.recipes;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class PUREXRecipe extends GenericRecipe {

	public PUREXRecipe(String name) {
		super(name);
	}
	
	@Override
	public void printNEIExtras() {

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		String line = BobMathUtil.getShortNumber(this.duration) + " ticks   " + BobMathUtil.getShortNumber(this.power) + "HE/t";

		int side = 164;
		fontRenderer.drawString(line, side - fontRenderer.getStringWidth(line), 57, 0x306030); // why so geen
	}
}
