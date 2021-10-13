package com.hbm.crafting.handlers;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class TestCraftingHandler implements IRecipe {

	ItemStack input;
	ItemStack output;
	
	public TestCraftingHandler(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		
		ItemStack inGrid = null;

		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				
				ItemStack stack = inventory.getStackInRowAndColumn(j, i);
				
				if(stack != null) {
					
					if(inGrid == null)
						inGrid = stack;
					else
						return false;
				}
			}
		}
		
		return inGrid != null && inGrid.getItem() == input.getItem() && inGrid.getItemDamage() == input.getItemDamage();
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		return output.copy();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

}
