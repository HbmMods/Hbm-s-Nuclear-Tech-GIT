package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Locale;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineArcWelder;
import com.hbm.inventory.recipes.ArcWelderRecipes;
import com.hbm.inventory.recipes.ArcWelderRecipes.ArcWelderRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class ArcWelderHandler extends NEIUniversalHandler {

	public ArcWelderHandler() {
		super(ModBlocks.machine_arc_welder.getLocalizedName(), ModBlocks.machine_arc_welder, ArcWelderRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmArcWelder";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(67, 26, 32, 14), "ntmArcWelder"));
		guiGui.add(GUIMachineArcWelder.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);
		Object[] original = (Object[]) rec.originalInputInstance;
		ItemStack output = rec.output[0].item;
		
		outer: for(ArcWelderRecipe arc : ArcWelderRecipes.recipes) {
			
			//checks do not include the fluid, will break of there's two recipes with identical input and output but with fluids
			if(ItemStack.areItemStacksEqual(arc.output, output) && arc.ingredients.length == original.length - (arc.fluid == null ? 0 : 1)) {
				
				for(int i = 0; i < rec.input.length - (arc.fluid == null ? 0 : 1); i++) {
					if(arc.ingredients[i] != original[i]) continue outer;
				}

				FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
				String duration = String.format(Locale.US, "%,d", arc.duration) + " ticks";
				String consumption = String.format(Locale.US, "%,d", arc.consumption) + " HE/t";
				int side = 160;
				fontRenderer.drawString(duration, side - fontRenderer.getStringWidth(duration), 43, 0x404040);
				fontRenderer.drawString(consumption, side - fontRenderer.getStringWidth(consumption), 55, 0x404040);
				return;
			}
		}
	}
}
