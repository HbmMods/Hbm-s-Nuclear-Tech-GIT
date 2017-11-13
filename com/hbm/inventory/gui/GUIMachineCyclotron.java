package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCyclotron extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_cyclotron.png");
	private TileEntityMachineCyclotron diFurnace;

	public GUIMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tedf) {
		super(new ContainerMachineCyclotron(invPlayer, tedf));
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

		int k = diFurnace.getPowerScaled(88);
		drawTexturedModalRect(guiLeft + 152, guiTop + 106 - k, 212, 88 - k, 16, k);

		int l = diFurnace.getProgressScaled(36);
		drawTexturedModalRect(guiLeft + 61, guiTop + 26, 176, 0, l, 36);
	}
}
