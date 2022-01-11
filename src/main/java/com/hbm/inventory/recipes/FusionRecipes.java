package com.hbm.inventory.recipes;

import java.util.HashMap;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class FusionRecipes {
	
	public static int getByproductChance(FluidTypeTheOldOne plasma) {
		
		switch(plasma) {
		case PLASMA_DT: return 1200;
		case PLASMA_DH3: return 600;
		case PLASMA_HD: return 1200;
		case PLASMA_HT: return 1200;
		case PLASMA_XM: return 2400;
		case PLASMA_BF: return 150;
		default: return 0;
		}
	}
	
	public static int getBreedingLevel(FluidTypeTheOldOne plasma) {
		
		switch(plasma) {
		case PLASMA_DT: return 1;
		case PLASMA_DH3: return 2;
		case PLASMA_HD: return 1;
		case PLASMA_HT: return 1;
		case PLASMA_XM: return 3; 
		case PLASMA_BF: return 4;
		default: return 0;
		}
	}
	
	public static ItemStack getByproduct(FluidTypeTheOldOne plasma) {
		
		switch(plasma) {
		case PLASMA_DT: return new ItemStack(ModItems.pellet_charged);
		case PLASMA_DH3: return new ItemStack(ModItems.pellet_charged);
		case PLASMA_HD: return new ItemStack(ModItems.pellet_charged);
		case PLASMA_HT: return new ItemStack(ModItems.pellet_charged);
		case PLASMA_XM: return new ItemStack(ModItems.powder_chlorophyte);
		case PLASMA_BF: return new ItemStack(ModItems.powder_balefire);
		default: return null;
		}
	}
	
	public static int getSteamProduction(FluidTypeTheOldOne plasma) {
		
		switch(plasma) {
		case PLASMA_DT: return 30;
		case PLASMA_DH3: return 50;
		case PLASMA_HD: return 20;
		case PLASMA_HT: return 25;
		case PLASMA_XM: return 60; 
		case PLASMA_BF: return 160;
		default: return 0;
		}
	}
	
	public static HashMap<ItemStack, ItemStack> getRecipes() {
		
		HashMap<ItemStack, ItemStack> map = new HashMap();

		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidTypeTheOldOne.PLASMA_DT.ordinal()), getByproduct(FluidTypeTheOldOne.PLASMA_DT));
		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidTypeTheOldOne.PLASMA_DH3.ordinal()), getByproduct(FluidTypeTheOldOne.PLASMA_DH3));
		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidTypeTheOldOne.PLASMA_HD.ordinal()), getByproduct(FluidTypeTheOldOne.PLASMA_HD));
		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidTypeTheOldOne.PLASMA_HT.ordinal()), getByproduct(FluidTypeTheOldOne.PLASMA_HT));
		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidTypeTheOldOne.PLASMA_XM.ordinal()), getByproduct(FluidTypeTheOldOne.PLASMA_XM));
		map.put(new ItemStack(ModItems.fluid_icon, 1, FluidTypeTheOldOne.PLASMA_BF.ordinal()), getByproduct(FluidTypeTheOldOne.PLASMA_BF));
		
		return map;
	}

}
