package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.inventory.recipes.CokerRecipes;

public class CokingHandler extends NEIUniversalHandler {

	public CokingHandler() {
		super(ModBlocks.machine_coker.getLocalizedName(), ModBlocks.machine_coker, CokerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCoking";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(55, 15, 36, 18), "ntmCoking"));
		guiGui.add(GUIMachineCoker.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
