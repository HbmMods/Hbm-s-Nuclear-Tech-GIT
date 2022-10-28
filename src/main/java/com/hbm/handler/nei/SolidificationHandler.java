package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.SolidificationRecipes;

public class SolidificationHandler extends NEIUniversalHandler {

	public SolidificationHandler() {
		super("Solidification", ModBlocks.machine_solidifier, SolidificationRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmSolidification";
	}
}
