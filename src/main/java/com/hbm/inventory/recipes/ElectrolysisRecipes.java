package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.SILEXRecipes.SILEXRecipe;

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
	
	public static class ElectrolysisRecipe {
		public int primaryMetalHex;
		public int secondaryMetalHex;
		public List<WeightedRandomObject> outputs = new ArrayList();
		
		public ElectrolysisRecipe(int primaryMetalHex, int secondaryMetalHex) {
			this.primaryMetalHex = primaryMetalHex;
			this.secondaryMetalHex = secondaryMetalHex;
		}
		
		public ElectrolysisRecipe addOut(WeightedRandomObject entry) {
			outputs.add(entry);
			return this;
		}
	}
}