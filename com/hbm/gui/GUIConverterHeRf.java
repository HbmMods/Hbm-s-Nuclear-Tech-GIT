package com.hbm.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.TileEntityConverterHeRf;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIConverterHeRf extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_he_rf_converter.png");
	private TileEntityConverterHeRf diFurnace;

	public GUIConverterHeRf(InventoryPlayer invPlayer, TileEntityConverterHeRf tedf) {
		super(new ContainerConverterHeRf(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 86;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.power > 0) {
			int i = diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 28, guiTop + 69 - i, 176, 52 - i, 12, i);
		}
		
		if(diFurnace.storage.getEnergyStored() > 0) {
			int i = diFurnace.getFluxScaled(52);
			drawTexturedModalRect(guiLeft + 136, guiTop + 69 - i, 188, 52 - i, 12, i);
		}
	}
}
