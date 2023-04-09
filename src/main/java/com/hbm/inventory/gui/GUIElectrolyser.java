package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerElectrolyser;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIElectrolyser extends GuiInfoContainer {
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_electrolyser.png");
	private TileEntityElectrolyser electrolyser;
	
	public GUIElectrolyser(InventoryPlayer invPlayer, TileEntityElectrolyser electrolyser) {
		super(new ContainerElectrolyser(invPlayer, electrolyser));
		this.electrolyser = electrolyser;

		this.xSize = 210;
		this.ySize = 247;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}
	
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.electrolyser.hasCustomInventoryName() ? this.electrolyser.getInventoryName() : I18n.format(this.electrolyser.getInventoryName());

		this.fontRendererObj.drawString(name, (this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2) - 16, 7, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}
}
