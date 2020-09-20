package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIIGenerator extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_igenerator.png");
	private TileEntityMachineIGenerator diFurnace;

	public GUIIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator tedf) {
		super(new ContainerIGenerator(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 106 - 88, 16, 88, diFurnace.power, diFurnace.maxPower);
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

		if(diFurnace.fuel > 0)
		{
			int j = diFurnace.getFuelScaled(88);
			drawTexturedModalRect(guiLeft + 122, guiTop + 106 - j, 176, 88 - j, 4, j);
		}
		if(diFurnace.lubricant > 0)
		{
			int j = diFurnace.getLubeScaled(88);
			drawTexturedModalRect(guiLeft + 128, guiTop + 106 - j, 180, 88 - j, 4, j);
		}
		if(diFurnace.water > 0)
		{
			int j = diFurnace.getWaterScaled(88);
			drawTexturedModalRect(guiLeft + 134, guiTop + 106 - j, 184, 88 - j, 4, j);
		}
		if(diFurnace.heat > 0)
		{
			int j = diFurnace.getHeatScaled(88);
			drawTexturedModalRect(guiLeft + 140, guiTop + 106 - j, 188, 88 - j, 4, j);
		}
		if(diFurnace.torque > 0)
		{
			int j = diFurnace.getTorqueScaled(88);
			drawTexturedModalRect(guiLeft + 146, guiTop + 106 - j, 192, 88 - j, 4, j);
		}
		if(diFurnace.power > 0)
		{
			int j = (int)diFurnace.getPowerScaled(88);
			drawTexturedModalRect(guiLeft + 152, guiTop + 106 - j, 196, 88 - j, 16, j);
		}
		if(diFurnace.burn > 0)
		{
			drawTexturedModalRect(guiLeft + 62, guiTop + 90, 212, 0, 18, 18);
		}
		if(diFurnace.getHeatScaled(100) < 90 && diFurnace.fuel > 0) {
			drawTexturedModalRect(guiLeft + 62 + 18 + 18, guiTop + 90 - 18 - 18, 212, 0, 18, 18);
		}
		if(diFurnace.water <= 0) {
			drawTexturedModalRect(guiLeft + 12, guiTop + 59, 230, 0, 6, 6);
		} else {
			drawTexturedModalRect(guiLeft + 12, guiTop + 59 + 24, 230, 0, 6, 6);
		}
	}
}
