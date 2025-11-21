package com.hbm.handler.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.fusion.MachineFusionTorus;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

public class ConstructionHandler extends NEIUniversalHandler {

	public ConstructionHandler() {
		super("Construction", getRecipes(true), getRecipes(false));
	}

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModItems.acetylene_torch),
				new ItemStack(ModItems.blowtorch),
				new ItemStack(ModItems.boltgun)};
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
				Mats.MAT_DURA.make(ModItems.bolt, 64),
				Mats.MAT_DURA.make(ModItems.bolt, 64),
				Mats.MAT_DURA.make(ModItems.bolt, 64),
				new ItemStack(ModBlocks.watz_element, 36),
				new ItemStack(ModBlocks.watz_cooler, 26),
				new ItemStack(ModItems.boltgun)};

		bufferedRecipes.put(watz, new ItemStack(ModBlocks.watz));
		bufferedTools.put(watz, new ItemStack(ModBlocks.struct_watz_core));
		
		/* ITER */
		ItemStack[] iter = new ItemStack[] {
				new ItemStack(ModBlocks.fusion_conductor, 36),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModBlocks.fusion_conductor, 256)),
				new ItemStack(ModItems.plate_cast, 36, Mats.MAT_STEEL.id),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModItems.plate_cast, 256, Mats.MAT_STEEL.id)),
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
				new ItemStack(ModBlocks.struct_launcher, 30),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModBlocks.struct_launcher, 384)),
				new ItemStack(ModBlocks.struct_scaffold, 63),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModBlocks.struct_scaffold, 384)),
				new ItemStack(ModBlocks.concrete_smooth, 38),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModBlocks.concrete_smooth, 320))};

		bufferedRecipes.put(soysauce, new ItemStack(ModBlocks.soyuz_launcher));
		bufferedTools.put(soysauce, new ItemStack(ModBlocks.struct_soyuz_core));
		
		/* ICF */
		ItemStack[] icf = new ItemStack[] {
				new ItemStack(ModBlocks.icf_component, 50, 0),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModBlocks.icf_component, 240, 3)),
				ItemStackUtil.addStackSizeLabel(Mats.MAT_DURA.make(ModItems.bolt, 960)),
				ItemStackUtil.addStackSizeLabel(Mats.MAT_STEEL.make(ModItems.plate_cast, 240)),
				ItemStackUtil.addStackSizeLabel(new ItemStack(ModBlocks.icf_component, 117, 1)),
				ItemStackUtil.addStackSizeLabel(Mats.MAT_BBRONZE.make(ModItems.plate_cast, 117)),
				new ItemStack(ModItems.blowtorch),
				new ItemStack(ModItems.boltgun) };

		bufferedRecipes.put(icf, new ItemStack(ModBlocks.icf));
		bufferedTools.put(icf, new ItemStack(ModBlocks.struct_icf_core));
		
		/* FUSION TORUS */
		int wallCount = 0;
		int blanketCount = 0;
		int pipeCount = -1; // one block is replaced by the core

		for(int iy = 0; iy < 5; iy++) {
			int l = iy > 2 ? 4 - iy : iy;
			int[][] layer = MachineFusionTorus.layout[l];
			for(int ix = 0; ix < layer.length; ix++) for(int iz = 0; iz < layer.length; iz++) {
				int meta = layer[ix][iz];
				if(meta == 1) wallCount++;
				if(meta == 2) blanketCount++;
				if(meta == 3) pipeCount++;
			}
		}
		
		List<ItemStack> torusItems = new ArrayList();
		int plateCount = wallCount;
		while(wallCount > 0) { int a = Math.min(wallCount, 256); torusItems.add(new ItemStack(ModBlocks.fusion_component, a, 0)); wallCount -= a; }
		while(plateCount > 0) { int a = Math.min(plateCount, 256); torusItems.add(Mats.MAT_STEEL.make(ModItems.plate_cast, a)); plateCount -= a; }
		while(blanketCount > 0) { int a = Math.min(blanketCount, 256); torusItems.add(new ItemStack(ModBlocks.fusion_component, a, 2)); blanketCount -= a; }
		while(pipeCount > 0) { int a = Math.min(pipeCount, 256); torusItems.add(new ItemStack(ModBlocks.fusion_component, a, 3)); pipeCount -= a; }
		torusItems.add(new ItemStack(ModItems.blowtorch));
		for(ItemStack stack : torusItems) ItemStackUtil.addStackSizeLabel(stack);
		ItemStack[] torus = torusItems.toArray(new ItemStack[0]);
		
		bufferedRecipes.put(torus, new ItemStack(ModBlocks.fusion_torus));
		bufferedTools.put(torus, new ItemStack(ModBlocks.struct_torus_core));
		
		return recipes ? bufferedRecipes : bufferedTools;
	}
}
