package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SILEXRecipes {
	
	private static HashMap<Object, SILEXRecipe> recipes = new HashMap();
	
	public static void register() {
		
		recipes.put("ingotSteel", new SILEXRecipe(100, 100)
				.addOut(new WeightedRandomObject(new ItemStack(Items.iron_ingot), 1))
				.addOut(new WeightedRandomObject(new ItemStack(Items.coal), 1))
				);
		
		recipes.put("ingotUranium", new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u235), 1))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_u238), 8))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_pu_mix), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 6))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 3))
				);
		
		recipes.put("ingotPlutonium", new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu238), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu239), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_pu240), 2))
				);
		
		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 4))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 3))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);
		
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new SILEXRecipe(900, 100)
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_schrabidium), 5))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_uranium), 2))
				.addOut(new WeightedRandomObject(new ItemStack(ModItems.nugget_plutonium), 2))
				);
	}
	
	public static SILEXRecipe getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return recipes.get(key);
		}
		
		return null;
	}
	
	public static class SILEXRecipe {
		
		public int fluidProduced;
		public int fluidConsumed;
		public List<WeightedRandomObject> outputs = new ArrayList();
		
		public SILEXRecipe(int fluidProduced, int fluidConsumed) {
			this.fluidProduced = fluidProduced;
			this.fluidConsumed = fluidConsumed;
		}
		
		public SILEXRecipe addOut(WeightedRandomObject entry) {
			outputs.add(entry);
			return this;
		}
	}
}
