package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.BreederRecipes.BreederRecipe;
import com.hbm.inventory.gui.GUIMachineReactor;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class ReactorRecipeHandler extends TemplateRecipeHandler {

	public class BreedingSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input;
		PositionedStack result;
		public int heat;
		public ArrayList<Fuel> fuels;

		public BreedingSet(ItemStack input, ItemStack result, int heat) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 51, 6);
			this.result = new PositionedStack(result, 111, 24);
			this.heat = heat;

			fuels = new ArrayList();

			for(ItemStack sta : BreederRecipes.getAllFuelsFromHEAT(heat)) {
				fuels.add(new Fuel(sta));
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}

		@Override
		public PositionedStack getOtherStack() {
			return fuels.get((cycleticks / 48) % fuels.size()).stack;
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}

	public static class Fuel {

		public Fuel(ItemStack ingred) {

			this.stack = new PositionedStack(ingred, 51, 42, false);
		}

		public PositionedStack stack;
	}

	@Override
	public String getRecipeName() {
		return "Breeding Reactor";
	}

	@Override
	public String getGuiTexture() {
		return GUIMachineReactor.texture.toString();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if((outputId.equals("breeding")) && getClass() == ReactorRecipeHandler.class) {

			Map<ItemStack, BreederRecipe> recipes = BreederRecipes.getAllRecipes();

			for(Map.Entry<ItemStack, BreederRecipe> recipe : recipes.entrySet()) {
				this.arecipes.add(new BreedingSet(recipe.getKey(), recipe.getValue().output, recipe.getValue().heat));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		Map<ItemStack, BreederRecipe> recipes = BreederRecipes.getAllRecipes();

		for(Map.Entry<ItemStack, BreederRecipe> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType(recipe.getValue().output, result))
				this.arecipes.add(new BreedingSet(recipe.getKey(), recipe.getValue().output, recipe.getValue().heat));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("breeding")) && getClass() == ReactorRecipeHandler.class) {
			loadCraftingRecipes("breeding", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		Map<ItemStack, BreederRecipe> recipes = BreederRecipes.getAllRecipes();

		for(Map.Entry<ItemStack, BreederRecipe> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType(ingredient, (ItemStack) recipe.getKey()))
				this.arecipes.add(new BreedingSet(recipe.getKey(), recipe.getValue().output, recipe.getValue().heat));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUIMachineReactor.class;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "breeding"));
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(50, 24, 176, 0, 14, 14, 48 * 3, 7);
		drawProgressBar(75, 23, 176, 16, 24, 16, 48, 0);

		int heat = ((BreedingSet) this.arecipes.get(recipe)).heat;
		drawProgressBar(43, 24, 194, 0, 4, 16, (float) 1 - heat / 4F, 7);
	}
}
