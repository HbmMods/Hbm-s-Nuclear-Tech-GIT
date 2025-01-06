package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.util.Tuple.Pair;

public class CompressorRecipes extends SerializableRecipe {
	
	public static HashMap<Pair<FluidType, Integer>, CompressorRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(new Pair(Fluids.STEAM, 0), new CompressorRecipe(1_000, new FluidStack(Fluids.HOTSTEAM, 100)));
		recipes.put(new Pair(Fluids.HOTSTEAM, 0), new CompressorRecipe(1_000, new FluidStack(Fluids.SUPERHOTSTEAM, 100)));
		recipes.put(new Pair(Fluids.SUPERHOTSTEAM, 0), new CompressorRecipe(1_000, new FluidStack(Fluids.ULTRAHOTSTEAM, 100)));

		recipes.put(new Pair(Fluids.PETROLEUM, 0), new CompressorRecipe(2_000, new FluidStack(Fluids.PETROLEUM, 2_000, 1), 20));
		recipes.put(new Pair(Fluids.PETROLEUM, 1), new CompressorRecipe(2_000, new FluidStack(Fluids.LPG, 1_000, 0), 20));

		recipes.put(new Pair(Fluids.BLOOD, 3), new CompressorRecipe(1_000, new FluidStack(Fluids.HEAVYOIL, 250, 0), 200));
	}
	
	public static class CompressorRecipe {
		
		public FluidStack output;
		public int inputAmount;
		public int duration;
		
		public CompressorRecipe(int input, FluidStack output, int duration) {
			this.output = output;
			this.inputAmount = input;
			this.duration = duration;
		}
		
		public CompressorRecipe(int input, FluidStack output) {
			this(input, output, 100);
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
		JsonObject obj = recipe.getAsJsonObject();

		FluidStack input = this.readFluidStack(obj.get("input").getAsJsonArray());
		FluidStack output = this.readFluidStack(obj.get("output").getAsJsonArray());
		
		recipes.put(new Pair(input.type, input.pressure), new CompressorRecipe(input.fill, output));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Pair<FluidType, Integer>, CompressorRecipe> entry = (Entry) recipe;
		
		writer.name("input");
		this.writeFluidStack(new FluidStack(entry.getKey().getKey(), entry.getValue().inputAmount, entry.getKey().getValue()), writer);
		writer.name("output");
		this.writeFluidStack(entry.getValue().output, writer);
	}
}
