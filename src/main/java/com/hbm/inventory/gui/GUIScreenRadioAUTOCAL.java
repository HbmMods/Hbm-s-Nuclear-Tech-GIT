package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityRadioAUTOCAL;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRadioAUTOCAL extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_autocal.png");
	protected TileEntityRadioAUTOCAL autocal;
	
	protected int xSize = 150;
	protected int ySize = 90;
	protected int guiLeft;
	protected int guiTop;

	public GUIScreenRadioAUTOCAL(TileEntityRadioAUTOCAL autocal) {
		this.autocal = autocal;
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
		
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(autocal.isOn) drawTexturedModalRect(guiLeft + 8, guiTop + 36, 150, 0, 18, 18);
		if(autocal.ignoreError) drawTexturedModalRect(guiLeft + 28, guiTop + 36, 150, 18, 18, 18);
		if(autocal.autoReboot) drawTexturedModalRect(guiLeft + 48, guiTop + 36, 150, 36, 18, 18);
	}

	@Override
	protected void keyTyped(char c, int b) {
		if(b == 1 || b == Minecraft.getMinecraft().gameSettings.keyBindInventory.getKeyCode()) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
	
	@Override public boolean doesGuiPauseGame() { return false; }
}
