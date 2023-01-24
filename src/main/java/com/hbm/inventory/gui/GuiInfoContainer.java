package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class GuiInfoContainer extends GuiContainer {
	
	static final ResourceLocation guiUtil =  new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_utility.png");

	public GuiInfoContainer(Container p_i1072_1_) {
		super(p_i1072_1_);
	}
	
	public void drawElectricityInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, long power, long maxPower) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			gui.drawInfo(new String[] { BobMathUtil.getShortNumber(power) + "/" + BobMathUtil.getShortNumber(maxPower) + "HE" }, mouseX, mouseY);
	}
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, String... text) { drawCustomInfoStat(mouseX, mouseY, x, y, width, height, tPosX, tPosY, Arrays.asList(text)); }
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, List text) {
		
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(text, tPosX, tPosY);
	}
	
	public void drawInfo(String[] text, int x, int y) {
		this.func_146283_a(Arrays.asList(text), x, y);
	}
	
	@Deprecated
	public void drawCustomInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, String[] text) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(Arrays.asList(text), mouseX, mouseY);
	}
	
	public void drawInfoPanel(int x, int y, int width, int height, int type) {

		Minecraft.getMinecraft().getTextureManager().bindTexture(guiUtil);
		
		switch(type) {
		case 0: drawTexturedModalRect(x, y, 0, 0, 8, 8); break; //Small blue I
		case 1: drawTexturedModalRect(x, y, 0, 8, 8, 8); break; //Small green I
		case 2: drawTexturedModalRect(x, y, 8, 0, 16, 16); break; //Large blue I
		case 3: drawTexturedModalRect(x, y, 24, 0, 16, 16); break; //Large green I
		case 4: drawTexturedModalRect(x, y, 0, 16, 8, 8); break; //Small red !
		case 5: drawTexturedModalRect(x, y, 0, 24, 8, 8); break; //Small yellow !
		case 6: drawTexturedModalRect(x, y, 8, 16, 16, 16); break; //Large red !
		case 7: drawTexturedModalRect(x, y, 24, 16, 16, 16); break; //Large yellow !
		case 8: drawTexturedModalRect(x, y, 0, 32, 8, 8); break; //Small blue *
		case 9: drawTexturedModalRect(x, y, 0, 40, 8, 8); break; //Small grey *
		case 10: drawTexturedModalRect(x, y, 8, 32, 16, 16); break; //Large blue *
		case 11: drawTexturedModalRect(x, y, 24, 32, 16, 16); break; //Large grey *
		}
	}
	
	protected boolean isMouseOverSlot(Slot slot, int x, int y) {
		return this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y);
	}
	
	/* Getters for external use of the GUI's rect rendering, such as NumberDisplay */
	public int getGuiTop() {
		return this.guiTop;
	}
	
	public int getGuiLeft() {
		return this.guiLeft;
	}
	
	public float getZLevel() {
		return this.zLevel;
	}
	
	public void setZLevel(float level) {
		this.zLevel = level;
	}
	
	public RenderItem getItemRenderer() {
		return this.itemRender;
	}
	
	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
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

			if(minX + longestline > this.width) {
				minX -= 28 + longestline;
			}

			if(minY + height + 6 > this.height) {
				minY = this.height - height - 6;
			}

			this.zLevel = 400.0F;
			itemRender.zLevel = 400.0F;
			//int j1 = -267386864;
			int colorBg = 0xF0100010;
			this.drawGradientRect(minX - 3, minY - 4, minX + longestline + 3, minY - 3, colorBg, colorBg);
			this.drawGradientRect(minX - 3, minY + height + 3, minX + longestline + 3, minY + height + 4, colorBg, colorBg);
			this.drawGradientRect(minX - 3, minY - 3, minX + longestline + 3, minY + height + 3, colorBg, colorBg);
			this.drawGradientRect(minX - 4, minY - 3, minX - 3, minY + height + 3, colorBg, colorBg);
			this.drawGradientRect(minX + longestline + 3, minY - 3, minX + longestline + 4, minY + height + 3, colorBg, colorBg);
			//int k1 = 1347420415;
			int color0 = 0x505000FF;
			//int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
			int color1 = (color0 & 0xFEFEFE) >> 1 | color0 & 0xFF000000;
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

						if(stack.stackSize == 0) {
							this.drawGradientRect(minX + indent - 1, minY - 1, minX + indent + 17, minY + 17, 0xffff0000, 0xffff0000);
							this.drawGradientRect(minX + indent, minY, minX + indent + 16, minY + 16, 0xffb0b0b0, 0xffb0b0b0);
						}
						GL11.glEnable(GL11.GL_DEPTH_TEST);
						itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, minX + indent, minY);
						itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, minX + indent, minY, null);
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

			this.zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
}
