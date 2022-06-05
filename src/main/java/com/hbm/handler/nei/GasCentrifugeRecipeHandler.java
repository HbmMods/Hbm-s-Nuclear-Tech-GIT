package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.gui.GUIMachineGasCent;
import com.hbm.inventory.recipes.GasCentrifugeRecipes;
import com.hbm.inventory.recipes.MachineRecipes;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipeHandler extends TemplateRecipeHandler {

	public static ArrayList<Fuel> fuels;

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		List<PositionedStack> output = new ArrayList();

		public SmeltingSet(ItemStack input, ItemStack[] results) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 25, 35 - 11);
			
			for(byte i = 0; i < results.length; i++) {
				this.output.add(new PositionedStack(results[i], i % 2 == 0 ? 128 : 146, i < 2 ? 26 - 11 : 44 - 11 ));
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(fuels.get((cycleticks / 48) % fuels.size()).stack);
			stacks.addAll(output);
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return output.get(0);
		}
	}

	public static class Fuel {
		public Fuel(ItemStack ingred) {

			this.stack = new PositionedStack(ingred, 3, 42, false);
		}

		public PositionedStack stack;
	}

	@Override
	public String getRecipeName() {
		return "Gas Centrifuge";
	}

	@Override
	public String getGuiTexture() {
		return GUIMachineGasCent.texture.toString();
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUIMachineGasCent.class;
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
		if((outputId.equals("gascentprocessing")) && getClass() == GasCentrifugeRecipeHandler.class) {
			Map<Object, Object[]> recipes = GasCentrifugeRecipes.getGasCentrifugeRecipes();
			for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack) recipe.getKey(), (ItemStack[]) recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object[]> recipes = GasCentrifugeRecipes.getGasCentrifugeRecipes();
		for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[0], result) || NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[1], result)
					|| NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue()[2], result))
				this.arecipes.add(new SmeltingSet((ItemStack) recipe.getKey(), (ItemStack[]) recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("gascentprocessing")) && getClass() == GasCentrifugeRecipeHandler.class) {
			loadCraftingRecipes("gascentprocessing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object[]> recipes = GasCentrifugeRecipes.getGasCentrifugeRecipes();
		for(Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if(compareFluidStacks(ingredient, (ItemStack) recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack) recipe.getKey(), (ItemStack[]) recipe.getValue()));
		}
	}

	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(3, 51 - 45, 176, 0, 16, 34, 480, 7);
		drawProgressBar(69, 26, 208, 0, 33, 12, 200, 0);
	}

	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(69, 26, 32, 12), "gascentprocessing"));
	}
}
