package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class CrystallizerRecipeHandler extends NEIUniversalHandler {

	public CrystallizerRecipeHandler() {
		super("Acidizer", ModBlocks.machine_crystallizer, CrystallizerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCrystallizer";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(80 - 5, 47 - 11, 27, 12), "ntmCrystallizer"));
		guiGui.add(GUICrystallizer.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);
		
		CrystallizerRecipe cRecipe = CrystallizerRecipes.getOutput(rec.input[1].item, Fluids.fromID(rec.input[0].item.getItemDamage()));
		
		if(cRecipe != null && cRecipe.productivity > 0) {
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			String momentum = "Effectiveness: +" + Math.min((int) (cRecipe.productivity * 100), 99) + "% per level";
			int side = 8;
			fontRenderer.drawString(momentum, side, 52, 0x404040);
		}
	}
}
