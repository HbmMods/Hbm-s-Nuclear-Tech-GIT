package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.FuelPoolRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.item.ItemStack;

public class FuelPoolHandler extends TemplateRecipeHandler {

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		
		PositionedStack input;
		PositionedStack output;

		public RecipeSet(ItemStack in, ItemStack out) {
			this.input = new PositionedStack(in, 48, 24);
			this.output = new PositionedStack(out, 102, 24);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(input));
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return Arrays.asList(new PositionedStack(new ItemStack(ModBlocks.machine_waste_drum), 75, 31));
		}
	}

	@Override
	public String getRecipeName() {
		return "Spent Fuel Pool Drum";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei.png";
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		drawTexturedModalRect(47, 23, 5, 87, 18, 18);
		drawTexturedModalRect(101, 23, 5, 87, 18, 18);
		drawTexturedModalRect(74, 14, 59, 87, 18, 38);
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmSpentDrum")) {
			
			for(Entry<ComparableStack, ItemStack> recipe : FuelPoolRecipes.recipes.entrySet()) {
				this.arecipes.add(new RecipeSet(recipe.getKey().toStack(), recipe.getValue()));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		for(Entry<ComparableStack, ItemStack> recipe : FuelPoolRecipes.recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				this.arecipes.add(new RecipeSet(recipe.getKey().toStack(), recipe.getValue()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmSpentDrum")) {
			loadCraftingRecipes("ntmSpentDrum", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		for(Entry<ComparableStack, ItemStack> recipe : FuelPoolRecipes.recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey().toStack(), ingredient)) {
				this.arecipes.add(new RecipeSet(recipe.getKey().toStack(), recipe.getValue()));
			}
		}
	}
}
