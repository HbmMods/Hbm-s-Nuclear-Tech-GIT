package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.PUREXRecipes;

public class PUREXRecipeHandler extends NEIGenericRecipeHandler {

	public PUREXRecipeHandler() {
		super(ModBlocks.machine_purex.getLocalizedName(), PUREXRecipes.INSTANCE, ModBlocks.machine_purex);
	}

	@Override public String getRecipeID() { return "ntmPUREX"; }
}
