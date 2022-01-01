package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class BreederRecipes {

	private static HashMap<ComparableStack, BreederRecipe> recipes = new HashMap();
	
	public static void registerRecipes() {

		//lithium and impure rods
		recipes.put(new ComparableStack(ModItems.rod_lithium), new BreederRecipe(ModItems.rod_tritium, 100));
		recipes.put(new ComparableStack(ModItems.rod_dual_lithium), new BreederRecipe(ModItems.rod_dual_tritium, 200));
		recipes.put(new ComparableStack(ModItems.rod_quad_lithium), new BreederRecipe(ModItems.rod_quad_tritium, 400));
		recipes.put(new ComparableStack(ModItems.rod_uranium), new BreederRecipe(ModItems.rod_plutonium, 1000));
		recipes.put(new ComparableStack(ModItems.rod_dual_uranium), new BreederRecipe(ModItems.rod_dual_plutonium, 2000));
		recipes.put(new ComparableStack(ModItems.rod_quad_uranium), new BreederRecipe(ModItems.rod_quad_plutonium, 4000));
		recipes.put(new ComparableStack(ModItems.rod_plutonium), new BreederRecipe(ModItems.rod_waste, 500));
		recipes.put(new ComparableStack(ModItems.rod_dual_plutonium), new BreederRecipe(ModItems.rod_dual_waste, 1000));
		recipes.put(new ComparableStack(ModItems.rod_quad_plutonium), new BreederRecipe(ModItems.rod_quad_waste, 2000));
		
		//isotopes
		recipes.put(new ComparableStack(ModItems.rod_th232), new BreederRecipe(ModItems.rod_u233, 1000));
		recipes.put(new ComparableStack(ModItems.rod_dual_th232), new BreederRecipe(ModItems.rod_dual_u233, 2000));
		recipes.put(new ComparableStack(ModItems.rod_quad_th232), new BreederRecipe(ModItems.rod_quad_u233, 4000));
		recipes.put(new ComparableStack(ModItems.rod_u233), new BreederRecipe(ModItems.rod_u235, 1000));
		recipes.put(new ComparableStack(ModItems.rod_dual_u233), new BreederRecipe(ModItems.rod_dual_u235, 2000));
		recipes.put(new ComparableStack(ModItems.rod_quad_u233), new BreederRecipe(ModItems.rod_quad_u235, 4000));
		recipes.put(new ComparableStack(ModItems.rod_u235), new BreederRecipe(ModItems.rod_neptunium, 500));
		recipes.put(new ComparableStack(ModItems.rod_dual_u235), new BreederRecipe(ModItems.rod_dual_neptunium, 1000));
		recipes.put(new ComparableStack(ModItems.rod_quad_u235), new BreederRecipe(ModItems.rod_quad_neptunium, 2000));
		recipes.put(new ComparableStack(ModItems.rod_u238), new BreederRecipe(ModItems.rod_pu239, 1000));
		recipes.put(new ComparableStack(ModItems.rod_dual_u238), new BreederRecipe(ModItems.rod_dual_pu239, 2000));
		recipes.put(new ComparableStack(ModItems.rod_quad_u238), new BreederRecipe(ModItems.rod_quad_pu239, 4000));
		recipes.put(new ComparableStack(ModItems.rod_neptunium), new BreederRecipe(ModItems.rod_pu238, 250));
		recipes.put(new ComparableStack(ModItems.rod_dual_neptunium), new BreederRecipe(ModItems.rod_dual_pu238, 500));
		recipes.put(new ComparableStack(ModItems.rod_quad_neptunium), new BreederRecipe(ModItems.rod_quad_pu238, 1000));
		recipes.put(new ComparableStack(ModItems.rod_pu238), new BreederRecipe(ModItems.rod_pu239, 1000));
		recipes.put(new ComparableStack(ModItems.rod_dual_pu238), new BreederRecipe(ModItems.rod_dual_pu239, 2000));
		recipes.put(new ComparableStack(ModItems.rod_quad_pu238), new BreederRecipe(ModItems.rod_quad_pu239, 4000));
		recipes.put(new ComparableStack(ModItems.rod_pu239), new BreederRecipe(ModItems.rod_pu240, 500));
		recipes.put(new ComparableStack(ModItems.rod_dual_pu239), new BreederRecipe(ModItems.rod_dual_pu240, 1000));
		recipes.put(new ComparableStack(ModItems.rod_quad_pu239), new BreederRecipe(ModItems.rod_quad_pu240, 2000));
		recipes.put(new ComparableStack(ModItems.rod_pu240), new BreederRecipe(ModItems.rod_waste, 500));
		recipes.put(new ComparableStack(ModItems.rod_dual_pu240), new BreederRecipe(ModItems.rod_dual_waste, 1000));
		recipes.put(new ComparableStack(ModItems.rod_quad_pu240), new BreederRecipe(ModItems.rod_quad_waste, 2000));

		//advanced
		recipes.put(new ComparableStack(ModItems.rod_schrabidium), new BreederRecipe(ModItems.rod_solinium, 2000));
		recipes.put(new ComparableStack(ModItems.rod_dual_schrabidium), new BreederRecipe(ModItems.rod_dual_solinium, 4000));
		recipes.put(new ComparableStack(ModItems.rod_quad_schrabidium), new BreederRecipe(ModItems.rod_quad_solinium, 8000));
		recipes.put(new ComparableStack(ModItems.rod_quad_solinium), new BreederRecipe(ModItems.rod_quad_euphemium, 2000));
		recipes.put(new ComparableStack(ModItems.rod_balefire), new BreederRecipe(ModItems.rod_balefire_blazing, 2000));
		recipes.put(new ComparableStack(ModItems.rod_dual_balefire), new BreederRecipe(ModItems.rod_dual_balefire_blazing, 4000));
		recipes.put(new ComparableStack(ModItems.rod_quad_balefire), new BreederRecipe(ModItems.rod_quad_balefire_blazing, 8000));

		//rocks
		recipes.put(new ComparableStack(Blocks.stone), new BreederRecipe(new ItemStack(ModBlocks.sellafield_0), 250));
		recipes.put(new ComparableStack(ModBlocks.sellafield_0), new BreederRecipe(new ItemStack(ModBlocks.sellafield_1), 250));
		recipes.put(new ComparableStack(ModBlocks.sellafield_1), new BreederRecipe(new ItemStack(ModBlocks.sellafield_2), 500));
		recipes.put(new ComparableStack(ModBlocks.sellafield_2), new BreederRecipe(new ItemStack(ModBlocks.sellafield_3), 500));
		recipes.put(new ComparableStack(ModBlocks.sellafield_3), new BreederRecipe(new ItemStack(ModBlocks.sellafield_4), 1000));
		recipes.put(new ComparableStack(ModBlocks.sellafield_4), new BreederRecipe(new ItemStack(ModBlocks.sellafield_core), 1000));
		
		recipes.put(new ComparableStack(ModItems.meteorite_sword_etched), new BreederRecipe(new ItemStack(ModItems.meteorite_sword_bred), 1000));
	}
	
	public static HashMap<ItemStack, BreederRecipe> getAllRecipes() {
		
		HashMap<ItemStack, BreederRecipe> map = new HashMap();
		
		for(Map.Entry<ComparableStack, BreederRecipe> recipe : recipes.entrySet()) {
			map.put(recipe.getKey().toStack(), recipe.getValue());
		}
		
		return map;
	}
	
	public static BreederRecipe getOutput(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack);
		return BreederRecipes.recipes.get(sta);
	}
	
	//nicer than opaque object arrays
	public static class BreederRecipe {
		
		public ItemStack output;
		public int flux;
		
		public BreederRecipe(Item output, int flux) {
			this(new ItemStack(output), flux);
		}
		
		public BreederRecipe(ItemStack output, int flux) {
			this.output = output;
			this.flux = flux;
		}
	}

}
