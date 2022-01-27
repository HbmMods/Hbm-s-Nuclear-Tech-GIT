package com.hbm.crafting.handlers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MKUCraftingHandler implements IRecipe {
	
	public static ItemStack[] MKURecipe;
	private static long lastSeed;

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		
		if(world == null)
			return false;
		
		if(MKURecipe == null || world.getSeed() != lastSeed)
			generateRecipe(world);
		
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInRowAndColumn(i % 3, i / 3);
			ItemStack recipe = MKURecipe[i];
			
			if(stack == null && recipe == null)
				continue;
			
			if(stack != null && recipe != null && stack.getItem() == recipe.getItem() && stack.getItemDamage() == recipe.getItemDamage())
				continue;
			
			return false;
		}
		
		return true;
	}
	
	public static void generateRecipe(World world) {
		Random rand = new Random(world.getSeed());
		
		if(lastSeed == world.getSeed() && MKURecipe != null)
			return;
		
		lastSeed = world.getSeed();
		
		List<ItemStack> list = Arrays.asList(new ItemStack[] {
				new ItemStack(ModItems.powder_iodine),
				new ItemStack(ModItems.powder_fire),
				new ItemStack(ModItems.dust),
				new ItemStack(ModItems.ingot_mercury),
				new ItemStack(ModItems.morning_glory),
				new ItemStack(ModItems.syringe_metal_empty),
				null,
				null,
				null
		});
		
		Collections.shuffle(list, rand);
		
		MKURecipe = list.toArray(new ItemStack[9]);
	}

	@Override
	public int getRecipeSize() {
		return 6;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		return getRecipeOutput();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.syringe_mkunicorn);
	}
}
