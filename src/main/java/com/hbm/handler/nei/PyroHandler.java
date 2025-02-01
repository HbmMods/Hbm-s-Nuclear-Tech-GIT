package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIPyroOven;
import com.hbm.inventory.recipes.PyroOvenRecipes;

public class PyroHandler extends NEIUniversalHandler {

	public PyroHandler() {
		super(ModBlocks.machine_pyrooven.getLocalizedName(), ModBlocks.machine_pyrooven, PyroOvenRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmPyrolysis";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(50, 35, 28, 14), "ntmPyrolysis"));
		guiGui.add(GUIPyroOven.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
