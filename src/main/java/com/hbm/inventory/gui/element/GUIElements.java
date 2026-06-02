package com.hbm.inventory.gui.element;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.lib.RefStrings;
import com.hbm.util.Vec3NT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIElements {
	
	@Deprecated public static enum Gauge {
		ROUND_SMALL("small_round", 18, 18, 13);
		ResourceLocation texture;
		int width, height, count;
		private Gauge(String texture, int width, int height, int count) {
			this.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gauges/" + texture + ".png");
			this.width = width;
			this.height = height;
			this.count = count;
		}
	}
	
	@Deprecated public static void renderGauge(Gauge gauge, double x, double y, double z, double progress) {
		Minecraft.getMinecraft().renderEngine.bindTexture(gauge.texture);
		int frameNum = (int) Math.round((gauge.count - 1) * progress);
		double singleFrame = 1D / (double)gauge.count;
		double frameOffset = singleFrame * frameNum;
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(x, 				y + gauge.height, 	z, 	0, 	frameOffset + singleFrame);
		tess.addVertexWithUV(x + gauge.width, 	y + gauge.height, 	z, 	1, 	frameOffset + singleFrame);
		tess.addVertexWithUV(x + gauge.width, 	y, 					z, 	1, 	frameOffset);
		tess.addVertexWithUV(x, 				y, 					z, 	0, 	frameOffset);
		tess.draw();
	}
	
	public static void drawSmoothGauge(int x, int y, double z, double progress, double tipLength, double backLength, double backSide, int color) {
		drawSmoothGauge(x, y, z, progress, tipLength, backLength, backSide, color, 0x000000);
	}

	private static Vec3NT tip = new Vec3NT();
	private static Vec3NT left = new Vec3NT();
	private static Vec3NT right = new Vec3NT();
	
	public static void drawSmoothGauge(int x, int y, double z, double progress, double tipLength, double backLength, double backSide, int color, int colorOuter) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		progress = MathHelper.clamp_double(progress, 0, 1);
		
		float angle = (float) Math.toRadians(-progress * 270 - 45);
		tip.setComponents(0, tipLength, 0);
		left.setComponents(backSide, -backLength, 0);
		right.setComponents(-backSide, -backLength, 0);

		tip.rotateAroundZ(angle);
		left.rotateAroundZ(angle);
		right.rotateAroundZ(angle);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_TRIANGLES);
		tess.setColorOpaque_I(colorOuter);
		double mult = 1.5;
		tess.addVertex(x + tip.xCoord * mult, y + tip.yCoord * mult, z);
		tess.addVertex(x + left.xCoord * mult, y + left.yCoord * mult, z);
		tess.addVertex(x + right.xCoord * mult, y + right.yCoord * mult, z);
		tess.setColorOpaque_I(color);
		tess.addVertex(x + tip.xCoord, y + tip.yCoord, z);
		tess.addVertex(x + left.xCoord, y + left.yCoord, z);
		tess.addVertex(x + right.xCoord, y + right.yCoord, z);
		tess.draw();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static final int STANDARD_COLOR_BACKGROUND = -0xFEFFFF0;
	public static final int STANDARD_COLOR_LINE0 = 0x505000FF;
	public static final int STANDARD_COLOR_LINE1 = (STANDARD_COLOR_LINE0 & 0xFEFEFE) >> 1 | STANDARD_COLOR_LINE0 & -0xFEFEFE;
	public static final int RECIPE_COLOR_LINE0 = 0xFFFF8000;
	public static final int RECIPE_COLOR_LINE1 = 0xFFFFFF00;
	public static final int STANDARD_HEADER_OFFSET = 2;
	public static final int STANDARD_LINE_DIST = 10;
	public static void drawHoveringText(List lines, int x, int y, FontRenderer font, RenderItem itemRender, int guiWidth, int guiHeight) {
		drawHoveringText(lines, x, y, font, itemRender, guiWidth, guiHeight, STANDARD_HEADER_OFFSET, STANDARD_LINE_DIST, STANDARD_COLOR_BACKGROUND, STANDARD_COLOR_BACKGROUND, STANDARD_COLOR_LINE0, STANDARD_COLOR_LINE1);
	}
	public static void drawHoveringTextRecipe(List lines, int x, int y, FontRenderer font, RenderItem itemRender, int guiWidth, int guiHeight) {
		drawHoveringText(lines, x, y, font, itemRender, guiWidth, guiHeight, 6, STANDARD_LINE_DIST, STANDARD_COLOR_BACKGROUND, STANDARD_COLOR_BACKGROUND, RECIPE_COLOR_LINE0, RECIPE_COLOR_LINE1);
	}

	public static void drawHoveringText(List lines, int x, int y, FontRenderer font, RenderItem itemRender, int guiWidth, int guiHeight, int headerOffset, int lineDist, int colBG0, int colBG1, int colLine0, int colLine1) {
		
		if(!lines.isEmpty()) {
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int width = 0;
			Iterator iterator = lines.iterator();

			while(iterator.hasNext()) {
				String line = (String) iterator.next();
				int lineLength = font.getStringWidth(line);

				if(lineLength > width) {
					width = lineLength;
				}
			}

			int boundX = x + 12;
			int boundY = y - 12;
			int height = 6 + headerOffset;

			if(lines.size() > 1) {
				height += 2 + (lines.size() - 1) * lineDist;
			}

			// if trying to leave bottom or right side, move inwards
			if(boundX + width + 4 > guiWidth) boundX -= 28 + width;
			if(boundY + height + 6 > guiHeight) boundY = guiHeight - height - 6;

			// afterwards, see if the tooltip exits the top or left and then fix that, this one's more important and for some fucking reason wasn't handled by vanilla t all
			if(boundX < 4) boundX = 4;
			if(boundY < 4) boundY = 4;

			itemRender.zLevel = 300.0F;
			drawGradientRect(boundX - 3, boundY - 4, boundX + width + 3, boundY - 3, colBG0, colBG0);
			drawGradientRect(boundX - 3, boundY + height + 3, boundX + width + 3, boundY + height + 4, colBG1, colBG1);
			drawGradientRect(boundX - 3, boundY - 3, boundX + width + 3, boundY + height + 3, colBG0, colBG1);
			drawGradientRect(boundX - 4, boundY - 3, boundX - 3, boundY + height + 3, colBG0, colBG1);
			drawGradientRect(boundX + width + 3, boundY - 3, boundX + width + 4, boundY + height + 3, colBG0, colBG1);
			
			drawGradientRect(boundX - 3, boundY - 3 + 1, boundX - 3 + 1, boundY + height + 3 - 1, colLine0, colLine1);
			drawGradientRect(boundX + width + 2, boundY - 3 + 1, boundX + width + 3, boundY + height + 3 - 1, colLine0, colLine1);
			drawGradientRect(boundX - 3, boundY - 3, boundX + width + 3, boundY - 3 + 1, colLine0, colLine0);
			drawGradientRect(boundX - 3, boundY + height + 2, boundX + width + 3, boundY + height + 3, colLine1, colLine1);

			for(int i = 0; i < lines.size(); ++i) {
				String line = (String) lines.get(i);
				font.drawStringWithShadow(line, boundX, boundY, -1);
				
				if(i == 0) boundY += headerOffset;
				boundY += lineDist;
			}

			itemRender.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	/** Colors don't use the RGBA, but rather ARGB (evil route) */
	protected static void drawGradientRect(int x0, int y0, int x1, int y1, int col0, int col1) {
		float a0 = (float) (col0 >> 24	& 255) / 255F;
		float r0 = (float) (col0 >> 16	& 255) / 255F;
		float g0 = (float) (col0 >> 8	& 255) / 255F;
		float b0 = (float) (col0		& 255) / 255F;
		float a1 = (float) (col1 >> 24	& 255) / 255F;
		float r1 = (float) (col1 >> 16	& 255) / 255F;
		float g1 = (float) (col1 >> 8	& 255) / 255F;
		float b1 = (float) (col1		& 255) / 255F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(r0, g0, b0, a0);
		tessellator.addVertex(x1, y0, 300D);
		tessellator.addVertex(x0, y0, 300D);
		tessellator.setColorRGBA_F(r1, g1, b1, a1);
		tessellator.addVertex(x0, y1, 300D);
		tessellator.addVertex(x1, y1, 300D);
		tessellator.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
