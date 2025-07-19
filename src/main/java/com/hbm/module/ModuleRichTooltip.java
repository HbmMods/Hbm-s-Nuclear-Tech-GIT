package com.hbm.module;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.gui.GuiInfoContainer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ModuleRichTooltip {
	
	private GuiInfoContainer gui;
	
	protected int colorBg = 0xF0100010;
	protected int color0 = 0x505000FF;
	protected int color1 = (color0 & 0xFEFEFE) >> 1 | color0 & 0xFF000000;
	
	public ModuleRichTooltip(GuiInfoContainer gui) {
		this.gui = gui;
	}
	
	public ModuleRichTooltip setBG(int colorBg) {
		this.colorBg = colorBg;
		return this;
	}
	
	public ModuleRichTooltip setColors(int color0, int color1) {
		this.color0 = color0;
		this.color1 = color1;
		return this;
	}
	
	protected void drawStackText(List lines, int x, int y, FontRenderer font) {
		
		if(!lines.isEmpty()) {
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);

			int height = 0;
			int longestline = 0;
			Iterator iterator = lines.iterator();

			while(iterator.hasNext()) {
				Object[] line = (Object[]) iterator.next();
				int lineWidth = 0;
				
				boolean hasStack = false;
				
				for(Object o : line) {
					
					if(o instanceof String) {
						lineWidth += font.getStringWidth((String) o);
					} else {
						lineWidth += 18;
						hasStack = true;
					}
				}
				
				if(hasStack) {
					height += 18;
				} else {
					height += 10;
				}

				if(lineWidth > longestline) {
					longestline = lineWidth;
				}
			}

			int minX = x + 12;
			int minY = y - 12;

			if(minX + longestline > gui.width) {
				minX -= 28 + longestline;
			}

			if(minY + height + 6 > gui.height) {
				minY = gui.height - height - 6;
			}

			gui.setZLevel(300F);
			gui.getItemRenderer().zLevel = 300.0F;
			
			this.drawGradientRect(minX - 3, minY - 4, minX + longestline + 3, minY - 3, colorBg, colorBg);
			this.drawGradientRect(minX - 3, minY + height + 3, minX + longestline + 3, minY + height + 4, colorBg, colorBg);
			this.drawGradientRect(minX - 3, minY - 3, minX + longestline + 3, minY + height + 3, colorBg, colorBg);
			this.drawGradientRect(minX - 4, minY - 3, minX - 3, minY + height + 3, colorBg, colorBg);
			this.drawGradientRect(minX + longestline + 3, minY - 3, minX + longestline + 4, minY + height + 3, colorBg, colorBg);
			
			this.drawGradientRect(minX - 3, minY - 3 + 1, minX - 3 + 1, minY + height + 3 - 1, color0, color1);
			this.drawGradientRect(minX + longestline + 2, minY - 3 + 1, minX + longestline + 3, minY + height + 3 - 1, color0, color1);
			this.drawGradientRect(minX - 3, minY - 3, minX + longestline + 3, minY - 3 + 1, color0, color0);
			this.drawGradientRect(minX - 3, minY + height + 2, minX + longestline + 3, minY + height + 3, color1, color1);

			for(int index = 0; index < lines.size(); ++index) {
				
				Object[] line = (Object[]) lines.get(index);
				int indent = 0;
				boolean hasStack = false;
				
				for(Object o : line) {
					if(!(o instanceof String)) {
						hasStack = true;
					}
				}
				
				for(Object o : line) {
					
					if(o instanceof String) {
						font.drawStringWithShadow((String) o, minX + indent, minY + (hasStack ? 4 : 0), -1);
						indent += font.getStringWidth((String) o) + 2;
					} else {
						ItemStack stack = (ItemStack) o;
						GL11.glColor3f(1F, 1F, 1F);
						gui.getItemRenderer().renderItemAndEffectIntoGUI(gui.getFontRenderer(), gui.mc.getTextureManager(), stack, minX + indent, minY);
						gui.getItemRenderer().renderItemOverlayIntoGUI(gui.getFontRenderer(), gui.mc.getTextureManager(), stack, minX + indent, minY, stack.stackSize == 0 ? (EnumChatFormatting.RED + "_ _") : null);
						RenderHelper.disableStandardItemLighting();
						GL11.glDisable(GL11.GL_DEPTH_TEST);
						indent += 18;
					}
				}

				if(index == 0) {
					minY += 2;
				}

				minY += hasStack ? 18 : 10;
			}

			gui.setZLevel(0F);
			gui.getItemRenderer().zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	protected void drawGradientRect(int minX, int minY, int maxX, int maxY, int colorTop, int colorBottom) {
		float zLevel = gui.getZLevel();
		float topA = (float) (colorTop >> 24 & 255) / 255.0F;
		float topR = (float) (colorTop >> 16 & 255) / 255.0F;
		float topG = (float) (colorTop >> 8 & 255) / 255.0F;
		float topB = (float) (colorTop & 255) / 255.0F;
		float bottomA = (float) (colorBottom >> 24 & 255) / 255.0F;
		float bottomR = (float) (colorBottom >> 16 & 255) / 255.0F;
		float bottomG = (float) (colorBottom >> 8 & 255) / 255.0F;
		float bottomB = (float) (colorBottom & 255) / 255.0F;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(topR, topG, topB, topA);
		tessellator.addVertex((double) maxX, (double) minY, (double) zLevel);
		tessellator.addVertex((double) minX, (double) minY, (double) zLevel);
		tessellator.setColorRGBA_F(bottomR, bottomG, bottomB, bottomA);
		tessellator.addVertex((double) minX, (double) maxY, (double) zLevel);
		tessellator.addVertex((double) maxX, (double) maxY, (double) zLevel);
		tessellator.draw();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static class TooltipLine {
		
		protected int contentHeight = 0;
	}
}
