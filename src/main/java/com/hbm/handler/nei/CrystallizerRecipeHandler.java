package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.inventory.recipes.CrystallizerRecipes;

public class CrystallizerRecipeHandler extends NEIUniversalHandler {

	public CrystallizerRecipeHandler() {
		super("Acidizer", ModBlocks.machine_crystallizer, CrystallizerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCrystallizer";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(80 - 5, 47 - 11, 27, 12), "ntmCrystallizer"));
		guiGui.add(GUICrystallizer.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
