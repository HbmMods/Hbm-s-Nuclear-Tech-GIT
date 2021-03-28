package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.inventory.gui.GUIMachineShredder;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class ShredderRecipeHandler extends TemplateRecipeHandler {

	public static ArrayList<Fuel> fuels;

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result;

		public SmeltingSet(ItemStack input, ItemStack result) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 83 - 27 - 18 + 1, 5 + 18 + 1);
			this.result = new PositionedStack(result, 83 + 27 + 18 + 1, 5 + 18 + 1);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(fuels.get((cycleticks / 24) % fuels.size()).stack0);
			stacks.add(fuels.get((cycleticks / 24) % fuels.size()).stack1);
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}

	public static class Fuel {
		public Fuel(ItemStack ingred) {

			this.stack0 = new PositionedStack(ingred, 83 + 1, 5 + 1, false);
			this.stack1 = new PositionedStack(ingred, 83 + 1, 5 + 36 + 1, false);
		}

		public PositionedStack stack0;
		public PositionedStack stack1;
	}

	@Override
	public String getRecipeName() {
		return "Shredder";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_shredder.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if((outputId.equals("shredding")) && getClass() == ShredderRecipeHandler.class) {
			Map<Object, Object> recipes = ShredderRecipes.getShredderRecipes();
			for(Map.Entry<Object, Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(((ComparableStack) recipe.getKey()).toStack(), (ItemStack) recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object> recipes = ShredderRecipes.getShredderRecipes();
		for(Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet(((ComparableStack) recipe.getKey()).toStack(), (ItemStack) recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("shredding")) && getClass() == ShredderRecipeHandler.class) {
			loadCraftingRecipes("shredding", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object> recipes = ShredderRecipes.getShredderRecipes();
		for(Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType(ingredient, ((ComparableStack) recipe.getKey()).toStack()))
				this.arecipes.add(new SmeltingSet(((ComparableStack) recipe.getKey()).toStack(), (ItemStack) recipe.getValue()));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		// return GUIMachineShredder.class;
		return null;
	}

	@Override
	public void loadTransferRects() {
		// transferRectsRec = new LinkedList<RecipeTransferRect>();
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		// guiRec = new LinkedList<Class<? extends GuiContainer>>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();

		transferRects.add(new RecipeTransferRect(new Rectangle(74 + 6, 23, 24, 18), "shredding"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(63 - 7 + 4, 89 - 11, 34, 18), "shredding"));
		// guiRec.add(GuiRecipe.class);
		guiGui.add(GUIMachineShredder.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		// RecipeTransferRectHandler.registerRectsToGuis(guiRec,
		// transferRectsRec);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);

		// for(Class<? extends GuiContainer> r : getRecipeTransferRectGuis())
		// System.out.println(r.toString());
	}

	@Override
	public void drawExtras(int recipe) {
		/*
		 * //Top drawTexturedModalRect(83, 5, 0, 140, 18, 18); //Bottom
		 * drawTexturedModalRect(83, 5 + 36, 0, 140, 18, 18); //Right
		 * drawTexturedModalRect(83 + 27 + 18, 5 + 18, 0, 140, 18, 18); //Left
		 * drawTexturedModalRect(83 - 27 - 18, 5 + 18, 0, 140, 18, 18);
		 * //Progress drawTexturedModalRect(83 - 3, 5 + 19, 100, 102, 24, 16);
		 * //Power drawTexturedModalRect(83 - (18 * 4) - 9, 5, 0, 86, 18, 18 *
		 * 3);
		 */

		drawProgressBar(83 - (18 * 4) - 9 + 1, 6, 36, 86, 16, 18 * 3 - 2, 480, 7);

		drawProgressBar(83 - 3, 5 + 18, 100, 118, 24, 16, 48, 0);
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		if(fuels == null || fuels.isEmpty())
			fuels = new ArrayList<Fuel>();
		for(ItemStack i : MachineRecipes.instance().getBlades()) {
			fuels.add(new Fuel(i));
		}
		return super.newInstance();
	}
}
