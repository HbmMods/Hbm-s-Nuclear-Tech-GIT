package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class FusionRecipes {
	
	public static HashMap<FluidType, Integer> delays = new HashMap();
	static {
		delays.put(Fluids.PLASMA_DT, 900);
		delays.put(Fluids.PLASMA_DH3, 600);
		delays.put(Fluids.PLASMA_HD, 1200);
		delays.put(Fluids.PLASMA_HT, 900);
		delays.put(Fluids.PLASMA_XM, 1200);
		delays.put(Fluids.PLASMA_BF, 150);
	}
	
	public static int getByproductDelay(FluidType plasma) {
		Integer delay = delays.get(plasma);
		return delay != null ? delay : 0;
	}
	
	public static HashMap<FluidType, Integer> levels = new HashMap();
	static {
		levels.put(Fluids.PLASMA_DT, 1000);
		levels.put(Fluids.PLASMA_DH3, 2000);
		levels.put(Fluids.PLASMA_HD, 1000);
		levels.put(Fluids.PLASMA_HT, 1000);
		levels.put(Fluids.PLASMA_XM, 3000);
		levels.put(Fluids.PLASMA_BF, 4000);
	}
	
	public static int getBreedingLevel(FluidType plasma) {
		Integer level = levels.get(plasma);
		return level != null ? level : 0;
	}
	
	public static HashMap<FluidType, ItemStack> byproducts = new HashMap();
	static {
		byproducts.put(Fluids.PLASMA_DT, new ItemStack(ModItems.pellet_charged));
		byproducts.put(Fluids.PLASMA_DH3, new ItemStack(ModItems.pellet_charged));
		byproducts.put(Fluids.PLASMA_HD, new ItemStack(ModItems.pellet_charged));
		byproducts.put(Fluids.PLASMA_HT, new ItemStack(ModItems.pellet_charged));
		byproducts.put(Fluids.PLASMA_XM, new ItemStack(ModItems.powder_chlorophyte));
		byproducts.put(Fluids.PLASMA_BF, new ItemStack(ModItems.powder_balefire));
	}
	
	public static ItemStack getByproduct(FluidType plasma) {
		ItemStack byproduct = byproducts.get(plasma);
		return byproduct != null ? byproduct.copy() : null;
	}
	
	public static HashMap<FluidType, Integer> steamprod = new HashMap();
	static {
		steamprod.put(Fluids.PLASMA_DT, 30);
		steamprod.put(Fluids.PLASMA_DH3, 50);
		steamprod.put(Fluids.PLASMA_HD, 20);
		steamprod.put(Fluids.PLASMA_HT, 25);
		steamprod.put(Fluids.PLASMA_XM, 60);
		steamprod.put(Fluids.PLASMA_BF, 160);
	}
	
	public static int getSteamProduction(FluidType plasma) {
		Integer steam = steamprod.get(plasma);
		return steam != null ? steam : 0;
	}
	
	public static HashMap<ItemStack, ItemStack> getRecipes() {
		
		HashMap<ItemStack, ItemStack> map = new HashMap();
		for(Entry<FluidType, ItemStack> entry : byproducts.entrySet()) {
			map.put(new ItemStack(ModItems.fluid_icon, 1, entry.getKey().getID()), entry.getValue().copy());
		}
		return map;
	}

}
