package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.RefineryRecipes;

public class VacuumRecipeHandler extends NEIUniversalHandler {

	public VacuumRecipeHandler() {
		super("Vacuum Refinery", ModBlocks.machine_vacuum_distill, RefineryRecipes.getVacuumRecipe());
	}

	@Override
	public String getKey() {
		return "ntmVacuum";
	}
}
