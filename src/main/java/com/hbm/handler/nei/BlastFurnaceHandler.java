package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.BlastFurnaceRecipesNT;

public class BlastFurnaceHandler extends NEIGenericRecipeHandler {

	public BlastFurnaceHandler() {
		super(ModBlocks.machine_blast_furnace.getLocalizedName(), BlastFurnaceRecipesNT.INSTANCE, ModBlocks.machine_blast_furnace);
	}

	@Override public String getRecipeID() { return "ntmBlastFurnace"; }
}
