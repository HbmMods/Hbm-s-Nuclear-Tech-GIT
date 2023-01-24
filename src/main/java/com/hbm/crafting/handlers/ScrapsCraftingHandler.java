package com.hbm.crafting.handlers;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ScrapsCraftingHandler implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		
		MaterialStack mat = null;
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInRowAndColumn(i % 3, i / 3);
			
			if(stack == null) continue;
			if(stack.getItem() != ModItems.scraps) return false;
			if(mat != null) return false;
			
			mat = ItemScraps.getMats(stack);
			if(mat.amount < 2) return false;
		}
		
		return mat != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		
		MaterialStack mat = null;
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInRowAndColumn(i % 3, i / 3);
			
			if(stack == null) continue;
			if(stack.getItem() != ModItems.scraps) return null;
			if(mat != null) return null;
			
			mat = ItemScraps.getMats(stack);
			if(mat.amount < 2) return null;
		}
		
		if(mat == null) return null;
		
		ItemStack scrap = ItemScraps.create(new MaterialStack(mat.material, mat.amount / 2));
		scrap.stackSize = 2;
		return scrap;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.scraps);
	}
}
