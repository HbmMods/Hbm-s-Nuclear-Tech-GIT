package com.hbm.inventory.recipes;

import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;

public class SuperComputerRecipes extends GenericRecipes<GenericRecipe> {
	
	public static final SuperComputerRecipes INSTANCE = new SuperComputerRecipes();

	@Override public int inputItemLimit() { return 3; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 3; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmSuperComputer.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {
		
	}
}
