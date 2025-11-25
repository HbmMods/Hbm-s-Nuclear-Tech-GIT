package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FusionRecipes;

public class FusionRecipeHandler extends NEIGenericRecipeHandler {

	public FusionRecipeHandler() {
		super(ModBlocks.fusion_torus.getLocalizedName(), FusionRecipes.INSTANCE, ModBlocks.fusion_torus);
	}

	@Override public String getRecipeID() { return "ntmFusion"; }
}
