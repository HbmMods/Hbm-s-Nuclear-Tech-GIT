package com.hbm.inventory.recipes;

import java.util.HashMap;

import com.hbm.items.ModItems;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ElectrolysisRecipes {
	
	private static HashMap<Object, ItemStack[]> recipes = new HashMap();
	
	public static void register() {
		
		recipes.put(new ComparableStack(ModItems.crystal_aluminium), new ItemStack[] {
				new ItemStack(ModItems.ingot_aluminium, 8),
				new ItemStack(Items.iron_ingot, 4),
		});
	}
}