package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;

import net.minecraft.item.ItemStack;

public class ElectrolyserMetalRecipes extends SerializableRecipe {
	
	public static HashMap<AStack, ElectrolysisMetalRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		
	}
	
	public static ElectrolysisMetalRecipe getRecipe(ItemStack stack) {
		return null;
	}

	@Override
	public String getFileName() {
		return "hbmElectrolyzerMetal.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		
	}
	
	public static class ElectrolysisMetalRecipe {
		
		public MaterialStack output1;
		public MaterialStack output2;
		public ItemStack[] byproducts;
	}
}
