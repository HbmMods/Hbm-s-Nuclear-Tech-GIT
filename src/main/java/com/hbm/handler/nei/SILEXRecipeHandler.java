package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.SILEXRecipes;
import com.hbm.inventory.SILEXRecipes.SILEXRecipe;
import com.hbm.inventory.gui.GUISILEX;
import com.hbm.lib.RefStrings;
import com.hbm.util.WeightedRandomObject;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class SILEXRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input;
		List<PositionedStack> outputs;
		List<Double> chances;

		public RecipeSet(Object input, SILEXRecipe recipe) {
			
			this.input = new PositionedStack(input, 30, 24);
			this.outputs = new ArrayList<PositionedStack>();
			this.chances = new ArrayList<Double>();
			
			double weight = 0;
			
			for(WeightedRandomObject obj : recipe.outputs) {
				weight += obj.itemWeight;
			}
			
			int off = 0;
			
			for(WeightedRandomObject obj : recipe.outputs) {
				outputs.add(new PositionedStack(obj.asStack(), 93, 24 + off - 9 * ((recipe.outputs.size() + 1) / 2)));
				chances.add(100 * obj.itemWeight / weight);
				off += 18;
			}
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(input));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return outputs;
		}

		@Override
		public PositionedStack getResult() {
			return outputs.get(0);
		}
	}

	@Override
	public String getRecipeName() {
		return "SILEX";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if(outputId.equals("silex") && getClass() == SILEXRecipeHandler.class) {

			Map<Object, SILEXRecipe> recipes = SILEXRecipes.getRecipes();
			
			for (Map.Entry<Object, SILEXRecipe> recipe : recipes.entrySet()) {
				this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
			}

		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		Map<Object, SILEXRecipe> recipes = SILEXRecipes.getRecipes();

		for(Map.Entry<Object, SILEXRecipe> recipe : recipes.entrySet()) {

			for(WeightedRandomObject out : recipe.getValue().outputs) {
				
				if(NEIServerUtils.areStacksSameTypeCrafting(out.asStack(), result)) {
					this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {

		if(inputId.equals("silex") && getClass() == SILEXRecipeHandler.class) {
			loadCraftingRecipes("silex", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		Map<Object, SILEXRecipe> recipes = SILEXRecipes.getRecipes();

		for(Map.Entry<Object, SILEXRecipe> recipe : recipes.entrySet()) {
			
			if(recipe.getKey() instanceof ItemStack) {

				if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()))
					this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
				
			} else if (recipe.getKey() instanceof ArrayList) {
				
				for(Object o : (ArrayList)recipe.getKey()) {
					ItemStack stack = (ItemStack)o;

					if (NEIServerUtils.areStacksSameType(ingredient, stack))
						this.arecipes.add(new RecipeSet(stack, recipe.getValue()));
				}
			}
		}
	}

	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();

		transferRects.add(new RecipeTransferRect(new Rectangle(60, 34 - 11, 24, 18), "silex"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(39, 60, 60, 50), "silex"));
		guiGui.add(GUISILEX.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		int off = 0;
		for(Double chance : rec.chances) {
			fontRenderer.drawString(((int)(chance * 10D) / 10D) + "%", 112, 28 + off - 9 * ((rec.chances.size() + 1) / 2), 0x404040);
			off += 18;
		}
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_silex.png";
	}
}
