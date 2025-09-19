package com.hbm.handler.nei;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;

import net.minecraft.item.ItemStack;

public class WatzRecipeHandler extends NEIUniversalHandler {

	public WatzRecipeHandler() {
		super(ModBlocks.watz.getLocalizedName(), ModBlocks.watz, getFuelRecipes());
	}

	@Override
	public String getKey() {
		return "ntmWatz";
	}
	
	public static HashMap getFuelRecipes() {
		
		HashMap<ItemStack, ItemStack> map = new HashMap();
		
		for(EnumWatzType fuel : EnumWatzType.values()) {
			map.put(new ItemStack(ModItems.watz_pellet, 1, fuel.ordinal()), new ItemStack(ModItems.watz_pellet_depleted, 1, fuel.ordinal()));
		}
		
		return map;
	}
}
