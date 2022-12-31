package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerWatzCore;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_watz_multiblock.png");
	private TileEntityWatzCore diFurnace;

	public GUIWatzCore(InventoryPlayer invPlayer, TileEntityWatzCore tedf) {
		super(new ContainerWatzCore(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		//this.ySize = 222;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 106 - 18 - 70, 16, 70);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 106 - 70, 16, 70 - 18, diFurnace.power, diFurnace.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory")/* + String.valueOf(diFurnace.powerList)*/, 8, this.ySize - 96 + 2 - 34, 4210752);
		this.fontRendererObj.drawString(String.valueOf(diFurnace.powerList + " HE/tick"), 8, this.ySize - 50 + 2 + 13, 4210752);
		this.fontRendererObj.drawString(String.valueOf(diFurnace.heatList + " heat"), 8, this.ySize - 50 + 2 + 22, 4210752);
		this.fontRendererObj.drawString(String.valueOf((diFurnace.decayMultiplier * diFurnace.heat)/100 /100 + " waste/tick"), 8, this.ySize - 50 + 2 + 31, 4210752);
		this.fontRendererObj.drawString(String.valueOf(diFurnace.powerMultiplier + "% power"), 100, this.ySize - 50 + 2 + 13, 4210752);
		this.fontRendererObj.drawString(String.valueOf(diFurnace.heatMultiplier + "% heat"), 100, this.ySize - 50 + 2 + 22, 4210752);
		this.fontRendererObj.drawString(String.valueOf(diFurnace.decayMultiplier + "% decay"), 100, this.ySize - 50 + 2 + 31, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int l = (int)diFurnace.getPowerScaled(70);
		drawTexturedModalRect(guiLeft + 152, guiTop + 106 - 18 - l, 192, 70 - l, 16, l);
		
		diFurnace.tank.renderTank(guiLeft + 134, guiTop + 106 - 18, this.zLevel, 16, 70);
	}
}
