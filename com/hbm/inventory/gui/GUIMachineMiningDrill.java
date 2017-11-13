package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineMiningDrill;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineMiningDrill extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_drill.png");
	private TileEntityMachineMiningDrill diFurnace;

	public GUIMachineMiningDrill(InventoryPlayer invPlayer, TileEntityMachineMiningDrill tedf) {
		super(new ContainerMachineMiningDrill(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 69 - 52, 16, 52, diFurnace.power, diFurnace.maxPower);
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
		
		int k = diFurnace.warning;
		if(k == 2)
			drawTexturedModalRect(guiLeft + 44, guiTop + 17, 192, 0, 16, 16);
		if(k == 1)
			drawTexturedModalRect(guiLeft + 44, guiTop + 17, 208, 0, 16, 16);
	}
}
