package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.RefineryRecipes;

public class FractioningHandler extends NEIUniversalHandler {

	public FractioningHandler() {
		super("Fractioning", ModBlocks.machine_fraction_tower, RefineryRecipes.getFractionRecipesForNEI());
	}

	@Override
	public String getKey() {
		return "ntmFractioning";
	}
}