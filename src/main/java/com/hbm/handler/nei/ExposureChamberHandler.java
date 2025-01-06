package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineExposureChamber;
import com.hbm.inventory.recipes.ExposureChamberRecipes;

public class ExposureChamberHandler extends NEIUniversalHandler {

	public ExposureChamberHandler() {
		super(ModBlocks.machine_exposure_chamber.getLocalizedName(), ModBlocks.machine_exposure_chamber, ExposureChamberRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmExposure";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(31, 28, 41, 10), "ntmExposure"));
		guiGui.add(GUIMachineExposureChamber.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
