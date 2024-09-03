package com.hbm.dim.laythe;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.SkyProviderCelestial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;

public class SkyProviderLaytheSunset extends SkyProviderCelestial {

	public SkyProviderLaytheSunset() {
		super();
	}

	@Override
	protected void renderSunset(float partialTicks, WorldClient world, Minecraft mc) {
		Tessellator tessellator = Tessellator.instance;

		float[] sunsetColor = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

		if(sunsetColor != null) {
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA, GL11.GL_ONE, GL11.GL_ZERO); // The magic sauce

			float[] anaglyphColor = mc.gameSettings.anaglyph ? applyAnaglyph(sunsetColor) : sunsetColor;
			byte segments = 16;

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			
			GL11.glPushMatrix();
			{

				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
	
				tessellator.startDrawing(6);
				tessellator.setColorRGBA_F(anaglyphColor[0], anaglyphColor[1], anaglyphColor[2], sunsetColor[3]);
				tessellator.addVertex(0.0D, 150.0D, 0.0D);
				tessellator.setColorRGBA_F(sunsetColor[0], sunsetColor[1], sunsetColor[2], 0.0F);
	
				for(int j = 0; j <= segments; ++j) {
					float angle = (float)j * (float)Math.PI * 2.0F / (float)segments;
					float sinAngle = MathHelper.sin(angle);
					float cosAngle = MathHelper.cos(angle);
					tessellator.addVertex((double)(sinAngle * 160.0F), (double)(cosAngle * 160.0F), (double)(-cosAngle * 90.0F * sunsetColor[3]));
				}
	
				tessellator.draw();

			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{

				GL11.glRotatef(135.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0, -60, 0);
	
				tessellator.startDrawing(6);
				tessellator.setColorRGBA_F(anaglyphColor[0], anaglyphColor[1], anaglyphColor[2], sunsetColor[3]);
				tessellator.addVertex(0.0D, 100.0D, 0.0D);
				tessellator.setColorRGBA_F(sunsetColor[0], sunsetColor[1], sunsetColor[2], 0.0F);
	
				for(int j = 0; j <= segments; ++j) {
					float angle = (float)j * (float)Math.PI * 2.0F / (float)segments;
					float sinAngle = MathHelper.sin(angle);
					float cosAngle = MathHelper.cos(angle);
					
					tessellator.addVertex((double)(sinAngle * 100.0F), (double)(cosAngle * 100.0F), (double)(-cosAngle * 90.0F));
				}
	
				tessellator.draw();

			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{			
	
				GL11.glRotatef(135.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0, -30, 0);
	
				tessellator.startDrawing(6);
				tessellator.setColorRGBA_F(anaglyphColor[0], anaglyphColor[1], anaglyphColor[2], sunsetColor[3]);
				tessellator.addVertex(0.0D, 80.0D, 0.0D);
				tessellator.setColorRGBA_F(sunsetColor[0], sunsetColor[1] * 0.2F, sunsetColor[2], 0.0F);
	
				for(int j = 0; j <= segments; ++j) {
					float angle = (float)j * (float)Math.PI * 2.0F / (float)segments;
					float sinAngle = MathHelper.sin(angle);
					float cosAngle = MathHelper.cos(angle);
					
					tessellator.addVertex((double)(sinAngle * 100.0F), (double)(cosAngle * 100.0F), (double)(-cosAngle * 90.0F));
				}
	
				tessellator.draw();

			}
			GL11.glPopMatrix();

			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}

}
