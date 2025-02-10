package com.hbm.handler.nei;

import java.util.Locale;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ParticleAcceleratorRecipes;
import com.hbm.inventory.recipes.ParticleAcceleratorRecipes.ParticleAcceleratorRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ParticleAcceleratorHandler extends NEIUniversalHandler {

	public ParticleAcceleratorHandler() {
		super(ModBlocks.pa_detector.getLocalizedName(), ModBlocks.pa_detector, ParticleAcceleratorRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmParticleAccelerator";
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);
		
		ParticleAcceleratorRecipe paRecipe = ParticleAcceleratorRecipes.getOutput(rec.input[0].item, rec.input[1].item);
		
		if(paRecipe != null) {
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			String momentum = "Momentum: " + String.format(Locale.US, "%,d", paRecipe.momentum);
			int side = 8;
			fontRenderer.drawString(momentum, side, 52, 0x404040);
		}
	}
}
