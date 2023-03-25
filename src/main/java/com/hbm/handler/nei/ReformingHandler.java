package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.ReformingRecipes;

public class ReformingHandler extends NEIUniversalHandler {

	public ReformingHandler() {
		super("Reforming", ModBlocks.machine_catalytic_reformer, ReformingRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmReforming";
	}
}
