package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadarNT extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radar_nt.png");
	
	protected TileEntityMachineRadarNT radar;
	protected int xSize = 216;
	protected int ySize = 234;
	protected int guiLeft;
	protected int guiTop;
	
	public GUIMachineRadarNT(TileEntityMachineRadarNT tile) {
		this.radar = tile;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void drawGuiContainerForegroundLayer(int x, int y) {
		if(checkClick(x, y, -10, 88, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.detectMissiles")), x, y);
		if(checkClick(x, y, -10, 98, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.detectShells")), x, y);
		if(checkClick(x, y, -10, 108, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.detectPlayers")), x, y);
		if(checkClick(x, y, -10, 118, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.smartMode")), x, y);
		if(checkClick(x, y, -10, 128, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.redMode")), x, y);
		if(checkClick(x, y, -10, 138, 8, 8)) this.func_146283_a(Arrays.asList(I18nUtil.resolveKeyArray("radar.showMap")), x, y);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	protected boolean checkClick(int x, int y, int left, int top, int sizeX, int sizeY) {
		return guiLeft + left <= x && guiLeft + left + sizeX > x && guiTop + top < y && guiTop + top + sizeY >= y;
	}
}
