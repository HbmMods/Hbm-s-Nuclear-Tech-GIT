package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.RefineryRecipes;

public class FractioningHandler extends NEIUniversalHandler {

	public FractioningHandler() {
		super("ntmFractioning", "Fractioning", ModBlocks.machine_fraction_tower, RefineryRecipes.getFractionRecipesForNEI());
	}
}