package com.hbm.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.TileEntityCoreTitanium;
import com.hbm.blocks.TileEntityMachineGenerator;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUICoreTitanium extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/factory_titanium.png");
	private TileEntityCoreTitanium diFurnace;

	public GUICoreTitanium(InventoryPlayer invPlayer, TileEntityCoreTitanium tedf) {
		super(new ContainerCoreTitanium(invPlayer, tedf));
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
		
		if(diFurnace.power > 0) {
			int i = diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 72, 0, 240, i, 16);
		}
		
		if(diFurnace.progress > 0) {
			int j = diFurnace.getProgressScaled(90);
			drawTexturedModalRect(guiLeft + 43, guiTop + 53, 0, 222, j, 18);
		}
	}
}
