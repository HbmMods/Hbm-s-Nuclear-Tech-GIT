package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.PlasmaForgeRecipes;

public class PlasmaForgeRecipeHandler extends AssemblyMachineRecipeHandler {

	public PlasmaForgeRecipeHandler() {
		super(ModBlocks.fusion_plasma_forge.getLocalizedName(), PlasmaForgeRecipes.INSTANCE, ModBlocks.fusion_plasma_forge);
	}

	@Override public String getRecipeID() { return "ntmPlasmaForge"; }
}
