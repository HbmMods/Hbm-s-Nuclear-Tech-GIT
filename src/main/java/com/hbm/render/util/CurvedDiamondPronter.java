package com.hbm.render.util;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class CurvedDiamondPronter {
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/danger_diamond.png");
	private static final float p = 1f / 256f;
	private static final float s = 1f / 139f;

	public static void pront(int poison, int flammability, int reactivity, EnumSymbol symbol, double curve, double size) {
		TextureManager tex = Minecraft.getMinecraft().renderEngine;
		tex.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);

		if(curve <= 0) curve = 10_000D;
		double axis = -curve;

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		quad(tess, axis, curve, size, 0.5, -0.5, -0.5, 0.5, 144 * p, 5 * p, 45 * p, 184 * p, 12);

		double r2 = curve + 0.01;
		digit(tess, axis, r2, size, poison, 0, 33 * s);
		digit(tess, axis, r2, size, flammability, 33 * s, 0);
		digit(tess, axis, r2, size, reactivity, 0, -33 * s);

		if(symbol != null && symbol != EnumSymbol.NONE) {
			float sym = 59f / 2f * s;
			float oY = -33 * s;
			quad(tess, axis, r2, size, oY + sym, oY - sym, -sym, sym, (symbol.x + 59) * p, symbol.x * p, symbol.y * p, (symbol.y + 59) * p, 4);
		}
		
		tess.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	private static void digit(Tessellator tess, double axis, double r, double size, int value, float oY, float oZ) {
		if(value < 0 || value > 5) return;
		int x = value == 0 ? 125 : 5 + (value - 1) * 24;
		int y = 5;
		float w = 10F * s;
		float h = 14F * s;
		quad(tess, axis, r, size, oY + h, oY - h, oZ - w, oZ + w, (x + 20) * p, x * p, y * p, (y + 28) * p, 3);
	}

	private static void quad(Tessellator tess, double axis, double r, double size, double yTop, double yBot, double zMin, double zMax,
							 double uAtZMin, double uAtZMax, double vTop, double vBot, int segments) {

		double top = yTop * size;
		double bot = yBot * size;

		for(int i = 0; i < segments; i++) {
			double fa = (double) i / segments;
			double fb = (double) (i + 1) / segments;

			double za = zMin + (zMax - zMin) * fa;
			double zb = zMin + (zMax - zMin) * fb;
			double ua = uAtZMin + (uAtZMax - uAtZMin) * fa;
			double ub = uAtZMin + (uAtZMax - uAtZMin) * fb;

			double ta = za * size / r;
			double tb = zb * size / r;

			double xa = axis + r * Math.cos(ta), zA = r * Math.sin(ta);
			double xb = axis + r * Math.cos(tb), zB = r * Math.sin(tb);

			tess.addVertexWithUV(xa, top, zA, ua, vTop);
			tess.addVertexWithUV(xb, top, zB, ub, vTop);
			tess.addVertexWithUV(xb, bot, zB, ub, vBot);
			tess.addVertexWithUV(xa, bot, zA, ua, vBot);
		}
	}
}
