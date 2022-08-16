package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.inventory.recipes.MachineRecipes;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CentrifugeRecipeHandler extends TemplateRecipeHandler {

	public static ArrayList<Fuel> fuels;

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result1;
		PositionedStack result2;
		PositionedStack result3;
		PositionedStack result4;

		public RecipeSet(Object input, ItemStack[] results) {
			this.input = new PositionedStack(input, 21, 6);
			this.result1 = new PositionedStack(results[0], 129, 6);
			this.result2 = new PositionedStack(results[1], 147, 6);
			this.result3 = new PositionedStack(results[2], 129, 42);
			this.result4 = new PositionedStack(results[3], 147, 42);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(input));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(fuels.get((cycleticks / 48) % fuels.size()).stack);
			stacks.add(result2);
			stacks.add(result3);
			stacks.add(result4);
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return result1;
		}
	}

	public static class Fuel {
		public Fuel(ItemStack ingred) {

			this.stack = new PositionedStack(ingred, 21, 42, false);
		}

		public PositionedStack stack;
	}

	@Override
	public String getRecipeName() {
		return "Centrifuge";
	}

	@Override
	public String getGuiTexture() {
		return GUIMachineCentrifuge.texture.toString();
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUIMachineCentrifuge.class;
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		if(fuels == null || fuels.isEmpty())
			fuels = new ArrayList<Fuel>();
		for(ItemStack i : MachineRecipes.instance().getBatteries()) {
			fuels.add(new Fuel(i));
		}
		return super.newInstance();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if((outputId.equals("centrifugeprocessing")) && getClass() == CentrifugeRecipeHandler.class) {

			Map<Object, Object[]> recipes = CentrifugeRecipes.getRecipes();

			for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new RecipeSet(recipe.getKey(), RecipesCommon.objectToStackArray(recipe.getValue())));
			}

		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		Map<Object, Object[]> recipes = CentrifugeRecipes.getRecipes();

		for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {

			if(NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[0], result) || NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[1], result)
					|| NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[2], result) || NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[3], result))
				this.arecipes.add(new RecipeSet(recipe.getKey(), RecipesCommon.objectToStackArray(recipe.getValue())));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {

		if((inputId.equals("centrifugeprocessing")) && getClass() == CentrifugeRecipeHandler.class) {

			loadCraftingRecipes("centrifugeprocessing", new Object[0]);

		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		Map<Object, Object[]> recipes = CentrifugeRecipes.getRecipes();

		for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {

			if(recipe.getKey() instanceof ItemStack) {

				if(NEIServerUtils.areStacksSameType(ingredient, (ItemStack) recipe.getKey()))
					this.arecipes.add(new RecipeSet(recipe.getKey(), RecipesCommon.objectToStackArray(recipe.getValue())));

			} else if(recipe.getKey() instanceof ArrayList) {

				for(Object o : (ArrayList) recipe.getKey()) {
					ItemStack stack = (ItemStack) o;

					if(NEIServerUtils.areStacksSameType(ingredient, stack))
						this.arecipes.add(new RecipeSet(stack, RecipesCommon.objectToStackArray(recipe.getValue())));
				}
			}
		}
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(21, 24, 195, 55, 16, 16, 48, 7);

		drawProgressBar(56, 5, 176, 0, 54, 54, 48 * 3, 0);

		drawProgressBar(3, 6, 177, 55, 16, 52, 480, 7);
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(56, 5, 54, 54), "centrifugeprocessing"));
	}

}
