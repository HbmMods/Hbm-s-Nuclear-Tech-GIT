package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIElectrolyserMetal;
import com.hbm.inventory.recipes.ElectrolyserMetalRecipes;

public class ElectrolyserMetalHandler extends NEIUniversalHandler {

	public ElectrolyserMetalHandler() {
		super(ModBlocks.machine_electrolyser.getLocalizedName(), ModBlocks.machine_electrolyser, ElectrolyserMetalRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmElectrolysisMetal";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(2, 35, 22, 25), "ntmElectrolysisMetal"));
		guiGui.add(GUIElectrolyserMetal.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
