package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

public class ElectrolyserMetalRecipes extends SerializableRecipe {
	
	public static HashMap<AStack, ElectrolysisMetalRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		recipes.put(new ComparableStack(ModItems.crystal_iron), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_TITANIUM, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 1)));
		recipes.put(new ComparableStack(ModItems.crystal_gold), new ElectrolysisMetalRecipe(
				new MaterialStack(Mats.MAT_IRON, MaterialShapes.INGOT.q(6)),
				new MaterialStack(Mats.MAT_LEAD, MaterialShapes.INGOT.q(2)),
				new ItemStack(ModItems.powder_lithium_tiny, 1),
				new ItemStack(ModItems.ingot_mercury, 1)));
	}
	
	public static ElectrolysisMetalRecipe getRecipe(ItemStack stack) {
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		
		if(recipes.containsKey(comp)) return recipes.get(comp);
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			OreDictStack ore = new OreDictStack(name);
			if(recipes.containsKey(ore)) return recipes.get(ore);
		}
		
		return null;
	}

	@Override
	public String getFileName() {
		return "hbmElectrolyzerMetal.json";
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
		
		AStack input = this.readAStack(obj.get("input").getAsJsonArray());

		JsonArray out1 = obj.get("output1").getAsJsonArray();
		String name1 = out1.get(0).getAsString();
		int amount1 = out1.get(1).getAsInt();
		MaterialStack output1 = new MaterialStack(Mats.matByName.get(name1), amount1);

		JsonArray out2 = obj.get("output2").getAsJsonArray();
		String name2 = out2.get(0).getAsString();
		int amount2 = out2.get(1).getAsInt();
		MaterialStack output2 = new MaterialStack(Mats.matByName.get(name2), amount2);
		
		ItemStack[] byproducts = new ItemStack[0];
		if(obj.has("byproducts")) byproducts = this.readItemStackArray(obj.get("byproducts").getAsJsonArray());
		
		recipes.put(input, new ElectrolysisMetalRecipe(output1, output2, byproducts));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<AStack, ElectrolysisMetalRecipe> rec = (Entry) recipe;
		
		writer.name("input"); this.writeAStack(rec.getKey(), writer);
		
		writer.name("output1");
		writer.beginArray();
		writer.setIndent("");
		writer.value(rec.getValue().output1.material.names[0]).value(rec.getValue().output1.amount);
		writer.endArray();
		writer.setIndent("  ");
		
		writer.name("output2");
		writer.beginArray();
		writer.setIndent("");
		writer.value(rec.getValue().output2.material.names[0]).value(rec.getValue().output2.amount);
		writer.endArray();
		writer.setIndent("  ");
		
		if(rec.getValue().byproduct != null && rec.getValue().byproduct.length > 0) {
			writer.name("byproducts").beginArray();
			for(ItemStack stack : rec.getValue().byproduct) this.writeItemStack(stack, writer);
			writer.endArray();
		}
	}
	
	public static class ElectrolysisMetalRecipe {
		
		public MaterialStack output1;
		public MaterialStack output2;
		public ItemStack[] byproduct;
		
		public ElectrolysisMetalRecipe(MaterialStack output1, MaterialStack output2, ItemStack... byproduct) {
			this.output1 = output1;
			this.output2 = output2;
			this.byproduct = byproduct;
		}
	}
}
