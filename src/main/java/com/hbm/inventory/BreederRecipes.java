package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class BreederRecipes {

	private static HashMap<ComparableStack, BreederRecipe> recipes = new HashMap();
	private static HashMap<ComparableStack, int[]> fuels = new HashMap();
	//for the int array: [0] => level (1-4) [1] => amount of operations
	
	public static void registerRecipes() {

		//lithium and impure rods
		recipes.put(new ComparableStack(ModItems.rod_lithium), new BreederRecipe(ModItems.rod_tritium, 1));
		recipes.put(new ComparableStack(ModItems.rod_dual_lithium), new BreederRecipe(ModItems.rod_dual_tritium, 1));
		recipes.put(new ComparableStack(ModItems.rod_quad_lithium), new BreederRecipe(ModItems.rod_quad_tritium, 1));
		recipes.put(new ComparableStack(ModItems.rod_uranium), new BreederRecipe(ModItems.rod_plutonium, 4));
		recipes.put(new ComparableStack(ModItems.rod_dual_uranium), new BreederRecipe(ModItems.rod_dual_plutonium, 4));
		recipes.put(new ComparableStack(ModItems.rod_quad_uranium), new BreederRecipe(ModItems.rod_quad_plutonium, 4));
		recipes.put(new ComparableStack(ModItems.rod_plutonium), new BreederRecipe(ModItems.rod_waste, 4));
		recipes.put(new ComparableStack(ModItems.rod_dual_plutonium), new BreederRecipe(ModItems.rod_dual_waste, 4));
		recipes.put(new ComparableStack(ModItems.rod_quad_plutonium), new BreederRecipe(ModItems.rod_quad_waste, 4));
		
		//isotopes
		recipes.put(new ComparableStack(ModItems.rod_th232), new BreederRecipe(ModItems.rod_tha, 2));
		recipes.put(new ComparableStack(ModItems.rod_dual_th232), new BreederRecipe(ModItems.rod_dual_tha, 2));
		recipes.put(new ComparableStack(ModItems.rod_quad_th232), new BreederRecipe(ModItems.rod_quad_tha, 2));
		recipes.put(new ComparableStack(ModItems.rod_u233), new BreederRecipe(ModItems.rod_u235, 2));
		recipes.put(new ComparableStack(ModItems.rod_dual_u233), new BreederRecipe(ModItems.rod_dual_u235, 2));
		recipes.put(new ComparableStack(ModItems.rod_quad_u233), new BreederRecipe(ModItems.rod_quad_u235, 2));
		recipes.put(new ComparableStack(ModItems.rod_u235), new BreederRecipe(ModItems.rod_neptunium, 3));
		recipes.put(new ComparableStack(ModItems.rod_dual_u235), new BreederRecipe(ModItems.rod_dual_neptunium, 3));
		recipes.put(new ComparableStack(ModItems.rod_quad_u235), new BreederRecipe(ModItems.rod_quad_neptunium, 3));
		recipes.put(new ComparableStack(ModItems.rod_u238), new BreederRecipe(ModItems.rod_pu239, 3));
		recipes.put(new ComparableStack(ModItems.rod_dual_u238), new BreederRecipe(ModItems.rod_dual_pu239, 3));
		recipes.put(new ComparableStack(ModItems.rod_quad_u238), new BreederRecipe(ModItems.rod_quad_pu239, 3));
		recipes.put(new ComparableStack(ModItems.rod_neptunium), new BreederRecipe(ModItems.rod_pu238, 3));
		recipes.put(new ComparableStack(ModItems.rod_dual_neptunium), new BreederRecipe(ModItems.rod_dual_pu238, 3));
		recipes.put(new ComparableStack(ModItems.rod_quad_neptunium), new BreederRecipe(ModItems.rod_quad_pu238, 3));
		recipes.put(new ComparableStack(ModItems.rod_pu238), new BreederRecipe(ModItems.rod_pu239, 2));
		recipes.put(new ComparableStack(ModItems.rod_dual_pu238), new BreederRecipe(ModItems.rod_dual_pu239, 2));
		recipes.put(new ComparableStack(ModItems.rod_quad_pu238), new BreederRecipe(ModItems.rod_quad_pu239, 2));
		recipes.put(new ComparableStack(ModItems.rod_pu239), new BreederRecipe(ModItems.rod_pu240, 2));
		recipes.put(new ComparableStack(ModItems.rod_dual_pu239), new BreederRecipe(ModItems.rod_dual_pu240, 2));
		recipes.put(new ComparableStack(ModItems.rod_quad_pu239), new BreederRecipe(ModItems.rod_quad_pu240, 2));
		recipes.put(new ComparableStack(ModItems.rod_pu240), new BreederRecipe(ModItems.rod_waste, 3));
		recipes.put(new ComparableStack(ModItems.rod_dual_pu240), new BreederRecipe(ModItems.rod_dual_waste, 3));
		recipes.put(new ComparableStack(ModItems.rod_quad_pu240), new BreederRecipe(ModItems.rod_quad_waste, 3));

		//advanced
		recipes.put(new ComparableStack(ModItems.rod_schrabidium), new BreederRecipe(ModItems.rod_solinium, 3));
		recipes.put(new ComparableStack(ModItems.rod_dual_schrabidium), new BreederRecipe(ModItems.rod_dual_solinium, 3));
		recipes.put(new ComparableStack(ModItems.rod_quad_schrabidium), new BreederRecipe(ModItems.rod_quad_solinium, 3));
		recipes.put(new ComparableStack(ModItems.rod_quad_solinium), new BreederRecipe(ModItems.rod_quad_euphemium, 4));
		recipes.put(new ComparableStack(ModItems.rod_balefire), new BreederRecipe(ModItems.rod_balefire_blazing, 4));
		recipes.put(new ComparableStack(ModItems.rod_dual_balefire), new BreederRecipe(ModItems.rod_dual_balefire_blazing, 4));
		recipes.put(new ComparableStack(ModItems.rod_quad_balefire), new BreederRecipe(ModItems.rod_quad_balefire_blazing, 4));
		recipes.put(new ComparableStack(ModItems.orichalcum, 1, 2), new BreederRecipe(new ItemStack(ModItems.orichalcum, 1, 4), 4));

		//rocks
		recipes.put(new ComparableStack(Blocks.stone), new BreederRecipe(new ItemStack(ModBlocks.sellafield_0), 2));
		recipes.put(new ComparableStack(ModBlocks.sellafield_0), new BreederRecipe(new ItemStack(ModBlocks.sellafield_1), 2));
		recipes.put(new ComparableStack(ModBlocks.sellafield_1), new BreederRecipe(new ItemStack(ModBlocks.sellafield_2), 3));
		recipes.put(new ComparableStack(ModBlocks.sellafield_2), new BreederRecipe(new ItemStack(ModBlocks.sellafield_3), 3));
		recipes.put(new ComparableStack(ModBlocks.sellafield_3), new BreederRecipe(new ItemStack(ModBlocks.sellafield_4), 4));
		recipes.put(new ComparableStack(ModBlocks.sellafield_4), new BreederRecipe(new ItemStack(ModBlocks.sellafield_core), 4));
		
		recipes.put(new ComparableStack(ModItems.meteorite_sword_etched), new BreederRecipe(new ItemStack(ModItems.meteorite_sword_bred), 4));
	}
	
	public static void registerFuels() {
		fuels.put(new ComparableStack(ModItems.rod_u233), new int[] {2, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_u233), new int[] {2, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_u233), new int[] {2, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_u235), new int[] {2, 3});
		fuels.put(new ComparableStack(ModItems.rod_dual_u235), new int[] {2, 6});
		fuels.put(new ComparableStack(ModItems.rod_quad_u235), new int[] {2, 12});
		
		fuels.put(new ComparableStack(ModItems.rod_u238), new int[] {1, 1});
		fuels.put(new ComparableStack(ModItems.rod_dual_u238), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_quad_u238), new int[] {1, 4});
		
		fuels.put(new ComparableStack(ModItems.rod_neptunium), new int[] {2, 3});
		fuels.put(new ComparableStack(ModItems.rod_dual_neptunium), new int[] {2, 6});
		fuels.put(new ComparableStack(ModItems.rod_quad_neptunium), new int[] {2, 12});
		
		fuels.put(new ComparableStack(ModItems.rod_pu238), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu238), new int[] {1, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu238), new int[] {1, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_pu239), new int[] {3, 5});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu239), new int[] {3, 10});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu239), new int[] {3, 20});
		
		fuels.put(new ComparableStack(ModItems.rod_pu240), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu240), new int[] {1, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu240), new int[] {1, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_schrabidium), new int[] {3, 10});
		fuels.put(new ComparableStack(ModItems.rod_dual_schrabidium), new int[] {3, 20});
		fuels.put(new ComparableStack(ModItems.rod_quad_schrabidium), new int[] {3, 40});
		
		fuels.put(new ComparableStack(ModItems.rod_solinium), new int[] {3, 15});
		fuels.put(new ComparableStack(ModItems.rod_dual_solinium), new int[] {3, 30});
		fuels.put(new ComparableStack(ModItems.rod_quad_solinium), new int[] {3, 60});
		
		fuels.put(new ComparableStack(ModItems.rod_polonium), new int[] {4, 2});
		fuels.put(new ComparableStack(ModItems.rod_dual_polonium), new int[] {4, 4});
		fuels.put(new ComparableStack(ModItems.rod_quad_polonium), new int[] {4, 8});
		
		fuels.put(new ComparableStack(ModItems.rod_tritium), new int[] {1, 1});
		fuels.put(new ComparableStack(ModItems.rod_dual_tritium), new int[] {1, 2});
		fuels.put(new ComparableStack(ModItems.rod_quad_tritium), new int[] {1, 4});
		
		fuels.put(new ComparableStack(ModItems.rod_balefire), new int[] {2, 150});
		fuels.put(new ComparableStack(ModItems.rod_dual_balefire), new int[] {2, 300});
		fuels.put(new ComparableStack(ModItems.rod_quad_balefire), new int[] {2, 600});
		
		fuels.put(new ComparableStack(ModItems.rod_balefire_blazing), new int[] {4, 75});
		fuels.put(new ComparableStack(ModItems.rod_dual_balefire_blazing), new int[] {4, 150});
		fuels.put(new ComparableStack(ModItems.rod_quad_balefire_blazing), new int[] {4, 300});
	}
	
	public static HashMap<ItemStack, BreederRecipe> getAllRecipes() {
		
		HashMap<ItemStack, BreederRecipe> map = new HashMap();
		
		for(Map.Entry<ComparableStack, BreederRecipe> recipe : recipes.entrySet()) {
			map.put(recipe.getKey().toStack(), recipe.getValue());
		}
		
		return map;
	}
	
	public static List<ItemStack> getAllFuelsFromHEAT(int heat) {
		
		List<ItemStack> list = new ArrayList();
		
		for(Map.Entry<ComparableStack, int[]> fuel : fuels.entrySet()) {
			
			if(fuel.getValue()[0] >= heat) {
				list.add(fuel.getKey().toStack());
			}
		}
		
		return list;
	}
	
	public static BreederRecipe getOutput(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack);
		return BreederRecipes.recipes.get(sta);
	}
	
	/**
	 * Returns an integer array of the fuel value of a certain stack
	 * @param stack
	 * @return an integer array (possibly null) with two fields, the HEAT value and the amount of operations
	 */
	public static int[] getFuelValue(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		ComparableStack sta = new ComparableStack(stack);
		int[] ret = BreederRecipes.fuels.get(sta);
		
		return ret;
	}
	
	public static String getHEATString(String string, int heat) {

		if(heat == 1)
			string =  EnumChatFormatting.GREEN + string;
		if(heat == 2)
			string = EnumChatFormatting.YELLOW + string;
		if(heat == 3)
			string = EnumChatFormatting.GOLD + string;
		if(heat == 4)
			string = EnumChatFormatting.RED + string;
		
		return string; //strings are reference types I GET IT
	}
	
	//nicer than opaque object arrays
	public static class BreederRecipe {
		
		public ItemStack output;
		public int heat;
		
		public BreederRecipe() { }
		
		public BreederRecipe(Item output, int heat) {
			this(new ItemStack(output), heat);
		}
		
		public BreederRecipe(ItemStack output, int heat) {
			this.output = output;
			this.heat = heat;
		}
	}

}
