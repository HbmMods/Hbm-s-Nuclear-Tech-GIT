package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AnnihilatorRecipes;

public class AnnihilatorHandler extends NEIUniversalHandler {

	public AnnihilatorHandler() {
		super("Annihilator", ModBlocks.machine_annihilator, AnnihilatorRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmAnnihilating";
	}
}
