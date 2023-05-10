package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineCryoDistill;
import com.hbm.inventory.gui.GUIMixer;
import com.hbm.inventory.recipes.CryoRecipes;
import com.hbm.inventory.recipes.ReformingRecipes;

import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRectHandler;

public class CryoHandler extends NEIUniversalHandler {

	public CryoHandler() {
		super("Cryogenic Distillation", ModBlocks.machine_cryo_distill, CryoRecipes.getCryoRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCryodistill";
	}
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(34, 25, 32, 22), "ntmCryodistill"));
		guiGui.add(GUIMachineCryoDistill.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}

