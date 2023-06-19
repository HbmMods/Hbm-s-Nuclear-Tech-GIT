package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;

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
		recipes.put(new ComparableStack(ModItems.waste_pu_mix, 1, 1), new ItemStack(ModItems.waste_pu_mix));
		recipes.put(new ComparableStack(ModItems.waste_pu241, 1, 1), new ItemStack(ModItems.waste_pu241));
		recipes.put(new ComparableStack(ModItems.waste_amrg, 1, 1), new ItemStack(ModItems.waste_amrg));
		recipes.put(new ComparableStack(ModItems.waste_amf, 1, 1), new ItemStack(ModItems.waste_amf));
		recipes.put(new ComparableStack(ModItems.waste_cmrg, 1, 1), new ItemStack(ModItems.waste_cmrg));
		recipes.put(new ComparableStack(ModItems.waste_cmf, 1, 1), new ItemStack(ModItems.waste_cmf));
		recipes.put(new ComparableStack(ModItems.waste_cf251, 1, 1), new ItemStack(ModItems.waste_cf251));
		recipes.put(new ComparableStack(ModItems.waste_cf252, 1, 1), new ItemStack(ModItems.waste_cf252));
		recipes.put(new ComparableStack(ModItems.waste_bk247, 1, 1), new ItemStack(ModItems.waste_bk247));
		recipes.put(new ComparableStack(ModItems.waste_es253, 1, 1), new ItemStack(ModItems.waste_es253));
		recipes.put(new ComparableStack(ModItems.waste_leaus, 1, 1), new ItemStack(ModItems.waste_leaus));
		recipes.put(new ComparableStack(ModItems.waste_meaus, 1, 1), new ItemStack(ModItems.waste_meaus));
		recipes.put(new ComparableStack(ModItems.waste_heaus, 1, 1), new ItemStack(ModItems.waste_heaus));
		recipes.put(new ComparableStack(ModItems.waste_gravel, 1, 1), new ItemStack(ModItems.waste_gravel));
		recipes.put(new ComparableStack(ModItems.waste_plate_u233, 1, 1), new ItemStack(ModItems.waste_plate_u233));
		recipes.put(new ComparableStack(ModItems.waste_plate_u235, 1, 1), new ItemStack(ModItems.waste_plate_u235));
		recipes.put(new ComparableStack(ModItems.waste_plate_mox, 1, 1), new ItemStack(ModItems.waste_plate_mox));
		recipes.put(new ComparableStack(ModItems.waste_plate_pu239, 1, 1), new ItemStack(ModItems.waste_plate_pu239));
		recipes.put(new ComparableStack(ModItems.waste_plate_sa326, 1, 1), new ItemStack(ModItems.waste_plate_sa326));
		recipes.put(new ComparableStack(ModItems.waste_plate_ra226be, 1, 1), new ItemStack(ModItems.waste_plate_ra226be));
		recipes.put(new ComparableStack(ModItems.waste_plate_pu238be, 1, 1), new ItemStack(ModItems.waste_plate_pu238be));
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
