package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;

import net.minecraft.item.ItemStack;

public class PWRRecipeHandler extends NEIUniversalHandler {

	public PWRRecipeHandler() {
		super(ModBlocks.pwr_controller.getLocalizedName(), ModBlocks.pwr_controller, getFuelRecipes());
	}

	@Override
	public String getKey() {
		return "ntmPWR";
	}
	
	public static HashMap getFuelRecipes() {
		
		HashMap<ItemStack, ItemStack> map = new HashMap();
		
		for(EnumPWRFuel fuel : EnumPWRFuel.values()) {
			map.put(new ItemStack(ModItems.pwr_fuel, 1, fuel.ordinal()), new ItemStack(ModItems.pwr_fuel_hot, 1, fuel.ordinal()));
		}
		
		return map;
	}
}
