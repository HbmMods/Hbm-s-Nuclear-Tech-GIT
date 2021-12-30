package com.hbm.inventory.recipes;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class FuelPoolRecipes {
	
	public static final HashMap<ComparableStack, ItemStack> recipes = new HashMap<ComparableStack, ItemStack>();
	
	public static void register() {
		recipes.put(new ComparableStack(ModItems.waste_natural_uranium_hot), new ItemStack(ModItems.waste_natural_uranium));
		recipes.put(new ComparableStack(ModItems.waste_uranium_hot), new ItemStack(ModItems.waste_uranium));
		recipes.put(new ComparableStack(ModItems.waste_thorium_hot), new ItemStack(ModItems.waste_thorium));
		recipes.put(new ComparableStack(ModItems.waste_mox_hot), new ItemStack(ModItems.waste_mox));
		recipes.put(new ComparableStack(ModItems.waste_plutonium_hot), new ItemStack(ModItems.waste_plutonium));
		recipes.put(new ComparableStack(ModItems.waste_u233_hot), new ItemStack(ModItems.waste_u233));
		recipes.put(new ComparableStack(ModItems.waste_u235_hot), new ItemStack(ModItems.waste_u235));
		recipes.put(new ComparableStack(ModItems.waste_schrabidium_hot), new ItemStack(ModItems.waste_schrabidium));
	}
}
