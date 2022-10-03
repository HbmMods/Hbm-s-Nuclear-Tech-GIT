package com.hbm.handler.nei;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class ChunkyHandler extends TemplateRecipeHandler {
	
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		ItemStack stack;
		
		public RecipeSet(ItemStack stack) {
			this.stack = stack.copy();
			this.stack.stackSize = 1;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
	}

	@Override
	public String getRecipeName() {
		return "The same thing but in big";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei.png";
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		this.arecipes.add(new RecipeSet(result));
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		this.arecipes.add(new RecipeSet(ingredient));
	}

	@Override
	public void drawExtras(int recipe) {
		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);
		GL11.glPushMatrix();
		double scale = 10D;
		GL11.glScaled(scale, scale, scale);
		GuiContainerManager.drawItem(50, 50, rec.stack); //TODO: center properly
		GL11.glPopMatrix();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		fontRenderer.drawString("so you can really stare at it", 52, 100, 0x404040);
	}
}
