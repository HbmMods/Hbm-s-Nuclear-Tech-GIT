package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.gui.GUIMachineReactorBreeding;
import com.hbm.inventory.recipes.BreederRecipes;
import com.hbm.inventory.recipes.BreederRecipes.BreederRecipe;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class BreederRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.machine_reactor_breeding)};
	}

	@Override
	public String getRecipeID() {
		return "breeding";
	}
	public class BreedingSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input;
		PositionedStack result;
		public int flux;

		public BreedingSet(ItemStack input, ItemStack result, int flux) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 30, 24);
			this.result = new PositionedStack(result, 120, 24);
			this.flux = flux;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}
		
		@Override
		public PositionedStack getResult() {
			return result;
		}
	}

	@Override
	public String getRecipeName() {
		return "Breeding Reactor";
	}

	@Override
	public String getGuiTexture() {
		return GUIMachineReactorBreeding.texture.toString();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if((outputId.equals("breeding")) && getClass() == BreederRecipeHandler.class) {

			Map<ItemStack, BreederRecipe> recipes = BreederRecipes.getAllRecipes();

			for(Map.Entry<ItemStack, BreederRecipe> recipe : recipes.entrySet()) {
				this.arecipes.add(new BreedingSet(recipe.getKey(), recipe.getValue().output, recipe.getValue().flux));
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
				this.arecipes.add(new BreedingSet(recipe.getKey(), recipe.getValue().output, recipe.getValue().flux));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("breeding")) && getClass() == BreederRecipeHandler.class) {
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
				this.arecipes.add(new BreedingSet(recipe.getKey(), recipe.getValue().output, recipe.getValue().flux));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUIMachineReactorBreeding.class;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(68, 9, 30, 37), "breeding"));
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(48, 21, 176, 0, 70, 20, 50, 0);
		
		String flux = ((BreedingSet) this.arecipes.get(recipe)).flux + "";
		GuiDraw.drawString(flux, 83 - GuiDraw.fontRenderer.getStringWidth(flux) / 2, 10, 0x08FF00);
	}
}
