package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

public class DeuteriumHandler extends NEIUniversalHandler {

	public DeuteriumHandler() {
		super(ModBlocks.machine_deuterium_extractor.getLocalizedName(), new ItemStack[] { new ItemStack(ModBlocks.machine_deuterium_extractor), new ItemStack(ModBlocks.machine_deuterium_tower) }, generateRecipes());
	}

	@Override
	public String getKey() {
		return "ntmDeuterium";
	}

	public static HashMap<Object, Object> generateRecipes() {
		HashMap<Object, Object> map = new HashMap();
		map.put(ItemFluidIcon.make(Fluids.WATER, 1_000), ItemFluidIcon.make(Fluids.HEAVYWATER, 20));
		return map;
	}
}
