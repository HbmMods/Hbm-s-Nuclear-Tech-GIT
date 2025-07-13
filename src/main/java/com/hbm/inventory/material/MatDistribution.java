package com.hbm.inventory.material;

import static com.hbm.inventory.material.Mats.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.util.Compat;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MatDistribution extends SerializableRecipe {

	@Override
	public void registerDefaults() {
		//vanilla crap
		registerOre("stone", MAT_STONE, BLOCK.q(1));
		registerOre("cobblestone", MAT_STONE, BLOCK.q(1));
		registerEntry(Blocks.obsidian, MAT_OBSIDIAN, BLOCK.q(1));
		registerEntry(Blocks.rail, MAT_IRON, INGOT.q(6, 16));
		registerEntry(Blocks.golden_rail, MAT_GOLD, INGOT.q(6, 6), MAT_REDSTONE, DUST.q(1, 6));
		registerEntry(Blocks.detector_rail, MAT_IRON, INGOT.q(6, 6), MAT_REDSTONE, DUST.q(1, 6));
		registerEntry(Items.minecart, MAT_IRON, INGOT.q(5));

		//castables
		registerEntry(ModItems.blade_titanium,				MAT_TITANIUM,		INGOT.q(2));
		registerEntry(ModItems.blade_tungsten,				MAT_TUNGSTEN,		INGOT.q(2));
		registerEntry(ModItems.blades_steel,				MAT_STEEL,			INGOT.q(4));
		registerEntry(ModItems.blades_titanium,				MAT_TITANIUM, 		INGOT.q(4));
		registerEntry(ModItems.blades_advanced_alloy,		MAT_ALLOY,			INGOT.q(4));
		registerEntry(ModItems.stamp_stone_flat,			MAT_STONE,			INGOT.q(3));
		registerEntry(ModItems.stamp_iron_flat,				MAT_IRON,			INGOT.q(3));
		registerEntry(ModItems.stamp_steel_flat,			MAT_STEEL,			INGOT.q(3));
		registerEntry(ModItems.stamp_titanium_flat,			MAT_TITANIUM,		INGOT.q(3));
		registerEntry(ModItems.stamp_obsidian_flat,			MAT_OBSIDIAN,		INGOT.q(3));
		registerEntry(ModItems.pipes_steel,					MAT_STEEL,			BLOCK.q(3));

		registerEntry(DictFrame.fromOne(ModItems.casing, EnumCasingType.SMALL),			MAT_GUNMETAL,		PLATE.q(1, 4));
		registerEntry(DictFrame.fromOne(ModItems.casing, EnumCasingType.SMALL_STEEL),	MAT_WEAPONSTEEL,	PLATE.q(1, 4));
		registerEntry(DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE),			MAT_GUNMETAL,		PLATE.q(1, 2));
		registerEntry(DictFrame.fromOne(ModItems.casing, EnumCasingType.LARGE_STEEL),	MAT_WEAPONSTEEL,	PLATE.q(1, 2));
		registerEntry(Items.minecart, MAT_IRON, INGOT.q(5));
		registerEntry(DictFrame.fromOne(ModItems.chunk_ore, ItemEnums.EnumChunkType.CRYOLITE), MAT_ALUMINIUM, INGOT.q(1), MAT_SODIUM, INGOT.q(1));
		//actual ores
		if(!Compat.isModLoaded(Compat.MOD_GT6)) {
			registerOre(OreDictManager.IRON.ore(), MAT_IRON, INGOT.q(2), MAT_TITANIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
			registerOre(OreDictManager.TI.ore(), MAT_TITANIUM, INGOT.q(2), MAT_IRON, NUGGET.q(3), MAT_STONE, QUART.q(1));
			registerOre(OreDictManager.W.ore(), MAT_TUNGSTEN, INGOT.q(2), MAT_STONE, QUART.q(1));
			registerOre(OreDictManager.AL.ore(), MAT_ALUMINIUM, INGOT.q(2), MAT_SODIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
		}

		registerOre(OreDictManager.COAL.ore(), MAT_CARBON, GEM.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.GOLD.ore(), MAT_GOLD, INGOT.q(2), MAT_LEAD, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.U.ore(), MAT_URANIUM, INGOT.q(2), MAT_LEAD, NUGGET.q(3), MAT_STONE, QUART.q(1));
		for(String ore : OreDictManager.TH232.all(MaterialShapes.ONLY_ORE)) registerOre(ore, MAT_THORIUM, INGOT.q(2), MAT_URANIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.CU.ore(), MAT_COPPER, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.PB.ore(), MAT_LEAD, INGOT.q(2), MAT_GOLD, NUGGET.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.BE.ore(), MAT_BERYLLIUM, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.CO.ore(), MAT_COBALT, INGOT.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.REDSTONE.ore(), MAT_REDSTONE, INGOT.q(4), MAT_STONE, QUART.q(1));

		registerOre(OreDictManager.HEMATITE.ore(), MAT_HEMATITE, INGOT.q(1));
		registerOre(OreDictManager.MALACHITE.ore(), MAT_MALACHITE, INGOT.q(6));

		registerEntry(DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.LIMESTONE), MAT_FLUX, DUST.q(10));
		registerEntry(ModItems.powder_flux, MAT_FLUX, DUST.q(1));
		registerEntry(new ItemStack(Items.coal, 1, 1), MAT_CARBON, NUGGET.q(3));

		registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD), MAT_CARBON, NUGGET.q(1));
		registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.COAL), MAT_CARBON, NUGGET.q(2));
		registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC), MAT_CARBON, NUGGET.q(1));
	}

	public static void registerEntry(Object key, Object... matDef) {
		ComparableStack comp = null;

		if(key instanceof Item) comp = new ComparableStack((Item) key);
		if(key instanceof Block) comp = new ComparableStack((Block) key);
		if(key instanceof ItemStack) comp = new ComparableStack((ItemStack) key);
		if(key instanceof ComparableStack) comp = (ComparableStack) key;

		if(comp == null) return;
		if(matDef.length % 2 == 1) return;

		List<MaterialStack> stacks = new ArrayList();

		for(int i = 0; i < matDef.length; i += 2) {
			stacks.add(new MaterialStack((NTMMaterial) matDef[i], (int) matDef[i + 1]));
		}

		if(stacks.isEmpty()) return;

		materialEntries.put(comp, stacks);
	}

	public static void registerOre(String key, Object... matDef) {
		if(matDef.length % 2 == 1) return;

		List<MaterialStack> stacks = new ArrayList();

		for(int i = 0; i < matDef.length; i += 2) {
			stacks.add(new MaterialStack((NTMMaterial) matDef[i], (int) matDef[i + 1]));
		}

		if(stacks.isEmpty()) return;

		materialOreEntries.put(key, stacks);
	}

	@Override
	public String getFileName() {
		return "hbmCrucibleSmelting.json";
	}

	@Override
	public Object getRecipeObject() {
		List entries = new ArrayList();
		entries.addAll(Mats.materialEntries.entrySet());
		entries.addAll(Mats.materialOreEntries.entrySet());
		return entries;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		AStack input = this.readAStack(obj.get("input").getAsJsonArray());
		List<MaterialStack> materials = new ArrayList();
		JsonArray output = obj.get("output").getAsJsonArray();
		for(int i = 0; i < output.size(); i++) {
			JsonArray entry = output.get(i).getAsJsonArray();
			String mat = entry.get(0).getAsString();
			int amount = entry.get(1).getAsInt();
			materials.add(new MaterialStack(Mats.matByName.get(mat), amount));
		}
		if(input instanceof ComparableStack) {
			Mats.materialEntries.put((ComparableStack) input, materials);
		} else if(input instanceof OreDictStack) {
			Mats.materialOreEntries.put(((OreDictStack) input).name, materials);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		AStack toSmelt = null;
		Entry entry = (Entry) recipe;
		List<MaterialStack> materials = (List<MaterialStack>) entry.getValue();
		if(entry.getKey() instanceof String) {
			toSmelt = new OreDictStack((String) entry.getKey());
		} else if(entry.getKey() instanceof ComparableStack) {
			toSmelt = (ComparableStack) entry.getKey();
		}
		if(toSmelt == null) return;
		writer.name("input");
		this.writeAStack(toSmelt, writer);
		writer.name("output").beginArray();
		writer.setIndent("");
		for(MaterialStack stack : materials) {
			writer.beginArray();
			writer.value(stack.material.names[0]).value(stack.amount);
			writer.endArray();
		}
		writer.endArray();
		writer.setIndent("  ");
	}

	@Override
	public void deleteRecipes() {
		Mats.materialEntries.clear();
		Mats.materialOreEntries.clear();
	}

	@Override
	public String getComment() {
		return "Defines a set of items that can be smelted. Smelting generated from the ore dictionary (prefix + material) is auto-generated and cannot be "
				+ "changed. This config only changes fixed items (like recycling for certain metallic objects) and ores (with variable byproducts). "
				+ "Amounts used are in quanta (1 quantum is 1/72 of an ingot or 1/8 of a nugget). Material names are the ore dict suffixes, case-sensitive.";
	}
}
