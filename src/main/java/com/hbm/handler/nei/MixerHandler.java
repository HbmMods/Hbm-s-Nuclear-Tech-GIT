package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMixer;
import com.hbm.inventory.recipes.MixerRecipes;

public class MixerHandler extends NEIUniversalHandler {

	public MixerHandler() {
		super(ModBlocks.machine_mixer.getLocalizedName(), ModBlocks.machine_mixer, MixerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmMixer";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(57, 25, 52, 44), "ntmMixer"));
		guiGui.add(GUIMixer.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
