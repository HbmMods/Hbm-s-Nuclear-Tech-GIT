package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.gui.GUIDiFurnace;
import com.hbm.inventory.recipes.BlastFurnaceRecipes;
import com.hbm.inventory.recipes.MachineRecipes;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class AlloyFurnaceRecipeHandler extends TemplateRecipeHandler {

	public static ArrayList<Fuel> fuels;

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input1;
		PositionedStack input2;
		PositionedStack result;

		public SmeltingSet(List<ItemStack> list, List<ItemStack> list2, ItemStack result) {
			this.input1 = new PositionedStack(list, 75, 7);
			this.input2 = new PositionedStack(list2, 75, 43);
			this.result = new PositionedStack(result, 129, 25);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input1, input2 }));
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

			this.stack = new PositionedStack(ingred, 3, 25, false);
		}

		public PositionedStack stack;
	}

	@Override
	public String getRecipeName() {
		return "Blast Furnace";
	}

	@Override
	public String getGuiTexture() {
		return GUIDiFurnace.texture.toString();
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if((outputId.equals("alloysmelting")) && getClass() == AlloyFurnaceRecipeHandler.class) {
			Map<List<ItemStack>[], ItemStack> recipes = BlastFurnaceRecipes.getRecipesForNEI();
			for(Entry<List<ItemStack>[], ItemStack> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<List<ItemStack>[], ItemStack> recipes = BlastFurnaceRecipes.getRecipesForNEI();
		for(Entry<List<ItemStack>[], ItemStack> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType(recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("alloysmelting")) && getClass() == AlloyFurnaceRecipeHandler.class) {
			loadCraftingRecipes("alloysmelting", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<List<ItemStack>[], ItemStack> recipes = BlastFurnaceRecipes.getRecipesForNEI();
		for(Entry<List<ItemStack>[], ItemStack> recipe : recipes.entrySet()) {
			List<ItemStack> combined = new ArrayList<ItemStack>();
			combined.addAll(recipe.getKey()[0]);
			combined.addAll(recipe.getKey()[1]);
			for(ItemStack combinedStack : combined)
				if(NEIServerUtils.areStacksSameType(ingredient, combinedStack) || NEIServerUtils.areStacksSameType(ingredient, combinedStack))
					this.arecipes.add(new SmeltingSet(recipe.getKey()[0], recipe.getKey()[1], recipe.getValue()));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUIDiFurnace.class;
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(96, 25, 24, 18), "alloysmelting"));
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(57, 26, 176, 0, 14, 14, 48, 7);

		drawProgressBar(96, 24, 176, 14, 24, 16, 48, 0);

		drawProgressBar(39, 7, 201, 0, 16, 52, 480, 7);
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		if(fuels == null || fuels.isEmpty())
			fuels = new ArrayList<Fuel>();
		for(ItemStack i : MachineRecipes.instance().getAlloyFuels()) {
			fuels.add(new Fuel(i));
		}
		return super.newInstance();
	}
}
