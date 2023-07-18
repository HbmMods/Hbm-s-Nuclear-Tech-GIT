package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCustom;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCustom extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_custom.png");
	private TileEntityCustomMachine custom;

	public GUIMachineCustom(InventoryPlayer invPlayer, TileEntityCustomMachine tedf) {
		super(new ContainerMachineCustom(invPlayer, tedf));
		custom = tedf;
		
		this.xSize = 176;
		this.ySize = 256;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.custom.getInventoryName();
		this.fontRendererObj.drawString(name, 68 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				int index = i * 3 + j;
				if(custom.config.itemInCount <= index) {
					drawTexturedModalRect(guiLeft + 7 + j * 18, guiTop + 71 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
					drawTexturedModalRect(guiLeft + 7 + j * 18, guiTop + 107 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
				}
				if(custom.config.itemOutCount <= index) {
					drawTexturedModalRect(guiLeft + 77 + j * 18, guiTop + 71 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
				}
			}
		}
		
		for(int i = 0; i < 3; i++) {
			if(custom.config.fluidInCount <= i) {
				drawTexturedModalRect(guiLeft + 7 + i * 18, guiTop + 17, 192 + i * 18, 32, 18, 54);
			}
			if(custom.config.fluidOutCount <= i) {
				drawTexturedModalRect(guiLeft + 77 + i * 18, guiTop + 17, 192 + i * 18, 32, 18, 36);
			}
		}
	}

}
