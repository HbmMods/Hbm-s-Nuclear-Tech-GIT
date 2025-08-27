package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;

public class ChemicalPlantRecipeHandler extends NEIGenericRecipeHandler {

	public ChemicalPlantRecipeHandler() {
		super(ModBlocks.machine_chemical_plant.getLocalizedName(), ChemicalPlantRecipes.INSTANCE, ModBlocks.machine_chemical_plant, ModBlocks.machine_chemical_factory);
	}

	@Override public String getRecipeID() { return "ntmChemicalPlant"; }
}
