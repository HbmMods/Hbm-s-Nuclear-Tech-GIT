package com.hbm.inventory;

import java.util.HashMap;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class FusionRecipes {
	
	public static int getByproductChance(FluidType plasma) {
		
		switch(plasma) {
		case PLASMA_XM: return 3 * 60 * 20; 
		case PLASMA_BF: return 10 * 60 * 20;
		default: return 0;
		}
	}
	
	public static int getBreedingLevel(FluidType plasma) {
		
		switch(plasma) {
		case PLASMA_DT: return 1;
		case PLASMA_HD: return 1;
		case PLASMA_HT: return 1;
		case PLASMA_XM: return 3; 
		case PLASMA_BF: return 4;
		default: return 0;
		}
	}
	
	public static ItemStack getByproduct(FluidType plasma) {
		
		switch(plasma) {
		case PLASMA_XM: return new ItemStack(ModItems.powder_chlorophyte); 
		case PLASMA_BF: return new ItemStack(ModItems.powder_balefire);
		default: return null;
		}
	}
	
	public static int getSteamProduction(FluidType plasma) {
		
		switch(plasma) {
		case PLASMA_DT: return 30;
		case PLASMA_HD: return 20;
		case PLASMA_HT: return 25;
		case PLASMA_XM: return 60; 
		case PLASMA_BF: return 160;
		default: return 0;
		}
	}
	
	public static HashMap<ItemStack, ItemStack> getRecipes() {
		
		HashMap<ItemStack, ItemStack> map = new HashMap();

		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidType.PLASMA_XM.ordinal()), new ItemStack(ModItems.powder_chlorophyte));
		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidType.PLASMA_BF.ordinal()), new ItemStack(ModItems.powder_balefire));
		
		return map;
	}

}
