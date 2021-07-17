package com.hbm.inventory;

import com.hbm.inventory.RecipesCommon.AStack;

import net.minecraft.item.ItemStack;

public class AnvilSmithingRecipe {
	
	public int tier;
	ItemStack output;
	AStack left;
	AStack right;
	boolean shapeless = false;
	
	public AnvilSmithingRecipe(int tier, ItemStack out, AStack left, AStack right) {
		this.tier = tier;
		this.output = out;
		this.left = left;
		this.right = right;
	}
	
	public AnvilSmithingRecipe makeShapeless() {
		this.shapeless = true;
		return this;
	}
	
	public boolean matches(ItemStack left, ItemStack right) {
		return matchesInt(left, right) != -1;
	}
	
	public int matchesInt(ItemStack left, ItemStack right) {
		
		if(doesStackMatch(left, this.left) && doesStackMatch(right, this.right))
			return 0;
		
		if(shapeless) {
			return doesStackMatch(right, this.left) && doesStackMatch(left, this.right) ? 1 : -1;
		}
		
		return -1;
	}
	
	public boolean doesStackMatch(ItemStack input, AStack recipe) {
		return recipe.matchesRecipe(input, false);
	}
	
	public ItemStack getOutput(ItemStack left, ItemStack right) {
		return output.copy();
	}
	
	public int amountConsumed(int index, boolean mirrored) {
		
		if(index == 0)
			return mirrored ? right.stacksize : left.stacksize;
		if(index == 1)
			return mirrored ? left.stacksize : right.stacksize;
		
		return 0;
	}
}
