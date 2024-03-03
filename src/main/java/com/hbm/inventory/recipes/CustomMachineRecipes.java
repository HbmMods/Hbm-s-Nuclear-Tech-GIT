package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CustomMachineRecipes extends SerializableRecipe {

	public static HashMap<String, List<CustomMachineRecipe>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		recipes.put("paperPress", new ArrayList() {{
			CustomMachineRecipe recipe = new CustomMachineRecipe();
			recipe.inputFluids = new FluidStack[] {new FluidStack(Fluids.WATER, 250)};
			recipe.inputItems = new AStack[] {new ComparableStack(ModItems.powder_sawdust)};
			recipe.outputFluids = new FluidStack[0];
			recipe.outputItems = new Pair[] {new Pair(new ItemStack(Items.paper, 3), 1F)};
			recipe.duration = 60;
			recipe.consumptionPerTick = 10;
			recipe.pollutionType = "SOOT";
			recipe.pollutionAmount = 0.03F;
			recipe.radiationAmount = 0;
			recipe.flux = 0;
			recipe.heat = 0;
			add(recipe);
		}});
	}

	@Override
	public String getFileName() {
		return "hbmCustomMachines.json";
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

		String name = obj.get("recipeKey").getAsString();
		List<CustomMachineRecipe> list = new ArrayList();
		JsonArray array = obj.get("recipes").getAsJsonArray();

		for(int i = 0; i < array.size(); i++) {
			JsonObject rec = array.get(i).getAsJsonObject();
			CustomMachineRecipe recipeInstance = new CustomMachineRecipe();
			recipeInstance.inputFluids = this.readFluidArray(rec.get("inputFluids").getAsJsonArray());
			recipeInstance.inputItems = this.readAStackArray(rec.get("inputItems").getAsJsonArray());
			recipeInstance.outputFluids = this.readFluidArray(rec.get("outputFluids").getAsJsonArray());
			recipeInstance.outputItems = this.readItemStackArrayChance(rec.get("outputItems").getAsJsonArray());
			recipeInstance.duration = rec.get("duration").getAsInt();
			recipeInstance.consumptionPerTick = rec.get("consumptionPerTick").getAsInt();

			if(rec.has("pollutionType") && rec.has("pollutionAmount")) {
				recipeInstance.pollutionType = rec.get("pollutionType").getAsString();
				recipeInstance.pollutionAmount = rec.get("pollutionAmount").getAsFloat();
			} else {
				recipeInstance.pollutionType = "";
			}
			
			if(rec.has("radiationAmount")) recipeInstance.radiationAmount = rec.get("radiationAmount").getAsFloat();
			if(rec.has("flux")) recipeInstance.flux = rec.get("flux").getAsInt();
			if(rec.has("heat")) recipeInstance.heat = rec.get("heat").getAsInt();
			
			list.add(recipeInstance);
		}

		recipes.put(name, list);
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<String, List<CustomMachineRecipe>> entry = (Entry) recipe;

		writer.name("recipeKey").value(entry.getKey());
		writer.name("recipes").beginArray();

		for(CustomMachineRecipe recipeInstance : entry.getValue()) {
			writer.beginObject();

			writer.name("inputFluids").beginArray();
			for(FluidStack stack : recipeInstance.inputFluids) this.writeFluidStack(stack, writer);
			writer.endArray();

			writer.name("inputItems").beginArray();
			for(AStack stack : recipeInstance.inputItems) this.writeAStack(stack, writer);
			writer.endArray();

			writer.name("outputFluids").beginArray();
			for(FluidStack stack : recipeInstance.outputFluids) this.writeFluidStack(stack, writer);
			writer.endArray();

			writer.name("outputItems").beginArray();
			for(Pair stack : recipeInstance.outputItems) this.writeItemStackChance(stack, writer);
			writer.endArray();

			writer.name("duration").value(recipeInstance.duration);
			writer.name("consumptionPerTick").value(recipeInstance.consumptionPerTick);
			writer.name("pollutionType").value(recipeInstance.pollutionType);
			writer.name("pollutionAmount").value(recipeInstance.pollutionAmount);
			writer.name("radiationAmount").value(recipeInstance.radiationAmount);
			writer.name("flux").value(recipeInstance.flux);
			writer.name("heat").value(recipeInstance.heat);

			writer.endObject();
		}

		writer.endArray();
	}

	public static class CustomMachineRecipe {

		public FluidStack[] inputFluids;
		public AStack[] inputItems;
		public FluidStack[] outputFluids;
		public Pair<ItemStack, Float>[] outputItems;

		public int duration;
		public int consumptionPerTick;

		public String pollutionType;
		public float pollutionAmount;
		public float radiationAmount;
		public int flux;
		public int heat;
	}

}
