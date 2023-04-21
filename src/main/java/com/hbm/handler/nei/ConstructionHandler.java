package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class ConstructionHandler extends NEIUniversalHandler {

	public ConstructionHandler() {
		super("Construction", getRecipes(true), getRecipes(false));
	}

	@Override
	public String getKey() {
		return "ntmConstruction";
	}

	public static HashMap<Object[], Object> bufferedRecipes = new HashMap();
	public static HashMap<Object[], Object> bufferedTools = new HashMap();
	
	public static HashMap<Object[], Object> getRecipes(boolean recipes) {
		
		if(!bufferedRecipes.isEmpty()) {
			return recipes ? bufferedRecipes : bufferedTools;
		}
		
		/* WATZ */
		ItemStack[] watz = new ItemStack[] {
				new ItemStack(ModBlocks.watz_end, 48),
				new ItemStack(ModItems.bolt_dura_steel, 64),
				new ItemStack(ModItems.bolt_dura_steel, 64),
				new ItemStack(ModItems.bolt_dura_steel, 64),
				new ItemStack(ModBlocks.watz_element, 36),
				new ItemStack(ModBlocks.watz_cooler, 26),
				new ItemStack(ModItems.boltgun)};

		bufferedRecipes.put(watz, new ItemStack(ModBlocks.watz));
		bufferedTools.put(watz, new ItemStack(ModBlocks.struct_watz_core));
		
		/* ITER */
		ItemStack[] iter = new ItemStack[] {
				new ItemStack(ModBlocks.fusion_conductor, 64),
				new ItemStack(ModBlocks.fusion_conductor, 64),
				new ItemStack(ModBlocks.fusion_conductor, 64),
				new ItemStack(ModBlocks.fusion_conductor, 64),
				new ItemStack(ModBlocks.fusion_conductor, 36),
				new ItemStack(ModBlocks.fusion_center, 64),
				new ItemStack(ModBlocks.fusion_motor, 4),
				new ItemStack(ModBlocks.reinforced_glass, 8)};

		bufferedRecipes.put(iter, new ItemStack(ModBlocks.iter));
		bufferedTools.put(iter, new ItemStack(ModBlocks.struct_iter_core));
		
		return recipes ? bufferedRecipes : bufferedTools;
	}
}
