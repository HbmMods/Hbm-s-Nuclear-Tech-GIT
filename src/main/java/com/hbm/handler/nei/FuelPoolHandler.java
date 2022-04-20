package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FuelPoolRecipes;

public class FuelPoolHandler extends NEIUniversalHandler {

	public FuelPoolHandler() {
		super("Spent Fuel Pool Drum", ModBlocks.machine_waste_drum, FuelPoolRecipes.recipes);
	}

	@Override
	public String getKey() {
		return "ntmSpentDrum";
	}
}
