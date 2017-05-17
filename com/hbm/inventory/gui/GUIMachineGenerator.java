package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerGenerator;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineGenerator;

public class GUIMachineGenerator extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_generator.png");
	private TileEntityMachineGenerator diFurnace;

	public GUIMachineGenerator(InventoryPlayer invPlayer, TileEntityMachineGenerator tedf) {
		super(new ContainerGenerator(invPlayer, tedf));
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
		
		if(diFurnace.hasWater()) {
			int i = diFurnace.getWaterScaled(52);
			drawTexturedModalRect(guiLeft + 8, guiTop + 88 - i, 176, 52 - i, 16, i);
		}
		
		if(diFurnace.hasCoolant()) {
			int i = diFurnace.getCoolantScaled(52);
			drawTexturedModalRect(guiLeft + 26, guiTop + 88 - i, 192, 52 - i, 16, i);
		}
		
		if(diFurnace.hasPower()) {
			int i = diFurnace.getPowerScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 88 - i, 224, 52 - i, 16, i);
		}
		
		if(diFurnace.hasHeat()) {
			int i = diFurnace.getHeatScaled(52);
			drawTexturedModalRect(guiLeft + 98, guiTop + 88 - i, 208, 52 - i, 16, i);
		}
	}
}
