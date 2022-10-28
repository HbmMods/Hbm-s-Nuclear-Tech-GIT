package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.LiquefactionRecipes;

public class LiquefactionHandler extends NEIUniversalHandler {

	public LiquefactionHandler() {
		super("Liquefaction", ModBlocks.machine_liquefactor, LiquefactionRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmLiquefaction";
	}
}
