package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.AnvilRecipes;
import com.hbm.inventory.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.inventory.AnvilRecipes.AnvilOutput;
import com.hbm.lib.RefStrings;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class AnvilRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		
		PositionedStack output;
		int tier;

		public RecipeSet(ItemStack output, int tier) {
			this.output = new PositionedStack(output, 111, 24);
			this.tier = tier;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(output));
		}

		@Override
		public PositionedStack getResult() {
			return output;
		}
	}

	@Override
	public String getRecipeName() {
		return "Anvil";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmAnvil")) {
			List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
			
			for(AnvilConstructionRecipe recipe : recipes) {
				this.arecipes.add(new RecipeSet(recipe.output.get(0).stack.copy(), recipe.tierLower));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
		
		for(AnvilConstructionRecipe recipe : recipes) {
			
			for(AnvilOutput out : recipe.output) {
				if(NEIServerUtils.areStacksSameTypeCrafting(out.stack, result)) {
					this.arecipes.add(new RecipeSet(recipe.output.get(0).stack.copy(), recipe.tierLower));
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmAnvil")) {
			loadCraftingRecipes("ntmAnvil", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
	}

	@Override
	public void loadTransferRects() {
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				fontRenderer.drawString("Tier " + rec.tier + " anvil", 51 + i, 50 + j, 0x000000);
		fontRenderer.drawString("Tier " + rec.tier + " anvil", 52, 51, 0xffffff);
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_anvil.png";
	}
	
	@Override
	public void drawBackground(int recipeIndex) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 5, 11, 166, 120);
	}
}
