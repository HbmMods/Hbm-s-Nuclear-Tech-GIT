package com.hbm.dim.laythe;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.SkyProviderCelestial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class SkyProviderLaytheSunset extends SkyProviderCelestial {

	public SkyProviderLaytheSunset() {
		super();
	}

	@Override
	public void renderSunset(float partialTicks, WorldClient world, Minecraft mc) {
		Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float f1 = (float) vec3.xCoord;
		float f2 = (float) vec3.yCoord;
		float f3 = (float) vec3.zCoord;
		float f6;

		if(mc.gameSettings.anaglyph) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
 
		Tessellator tessellator = Tessellator.instance;
		float f7;
		float f8;
		float f9;
		float f10;
		

		float[] afloat = mc.theWorld.provider.calcSunriseSunsetColors(mc.theWorld.getCelestialAngle(partialTicks), partialTicks);


		if(afloat != null) {
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			
			GL11.glPushMatrix();
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(MathHelper.sin(mc.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			f6 = afloat[0];
			f7 = afloat[1];
			f8 = afloat[2];
			float f11;

			if (mc.gameSettings.anaglyph)
			{
				f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
				f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
				f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
				f6 = f9;
				f7 = f10;
				f8 = f11;
			}

			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(f6, f7, f8, afloat[3]);
			tessellator.addVertex(0.0D, 150.0D, 0.0D);
			byte b0 = 16;
			tessellator.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);

			for (int j = 0; j <= b0; ++j)
			{
				f11 = (float)j * (float)Math.PI * 2.0F / (float)b0;
				float f12 = MathHelper.sin(f11);
				float f13 = MathHelper.cos(f11);
				tessellator.addVertex((double)(f12 * 160.0F), (double)(f13 * 160.0F), (double)(-f13 * 90.0F * afloat[3]));
			}

			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(GL11.GL_FLAT);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);

			GL11.glPushMatrix();
			GL11.glRotatef(135.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0, -60, 0);
			f6 = afloat[0];
			f7 = afloat[1];
			f8 = afloat[2];
			if (mc.gameSettings.anaglyph) {
				f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
				f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
				f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
				f6 = f9;
				f7 = f10;
				f8 = f11;
			}

			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(f6, f7, f8, afloat[3]);
			tessellator.addVertex(0.0D, 100.0D, 0.0D);

			tessellator.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);

			for (int j = 0; j <= b0; ++j) {
				f11 = (float)j * (float)Math.PI * 2.0F / (float)b0;
				float f12 = MathHelper.sin(f11);
				float f13 = MathHelper.cos(f11);
				
				tessellator.addVertex((double)(f12 * 100.0F), (double)(f13 * 100.0F), (double)(-f13 * 90.0F));
			}

			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(GL11.GL_FLAT);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glPushMatrix();

			GL11.glRotatef(135.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0, -30, 0);
			f6 = afloat[0];
			f7 = afloat[1];
			f8 = afloat[2];
			if (mc.gameSettings.anaglyph) {
				f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
				f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
				f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
				f6 = f9;
				f7 = f10;
				f8 = f11;
			}

			tessellator.startDrawing(6);
			tessellator.setColorRGBA_F(f6, f7, f8, afloat[3]);
			tessellator.addVertex(0.0D, 80.0D, 0.0D);

			tessellator.setColorRGBA_F(afloat[0], afloat[1] * 0.2F, afloat[2], 0.0F);

			for (int j = 0; j <= b0; ++j) {
				f11 = (float)j * (float)Math.PI * 2.0F / (float)b0;
				float f12 = MathHelper.sin(f11);
				float f13 = MathHelper.cos(f11);
				
				tessellator.addVertex((double)(f12 * 100.0F), (double)(f13 * 100.0F), (double)(-f13 * 90.0F));
			}

			tessellator.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(GL11.GL_FLAT);

		}
	}

}
