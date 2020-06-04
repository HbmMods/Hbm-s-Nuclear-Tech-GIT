package com.hbm.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes {
	
	//'Object' is either a ComparableStack or the key for th ore dict
	private static HashMap<Object, ItemStack> recipes = new HashMap();
	
	public static void register() {
		
		recipes.put("oreIron", new ItemStack(Items.iron_ingot));
	}
	
	public static ItemStack getOutput(ItemStack stack) {
		
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

	public Map<Object, Object> getRecipes() {
		
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(Entry<Object, ItemStack> entry : CrystallizerRecipes.recipes.entrySet()) {
			
			if(entry.getKey() instanceof String) {
				List<ItemStack> ingredients = OreDictionary.getOres((String)entry.getKey());
				recipes.put(ingredients, entry.getValue());
			} else {
				recipes.put(((ComparableStack)entry.getKey()).toStack(), entry.getValue());
			}
		}
		
		return recipes;
	}

}
