package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Locale;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIVacuumCircuit;
import com.hbm.inventory.recipes.VacuumCircuitRecipes.VacuumCircuitRecipe;
import com.hbm.inventory.recipes.VacuumCircuitRecipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class VacuumCircuitHandler extends NEIUniversalHandler {

    public VacuumCircuitHandler() {
		super("Vacuum Solderer", ModBlocks.machine_vacuum_circuit, VacuumCircuitRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmVacuumCircuit";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(45, 38, 32, 14), "ntmVacuumCircuit"));
		guiGui.add(GUIVacuumCircuit.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);
		ItemStack output = rec.output[0].item;
		
		for(VacuumCircuitRecipe sol : VacuumCircuitRecipes.recipes) {
			
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
