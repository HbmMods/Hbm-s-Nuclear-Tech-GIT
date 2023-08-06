package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

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
				new ItemStack(ModBlocks.fusion_conductor, 36),
				ItemStackUtil.addTooltipToStack(new ItemStack(ModBlocks.fusion_conductor, 256), EnumChatFormatting.RED + "4x64"),
				new ItemStack(ModItems.plate_cast, 36, Mats.MAT_STEEL.id),
				ItemStackUtil.addTooltipToStack(new ItemStack(ModItems.plate_cast, 256, Mats.MAT_STEEL.id), EnumChatFormatting.RED + "4x64"),
				new ItemStack(ModBlocks.fusion_center, 64),
				new ItemStack(ModBlocks.fusion_motor, 4),
				new ItemStack(ModBlocks.reinforced_glass, 8),
				new ItemStack(ModItems.blowtorch)};

		bufferedRecipes.put(iter, new ItemStack(ModBlocks.iter));
		bufferedTools.put(iter, new ItemStack(ModBlocks.struct_iter_core));
		
		/* PLASMA HEATER */
		ItemStack[] heater = new ItemStack[] {
				new ItemStack(ModBlocks.fusion_heater, 7),
				new ItemStack(ModBlocks.fusion_heater, 64),
				new ItemStack(ModBlocks.fusion_heater, 64) };

		bufferedRecipes.put(heater, new ItemStack(ModBlocks.plasma_heater));
		bufferedTools.put(heater, new ItemStack(ModBlocks.struct_plasma_core));
		
		/* COMPACT LAUNCHER */
		ItemStack[] launcher = new ItemStack[] { new ItemStack(ModBlocks.struct_launcher, 8) };

		bufferedRecipes.put(launcher, new ItemStack(ModBlocks.compact_launcher));
		bufferedTools.put(launcher, new ItemStack(ModBlocks.struct_launcher_core));
		
		/* LAUNCH TABLE */
		ItemStack[] table = new ItemStack[] {
				new ItemStack(ModBlocks.struct_launcher, 16),
				new ItemStack(ModBlocks.struct_launcher, 64),
				new ItemStack(ModBlocks.struct_scaffold, 11)};

		bufferedRecipes.put(table, new ItemStack(ModBlocks.launch_table));
		bufferedTools.put(table, new ItemStack(ModBlocks.struct_launcher_core_large));
		
		/* SOYUZ LAUNCHER */
		ItemStack[] soysauce = new ItemStack[] {
				new ItemStack(ModBlocks.struct_launcher, 60),
				ItemStackUtil.addTooltipToStack(new ItemStack(ModBlocks.struct_launcher, 320), EnumChatFormatting.RED + "5x64"),
				new ItemStack(ModBlocks.struct_scaffold, 53),
				ItemStackUtil.addTooltipToStack(new ItemStack(ModBlocks.struct_scaffold, 384), EnumChatFormatting.RED + "6x64"),
				new ItemStack(ModBlocks.concrete_smooth, 8),
				ItemStackUtil.addTooltipToStack(new ItemStack(ModBlocks.concrete_smooth, 320), EnumChatFormatting.RED + "5x64"),};

		bufferedRecipes.put(soysauce, new ItemStack(ModBlocks.soyuz_launcher));
		bufferedTools.put(soysauce, new ItemStack(ModBlocks.struct_soyuz_core));
		
		return recipes ? bufferedRecipes : bufferedTools;
	}
}
