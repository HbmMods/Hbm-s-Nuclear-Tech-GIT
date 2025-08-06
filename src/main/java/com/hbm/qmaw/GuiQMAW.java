package com.hbm.qmaw;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiQMAW extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_wiki.png");
	
	public String title;
	public List<ManualElement[]> lines = new ArrayList();
	
	protected int xSize = 192;
	protected int ySize = 256;
	protected int guiLeft;
	protected int guiTop;
	
	public GuiQMAW(QuickManualAndWiki qmaw) {
		parseQMAW(qmaw);
	}
	
	protected void parseQMAW(QuickManualAndWiki qmaw) {
		
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

	private void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		int x = 0;
		int y = 0;
		
		this.fontRendererObj.drawString(title, guiLeft + x, guiTop + y, 0xFFFFFF);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
