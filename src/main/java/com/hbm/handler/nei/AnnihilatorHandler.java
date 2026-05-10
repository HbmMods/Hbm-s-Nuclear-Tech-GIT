package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.AnnihilatorRecipes;
import com.hbm.items.ModItems;
import com.hbm.util.InventoryUtil;
import com.hbm.util.Tuple.Pair;

import codechicken.nei.NEIServerUtils;
import net.minecraft.item.ItemStack;

public class AnnihilatorHandler extends NEIUniversalHandler {

	public AnnihilatorHandler() {
		super("Annihilator", ModBlocks.machine_annihilator, AnnihilatorRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmAnnihilating";
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		outer: for(Pair<Object, Object> recipe : recipes) {
			ItemStack[][] ins = InventoryUtil.extractObject(recipe.getKey());
			ItemStack[][] outs = InventoryUtil.extractObject(recipe.getValue());
			
			for(ItemStack[] array : ins) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
			for(ItemStack[] array : outs) for(ItemStack stack : array) if(stack.getItem() == ModItems.item_secret) continue outer;
			
			match:
			for(ItemStack[] array : outs) {
				for(ItemStack stack : array) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, result) && ItemStack.areItemStackTagsEqual(stack, result)) {
						this.arecipes.add(new RecipeSet(ins, outs, recipe.getKey()));
						break match;
					}
				}
			}
		}
	}
}
