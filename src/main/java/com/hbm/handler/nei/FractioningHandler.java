package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FractionRecipes;

public class FractioningHandler extends NEIUniversalHandler {

	public FractioningHandler() {
		super(ModBlocks.machine_fraction_tower.getLocalizedName(), ModBlocks.machine_fraction_tower, FractionRecipes.getFractionRecipesForNEI());
	}

	@Override
	public String getKey() {
		return "ntmFractioning";
	}
}
