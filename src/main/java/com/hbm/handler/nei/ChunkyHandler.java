package com.hbm.handler.nei;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
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
		return "";
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
		drawTexturedModalRect(145, 0, 20, 20, 20, 20);
		GL11.glPushMatrix();
		GL11.glTranslated(83, 50, 0);
		double scale = 5D;
		GL11.glScaled(scale, scale, scale);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glTranslated(-8, -8, 0);
		GuiContainerManager.drawItem(0, 0, rec.stack);
		GL11.glPopMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		
		int w = 83;
		String top = "The same thing but in big";
		String bottom = "so you can really stare at it";
		font.drawString(top, w - font.getStringWidth(top) / 2, 100, 0x404040);
		font.drawString(bottom, w - font.getStringWidth(bottom) / 2, 110, 0x404040);
	}
}
