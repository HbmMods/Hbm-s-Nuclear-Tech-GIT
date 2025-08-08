package com.hbm.qmaw.components;

import org.lwjgl.opengl.GL11;

import com.hbm.qmaw.GuiQMAW;
import com.hbm.qmaw.ManualElement;
import com.hbm.qmaw.QMAWLoader;
import com.hbm.qmaw.QuickManualAndWiki;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
		
		this.font = Minecraft.getMinecraft().fontRenderer;
	}
	
	public QComponentLink setColor(int color, int hoverColor) {
		this.color = color;
		this.hoverColor = hoverColor;
		return this;
	}

	@Override
	public int getWidth() {
		return font.getStringWidth(text) + (icon != null ? 18 : 0);
	}

	@Override
	public int getHeight() {
		return Math.max(font.FONT_HEIGHT, icon != null ? 16 : 0);
	}

	@Override
	public void render(boolean isMouseOver, int x, int y, int mouseX, int mouseY) {
		
		if(this.icon != null) {
			
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			Minecraft mc = Minecraft.getMinecraft();
			GL11.glRotated(180, 1, 0, 0);
			RenderHelper.enableStandardItemLighting();
			GL11.glRotated(-180, 1, 0, 0);
			itemRender.renderItemAndEffectIntoGUI(this.font, mc.renderEngine, this.icon, x, y);
			itemRender.renderItemOverlayIntoGUI(this.font, mc.renderEngine, this.icon, x, y, null);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glPopMatrix();
			
			x += 18;
			y += (16 - font.FONT_HEIGHT) / 2;
		}
		
		font.drawString(text, x, y, isMouseOver ? hoverColor : color);
	}

	@Override public void onClick() {
		QuickManualAndWiki qmaw = QMAWLoader.qmaw.get(link);
		if(qmaw != null) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			FMLCommonHandler.instance().showGuiScreen(new GuiQMAW(qmaw));
		}
	}
}
