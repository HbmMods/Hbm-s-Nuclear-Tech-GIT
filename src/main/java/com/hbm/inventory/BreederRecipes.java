package com.hbm.inventory;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class BreederRecipes {

	private static HashMap<ComparableStack, ItemStack> recipes = new HashMap();
	private static HashMap<ComparableStack, int[]> fuels = new HashMap();
	
	public static void registerRecipes() {
		
	}
	
	public static void registerFuels() {
		fuels.put(new ComparableStack(ModItems.rod_u233), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_u233), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_u233), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_u235), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_u235), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_u235), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_u238), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_u238), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_u238), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_neptunium), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_neptunium), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_neptunium), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_pu238), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu238), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu238), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_pu239), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu239), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu239), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_pu240), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_dual_pu240), new int[] {0, 0});
		fuels.put(new ComparableStack(ModItems.rod_quad_pu240), new int[] {0, 0});
	}

}
