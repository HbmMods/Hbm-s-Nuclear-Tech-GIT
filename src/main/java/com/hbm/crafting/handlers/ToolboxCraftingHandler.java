package com.hbm.crafting.handlers;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemKitNBT;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ToolboxCraftingHandler implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		
		int itemCount = 0;
		int kitCount = 0;
		
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInRowAndColumn(i % 3, i / 3);
			
			if(stack != null) {
				
				if(stack.getItem().hasContainerItem(stack) || !stack.getItem().doesContainerItemLeaveCraftingGrid(stack))
					return false;
				
				itemCount++;
				
				if(stack.getItem() == ModItems.kit_toolbox_empty) {
					kitCount++;
				}
			}
		}
		
		return itemCount > 1 && kitCount == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		
		List<ItemStack> stacks = new ArrayList();
		
		for(int i = 0; i < 9; i++) {
			ItemStack stack = inventory.getStackInRowAndColumn(i % 3, i / 3);
			
			if(stack != null && stack.getItem() != ModItems.kit_toolbox_empty) {
				ItemStack copy = stack.copy();
				copy.stackSize = 1;
				stacks.add(copy);
			}
		}
		
		return ItemKitNBT.create(stacks.toArray(new ItemStack[0]));
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.kit_toolbox);
	}
}
