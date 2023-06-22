package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.util.Tuple.Pair;

public class CompressorRecipes extends SerializableRecipe {
	
	public static HashMap<Pair<FluidType, Integer>, CompressorRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		
	}
	
	public static class CompressorRecipe {
		
		public FluidStack output;
		public int inputAmount;
		
		public CompressorRecipe(int input, FluidStack output) {
			this.output = output;
			this.inputAmount = input;
		}
	}

	@Override
	public String getFileName() {
		return "hbmCompressor.json";
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
}
