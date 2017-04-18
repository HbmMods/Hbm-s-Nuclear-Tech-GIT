package com.hbm.gui.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.gui.container.ContainerMachineCMBFactory;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineCMBFactory;
import com.hbm.tileentity.TileEntityMachineShredder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCMBFactory extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_cmb_manufactory.png");
	private TileEntityMachineCMBFactory diFurnace;

	public GUIMachineCMBFactory(InventoryPlayer invPlayer, TileEntityMachineCMBFactory tedf) {
		super(new ContainerMachineCMBFactory(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
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
			drawTexturedModalRect(guiLeft + 8, guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		if(diFurnace.waste > 0) {
			int i = diFurnace.getWasteScaled(52);
			drawTexturedModalRect(guiLeft + 26, guiTop + 69 - i, 192, 52 - i, 16, i);
		}
		
		int j1 = diFurnace.getProgressScaled(24);
		drawTexturedModalRect(guiLeft + 101, guiTop + 34, 208, 0, j1 + 1, 16);
	}
}
