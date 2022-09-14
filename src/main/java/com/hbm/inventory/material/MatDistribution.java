package com.hbm.inventory.material;

import static com.hbm.inventory.material.Mats.*;
import static com.hbm.inventory.material.MaterialShapes.*;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MatDistribution {

	public static void register() {
		registerEntry(Blocks.rail, MAT_IRON, INGOT.q(6, 16));
		registerEntry(Blocks.golden_rail, MAT_GOLD, INGOT.q(6), MAT_REDSTONE, DUST.q(1));
		registerEntry(Blocks.detector_rail, MAT_IRON, INGOT.q(6), MAT_REDSTONE, DUST.q(1));
		registerEntry(Items.minecart, MAT_IRON, INGOT.q(5));

		registerOre(OreDictManager.COAL.ore(), MAT_IRON, INGOT.q(4), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.IRON.ore(), MAT_IRON, INGOT.q(3), MAT_TITANIUM, INGOT.q(1), MAT_STONE, QUART.q(1));
		registerOre(OreDictManager.GOLD.ore(), MAT_GOLD, INGOT.q(3), MAT_LEAD, INGOT.q(1), MAT_STONE, QUART.q(1));
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
