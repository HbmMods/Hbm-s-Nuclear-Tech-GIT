package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.CompressorRecipes;

import net.minecraft.item.ItemStack;

public class CompressorHandler extends NEIUniversalHandler {

	public CompressorHandler() {
		super(ModBlocks.machine_compressor.getLocalizedName(), new ItemStack[] {
				new ItemStack(ModBlocks.machine_compressor),
				new ItemStack(ModBlocks.machine_compressor_compact),
			}, CompressorRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCompressor";
	}
}
