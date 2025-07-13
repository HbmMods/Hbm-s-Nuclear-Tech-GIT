package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIRBMKOutgasser;
import com.hbm.inventory.recipes.OutgasserRecipes;

public class OutgasserHandler extends NEIUniversalHandler {

	public OutgasserHandler() {
		super(ModBlocks.rbmk_outgasser.getLocalizedName(), ModBlocks.rbmk_outgasser, OutgasserRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmOutgasser";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(75, 26, 16, 32), "ntmOutgasser"));
		guiGui.add(GUIRBMKOutgasser.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
