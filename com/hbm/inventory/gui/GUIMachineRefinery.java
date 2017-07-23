package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRefinery;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineRefinery;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRefinery extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_refinery.png");
	private TileEntityMachineRefinery diFurnace;

	public GUIMachineRefinery(InventoryPlayer invPlayer, TileEntityMachineRefinery tedf) {
		super(new ContainerMachineRefinery(invPlayer, tedf));
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

		int i = diFurnace.getOilScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 70 - i, 192, 52 - i, 34, i);
		int j = diFurnace.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - j, 176, 52 - j, 16, j);
		int k = diFurnace.getSmearScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 124 - k, 176, 104 - k, 7, k);
		int l = diFurnace.getLubricantScaled(52);
		drawTexturedModalRect(guiLeft + 44, guiTop + 124 - l, 183, 104 - l, 7, l);
		int m = diFurnace.getDieselScaled(52);
		drawTexturedModalRect(guiLeft + 80, guiTop + 124 - m, 190, 104 - m, 7, m);
		int n = diFurnace.getKeroseneScaled(52);
		drawTexturedModalRect(guiLeft + 116, guiTop + 124 - n, 197, 104 - n, 7, n);
	}
}
