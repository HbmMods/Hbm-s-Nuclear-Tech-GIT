package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerConverterRfHe;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIConverterRfHe extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_rf_he_converter.png");
	private TileEntityConverterRfHe diFurnace;

	public GUIConverterRfHe(InventoryPlayer invPlayer, TileEntityConverterRfHe tedf) {
		super(new ContainerConverterRfHe(invPlayer, tedf));
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
			drawTexturedModalRect(guiLeft + 136, guiTop + 69 - i, 188, 52 - i, 12, i);
		}
		
		if(diFurnace.storage.getEnergyStored() > 0) {
			int i = diFurnace.getFluxScaled(52);
			drawTexturedModalRect(guiLeft + 28, guiTop + 69 - i, 176, 52 - i, 12, i);
		}
	}
}
