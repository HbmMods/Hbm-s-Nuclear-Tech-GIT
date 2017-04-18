package com.hbm.gui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.gui.container.ContainerMachineDeuterium;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineDeuterium;

public class GUIMachineDeuterium extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_deuterium.png");
	private TileEntityMachineDeuterium diFurnace;

	public GUIMachineDeuterium(InventoryPlayer invPlayer, TileEntityMachineDeuterium tedf) {
		super(new ContainerMachineDeuterium(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.water > 0) {
			int i = diFurnace.getWaterScaled(52);
			drawTexturedModalRect(guiLeft + 26, guiTop + 88 - i, 192, 52 - i, 16, i);
		}
		
		if(diFurnace.sulfur > 0) {
			int i = diFurnace.getSulfurScaled(52);
			drawTexturedModalRect(guiLeft + 44, guiTop + 88 - i, 208, 52 - i, 16, i);
		}
		
		if(diFurnace.power > 0) {
			int i = diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 8, guiTop + 88 - i, 176, 52 - i, 16, i);
		}
		
		int j1 = diFurnace.getProgressScaled(24);
		drawTexturedModalRect(guiLeft + 103, guiTop + 53, 224, 14, j1 + 1, 17);
		
		if(diFurnace.canProcess())
		{
			drawTexturedModalRect(guiLeft + 81, guiTop + 73, 224, 0, 14, 14);
		}
	}
}
