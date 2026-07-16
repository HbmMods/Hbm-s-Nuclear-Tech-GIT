package com.hbm.inventory.gui.element;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.util.ColorUtil;
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

	public static void drawSmoothLinearGauge(int x, int y, double z, double progress, double tipLength, double backLength, double backSide, double scale, float rotation, int color) {
		drawSmoothLinearGauge(x, y, z, progress, tipLength, backLength, backSide, scale, rotation, color, 0x000000);
	}

	private static Vec3NT Bleft = new Vec3NT();
	private static Vec3NT Bright = new Vec3NT();

	public static void drawSmoothLinearGauge(int x, int y, double z, double progress, double tipLength, double backLength, double backSide, double scale, float rotation, int color, int colorOuter) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		scale = Math.max(scale, 1);
		progress = MathHelper.clamp_double(progress, 0, 1) * scale;

		tip.setComponents(0, -tipLength, 0);
		right.setComponents(-backSide, 0, 0);
		Bright.setComponents(-backSide, backLength, 0);
		Bleft.setComponents(backSide, backLength, 0);
		left.setComponents(backSide, 0, 0);

		float angle = (float) Math.toRadians(-rotation);

		tip.rotateAroundZ(angle);
		right.rotateAroundZ(angle);
		Bright.rotateAroundZ(angle);
		Bleft.rotateAroundZ(angle);
		left.rotateAroundZ(angle);

		double deltaX = progress * MathHelper.cos(angle);
		double deltaY = progress * MathHelper.sin(angle);

		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_POLYGON);
		tess.setColorOpaque_I(colorOuter);
		double mult = 1.5;
		tess.addVertex(x + deltaX + tip.xCoord * mult, y + deltaY + tip.yCoord * mult, z);
		tess.addVertex(x + deltaX + right.xCoord * mult, y + deltaY + right.yCoord * mult, z);
		tess.addVertex(x + deltaX + Bright.xCoord * mult, y + deltaY + Bright.yCoord, z);
		tess.addVertex(x + deltaX + Bleft.xCoord * mult, y + deltaY + Bleft.yCoord, z);
		tess.addVertex(x + deltaX + left.xCoord * mult, y + deltaY + left.yCoord * mult, z);
		tess.draw();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		tess.startDrawing(GL11.GL_POLYGON);
		tess.setColorOpaque_I(color);
		tess.addVertex(x + deltaX + tip.xCoord, y + deltaY + tip.yCoord, z);
		tess.addVertex(x + deltaX + right.xCoord, y + deltaY + right.yCoord, z);
		tess.addVertex(x + deltaX + Bright.xCoord, y + deltaY + Bright.yCoord, z);
		tess.addVertex(x + deltaX + Bleft.xCoord, y + deltaY + Bleft.yCoord, z);
		tess.addVertex(x + deltaX + left.xCoord, y + deltaY + left.yCoord, z);
		tess.draw();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void drawSmoothTextureModalCircle(int xDraw, int yDraw, float zDraw, int xStart, int yStart, int xDelta, int yDelta, double progress) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;

		progress = MathHelper.clamp_double(progress, 0, 1);
		float angle = (float) (-progress * 270.0);
		double theta = Math.toRadians(angle - 135);
		int addons = 0;
		double xTarget = 0;
		double yTarget = 0;

		// addons is just a numeric flag for how many fixed point to add, to generate the triangles
		if (angle >= -180 && angle < -90) {
			addons = 1;
		} else if (angle >= -270 && angle < -180) {
			addons = 2;
		}

		// the abysmal control under here is responsible for the positioning of the last point
		// basically this whole function makes this shape:
		// + - - - - - - - - - - - +
		// | \ * * * * * * * * * / |
		// | * \ * * * * * * * / * |
		// | * * \ * * * * * / * * |
		// | * * * \ * * * / * * * |
		// | * * * * \ * / * * * * |
		// | * * * * * + * * * * * |
		// | * * * * /   \ * * * * |
		// | * * * /       \ * * * |
		// | * * /           \ * * |
		// | * /               \ * |
		// | /                   \ |
		// + - - - - - - - - - - - +
		// the "/, \" are the sides, "+" points and "*" rendered part (lower triangle isn't shown)
		if (angle >= -90) {
			xTarget = -1;
			yTarget = -Math.tan(theta);
		} else if (angle > -135 && angle < -90) {
			xTarget = Math.tan(Math.PI/2 - theta);
			yTarget = 1;
		} else if (angle > -180 && angle < -135) {
			xTarget = Math.tan(Math.PI/2 - theta);
			yTarget = 1;
		} else if (angle <= -180) {
			xTarget = 1;
			yTarget = Math.tan(theta);
		} else if (angle == -135) {
			xTarget = 0;
			yTarget = 1;
		}

		double xMid = (double) xDelta / 2;
		double yMid = (double) yDelta / 2;

		xTarget *= xMid;
		yTarget *= yMid;

		Tessellator tess = Tessellator.instance;

		tess.startDrawing(GL11.GL_TRIANGLES); // i should've used GL_POLYGON yes i know, too bad im either too retarded to understand it or OpenGL makes it funky, i already tried
		tess.addVertexWithUV(xDraw, yDraw + yDelta, zDraw, ((float) (xStart) * var7), ((float) (yStart + yDelta) * var8));
		tess.addVertexWithUV(xDraw + xMid, yDraw + yMid, zDraw, (float) (xStart + xMid) * var7, (float) (yStart + yMid) * var8);
		if (addons == 2 || addons == 1) {
			tess.addVertexWithUV(xDraw, yDraw, zDraw, (float) (xStart) * var7, (float) (yStart) * var8);
			tess.draw();
			tess.startDrawing(GL11.GL_TRIANGLES);
			tess.addVertexWithUV(xDraw, yDraw, zDraw, (float) (xStart) * var7, (float) (yStart) * var8);
			tess.addVertexWithUV(xDraw + xMid, yDraw + yMid, zDraw, (float) (xStart + xMid) * var7, (float) (yStart + yMid) * var8);
		}
		if (addons == 2) {
			tess.addVertexWithUV(xDraw + xDelta, yDraw, zDraw, (float) (xStart + xDelta) * var7, (float) (yStart) * var8);
			tess.draw();
			tess.startDrawing(GL11.GL_TRIANGLES);
			tess.addVertexWithUV(xDraw + xDelta, yDraw, (double) zDraw, (float) (xStart + xDelta) * var7, (float) (yStart) * var8);
			tess.addVertexWithUV(xDraw + xMid, yDraw + yMid, zDraw, (float) (xStart + xMid) * var7, (float) (yStart + yMid) * var8);
		}
		tess.addVertexWithUV(xDraw + xTarget + xMid, yDraw - yTarget + yMid, zDraw, (float) (xStart + xTarget + xMid) * var7, (float) (yStart - yTarget + yMid) * var8);
		tess.draw();
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

	public static void drawHoveringTextFluid(List lines, int x, int y, FontRenderer font, RenderItem itemRender, int guiWidth, int guiHeight, FluidType type) {
		int color0 = type.getColor();
		int r = ColorUtil.ir(color0);
		int g = ColorUtil.ig(color0);
		int b = ColorUtil.ib(color0);
		int add = (r + g + b) / 3 > 0x80 ? -0x40 : 0x40;
		int color1 = ColorUtil.color(MathHelper.clamp_int(r + add, 0, 255), MathHelper.clamp_int(g + add, 0, 255), MathHelper.clamp_int(b + add, 0, 255));
		color0 |= 0xff000000;
		color1 |= 0xff000000;
		drawHoveringText(lines, x, y, font, itemRender, guiWidth, guiHeight, 6, STANDARD_LINE_DIST, STANDARD_COLOR_BACKGROUND, STANDARD_COLOR_BACKGROUND, color0, color1);
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
