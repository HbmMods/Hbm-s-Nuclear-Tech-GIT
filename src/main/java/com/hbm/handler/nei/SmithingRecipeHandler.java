package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.hbm.inventory.AnvilRecipes;
import com.hbm.inventory.AnvilSmithingRecipe;
import com.hbm.inventory.gui.GUIAnvil;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class SmithingRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<RecipeTransferRect>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<RecipeTransferRect>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<Class<? extends GuiContainer>>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<Class<? extends GuiContainer>>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input1;
		PositionedStack input2;
		PositionedStack output;
		int tier;

		public RecipeSet(AnvilSmithingRecipe recipe) {
			this.input1 = new PositionedStack(recipe.getLeft(), 39, 24);
			this.input2 = new PositionedStack(recipe.getRight(), 75, 24);
			this.output = new PositionedStack(recipe.getOutput(input1.item, input2.item), 111, 24);
			this.tier = recipe.tier;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 48, Arrays.asList(input1, input2));
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
		
		if(outputId.equals("ntmSmithing")) {
			List<AnvilSmithingRecipe> recipes = AnvilRecipes.getSmithing();
			
			for(AnvilSmithingRecipe recipe : recipes) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		List<AnvilSmithingRecipe> recipes = AnvilRecipes.getSmithing();
		
		for(AnvilSmithingRecipe recipe : recipes) {
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getSimpleOutput(), result)) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmSmithing")) {
			loadCraftingRecipes("ntmSmithing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		List<AnvilSmithingRecipe> recipes = AnvilRecipes.getSmithing();
		
		outer:
		for(AnvilSmithingRecipe recipe : recipes) {
			
			for(ItemStack left : recipe.getLeft()) {
				if(NEIServerUtils.areStacksSameTypeCrafting(left, ingredient)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
			
			for(ItemStack right : recipe.getRight()) {
				if(NEIServerUtils.areStacksSameTypeCrafting(right, ingredient)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
		}
	}

	@Override
	public void loadTransferRects() {
		transferRectsGui = new LinkedList<RecipeTransferRect>();
		guiGui = new LinkedList<Class<? extends GuiContainer>>();

		transferRects.add(new RecipeTransferRect(new Rectangle(56, 24, 18, 18), "ntmSmithing"));
		transferRects.add(new RecipeTransferRect(new Rectangle(92, 24, 18, 18), "ntmSmithing"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(56 + 9, 24 - 9, 18, 18), "ntmSmithing"));
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(56 + 9 - 18 * 2, 24 - 9, 18, 18), "ntmSmithing"));
		guiGui.add(GUIAnvil.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {
		loadTransferRects();

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		fontRenderer.drawString("Tier " + rec.tier, 52, 43, 0x404040);
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_smithing.png";
	}
}
