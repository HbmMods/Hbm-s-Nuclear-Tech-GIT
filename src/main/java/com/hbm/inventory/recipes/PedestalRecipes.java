package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class PedestalRecipes extends SerializableRecipe {
	
	public static List<PedestalRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {

		recipes.add(new PedestalRecipe(new ItemStack(ModItems.gun_light_revolver_dani),
				null,	null,												null,
				null,	new ComparableStack(ModItems.gun_light_revolver),	null,
				null,	null,												null));
	}

	@Override
	public String getFileName() {
		return "hbmPedestal.json";
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
		JsonObject obj = (JsonObject) recipe;
		
		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		JsonArray inputArray = obj.get("input").getAsJsonArray();
		AStack[] input = new AStack[9];
		
		for(int i = 0; i < 9; i++) {
			JsonElement element = inputArray.get(i);
			if(element.isJsonNull()) {
				input[i] = null;
			} else {
				input[i] = this.readAStack(element.getAsJsonArray());
			}
		}
		
		this.recipes.add(new PedestalRecipe(output, input));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		PedestalRecipe rec = (PedestalRecipe) recipe;
		
		writer.name("output");
		this.writeItemStack(rec.output, writer);
		
		writer.name("input").beginArray();
		for(int i = 0; i < rec.input.length; i++) {
			if(rec.input[i] == null) {
				writer.nullValue();
			} else {
				this.writeAStack(rec.input[i], writer);
			}
		}
		writer.endArray();
	}
	
	public static class PedestalRecipe {
		public ItemStack output;
		public AStack[] input;
		
		public PedestalRecipe(ItemStack output, AStack... input) {
			this.output = output;
			this.input = input;
		}
	}
}
