package com.hbm.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.TileEntityDiFurnace;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GUITestDiFurnace extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIDiFurnace.png");
	private TileEntityDiFurnace diFurnace;

	public GUITestDiFurnace(InventoryPlayer invPlayer, TileEntityDiFurnace tedf) {
		super(new ContainerDiFurnace(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
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
		
		if(diFurnace.hasPower())
		{
			int i1 = diFurnace.getPowerRemainingScaled(56);
			drawTexturedModalRect(guiLeft + 44, guiTop + 70 - i1, 201, 53 - i1, 16, i1);
		}
		
		int j1 = diFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(guiLeft + 101, guiTop + 35, 176, 14, j1 + 1, 17);
		
		if(diFurnace.hasPower() && diFurnace.canProcess()) {
			drawTexturedModalRect(guiLeft + 63, guiTop + 37, 176, 0, 14, 14);
		}
	}

}
