package com.hbm.inventory.material;

import static com.hbm.inventory.material.Mats.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MatDistribution {

	public static void register() {
		//vanilla crap
		registerOre("stone", MAT_STONE, BLOCK.q(1));
		registerOre("cobblestone", MAT_STONE, BLOCK.q(1));
		registerEntry(Blocks.obsidian, MAT_OBSIDIAN, BLOCK.q(1));
		registerEntry(Blocks.rail, MAT_IRON, INGOT.q(6, 16));
		registerEntry(Blocks.golden_rail, MAT_GOLD, INGOT.q(6, 6), MAT_REDSTONE, DUST.q(1, 6));
		registerEntry(Blocks.detector_rail, MAT_IRON, INGOT.q(6, 6), MAT_REDSTONE, DUST.q(1, 6));
		registerEntry(Items.minecart, MAT_IRON, INGOT.q(5));
		
		//castables
		registerEntry(ModItems.blade_titanium,			MAT_TITANIUM,		INGOT.q(2));
		registerEntry(ModItems.blade_tungsten,			MAT_TUNGSTEN,		INGOT.q(2));
		registerEntry(ModItems.blades_gold,				MAT_GOLD,			INGOT.q(4));
		registerEntry(ModItems.blades_aluminium,		MAT_ALUMINIUM,		INGOT.q(4));
		registerEntry(ModItems.blades_iron,				MAT_IRON,			INGOT.q(4));
		registerEntry(ModItems.blades_steel,			MAT_STEEL,			INGOT.q(4));
		registerEntry(ModItems.blades_titanium,			MAT_TITANIUM, 		INGOT.q(4));
		registerEntry(ModItems.blades_advanced_alloy,	MAT_ALLOY,			INGOT.q(4));
		registerEntry(ModItems.blades_combine_steel,	MAT_CMB,			INGOT.q(4));
		registerEntry(ModItems.blades_schrabidium,		MAT_SCHRABIDIUM,	INGOT.q(4));
		registerEntry(ModItems.stamp_stone_flat,		MAT_STONE,			INGOT.q(3));
		registerEntry(ModItems.stamp_iron_flat,			MAT_IRON,			INGOT.q(3));
		registerEntry(ModItems.stamp_steel_flat,		MAT_STEEL,			INGOT.q(3));
		registerEntry(ModItems.stamp_titanium_flat,		MAT_TITANIUM,		INGOT.q(3));
		registerEntry(ModItems.stamp_obsidian_flat,		MAT_OBSIDIAN,		INGOT.q(3));
		registerEntry(ModItems.stamp_schrabidium_flat,	MAT_SCHRABIDIUM,	INGOT.q(3));
		registerEntry(ModItems.hull_small_steel,		MAT_STEEL,			INGOT.q(2));
		registerEntry(ModItems.hull_small_aluminium,	MAT_ALUMINIUM,		INGOT.q(2));
		registerEntry(ModItems.hull_big_steel,			MAT_STEEL,			INGOT.q(6));
		registerEntry(ModItems.hull_big_aluminium,		MAT_ALUMINIUM,		INGOT.q(6));
		registerEntry(ModItems.hull_big_titanium,		MAT_TITANIUM,		INGOT.q(6));
		registerEntry(ModItems.pipes_steel,				MAT_STEEL,			BLOCK.q(3));

		//actual ores
		registerOre(OreDictManager.COAL.ore(), MAT_COAL, GEM.q(4), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.FE.ore(), MAT_IRON, INGOT.q(2), MAT_TITANIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.AU.ore(), MAT_GOLD, INGOT.q(2), MAT_LEAD, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.U.ore(), MAT_URANIUM, INGOT.q(2), MAT_LEAD, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.TH232.ore(), MAT_THORIUM, INGOT.q(2), MAT_URANIUM, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.TI.ore(), MAT_TITANIUM, INGOT.q(2), MAT_IRON, NUGGET.q(3), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.CU.ore(), MAT_COPPER, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.W.ore(), MAT_TUNGSTEN, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.AL.ore(), MAT_ALUMINIUM, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.PB.ore(), MAT_LEAD, INGOT.q(2), MAT_GOLD, NUGGET.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.BE.ore(), MAT_BERYLLIUM, INGOT.q(2), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.CO.ore(), MAT_COBALT, INGOT.q(1), MAT_STONE, QUART.q(1));
	}
	
	public static void registerEntry(Object key, Object... matDef) {
		ComparableStack comp = null;

		if(key instanceof Item) comp = new ComparableStack((Item) key);
		if(key instanceof Block) comp = new ComparableStack((Block) key);
		if(key instanceof ItemStack) comp = new ComparableStack((ItemStack) key);
		
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
}
