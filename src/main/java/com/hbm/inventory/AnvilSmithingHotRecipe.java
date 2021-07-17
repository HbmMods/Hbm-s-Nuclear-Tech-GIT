package com.hbm.inventory;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.special.ItemHot;

import net.minecraft.item.ItemStack;

public class AnvilSmithingHotRecipe extends AnvilSmithingRecipe {

	public AnvilSmithingHotRecipe(int tier, ItemStack out, AStack left, AStack right) {
		super(tier, out, left, right);
	}
	
	public boolean doesStackMatch(ItemStack input, AStack recipe) {
		
		if(input != null && input.getItem() instanceof ItemHot) {
			double heat = ItemHot.getHeat(input);
			
			if(heat < 0.5D)
				return false;
		}
		
		return recipe.matchesRecipe(input, false);
	}
	
	public ItemStack getOutput(ItemStack left, ItemStack right) {
		
		if(left.getItem() instanceof ItemHot && right.getItem() instanceof ItemHot && output.getItem() instanceof ItemHot) {
			
			double h1 = ItemHot.getHeat(left);
			double h2 = ItemHot.getHeat(right);
			
			ItemStack out = output.copy();
			ItemHot.heatUp(out, (h1 + h2) / 2D);
			
			return out;
		}
		
		return output.copy();
	}
}
