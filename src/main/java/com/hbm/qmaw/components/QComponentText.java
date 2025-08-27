package com.hbm.qmaw.components;

import com.hbm.qmaw.GuiQMAW;
import com.hbm.qmaw.ManualElement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class QComponentText extends ManualElement {
	
	protected String text;
	protected FontRenderer font;
	protected int color = 0xFFFFFF;
	
	public QComponentText(String text) {
		this(text, Minecraft.getMinecraft().fontRenderer);
	}
	
	public QComponentText(String text, FontRenderer font) {
		this.text = text;
		this.font = font;
	}
	
	public QComponentText setColor(int color) {
		this.color = color;
		return this;
	}

	@Override
	public int getWidth() {
		return font.getStringWidth(text);
	}

	@Override
	public int getHeight() {
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(boolean isMouseOver, int x, int y, int mouseX, int mouseY) {
		font.drawString(text, x, y, color);
	}

	@Override public void onClick(GuiQMAW gui) { }
}
