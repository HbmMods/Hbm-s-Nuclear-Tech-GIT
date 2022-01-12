package com.hbm.inventory.recipes;

import java.util.HashMap;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class FuelPoolRecipes {
	
	public static final HashMap<ComparableStack, ItemStack> recipes = new HashMap<ComparableStack, ItemStack>();
	
	public static void register() {
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_natural_uranium, 1, 1)), new ItemStack(ModItems.waste_natural_uranium));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_uranium, 1, 1)), new ItemStack(ModItems.waste_uranium));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_thorium, 1, 1)), new ItemStack(ModItems.waste_thorium));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_mox, 1, 1)), new ItemStack(ModItems.waste_mox));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plutonium, 1, 1)), new ItemStack(ModItems.waste_plutonium));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_u233, 1, 1)), new ItemStack(ModItems.waste_u233));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_u235, 1, 1)), new ItemStack(ModItems.waste_u235));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_schrabidium, 1, 1)), new ItemStack(ModItems.waste_schrabidium));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_u233, 1, 1)), new ItemStack(ModItems.waste_plate_u233));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_u235, 1, 1)), new ItemStack(ModItems.waste_plate_u235));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_mox, 1, 1)), new ItemStack(ModItems.waste_plate_mox));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_pu239, 1, 1)), new ItemStack(ModItems.waste_plate_pu239));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_sa326, 1, 1)), new ItemStack(ModItems.waste_plate_sa326));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_ra226be, 1, 1)), new ItemStack(ModItems.waste_plate_ra226be));
		recipes.put(new ComparableStack(new ItemStack(ModItems.waste_plate_pu238be, 1, 1)), new ItemStack(ModItems.waste_plate_pu238be));
	}
}
