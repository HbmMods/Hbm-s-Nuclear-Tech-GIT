package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.RefineryRecipes;

public class CrackingHandler extends NEIUniversalHandler {

	public CrackingHandler() {
		super("ntmCracking", "Cracking", ModBlocks.machine_catalytic_cracker, RefineryRecipes.getCrackingRecipesForNEI());
	}
}