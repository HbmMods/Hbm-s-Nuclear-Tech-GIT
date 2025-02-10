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
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class CompressorRecipes extends SerializableRecipe {
	
	public static HashMap<Pair<FluidType, Integer>, CompressorRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(new Pair(Fluids.PETROLEUM, 0), new CompressorRecipe(2_000, new FluidStack(Fluids.PETROLEUM, 2_000, 1), 20));
		recipes.put(new Pair(Fluids.PETROLEUM, 1), new CompressorRecipe(2_000, new FluidStack(Fluids.LPG, 1_000, 0), 20));

		recipes.put(new Pair(Fluids.BLOOD, 3), new CompressorRecipe(1_000, new FluidStack(Fluids.HEAVYOIL, 250, 0), 200));

		recipes.put(new Pair(Fluids.PERFLUOROMETHYL, 0), new CompressorRecipe(1_000, new FluidStack(Fluids.PERFLUOROMETHYL, 1_000, 1), 50));
		recipes.put(new Pair(Fluids.PERFLUOROMETHYL, 1), new CompressorRecipe(1_000, new FluidStack(Fluids.PERFLUOROMETHYL_COLD, 1_000, 0), 50));
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(Entry<Pair<FluidType, Integer>, CompressorRecipe> entry : CompressorRecipes.recipes.entrySet()) {
			ItemStack input = ItemFluidIcon.make(entry.getKey().getKey(), entry.getValue().inputAmount, entry.getKey().getValue());
			ItemStack output = ItemFluidIcon.make(entry.getValue().output);
			if(input.getItemDamage() == output.getItemDamage()) continue;
			recipes.put(input, output);
		}
		
		return recipes;
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
