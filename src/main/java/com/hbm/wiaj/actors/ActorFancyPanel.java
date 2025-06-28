package com.hbm.wiaj.actors;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ActorFancyPanel implements ISpecialActor {
	
	static final ResourceLocation guiUtil =  new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_utility.png");

	List<Object[]> lines = new ArrayList();
	RenderItem itemRender = new RenderItem();
	TextureManager texman = Minecraft.getMinecraft().getTextureManager();
	FontRenderer font;
	
	int x;
	int y;
	
	boolean consistentHeight = false;
	int lineDist = 2;
	int tallestElement = 0;
	static final int STACK_HEIGHT = 18;
	
	Orientation o = Orientation.CENTER;
	int colorBrighter = 0xFFCCCCCC;
	int colorDarker = 0xFF7D7D7D;
	int colorFrame = 0xFFA0A0A0;
	int colorBg = 0xFF302E36;
	
	public ActorFancyPanel(FontRenderer font, int x, int y, Object[][] raw, int autowrap) {
		
		this.font = font;
		this.x = x;
		this.y = y;
		
		if(autowrap <= 0) { //if autowrap is off, just add the lines 1:1
			for(Object[] o : raw) {
				lines.add(o);
			}
		} else {
			
			for(Object[] line : raw) {
				
				if(line.length == 1 && line[0] instanceof String) { //auto wrap can only apply to text-only lines
					List<String> frags = I18nUtil.autoBreak(font, (String)line[0], autowrap);
					for(String s : frags) {
						lines.add(new Object[] { s });
					}
				} else {
					lines.add(line);
				}
			}
		}
	}
	
	public ActorFancyPanel enforceConsistentHeight() {
		this.consistentHeight = true;
		return this;
	}
	
	public ActorFancyPanel setLineDist(int dist) {
		this.lineDist = dist;
		return this;
	}
	
	public ActorFancyPanel setOrientation(Orientation o) {
		this.o = o;
		return this;
	}
	
	public ActorFancyPanel setColors(int brighter, int frame, int darker, int background) {
		this.colorBrighter = brighter;
		this.colorFrame = frame;
		this.colorDarker = darker;
		this.colorBg = background;
		return this;
	}
	
	public ActorFancyPanel setColors(int[] colors) {
		return setColors(colors[0], colors[1], colors[2], colors[3]);
	}
	
	public int getTallestElement() {
		if(this.tallestElement > 0) {
			return this.tallestElement;
		}
		
		for(Object[] line : this.lines) {
			for(Object element : line) {
				
				int height = getElementHeight(element);
				
				if(height > this.tallestElement) {
					this.tallestElement = height;
				}
			}
		}
		
		return this.tallestElement;
	}
	
	public int getElementHeight(Object element) {
		
		if(element instanceof String) {
			return this.font.FONT_HEIGHT;
		}
		
		if(element instanceof ItemStack) {
			return STACK_HEIGHT;
		}
		
		if(element instanceof Object[]) {
			Object[] scaledStack = (Object[]) element; //[0] ItemStack, [1] double
			return (int) Math.ceil(STACK_HEIGHT * (double) scaledStack[1]);
		}
		
		return 0;
	}
	
	public int getElementWidth(Object element) {
		
		if(element instanceof String) {
			return this.font.getStringWidth((String) element);
		}
		
		if(element instanceof ItemStack) {
			return STACK_HEIGHT;
		}
		
		if(element instanceof Object[]) {
			Object[] scaledStack = (Object[]) element; //[0] ItemStack, [1] double
			return (int) Math.ceil(STACK_HEIGHT * (double) scaledStack[1]);
		}
		
		return 0;
	}
	
	int blockHeight = 0;
	private int getBlockHeight() {
		
		if(this.blockHeight > 0) {
			return this.blockHeight;
		}
		
		for(Object[] line : this.lines) {
			
			if(this.blockHeight > 0) {
				this.blockHeight += this.lineDist;
			}
			
			int lineHeight = this.font.FONT_HEIGHT;
			
			if(this.consistentHeight) {
				lineHeight = Math.max(lineHeight, getTallestElement());
			} else {
				
				for(Object o : line) {
					lineHeight = Math.max(lineHeight, getElementHeight(o));
				}
			}
			
			this.blockHeight += lineHeight;
		}
		
		return this.blockHeight;
	}
	
	int blockWidth = 0;
	private int getBlockWidth() {
		
		if(this.blockWidth > 0) {
			return this.blockWidth;
		}
		
		for(Object[] line : this.lines) {
			
			int lineWidth = 0;
			
			for(Object o : line) {
				
				if(lineWidth > 0) {
					lineWidth += 2;
				}
				
				lineWidth += getElementWidth(o);
			}
			
			if(lineWidth > this.blockWidth) {
				this.blockWidth = lineWidth;
			}
		}
		
		return this.blockWidth;
	}

	@Override
	public void drawForegroundComponent(int w, int h, int ticks, float interp) {
		
		int height = this.getBlockHeight();
		int width = this.getBlockWidth();
		
		int posX = w / 2 + x;
		int posY = h / 2 + y;
		
		switch(o) {
		case TOP:
			posX -= width / 2;
			posY += 15;
			break;
		case BOTTOM:
			posX -= width / 2;
			posY -= height + 15;
			break;
		case LEFT:
			posX += 15;
			posY -= height / 2;
			break;
		case RIGHT:
			posX -= width + 15;
			posY -= height / 2;
			break;
		case CENTER:
			posX -= width / 2;
			posY -= height / 2;
			break;
		}

		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		this.drawRect(posX - 5, posY - 5, posX + width + 5, posY + height + 5, colorFrame);
		
		this.drawRect(posX - 5, posY - 5, posX - 4, posY + height + 4, colorBrighter);
		this.drawRect(posX - 5, posY - 5, posX + width + 4, posY - 4, colorBrighter);
		this.drawRect(posX + width + 2, posY - 2, posX + width + 3, posY + height + 3, colorBrighter);
		this.drawRect(posX - 2, posY + height + 2, posX + width + 3, posY + height + 3, colorBrighter);
		
		this.drawRect(posX - 3, posY - 3, posX - 2, posY + height + 2, colorDarker);
		this.drawRect(posX - 3, posY - 3, posX + width + 2, posY - 2, colorDarker);
		this.drawRect(posX + width + 4, posY - 4, posX + width + 5, posY + height + 5, colorDarker);
		this.drawRect(posX - 4, posY + height + 4, posX + width + 5, posY + height + 5, colorDarker);
		
		this.drawRect(posX - 2, posY - 2, posX + width + 2, posY + height + 2, colorBg);

		Minecraft.getMinecraft().getTextureManager().bindTexture(guiUtil);
		
		switch(o) {
		case TOP:
			this.drawArrow(posX + width / 2 - 7, posY - 15, 40, 14, 14, 10);
			break;
		case BOTTOM:
			this.drawArrow(posX + width / 2 - 7, posY + height + 5, 54, 14, 14, 10);
			break;
		case LEFT:
			this.drawArrow(posX - 15, posY + height / 2 - 7, 40, 0, 10, 14);
			break;
		case RIGHT:
			this.drawArrow(posX + width + 5, posY + height / 2 - 7, 50, 0, 10, 14);
			break;
		case CENTER: break;
		}
		
		int offsetY = 0;
		
		for(Object[] line : this.lines) {
			
			if(offsetY > 0) offsetY+= this.lineDist;
			int lineHeight = 0;
			for(Object element : line) lineHeight = Math.max(lineHeight, this.getElementHeight(element));
			
			int indent = 0;
			for(Object element : line) {
				
				if(indent > 0) {
					indent += 2;
				}
				
				drawElement(posX + indent, posY + offsetY + lineHeight / 2, element);
				indent += getElementWidth(element);
			}
			
			offsetY += lineHeight;
		}

		itemRender.zLevel = 0.0F;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	}
	
	private void drawArrow(int posX, int posY, int sourceX, int sourceY, int sizeX, int sizeY) {
		this.drawTexturedModalRect(posX, posY, sourceX + 28 * 0, sourceY, sizeX, sizeY, this.colorBrighter);
		this.drawTexturedModalRect(posX, posY, sourceX + 28 * 1, sourceY, sizeX, sizeY, this.colorFrame);
		this.drawTexturedModalRect(posX, posY, sourceX + 28 * 2, sourceY, sizeX, sizeY, this.colorDarker);
		this.drawTexturedModalRect(posX, posY, sourceX + 28 * 3, sourceY, sizeX, sizeY, this.colorBg);
	}
	
	private void drawElement(int x, int y, Object element) {
		
		if(element instanceof String) {
			String text = (String) element;
			this.font.drawString(text, x, y - this.font.FONT_HEIGHT / 2, 0xffffff);
			
		} else if(element instanceof ItemStack) {
			
			ItemStack stack = (ItemStack) element;
			GL11.glColor3f(1F, 1F, 1F);

			if(stack.stackSize == 0) {
				this.drawGradientRect(x - 1, y - 1 - 8, x + 17, y + 17, 0xffff0000, 0xffff0000);
				this.drawGradientRect(x, y - 8, x + 16, y + 16, 0xffb0b0b0, 0xffb0b0b0);
			}
			itemRender.renderItemAndEffectIntoGUI(this.font, texman, stack, x, y - 8);
			itemRender.renderItemOverlayIntoGUI(this.font, texman, stack, x, y - 8, null);
			RenderHelper.disableStandardItemLighting();
			GL11.glColor3f(1F, 1F, 1F);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		} //TODO: scaled stacks
	}

	@Override public void drawBackgroundComponent(WorldInAJar world, int ticks, float interp) { }
	@Override public void updateActor(JarScene scene) { }
	@Override public void setActorData(NBTTagCompound data) { }
	@Override public void setDataPoint(String tag, Object o) { }

	protected void drawRect(int minX, int minY, int maxX, int maxY, int color) {
		drawGradientRect(minX, minY, maxX, maxY, color, color);
	}

	protected void drawGradientRect(int minX, int minY, int maxX, int maxY, int color1, int color2) {
		
		double zLevel = 300D;
		float a1 = (float) (color1 >> 24 & 255) / 255.0F;
		float r1 = (float) (color1 >> 16 & 255) / 255.0F;
		float g1 = (float) (color1 >> 8 & 255) / 255.0F;
		float b1 = (float) (color1 & 255) / 255.0F;
		float a2 = (float) (color2 >> 24 & 255) / 255.0F;
		float r2 = (float) (color2 >> 16 & 255) / 255.0F;
		float g2 = (float) (color2 >> 8 & 255) / 255.0F;
		float b2 = (float) (color2 & 255) / 255.0F;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(r1, g1, b1, a1);
		tessellator.addVertex((double) maxX, (double) minY, (double) zLevel);
		tessellator.addVertex((double) minX, (double) minY, (double) zLevel);
		tessellator.setColorRGBA_F(r2, g2, b2, a2);
		tessellator.addVertex((double) minX, (double) maxY, (double) zLevel);
		tessellator.addVertex((double) maxX, (double) maxY, (double) zLevel);
		tessellator.draw();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void drawTexturedModalRect(int posX, int posY, int u, int v, int sizeX, int sizeY) {
		drawTexturedModalRect(posX, posY, u, v, sizeX, sizeY, 0xffffff);
	}

	public void drawTexturedModalRect(int posX, int posY, int u, int v, int sizeX, int sizeY, int color) {
		double zLevel = 300D;
		float a = (float) (color >> 24 & 255) / 255.0F;
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(r, g, b, a);
		tessellator.addVertexWithUV(posX + 0, posY + sizeY, zLevel, (u + 0) * f, (v + sizeY) * f1);
		tessellator.addVertexWithUV(posX + sizeX, posY + sizeY, zLevel, (u + sizeX) * f, (v + sizeY) * f1);
		tessellator.addVertexWithUV(posX + sizeX, posY + 0, zLevel, (u + sizeX) * f, (v + 0) * f1);
		tessellator.addVertexWithUV(posX + 0, posY + 0, zLevel, (u + 0) * f, (v + 0) * f);
		tessellator.draw();
	}
	
	/** where the arrow should be or if the box should be centered around the home position */
	public static enum Orientation {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
		CENTER
	}
}
