package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIElectrolyserFluid;
import com.hbm.inventory.recipes.ElectrolyserFluidRecipes;

public class ElectrolyserFluidHandler extends NEIUniversalHandler {

	public ElectrolyserFluidHandler() {
		super(ModBlocks.machine_electrolyser.getLocalizedName(), ModBlocks.machine_electrolyser, ElectrolyserFluidRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmElectrolysisFluid";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(57, 15, 12, 40), "ntmElectrolysisFluid"));
		guiGui.add(GUIElectrolyserFluid.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
