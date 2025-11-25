package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

// i'm gonna be honest, i got so carried away with the new fusion reactor stuff that i forgot what fluid irradiation was even supposed to be for
public class FluidBreederRecipes extends SerializableRecipe {
	
	public static Map<FluidType, Pair<Integer, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		register(new FluidStack(Fluids.GAS, 1_000),				new FluidStack(Fluids.SYNGAS, 1_000));
		register(new FluidStack(Fluids.LIGHTOIL, 1_000),		new FluidStack(Fluids.REFORMGAS, 1_000));
		register(new FluidStack(Fluids.LIGHTOIL_CRACK, 1_000),	new FluidStack(Fluids.REFORMGAS, 1_000));
	}
	
	public static void register(FluidStack input, FluidStack output) {
		recipes.put(input.type, new Pair(input.fill, output));
	}
	
	public static Pair<Integer, FluidStack> getOutput(FluidType type) {
		return recipes.get(type);
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(Entry<FluidType, Pair<Integer, FluidStack>> entry : FluidBreederRecipes.recipes.entrySet()) {
			recipes.put(ItemFluidIcon.make(entry.getKey(),  entry.getValue().getKey()), ItemFluidIcon.make(entry.getValue().getValue()));
		}
		
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		FluidStack input = this.readFluidStack(obj.get("input").getAsJsonArray());
		FluidStack output = this.readFluidStack(obj.get("output").getAsJsonArray());
		this.recipes.put(input.type, new Pair(input.fill, output));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Pair<Integer, FluidStack>> rec = (Entry<FluidType, Pair<Integer, FluidStack>>) recipe;
		
		writer.name("input");
		this.writeFluidStack(new FluidStack(rec.getKey(), rec.getValue().getKey()), writer);
		writer.name("output");
		this.writeFluidStack(rec.getValue().getValue(), writer);
	}

	@Override public String getFileName() { return "hbmIrradiationFluids.json"; }
	@Override public Object getRecipeObject() { return recipes; }
	@Override public void deleteRecipes() { recipes.clear(); }
}
