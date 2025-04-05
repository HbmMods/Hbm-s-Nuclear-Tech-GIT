package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FuelPoolRecipes;
import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.item.ItemStack;

public class FuelPoolHandler extends NEIUniversalHandler {

	public FuelPoolHandler() {
		super("Spent Fuel Pool Drum", ModBlocks.machine_waste_drum, FuelPoolRecipes.recipes);
	}

	@Override
	public String getKey() {
		return "ntmSpentDrum";
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if(ingredient != null && ingredient.getItem() != null && ingredient.getItem() instanceof ItemRBMKRod) {
			if(ItemRBMKRod.getCoreHeat(ingredient) < 50 && ItemRBMKRod.getHullHeat(ingredient) < 50) return;
		}

		super.loadUsageRecipes(ingredient);
	}

	@Override
	public void loadCraftingRecipes(ItemStack ingredient) {
		if(ingredient != null && ingredient.getItem() != null && ingredient.getItem() instanceof ItemRBMKRod) {
			if(ItemRBMKRod.getCoreHeat(ingredient) >= 50 || ItemRBMKRod.getHullHeat(ingredient) >= 50) return;
		}

		super.loadCraftingRecipes(ingredient);
	}
}
