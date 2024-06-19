package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ArcFurnaceRecipes;

public class ArcFurnaceFluidHandler extends NEIUniversalHandler {

	public ArcFurnaceFluidHandler() {
		super("Arc Furnace (Fluid)", ModBlocks.machine_arc_furnace, ArcFurnaceRecipes.getFluidRecipes());
	}

	@Override
	public String getKey() {
		return "ntmArcFurnaceFluid";
	}
}
