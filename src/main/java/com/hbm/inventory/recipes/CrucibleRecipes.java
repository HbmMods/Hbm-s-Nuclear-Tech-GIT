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
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;
import com.hbm.util.Compat;
import com.hbm.items.machine.ItemScraps;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrucibleRecipes extends SerializableRecipe {

	public static HashMap<Integer, CrucibleRecipe> indexMapping = new HashMap();
	public static List<CrucibleRecipe> recipes = new ArrayList();
	
	/*
	 * IMPORTANT: crucibles do not have stack size checks for the recipe's result, meaning that they can overflow if the resulting stacks are
	 * bigger than the input stacks, so make sure that material doesn't "expand". very few things do that IRL when alloying anyway.
	 */
	
	@Override
	public void registerDefaults() {

		int n = MaterialShapes.NUGGET.q(1);
		int i = MaterialShapes.INGOT.q(1);
		
		recipes.add(new CrucibleRecipe(0, "crucible.steel", 2, new ItemStack(ModItems.ingot_steel))
				.inputs(new MaterialStack(Mats.MAT_IRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
				.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
		
		if(Compat.isModLoaded(Compat.MOD_GT6)) {
			recipes.add(new CrucibleRecipe(9, "crucible.steelWrought", 2, new ItemStack(ModItems.ingot_steel))
					.inputs(new MaterialStack(Mats.MAT_WROUGHTIRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
					.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
			recipes.add(new CrucibleRecipe(10, "crucible.steelPig", 2, new ItemStack(ModItems.ingot_steel))
					.inputs(new MaterialStack(Mats.MAT_PIGIRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
					.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
			recipes.add(new CrucibleRecipe(11, "crucible.steelMeteoric", 2, new ItemStack(ModItems.ingot_steel))
					.inputs(new MaterialStack(Mats.MAT_METEORICIRON, n * 2), new MaterialStack(Mats.MAT_CARBON, n))
					.outputs(new MaterialStack(Mats.MAT_STEEL, n * 2)));
		}
		
		recipes.add(new CrucibleRecipe(7, "crucible.hematite", 6, DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.HEMATITE))
				.inputs(new MaterialStack(Mats.MAT_HEMATITE, i * 2), new MaterialStack(Mats.MAT_FLUX, n * 2))
				.outputs(new MaterialStack(Mats.MAT_IRON, i), new MaterialStack(Mats.MAT_SLAG, n * 3)));
		
		recipes.add(new CrucibleRecipe(8, "crucible.malachite", 6, DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.MALACHITE))
				.inputs(new MaterialStack(Mats.MAT_MALACHITE, i * 2), new MaterialStack(Mats.MAT_FLUX, n * 2))
				.outputs(new MaterialStack(Mats.MAT_COPPER, i), new MaterialStack(Mats.MAT_SLAG, n * 3)));
		
		recipes.add(new CrucibleRecipe(1, "crucible.redcopper", 2, new ItemStack(ModItems.ingot_red_copper))
				.inputs(new MaterialStack(Mats.MAT_COPPER, n), new MaterialStack(Mats.MAT_REDSTONE, n))
				.outputs(new MaterialStack(Mats.MAT_MINGRADE, n * 2)));
		
		recipes.add(new CrucibleRecipe(2, "crucible.aa", 2, new ItemStack(ModItems.ingot_advanced_alloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n), new MaterialStack(Mats.MAT_MINGRADE, n))
				.outputs(new MaterialStack(Mats.MAT_ALLOY, n * 2)));
		
		recipes.add(new CrucibleRecipe(3, "crucible.hss", 9, new ItemStack(ModItems.ingot_dura_steel))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 5), new MaterialStack(Mats.MAT_TUNGSTEN, n * 3), new MaterialStack(Mats.MAT_COBALT, n * 1))
				.outputs(new MaterialStack(Mats.MAT_DURA, n * 9)));
		
		recipes.add(new CrucibleRecipe(4, "crucible.ferro", 3, new ItemStack(ModItems.ingot_ferrouranium))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 2), new MaterialStack(Mats.MAT_U238, n))
				.outputs(new MaterialStack(Mats.MAT_FERRO, n * 3)));
		
		recipes.add(new CrucibleRecipe(5, "crucible.tcalloy", 9, new ItemStack(ModItems.ingot_tcalloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 8), new MaterialStack(Mats.MAT_TECHNETIUM, n))
				.outputs(new MaterialStack(Mats.MAT_TCALLOY, i)));
		
		recipes.add(new CrucibleRecipe(12, "crucible.cdalloy", 9, new ItemStack(ModItems.ingot_cdalloy))
				.inputs(new MaterialStack(Mats.MAT_STEEL, n * 8), new MaterialStack(Mats.MAT_CADMIUM, n))
				.outputs(new MaterialStack(Mats.MAT_CDALLOY, i)));
		
		recipes.add(new CrucibleRecipe(14, "crucible.bbronze", 9, new ItemStack(ModItems.ingot_bismuth_bronze))
				.inputs(new MaterialStack(Mats.MAT_COPPER, n * 8), new MaterialStack(Mats.MAT_BISMUTH, n), new MaterialStack(Mats.MAT_FLUX, n * 3))
				.outputs(new MaterialStack(Mats.MAT_BBRONZE, i), new MaterialStack(Mats.MAT_SLAG, n * 3)));
		
		recipes.add(new CrucibleRecipe(15, "crucible.abronze", 9, new ItemStack(ModItems.ingot_arsenic_bronze))
				.inputs(new MaterialStack(Mats.MAT_COPPER, n * 8), new MaterialStack(Mats.MAT_ARSENIC, n), new MaterialStack(Mats.MAT_FLUX, n * 3))
				.outputs(new MaterialStack(Mats.MAT_ABRONZE, i), new MaterialStack(Mats.MAT_SLAG, n * 3)));
		
		recipes.add(new CrucibleRecipe(13, "crucible.cmb", 3, new ItemStack(ModItems.ingot_combine_steel))
				.inputs(new MaterialStack(Mats.MAT_MAGTUNG, n * 6), new MaterialStack(Mats.MAT_MUD, n * 3))
				.outputs(new MaterialStack(Mats.MAT_CMB, i)));
		
		recipes.add(new CrucibleRecipe(16, "crucible.magtung", 3, new ItemStack(ModItems.ingot_magnetized_tungsten))
				.inputs(new MaterialStack(Mats.MAT_TUNGSTEN, i), new MaterialStack(Mats.MAT_SCHRABIDIUM, n * 1))
				.outputs(new MaterialStack(Mats.MAT_MAGTUNG, i)));
		
		recipes.add(new CrucibleRecipe(17, "crucible.bscco", 3, new ItemStack(ModItems.ingot_bscco))
				.inputs(new MaterialStack(Mats.MAT_BISMUTH, n * 2), new MaterialStack(Mats.MAT_STRONTIUM, n * 2), new MaterialStack(Mats.MAT_CALCIUM, n * 2), new MaterialStack(Mats.MAT_COPPER, n * 3))
				.outputs(new MaterialStack(Mats.MAT_BSCCO, i)));
		
		registerMoldsForNEI();
	}

	public static class CrucibleRecipe {
		public MaterialStack[] input;
		public MaterialStack[] output;
		private int id;
		private String name;
		public int frequency = 1;
		public ItemStack icon;
		
		public CrucibleRecipe(int id, String name, int frequency, ItemStack icon) {
			this.id = id;
			this.name = name;
			this.frequency = frequency;
			this.icon = icon;
			
			if(!indexMapping.containsKey(id)) {
				indexMapping.put(id, this);
			} else {
				throw new IllegalStateException("Crucible recipe " + name + " has been registered with duplicate id " + id + " used by " + indexMapping.get(id).name + "!");
			}
		}
		
		public CrucibleRecipe inputs(MaterialStack... input) {
			this.input = input;
			return this;
		}
		
		public CrucibleRecipe outputs(MaterialStack... output) {
			this.output = output;
			return this;
		}
		
		public int getId() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getInputAmount() {
			
			int content = 0;
			
			for(MaterialStack stack : input) {
				content += stack.amount;
			}
			
			return content;
		}
	}

	@Override
	public String getFileName() {
		return "hbmCrucible.json";
	}

	@Override
	public Object getRecipeObject() {
		return this.recipes;
	}

	@Override
	public String getComment() {
		return "ID must be unique, but not sequential. Order in which the recipes are defined determines the order in which they are displayed in-game. "
				+ "Frequency is the amount of ticks between operations, must be at least 1. The names are unlocalized by default, but if they can't be found in "
				+ "the lang files the names will be displayed as-is. The icon is what's being displayed when holding shift on the template.";
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		int id = obj.get("id").getAsInt();
		String name = obj.get("name").getAsString();
		int freq = obj.get("frequency").getAsInt();
		ItemStack icon = this.readItemStack(obj.get("icon").getAsJsonArray());
		MaterialStack[] input = new MaterialStack[obj.get("input").getAsJsonArray().size()];
		for(int i = 0; i < input.length; i++) {
			JsonArray entry = obj.get("input").getAsJsonArray().get(i).getAsJsonArray();
			String matname = entry.get(0).getAsString();
			int amount = entry.get(1).getAsInt();
			input[i] = new MaterialStack(Mats.matByName.get(matname), amount);
		}
		MaterialStack[] output = new MaterialStack[obj.get("output").getAsJsonArray().size()];
		for(int i = 0; i < output.length; i++) {
			JsonArray entry = obj.get("output").getAsJsonArray().get(i).getAsJsonArray();
			String matname = entry.get(0).getAsString();
			int amount = entry.get(1).getAsInt();
			output[i] = new MaterialStack(Mats.matByName.get(matname), amount);
		}
		recipes.add(new CrucibleRecipe(id, name, freq, icon).inputs(input).outputs(output));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		CrucibleRecipe rec = (CrucibleRecipe) recipe;
		writer.name("id").value(rec.id);
		writer.name("name").value(rec.name);
		writer.name("frequency").value(rec.frequency);
		writer.name("icon");
		this.writeItemStack(rec.icon, writer);
		writer.name("input");
		writer.beginArray();
		for(MaterialStack mat : rec.input) {
			writer.beginArray();
			writer.setIndent("");
			writer.value(mat.material.names[0]).value(mat.amount);
			writer.endArray();
			writer.setIndent("  ");
		}
		writer.endArray();
		writer.name("output");
		writer.beginArray();
		for(MaterialStack mat : rec.output) {
			writer.beginArray();
			writer.setIndent("");
			writer.value(mat.material.names[0]).value(mat.amount);
			writer.endArray();
			writer.setIndent("  ");
		}
		writer.endArray();
	}

	@Override
	public void deleteRecipes() {
		this.indexMapping.clear();
		this.recipes.clear();
		this.moldRecipes.clear();
	}
	
	/** Returns a map containing all recipes where an item becomes a liquid material in the crucible. */
	public static HashMap<AStack, List<ItemStack>> getSmeltingRecipes() {
		HashMap<AStack, List<ItemStack>> map = new HashMap();
		
		for(NTMMaterial material : Mats.orderedList) {
			int in = material.convIn;
			int out = material.convOut;
			NTMMaterial convert = material.smeltsInto;
			if(convert.smeltable == SmeltingBehavior.SMELTABLE || convert.smeltable == SmeltingBehavior.ADDITIVE) for(MaterialShapes shape : MaterialShapes.allShapes) {
				//TODO: buffer these
				if(!shape.noAutogen) {
					String name = shape.make(material);
					List<ItemStack> ores = OreDictionary.getOres(name);
					
					if(!ores.isEmpty()) {
						List<ItemStack> stacks = new ArrayList();
						stacks.add(ItemScraps.create(new MaterialStack(convert, (int) (shape.q(1) * out / in)), true));
						map.put(new OreDictStack(name), stacks);
					}
				}
			}
		}
		
		for(Entry<String, List<MaterialStack>> entry : Mats.materialOreEntries.entrySet()) {
			List<ItemStack> stacks = new ArrayList();
			for(MaterialStack mat : entry.getValue()) {
				stacks.add(ItemScraps.create(mat, true));
			}
			map.put(new OreDictStack(entry.getKey()), stacks);
		}
		
		for(Entry<ComparableStack, List<MaterialStack>> entry : Mats.materialEntries.entrySet()) {
			List<ItemStack> stacks = new ArrayList();
			for(MaterialStack mat : entry.getValue()) {
				stacks.add(ItemScraps.create(mat, true));
			}
			map.put(entry.getKey().copy(), stacks);
		}
		
		return map;
	}
	
	private static List<ItemStack[]> moldRecipes = new ArrayList();
	
	public static List<ItemStack[]> getMoldRecipes() {
		if(moldRecipes.isEmpty()) {
			registerMoldsForNEI();
		}
		
		return moldRecipes;
	}
	
	private static void registerMoldsForNEI() {
		
		for(NTMMaterial material : Mats.orderedList) {
			
			if(material.smeltable != SmeltingBehavior.SMELTABLE)
				continue;
			
			for(Mold mold : ItemMold.molds) {
				ItemStack out = mold.getOutput(material);
				if(out != null) {
					ItemStack scrap = ItemScraps.create(new MaterialStack(material, mold.getCost()), true);
					ItemStack shape = new ItemStack(ModItems.mold, 1, mold.id);
					ItemStack basin = new ItemStack(mold.size == 0 ? ModBlocks.foundry_mold : mold.size == 1 ? ModBlocks.foundry_basin : Blocks.fire);
					ItemStack[] entry = new ItemStack[] {scrap, shape, basin, out};
					moldRecipes.add(entry);
				}
			}
		}
	}
}
