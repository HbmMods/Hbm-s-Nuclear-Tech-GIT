package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.nei.RBMKRodDisassemblyHandler;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemRBMKRod;

import net.minecraft.item.ItemStack;

public class FuelPoolRecipes extends SerializableRecipe {

	public static final HashMap<ComparableStack, ItemStack> recipes = new HashMap<ComparableStack, ItemStack>();
	public static final FuelPoolRecipes instance = new FuelPoolRecipes();

	@Override
	public void registerDefaults() {
		recipes.put(new ComparableStack(ModItems.waste_natural_uranium, 1, 1), new ItemStack(ModItems.waste_natural_uranium));
		recipes.put(new ComparableStack(ModItems.waste_uranium, 1, 1), new ItemStack(ModItems.waste_uranium));
		recipes.put(new ComparableStack(ModItems.waste_thorium, 1, 1), new ItemStack(ModItems.waste_thorium));
		recipes.put(new ComparableStack(ModItems.waste_mox, 1, 1), new ItemStack(ModItems.waste_mox));
		recipes.put(new ComparableStack(ModItems.waste_plutonium, 1, 1), new ItemStack(ModItems.waste_plutonium));
		recipes.put(new ComparableStack(ModItems.waste_u233, 1, 1), new ItemStack(ModItems.waste_u233));
		recipes.put(new ComparableStack(ModItems.waste_u235, 1, 1), new ItemStack(ModItems.waste_u235));
		recipes.put(new ComparableStack(ModItems.waste_schrabidium, 1, 1), new ItemStack(ModItems.waste_schrabidium));
		recipes.put(new ComparableStack(ModItems.waste_zfb_mox, 1, 1), new ItemStack(ModItems.waste_zfb_mox));
		recipes.put(new ComparableStack(ModItems.waste_plate_u233, 1, 1), new ItemStack(ModItems.waste_plate_u233));
		recipes.put(new ComparableStack(ModItems.waste_plate_u235, 1, 1), new ItemStack(ModItems.waste_plate_u235));
		recipes.put(new ComparableStack(ModItems.waste_plate_mox, 1, 1), new ItemStack(ModItems.waste_plate_mox));
		recipes.put(new ComparableStack(ModItems.waste_plate_pu239, 1, 1), new ItemStack(ModItems.waste_plate_pu239));
		recipes.put(new ComparableStack(ModItems.waste_plate_sa326, 1, 1), new ItemStack(ModItems.waste_plate_sa326));
		recipes.put(new ComparableStack(ModItems.waste_plate_ra226be, 1, 1), new ItemStack(ModItems.waste_plate_ra226be));
		recipes.put(new ComparableStack(ModItems.waste_plate_pu238be, 1, 1), new ItemStack(ModItems.waste_plate_pu238be));

		for(EnumPWRFuel pwr : EnumPWRFuel.values()) recipes.put(new ComparableStack(ModItems.pwr_fuel_hot, 1, pwr.ordinal()), new ItemStack(ModItems.pwr_fuel_depleted, 1, pwr.ordinal()));

		for(ItemRBMKRod rod : ItemRBMKRod.craftableRods) {
			ItemStack result = new ItemStack(rod);
			ItemRBMKRod.setYield(result, 0.2 * rod.yield);
			recipes.put(new RBMKRodDisassemblyHandler.ComparableStackHeat(rod, true), result);
		}
	}

	@Override
	public String getFileName() {
		return "hbmFuelpool.json";
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
		JsonElement input = ((JsonObject)recipe).get("input");
		JsonElement output = ((JsonObject)recipe).get("output");
		ItemStack in = this.readItemStack((JsonArray) input);
		ItemStack out = this.readItemStack((JsonArray) output);
		recipes.put(new ComparableStack(in), out);
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<ComparableStack, ItemStack> entry = (Entry<ComparableStack, ItemStack>) recipe;
		ItemStack in = entry.getKey().toStack();
		ItemStack out = entry.getValue();

		writer.name("input");
		this.writeItemStack(in, writer);
		writer.name("output");
		this.writeItemStack(out, writer);
	}
}
