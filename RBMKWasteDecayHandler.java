package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;

import net.minecraft.item.ItemStack;

public class RBMKWasteDecayHandler extends NEIUniversalHandler {

	public RBMKWasteDecayHandler() {
		super("Nuclear Waste Decay", ModBlocks.machine_storage_drum, getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmRBMKWaste";
	}

	public static HashMap<ComparableStack, ItemStack> getRecipes() {
		HashMap<ComparableStack, ItemStack> map = new HashMap<>();

		for(ItemWasteShort.WasteClass waste : ItemWasteShort.WasteClass.values()) {
			map.put(new ComparableStack(ModItems.nuclear_waste_short, 1, waste), new ItemStack(ModItems.nuclear_waste_short_depleted, 1, waste.ordinal()));
			map.put(new ComparableStack(ModItems.nuclear_waste_short_tiny, 1, waste), new ItemStack(ModItems.nuclear_waste_short_depleted_tiny, 1, waste.ordinal()));
		}

		for(ItemWasteLong.WasteClass waste : ItemWasteLong.WasteClass.values()) {
			map.put(new ComparableStack(ModItems.nuclear_waste_long, 1, waste), new ItemStack(ModItems.nuclear_waste_long_depleted, 1, waste.ordinal()));
			map.put(new ComparableStack(ModItems.nuclear_waste_long_tiny, 1, waste), new ItemStack(ModItems.nuclear_waste_long_depleted_tiny, 1, waste.ordinal()));
		}

		return map;
	}

}
