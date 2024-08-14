package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.ICompatNHNEI;
import com.hbm.inventory.gui.GUIMachineRefinery;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class RefineryRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.machine_refinery)};
	}
	@Override
	public String getRecipeID() {
		return "refinery";
	}
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result1;
		PositionedStack result2;
		PositionedStack result3;
		PositionedStack result4;
		PositionedStack result5;

		public SmeltingSet(ItemStack input, ItemStack result1, ItemStack result2, ItemStack result3, ItemStack result4, ItemStack result5) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 21 + 27, 6 + 18);
			this.result1 = new PositionedStack(result1, 129 - 18, 6);
			this.result2 = new PositionedStack(result2, 147 - 18, 6 + 9);
			this.result3 = new PositionedStack(result3, 129 - 18, 42 - 18);
			this.result4 = new PositionedStack(result4, 147 - 18, 42 - 9);
			this.result5 = new PositionedStack(result5, 147 - 36, 42);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(new PositionedStack[] { input }));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(result2);
			stacks.add(result3);
			stacks.add(result4);
			stacks.add(result5);
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return result1;
		}
	}
    
	@Override
	public String getRecipeName() {
		return "Refinery";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_refinery.png";
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return null;
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return super.newInstance();
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("refinery")) && getClass() == RefineryRecipeHandler.class) {
			Map<Object, Object[]> recipes = RefineryRecipes.getRefineryRecipe();
			for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1], 
						(ItemStack)recipe.getValue()[2], (ItemStack)recipe.getValue()[3], 
						(ItemStack)recipe.getValue()[4]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object[]> recipes = RefineryRecipes.getRefineryRecipe();
		for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if (compareFluidStacks((ItemStack)recipe.getValue()[0], result) || 
					compareFluidStacks((ItemStack)recipe.getValue()[1], result) || 
					compareFluidStacks((ItemStack)recipe.getValue()[2], result) || 
					compareFluidStacks((ItemStack)recipe.getValue()[3], result) || 
					compareFluidStacks((ItemStack)recipe.getValue()[4], result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1], 
						(ItemStack)recipe.getValue()[2], (ItemStack)recipe.getValue()[3], 
						(ItemStack)recipe.getValue()[4]));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("refinery")) && getClass() == RefineryRecipeHandler.class) {
			loadCraftingRecipes("refinery", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object[]> recipes = RefineryRecipes.getRefineryRecipe();
		for (Map.Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if (compareFluidStacks(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1], 
						(ItemStack)recipe.getValue()[2], (ItemStack)recipe.getValue()[3], 
						(ItemStack)recipe.getValue()[4]));				
		}
	}
	
	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(83 - (18 * 4) - 9 + 1, 6, 0, 86, 16, 18 * 3 - 2, 480, 7);

		drawProgressBar(56 + 22, 5 + 19, 16, 86, 24, 17, 48, 0);
	}

	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();

		transferRects.add(new RecipeTransferRect(new Rectangle(48, 5, 31, 101), "refinery"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(48, 5, 31, 101), "refinery"));
		guiGui.add(GUIMachineRefinery.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

}
