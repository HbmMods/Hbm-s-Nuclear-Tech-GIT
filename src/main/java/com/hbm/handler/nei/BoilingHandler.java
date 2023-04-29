package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FluidsEditor;

public class BoilingHandler extends NEIUniversalHandler {

	public BoilingHandler() {
		super("Boiler", ModBlocks.machine_boiler, FluidsEditor.generateRecipes());
	}

	@Override
	public String getKey() {
		return "ntmBoiling";
	}
}
