package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;

import net.minecraft.item.ItemStack;

public class AmmoPressRecipes extends SerializableRecipe {
	
	public List<AmmoPressRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {
		
	}

	@Override
	public String getFileName() {
		return "hbmAmmoPress.json";
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
	
	public static class AmmoPressRecipe {
		public ItemStack output;
		public AStack[] input;
		
		public AmmoPressRecipe(ItemStack output, AStack... input) {
			this.output = output;
			this.input = input;
		}
	}
}
