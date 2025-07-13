package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AmmoPressRecipes;

public class AmmoPressHandler extends NEIUniversalHandler {

	public AmmoPressHandler() {
		super(ModBlocks.machine_ammo_press.getLocalizedName(), ModBlocks.machine_ammo_press, AmmoPressRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmAmmoPress";
	}
}
