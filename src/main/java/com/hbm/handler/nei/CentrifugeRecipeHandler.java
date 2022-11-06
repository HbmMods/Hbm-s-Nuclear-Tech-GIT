package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.recipes.CentrifugeRecipes;

public class CentrifugeRecipeHandler extends NEIUniversalHandler {

	public CentrifugeRecipeHandler() {
		super("Centrifuge", ModBlocks.machine_centrifuge, CentrifugeRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCentrifuge";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(56, 0, 80, 38), "ntmCentrifuge"));
		guiGui.add(GUIMachineCentrifuge.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
