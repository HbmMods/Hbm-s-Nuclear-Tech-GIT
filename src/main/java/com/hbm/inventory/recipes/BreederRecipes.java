package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BreederRecipes extends SerializableRecipe {

	public static HashMap<ComparableStack, BreederRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		
		setRecipe(BreedingRodType.LITHIUM, BreedingRodType.TRITIUM, 200);
		setRecipe(BreedingRodType.CO, BreedingRodType.CO60, 100);
		setRecipe(BreedingRodType.RA226, BreedingRodType.AC227, 300);
		setRecipe(BreedingRodType.TH232, BreedingRodType.THF, 500);
		setRecipe(BreedingRodType.U235, BreedingRodType.NP237, 300);
		setRecipe(BreedingRodType.NP237, BreedingRodType.PU238, 200);
		setRecipe(BreedingRodType.PU238, BreedingRodType.PU239, 1000);
		setRecipe(BreedingRodType.U238, BreedingRodType.RGP, 300);
		setRecipe(BreedingRodType.URANIUM, BreedingRodType.RGP, 200);
		setRecipe(BreedingRodType.RGP, BreedingRodType.WASTE, 200);
		
		recipes.put(new ComparableStack(ModItems.meteorite_sword_etched), new BreederRecipe(new ItemStack(ModItems.meteorite_sword_bred), 1000));
	}
	
	/** Sets recipes for single, dual, and quad rods **/
	public static void setRecipe(BreedingRodType inputType, BreedingRodType outputType, int flux) {
		recipes.put(new ComparableStack(new ItemStack(ModItems.rod, 1, inputType.ordinal())), new BreederRecipe(new ItemStack(ModItems.rod, 1, outputType.ordinal()), flux));
		recipes.put(new ComparableStack(new ItemStack(ModItems.rod_dual, 1, inputType.ordinal())), new BreederRecipe(new ItemStack(ModItems.rod_dual, 1, outputType.ordinal()), flux * 2));
		recipes.put(new ComparableStack(new ItemStack(ModItems.rod_quad, 1, inputType.ordinal())), new BreederRecipe(new ItemStack(ModItems.rod_quad, 1, outputType.ordinal()), flux * 3));
	}
	
	public static HashMap<ItemStack, BreederRecipe> getAllRecipes() {
		
		HashMap<ItemStack, BreederRecipe> map = new HashMap();
		
		for(Map.Entry<ComparableStack, BreederRecipe> recipe : recipes.entrySet()) {
			map.put(recipe.getKey().toStack(), recipe.getValue());
		}
		
		return map;
	}
	
	public static BreederRecipe getOutput(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack).makeSingular();
		return BreederRecipes.recipes.get(sta);
	}
	
	//nicer than opaque object arrays
	public static class BreederRecipe {
		
		public ItemStack output;
		public int flux;
		
		public BreederRecipe(Item output, int flux) {
			this(new ItemStack(output), flux);
		}
		
		public BreederRecipe(ItemStack output, int flux) {
			this.output = output;
			this.flux = flux;
		}
	}

	@Override
	public String getFileName() {
		return "hbmBreeder.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		AStack in = this.readAStack(obj.get("input").getAsJsonArray());
		int flux = obj.get("flux").getAsInt();
		ItemStack out = this.readItemStack(obj.get("output").getAsJsonArray());
		recipes.put(((ComparableStack) in), new BreederRecipe(out, flux));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<ComparableStack, BreederRecipe> rec = (Entry<ComparableStack, BreederRecipe>) recipe;
		ComparableStack in = rec.getKey();

		writer.name("input");
		this.writeAStack(in, writer);
		writer.name("flux").value(rec.getValue().flux);
		writer.name("output");
		this.writeItemStack(rec.getValue().output, writer);
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
