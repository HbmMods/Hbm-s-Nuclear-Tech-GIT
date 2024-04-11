package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CrucibleSmeltingHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.machine_crucible)};
	}
	@Override
	public String getRecipeID() {
		return "ntmCrucibleSmelting";
	}
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input;
		PositionedStack crucible;
		List<PositionedStack> outputs = new ArrayList();
		
		public RecipeSet(AStack input, List<ItemStack> outputs) {
			this.input = new PositionedStack(input.extractForNEI(), 48, 24);
			this.crucible = new PositionedStack(new ItemStack(ModBlocks.machine_crucible), 75, 42);
			
			for(int i = 0; i < outputs.size(); i++) {
				PositionedStack pos = new PositionedStack(outputs.get(i), 102 + (i % 3) * 18, 6 + (i / 3) * 18);
				this.outputs.add(pos);
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, Arrays.asList(input));
		}

		@Override
		public PositionedStack getResult() {
			return outputs.get(0);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList();
			other.add(input);
			other.add(crucible);
			other.addAll(outputs);
			return getCycledIngredients(cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return "Crucible Smelting";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_crucible_smelting.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmCrucibleSmelting")) {
			
			HashMap<AStack, List<ItemStack>> smelting = CrucibleRecipes.getSmeltingRecipes();
			
			for(Entry<AStack, List<ItemStack>> recipe : smelting.entrySet()) {
				this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<AStack, List<ItemStack>> smelting = CrucibleRecipes.getSmeltingRecipes();

		for(Entry<AStack, List<ItemStack>> recipe : smelting.entrySet()) {
			
			for(ItemStack stack : recipe.getValue()) {
				
				if(NEIServerUtils.areStacksSameTypeCrafting(stack, result)) {
					this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
					break;
				}
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmCrucibleSmelting")) {
			loadCraftingRecipes("ntmCrucibleSmelting", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		HashMap<AStack, List<ItemStack>> smelting = CrucibleRecipes.getSmeltingRecipes();

		for(Entry<AStack, List<ItemStack>> recipe : smelting.entrySet()) {
			if(recipe.getKey().matchesRecipe(ingredient, true)) {
				this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
			}
		}
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(65, 23, 36, 18), "ntmCrucibleSmelting"));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
	}
}
