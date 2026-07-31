package com.hbm.inventory.recipes;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;

public class RockMillRecipes extends GenericRecipes<GenericRecipe> {

	public static final RockMillRecipes INSTANCE = new RockMillRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 3; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmRockMill.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		// TBI
	}
}
