package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class DiamondPronter {
	
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/models/misc/danger_diamond.png");
	
	public static void pront(int poison, int flammability, int reactivity, EnumSymbol symbol) {
		
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		float p = 1F/256F;
		float s = 1F/139F;
		Tessellator tess = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

		tess.startDrawingQuads();
		tess.addVertexWithUV(0.0, 0.5, -0.5, p * 144, p * 45);
		tess.addVertexWithUV(0.0, 0.5, 0.5, p * 5, p * 45);
		tess.addVertexWithUV(0.0, -0.5, 0.5, p * 5, p * 184);
		tess.addVertexWithUV(0.0, -0.5, -0.5, p * 144, p * 184);
		tess.draw();
		
		float width = 10F * s;
		float height = 14F * s;
		
		if(poison >= 0 && poison < 6) {
			
			float oY = 0;
			float oZ = 33 * s;
			
			int x = 5 + (poison - 1) * 24;
			int y = 5;
			
			if(poison == 0) x = 125;

			tess.startDrawingQuads();
			tess.addVertexWithUV(0.01, height + oY, -width + oZ, (x + 20) * p, y * p);
			tess.addVertexWithUV(0.01, height + oY, width + oZ, x * p, y * p);
			tess.addVertexWithUV(0.01, -height + oY, width + oZ, x * p, (y + 28) * p);
			tess.addVertexWithUV(0.01, -height + oY, -width + oZ, (x + 20) * p, (y + 28) * p);
			tess.draw();
		}
		
		if(flammability >= 0 && flammability < 6) {
			
			float oY = 33 * s;
			float oZ = 0;
			
			int x = 5 + (flammability - 1) * 24;
			int y = 5;
			
			if(flammability == 0) x = 125;

			tess.startDrawingQuads();
			tess.addVertexWithUV(0.01, height + oY, -width + oZ, (x + 20) * p, y * p);
			tess.addVertexWithUV(0.01, height + oY, width + oZ, x * p, y * p);
			tess.addVertexWithUV(0.01, -height + oY, width + oZ, x * p, (y + 28) * p);
			tess.addVertexWithUV(0.01, -height + oY, -width + oZ, (x + 20) * p, (y + 28) * p);
			tess.draw();
		}
		
		if(reactivity >= 0 && reactivity < 6) {
			
			float oY = 0;
			float oZ = -33 * s;
			
			int x = 5 + (reactivity - 1) * 24;
			int y = 5;
			
			if(reactivity == 0) x = 125;

			tess.startDrawingQuads();
			tess.addVertexWithUV(0.01, height + oY, -width + oZ, (x + 20) * p, y * p);
			tess.addVertexWithUV(0.01, height + oY, width + oZ, x * p, y * p);
			tess.addVertexWithUV(0.01, -height + oY, width + oZ, x * p, (y + 28) * p);
			tess.addVertexWithUV(0.01, -height + oY, -width + oZ, (x + 20) * p, (y + 28) * p);
			tess.draw();
		}
		

		float symSize = 59F/2F * s;
		
		if(symbol != EnumSymbol.NONE) {
			
			float oY = -33 * s;
			float oZ = 0;
			
			int x = symbol.x;
			int y = symbol.y;

			tess.startDrawingQuads();
			tess.addVertexWithUV(0.01, symSize + oY, -symSize + oZ, (x + 59) * p, y * p);
			tess.addVertexWithUV(0.01, symSize + oY, symSize + oZ, x * p, y * p);
			tess.addVertexWithUV(0.01, -symSize + oY, symSize + oZ, x * p, (y + 59) * p);
			tess.addVertexWithUV(0.01, -symSize + oY, -symSize + oZ, (x + 59) * p, (y + 59) * p);
			tess.draw();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

}
