package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.RockMillRecipes;

public class RockMillRecipeHandler extends NEIGenericRecipeHandler {

	public RockMillRecipeHandler() {
		super(ModBlocks.machine_rockmill.getLocalizedName(), RockMillRecipes.INSTANCE, ModBlocks.machine_rockmill);
	}

	@Override public String getRecipeID() { return "ntmRockMill"; }
}
