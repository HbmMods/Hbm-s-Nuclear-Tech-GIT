package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.SuperComputerRecipes;

public class SuperComputerRecipeHandler extends NEIGenericRecipeHandler {

	public SuperComputerRecipeHandler() {
		super(ModBlocks.machine_supercomputer.getLocalizedName(), SuperComputerRecipes.INSTANCE, ModBlocks.machine_supercomputer);
	}

	@Override public String getRecipeID() { return "ntmSuperComputer"; }
}
