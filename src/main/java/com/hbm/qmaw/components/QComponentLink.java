package com.hbm.qmaw.components;

import org.lwjgl.opengl.GL11;

import com.hbm.qmaw.GuiQMAW;
import com.hbm.qmaw.ManualElement;
import com.hbm.qmaw.QMAWLoader;
import com.hbm.qmaw.QuickManualAndWiki;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class QComponentLink extends ManualElement {

	protected String link;
	protected ItemStack icon;
	protected String text;
	protected FontRenderer font;
	protected int color = 0x0094FF;
	protected int hoverColor = 0xFFD800;
	
	protected static RenderItem itemRender = new RenderItem();
	
	public QComponentLink(String link, String text) {
		this.text = text;
		this.link = link;
		
		QuickManualAndWiki qmaw = QMAWLoader.qmaw.get(link);
		if(qmaw == null) {
			this.color = this.hoverColor = 0xFF7F7F;
		} else {
			this.icon = qmaw.icon;
		}
	}
	
	public QComponentLink setColor(int color, int hoverColor) {
		this.color = color;
		this.hoverColor = hoverColor;
		return this;
	}

	@Override
	public int getWidth() {
		return font.getStringWidth(text) + (icon != null ? 20 : 0);
	}

	@Override
	public int getHeight() {
		return Math.max(font.FONT_HEIGHT, icon != null ? 18 : 0);
	}

	@Override
	public void render(boolean isMouseOver, int mouseX, int mouseY) {
		int x = this.x;
		int y = this.y;
		
		if(this.icon != null) {

			GL11.glEnable(GL11.GL_DEPTH_TEST);
			Minecraft mc = Minecraft.getMinecraft();
			itemRender.renderItemAndEffectIntoGUI(this.font, mc.renderEngine, this.icon, x, y);
			itemRender.renderItemOverlayIntoGUI(this.font, mc.renderEngine, this.icon, x, y, null);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			x += 20;
			y += (18 - font.FONT_HEIGHT) / 2;
		}
		
		font.drawString(text, x, y, isMouseOver ? hoverColor : color);
	}

	@Override public void onClick() {
		QuickManualAndWiki qmaw = QMAWLoader.qmaw.get(link);
		if(qmaw != null) FMLCommonHandler.instance().showGuiScreen(new GuiQMAW(qmaw));
	}
}
