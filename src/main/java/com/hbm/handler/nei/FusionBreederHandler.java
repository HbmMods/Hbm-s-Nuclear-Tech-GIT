package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FluidBreederRecipes;

public class FusionBreederHandler extends NEIUniversalHandler {

	public FusionBreederHandler() {
		super(ModBlocks.fusion_breeder.getLocalizedName(), ModBlocks.fusion_breeder, FluidBreederRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmFluidBreeder";
	}
}
