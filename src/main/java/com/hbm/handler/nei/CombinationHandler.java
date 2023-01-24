package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.CombinationRecipes;

public class CombinationHandler extends NEIUniversalHandler {

	public CombinationHandler() {
		super("Combination Furnace", ModBlocks.furnace_combination, CombinationRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCombination";
	}
}
