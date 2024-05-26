package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ArcFurnaceRecipes;

public class ArcFurnaceSolidHandler extends NEIUniversalHandler {

	public ArcFurnaceSolidHandler() {
		super("Arc Furnace (Solid)", ModBlocks.machine_arc_furnace, ArcFurnaceRecipes.getSolidRecipes());
	}

	@Override
	public String getKey() {
		return "ntmArcFurnaceSolid";
	}
}
