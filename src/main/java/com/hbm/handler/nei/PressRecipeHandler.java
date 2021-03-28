package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.gui.GUIMachineEPress;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class PressRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result;

		public ArrayList<Fuel> fuels = new ArrayList<Fuel>();

		public SmeltingSet(List<ItemStack> stamp, ItemStack input, ItemStack result) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 83 - 35, 5 + 36 + 1);
			this.result = new PositionedStack(result, 83 + 28, 5 + 18 + 1);

			if(stamp.isEmpty())
				fuels.add(new Fuel(new ItemStack(ModItems.nothing)));
			else
				for(ItemStack sta : stamp)
					fuels.add(new Fuel(sta));
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(fuels.get((cycleticks / 24) % fuels.size()).stack);
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return result;
		}
	}

	public static class Fuel {
		public Fuel(ItemStack ingred) {

			this.stack = new PositionedStack(ingred, 83 - 35, 6, false);
		}

		public PositionedStack stack;
	}

	@Override
	public String getRecipeName() {
		return "Press";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_press.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if((outputId.equals("pressing")) && getClass() == PressRecipeHandler.class) {
			Map<Object[], Object> recipes = MachineRecipes.instance().getPressRecipes();
			for(Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((List<ItemStack>) recipe.getKey()[0], (ItemStack) recipe.getKey()[1], (ItemStack) recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object> recipes = MachineRecipes.instance().getPressRecipes();
		for(Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType((ItemStack) recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet((List<ItemStack>) recipe.getKey()[0], (ItemStack) recipe.getKey()[1], (ItemStack) recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("pressing")) && getClass() == PressRecipeHandler.class) {
			loadCraftingRecipes("pressing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object> recipes = MachineRecipes.instance().getPressRecipes();
		for(Map.Entry<Object[], Object> recipe : recipes.entrySet()) {

			boolean b = false;
			for(int i = 0; i < ((List<ItemStack>) recipe.getKey()[0]).size(); i++) {
				if(NEIServerUtils.areStacksSameType(((List<ItemStack>) recipe.getKey()[0]).get(i), ingredient)) {
					b = true;
					break;
				}
			}

			if(b || NEIServerUtils.areStacksSameType(ingredient, (ItemStack) recipe.getKey()[1]))
				this.arecipes.add(new SmeltingSet((List<ItemStack>) recipe.getKey()[0], (ItemStack) recipe.getKey()[1], (ItemStack) recipe.getValue()));
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

		transferRects.add(new RecipeTransferRect(new Rectangle(74 + 6, 23, 24, 18), "pressing"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(74 + 6 + 18, 23, 24, 18), "pressing"));
		// guiRec.add(GuiRecipe.class);
		guiGui.add(GUIMachinePress.class);
		guiGui.add(GUIMachineEPress.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		// RecipeTransferRectHandler.registerRectsToGuis(guiRec,
		// transferRectsRec);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);

		// for(Class<? extends GuiContainer> r : getRecipeTransferRectGuis())
		// System.out.println(r.toString());
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(47, 24, 0, 86, 18, 18, 20, 1);
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return super.newInstance();
	}
}
