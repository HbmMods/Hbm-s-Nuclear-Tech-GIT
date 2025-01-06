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
import com.hbm.inventory.gui.GUIRadiolysis;
import com.hbm.inventory.recipes.RadiolysisRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class RadiolysisRecipeHandler extends TemplateRecipeHandler implements ICompatNHNEI {

	@Override
	public ItemStack[] getMachinesForRecipe() {
		return new ItemStack[]{
				new ItemStack(ModBlocks.machine_radiolysis)};
	}
	@Override
	public String getRecipeID() {
		return "ntmRadiolysis";
	}
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();
		
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack output1;
		PositionedStack output2;
		
		public RecipeSet(ItemStack input, ItemStack output1, ItemStack output2) {
			input.stackSize = 1;
			this.input = new PositionedStack(input, 34, 25);
			this.output1 = new PositionedStack(output1, 118, 16);
			this.output2 = new PositionedStack(output2, 118, 34);
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 20, Arrays.asList(input));
		}
		
		@Override
		public PositionedStack getResult() {
			return output1;
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<PositionedStack>();
			stacks.add(output2);
			return stacks;
		}
	}

	@Override
	public String getRecipeName() {
		return "Radiolysis";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_radiolysis.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmRadiolysis")) {
			HashMap<Object, Object[]> recipes = (HashMap<Object, Object[]>) RadiolysisRecipes.getRecipesForNEI();
			
			for(Entry<Object, Object[]> recipe : recipes.entrySet()) {
				this.arecipes.add(new RecipeSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1]));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<Object, Object[]> recipes = (HashMap<Object, Object[]>) RadiolysisRecipes.getRecipesForNEI();
		
		for(Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if(compareFluidStacks((ItemStack)recipe.getValue()[0], result) || compareFluidStacks((ItemStack)recipe.getValue()[1], result))
				this.arecipes.add(new RecipeSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1]));
		}
	}
	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmRadiolysis")) {
			loadCraftingRecipes("ntmRadiolysis", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		HashMap<Object, Object[]> recipes = (HashMap<Object, Object[]>) RadiolysisRecipes.getRecipesForNEI();
		
		for(Entry<Object, Object[]> recipe : recipes.entrySet()) {
			if(compareFluidStacks((ItemStack)recipe.getKey(), ingredient))
				this.arecipes.add(new RecipeSet((ItemStack)recipe.getKey(), (ItemStack)recipe.getValue()[0], (ItemStack)recipe.getValue()[1]));
		}
	}

	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}
	
	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(52, 19, 5, 87, 64, 28, 60, 0);
	}
	
	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();
		
		transferRects.add(new RecipeTransferRect(new Rectangle(52, 19, 64, 27), "ntmRadiolysis"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(66, 25, 25, 14), "ntmRadiolysis"));
		guiGui.add(GUIRadiolysis.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
	
}
