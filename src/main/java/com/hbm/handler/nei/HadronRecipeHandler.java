package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hbm.inventory.HadronRecipes;
import com.hbm.inventory.HadronRecipes.HadronRecipe;
import com.hbm.inventory.gui.GUIHadron;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class HadronRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input1;
		PositionedStack input2;
		PositionedStack output1;
		PositionedStack output2;
		int momentum;
		boolean analysisOnly;

		public RecipeSet(HadronRecipe recipe) {

			this.input1 = new PositionedStack(recipe.in1.toStack(), 12, 24);
			this.input2 = new PositionedStack(recipe.in2.toStack(), 30, 24);
			this.output1 = new PositionedStack(recipe.out1, 84, 24);
			this.output2 = new PositionedStack(recipe.out2, 102, 24);
			this.momentum = recipe.momentum;
			this.analysisOnly = recipe.analysisOnly;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return Arrays.asList(new PositionedStack[] { input1, input2 });
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return Arrays.asList(new PositionedStack[] { output1, output2 });
		}

		@Override
		public PositionedStack getResult() {
			return output1;
		}
	}

	@Override
	public String getRecipeName() {
		return "Particle Accelerator";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if(outputId.equals("hadron") && getClass() == HadronRecipeHandler.class) {

			List<HadronRecipe> recipes = HadronRecipes.getRecipes();

			for(HadronRecipe recipe : recipes) {
				this.arecipes.add(new RecipeSet(recipe));
			}

		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		List<HadronRecipe> recipes = HadronRecipes.getRecipes();

		for(HadronRecipe recipe : recipes) {

			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.out1, result) || NEIServerUtils.areStacksSameTypeCrafting(recipe.out2, result)) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {

		if(inputId.equals("hadron") && getClass() == HadronRecipeHandler.class) {
			loadCraftingRecipes("hadron", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		List<HadronRecipe> recipes = HadronRecipes.getRecipes();

		for(HadronRecipe recipe : recipes) {

			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.in1.toStack(), ingredient) || NEIServerUtils.areStacksSameTypeCrafting(recipe.in2.toStack(), ingredient)) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		}
	}

	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();

		transferRects.add(new RecipeTransferRect(new Rectangle(58 - 5, 34 - 11, 24, 18), "hadron"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(72 - 5, 28 - 11, 30, 30), "hadron"));
		guiGui.add(GUIHadron.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		if(rec.analysisOnly)
			drawTexturedModalRect(128, 23, 0, 86, 18, 18);

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		String mom = "" + rec.momentum;
		fontRenderer.drawString(mom, -fontRenderer.getStringWidth(mom) / 2 + 30, 42, 0x404040);
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_hadron.png";
	}
}
