package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.PrecAssRecipes;

public class PrecAssRecipeHandler extends NEIGenericRecipeHandler {

	public PrecAssRecipeHandler() {
		super(ModBlocks.machine_precass.getLocalizedName(), PrecAssRecipes.INSTANCE, ModBlocks.machine_precass);
	}

	@Override public String getRecipeID() { return "ntmPrecAss"; }
}
