package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.CompressorRecipes;

public class CompressorHandler extends NEIUniversalHandler {

	public CompressorHandler() {
		super(ModBlocks.machine_compressor.getLocalizedName(), ModBlocks.machine_compressor, CompressorRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCompressor";
	}
}
