package com.hbm.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes {
	
	//'Object' is either a ComparableStack or the key for the ore dict
	private static HashMap<Object, ItemStack> recipes = new HashMap();
	
	public static void register() {

		recipes.put("oreIron", new ItemStack(ModItems.crystal_iron));
		recipes.put("oreGold", new ItemStack(ModItems.crystal_gold));
		recipes.put("oreRedstone", new ItemStack(ModItems.crystal_redstone));
		recipes.put("oreUranium", new ItemStack(ModItems.crystal_uranium));
		recipes.put("oreThorium", new ItemStack(ModItems.crystal_thorium));
		recipes.put("orePlutonium", new ItemStack(ModItems.crystal_plutonium));
		recipes.put("oreTitanium", new ItemStack(ModItems.crystal_titanium));
		recipes.put("oreSulfur", new ItemStack(ModItems.crystal_sulfur));
		recipes.put("oreNiter", new ItemStack(ModItems.crystal_niter));
		recipes.put("oreSalpeter", new ItemStack(ModItems.crystal_niter));
		recipes.put("oreCopper", new ItemStack(ModItems.crystal_copper));
		recipes.put("oreTungsten", new ItemStack(ModItems.crystal_tungsten));
		recipes.put("oreAluminum", new ItemStack(ModItems.crystal_aluminium));
		recipes.put("oreFluorite", new ItemStack(ModItems.crystal_fluorite));
		recipes.put("oreBeryllium", new ItemStack(ModItems.crystal_beryllium));
		recipes.put("oreLead", new ItemStack(ModItems.crystal_lead));
		recipes.put("oreSchrabidium", new ItemStack(ModItems.crystal_schrabidium));
		recipes.put(new ComparableStack(ModBlocks.ore_rare), new ItemStack(ModItems.crystal_rare));
		recipes.put(new ComparableStack(ModBlocks.ore_nether_fire), new ItemStack(ModItems.crystal_phosphorus));
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new ItemStack(ModItems.crystal_trixite));
		recipes.put("oreLithium", new ItemStack(ModItems.crystal_lithium));
		recipes.put("oreStarmetal", new ItemStack(ModItems.crystal_starmetal));
		recipes.put("sand", new ItemStack(ModItems.ingot_fiberglass));
	}
	
	public static ItemStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp))
			return recipes.get(comp).copy();
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return recipes.get(key).copy();
		}
		
		return null;
	}

	public static Map<Object, Object> getRecipes() {
		
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
