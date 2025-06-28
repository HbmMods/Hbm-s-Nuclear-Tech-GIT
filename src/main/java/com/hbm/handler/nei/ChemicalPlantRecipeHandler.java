package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;

public class ChemicalPlantRecipeHandler extends NEIUniversalHandler { //TODO: make a new universal handler

	public ChemicalPlantRecipeHandler() {
		super(ModBlocks.machine_chemical_plant.getLocalizedName(), ModBlocks.machine_chemical_plant, ChemicalPlantRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmChemicalPlant";
	}
}
