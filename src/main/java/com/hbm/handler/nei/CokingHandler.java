package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.CokerRecipes;

public class CokingHandler extends NEIUniversalHandler {

	public CokingHandler() {
		super("Coking", ModBlocks.machine_coker, CokerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCoking";
	}
}
