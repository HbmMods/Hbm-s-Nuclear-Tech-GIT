package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class AnvilRecipes {
	
	private static List<AnvilSmithingRecipe> smithingRecipes = new ArrayList();
	
	public static void register() {
		
		smithingRecipes.add(new AnvilSmithingRecipe(2, new ItemStack(ModItems.plate_steel, 2),
				new OreDictStack("ingotSteel"), new OreDictStack("ingotSteel")));

		Block[] anvils = new Block[]{ModBlocks.anvil_iron, ModBlocks.anvil_lead};
		
		for(Block anvil : anvils) {
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_bismuth, 1),
					new ComparableStack(anvil), new ComparableStack(ModItems.ingot_bismuth, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_dnt, 1),
					new ComparableStack(anvil), new ComparableStack(ModItems.ingot_dineutronium, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_ferrouranium, 1),
					new ComparableStack(anvil), new ComparableStack(ModItems.ingot_u238, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_meteorite, 1),
					new ComparableStack(anvil), new ComparableStack(ModItems.ingot_meteorite, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_schrabidate, 1),
					new ComparableStack(anvil), new ComparableStack(ModItems.ingot_schrabidate, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_starmetal, 1),
					new ComparableStack(anvil), new ComparableStack(ModItems.ingot_starmetal, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_steel, 1),
					new ComparableStack(anvil), new OreDictStack("ingotSteel", 10)));
		}
		
		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.gun_ar15, 1),
				new ComparableStack(ModItems.gun_thompson), new ComparableStack(ModItems.pipe_lead)));
	}
	
	public static List<AnvilSmithingRecipe> getSmithing() {
		return smithingRecipes;
	}
	
	public static class AnvilSmithingRecipe {
		
		int tier;
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
			return recipe.matchesRecipe(input);
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
}
