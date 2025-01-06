package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Locale;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineSolderingStation;
import com.hbm.inventory.recipes.SolderingRecipes;
import com.hbm.inventory.recipes.SolderingRecipes.SolderingRecipe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class SolderingStationHandler extends NEIUniversalHandler {

	public SolderingStationHandler() {
		super(ModBlocks.machine_soldering_station.getLocalizedName(), ModBlocks.machine_soldering_station, SolderingRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmSoldering";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(67, 17, 32, 14), "ntmSoldering"));
		guiGui.add(GUIMachineSolderingStation.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);
		ItemStack output = rec.output[0].item;
		
		for(SolderingRecipe sol : SolderingRecipes.recipes) {
			
			//TODO: rethink this concept, checks only use the output and if two things output the same thing it'll break
			
			if(ItemStack.areItemStacksEqual(sol.output, output)) {

				FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
				String duration = String.format(Locale.US, "%,d", sol.duration) + " ticks";
				String consumption = String.format(Locale.US, "%,d", sol.consumption) + " HE/t";
				int side = 160;
				fontRenderer.drawString(duration, side - fontRenderer.getStringWidth(duration), 43, 0x404040);
				fontRenderer.drawString(consumption, side - fontRenderer.getStringWidth(consumption), 55, 0x404040);
				return;
			}
		}
	}
}
