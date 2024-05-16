package com.hbm.dim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.SolarSystem.AstroMetric;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CelestialBodyTrait.CBT_SUNEXPLODED;
import com.hbm.extprop.HbmLivingProps;

public class SkyProviderCelestial extends IRenderHandler {
	
	private static final ResourceLocation planetTexture = new ResourceLocation("hbm:textures/misc/space/planet.png");
	private static final ResourceLocation overlayNew = new ResourceLocation("hbm:textures/misc/space/phase_overlay_new.png");
	private static final ResourceLocation overlayCrescent = new ResourceLocation("hbm:textures/misc/space/phase_overlay_crescent.png");
	private static final ResourceLocation overlayHalf = new ResourceLocation("hbm:textures/misc/space/phase_overlay_half.png");
	private static final ResourceLocation overlayGibbous = new ResourceLocation("hbm:textures/misc/space/phase_overlay_gibbous.png");
	// private static final ResourceLocation flash = new ResourceLocation("hbm:textures/misc/space/flare.png");
	private static final ResourceLocation flareTexture = new ResourceLocation("hbm:textures/misc/space/sunspike.png");
	private static final ResourceLocation nightTexture = new ResourceLocation("hbm:textures/misc/space/night.png");
	private static final ResourceLocation digammaStar = new ResourceLocation("hbm:textures/misc/space/star_digamma.png");

	public static boolean displayListsInitialized = false;
	public static int glSkyList;
	public static int glSkyList2;

	public SkyProviderCelestial() {
		if (!displayListsInitialized) {
			initializeDisplayLists();
		}
	}

	private void initializeDisplayLists() {
		glSkyList = GLAllocation.generateDisplayLists(2);
		glSkyList2 = glSkyList + 1;

		final Tessellator tessellator = Tessellator.instance;
		final byte byte2 = 64;
		final int i = 256 / byte2 + 2;

		GL11.glNewList(glSkyList, GL11.GL_COMPILE);
		{
			float f = 16F;
			tessellator.startDrawingQuads();

			for(int j = -byte2 * i; j <= byte2 * i; j += byte2) {
				for(int l = -byte2 * i; l <= byte2 * i; l += byte2) {
					tessellator.addVertex(j + 0, f, l + 0);
					tessellator.addVertex(j + byte2, f, l + 0);
					tessellator.addVertex(j + byte2, f, l + byte2);
					tessellator.addVertex(j + 0, f, l + byte2);
				}
			}

			tessellator.draw();
		}
		GL11.glEndList();

		GL11.glNewList(glSkyList2, GL11.GL_COMPILE);
		{
			float f = -16F;
			tessellator.startDrawingQuads();

			for(int k = -byte2 * i; k <= byte2 * i; k += byte2) {
				for(int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2) {
					tessellator.addVertex(k + byte2, f, i1 + 0);
					tessellator.addVertex(k + 0, f, i1 + 0);
					tessellator.addVertex(k + 0, f, i1 + byte2);
					tessellator.addVertex(k + byte2, f, i1 + byte2);
				}
			}

			tessellator.draw();
		}
		GL11.glEndList();

		displayListsInitialized = true;
	}
	

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		CelestialBody body = CelestialBody.getBody(world);

		boolean hasAtmosphere = body.hasTrait(CBT_Atmosphere.class);
		boolean sundied = body.hasTrait(CBT_SUNEXPLODED.class);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float skyR = (float) vec3.xCoord;
		float skyG = (float) vec3.yCoord;
		float skyB = (float) vec3.zCoord;

		if(mc.gameSettings.anaglyph) {
			float f4 = (skyR * 30.0F + skyG * 59.0F + skyB * 11.0F) / 100.0F;
			float f5 = (skyR * 30.0F + skyG * 70.0F) / 100.0F;
			float f6 = (skyR * 30.0F + skyB * 70.0F) / 100.0F;
			skyR = f4;
			skyG = f5;
			skyB = f6;
		}

		GL11.glColor3f(skyR, skyG, skyB);
		Tessellator tessellator = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(skyR, skyG, skyB);
		GL11.glCallList(glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();

		float starBrightness = world.getStarBrightness(partialTicks);
		float celestialAngle = world.getCelestialAngle(partialTicks);

		// Handle any special per-body sunset rendering
		renderSunset(partialTicks, world, mc);

		if(starBrightness > 0.0F) {
			GL11.glPushMatrix();
			{
				GL11.glRotatef(body.axialTilt, 1.0F, 0.0F, 0.0F);

				mc.renderEngine.bindTexture(nightTexture);
	
				GL11.glEnable(3553);
				GL11.glBlendFunc(770, 1);
	
				float starBrightnessAlpha = starBrightness * 0.6f;
				GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightnessAlpha);
				
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
	
				GL11.glRotatef(celestialAngle * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightnessAlpha);
				
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 4);
				
				GL11.glPushMatrix();
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				renderSkyboxSide(tessellator, 1);
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				renderSkyboxSide(tessellator, 0);
				GL11.glPopMatrix();
				
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 5);
				
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 2);
				
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				renderSkyboxSide(tessellator, 3);
				GL11.glDisable(3553);

			}
			GL11.glPopMatrix();
		}

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
		
		GL11.glPushMatrix();
		{

			GL11.glRotatef(body.axialTilt, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(celestialAngle * 360.0F, 1.0F, 0.0F, 0.0F);

			double sunSize = SolarSystem.calculateSunSize(body);
			double coronaSize = sunSize * (hasAtmosphere ? 2 : 3);

			// Some blanking to conceal the stars
			if(!sundied) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
	
				tessellator.startDrawingQuads();
				tessellator.addVertex(-sunSize, 99.9D, -sunSize);
				tessellator.addVertex(sunSize, 99.9D, -sunSize);
				tessellator.addVertex(sunSize, 99.9D, sunSize);
				tessellator.addVertex(-sunSize, 99.9D, sunSize);
				tessellator.draw();

				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			// Draw the MIGHTY SUN
			if(!sundied) {

				mc.renderEngine.bindTexture(SolarSystem.kerbol.texture);

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-sunSize, 100.0D, -sunSize, 0.0D, 0.0D);
				tessellator.addVertexWithUV(sunSize, 100.0D, -sunSize, 1.0D, 0.0D);
				tessellator.addVertexWithUV(sunSize, 100.0D, sunSize, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-sunSize, 100.0D, sunSize, 0.0D, 1.0D);
				tessellator.draw();

			}

			// Draw a big ol' spiky flare! Less so when there is an atmosphere
			if(!sundied) {

				if(hasAtmosphere) {
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25f);
				}

				mc.renderEngine.bindTexture(flareTexture);

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-coronaSize, 100.0D, -coronaSize, 0.0D, 0.0D);
				tessellator.addVertexWithUV(coronaSize, 100.0D, -coronaSize, 1.0D, 0.0D);
				tessellator.addVertexWithUV(coronaSize, 100.0D, coronaSize, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-coronaSize, 100.0D, coronaSize, 0.0D, 1.0D);
				tessellator.draw();

			}

			
			double minSize = 1D;
			float blendAmount = hasAtmosphere ? MathHelper.clamp_float(1 - world.getSunBrightnessFactor(partialTicks), 0.25F, 1F) : 1F;
			float blendDarken = 0.1F;

			double longitude = 0;
			CelestialBody tidalLockedBody = body.tidallyLockedTo != null ? CelestialBody.getBody(body.tidallyLockedTo) : null;

			if(tidalLockedBody != null) {
				longitude = SolarSystem.calculateSingleAngle(world, partialTicks, body, tidalLockedBody) + celestialAngle * 360.0 + 60.0;
			}

			// Get our orrery of bodies
			List<AstroMetric> metrics = SolarSystem.calculateMetricsFromBody(world, partialTicks, longitude, body);
			
			for(AstroMetric metric : metrics) {

				// Ignore self
				if(metric.distance == 0)
					continue;

				GL11.glPushMatrix();
				{

					double size = MathHelper.clamp_double(metric.apparentSize, 0, 24);
					boolean renderAsPoint = size < minSize;

					if(renderAsPoint) {
						GL11.glColor4f(metric.body.color[0], metric.body.color[1], metric.body.color[2], (float)size * 100);
						mc.renderEngine.bindTexture(planetTexture);

						size = minSize;
					} else {
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture(metric.body.texture);
					}

					if(metric.body == tidalLockedBody) {
						GL11.glRotated(celestialAngle * -360.0 - 60.0, 1.0, 0.0, 0.0);
					} else {
						GL11.glRotated(metric.angle, 1.0, 0.0, 0.0);
					}
					GL11.glRotatef(metric.body.axialTilt + 90.0F, 0.0F, 1.0F, 0.0F);

					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-size, 100.0D, -size, 0.0D, 0.0D);
					tessellator.addVertexWithUV(size, 100.0D, -size, 1.0D, 0.0D);
					tessellator.addVertexWithUV(size, 100.0D, size, 1.0D, 1.0D);
					tessellator.addVertexWithUV(-size, 100.0D, size, 0.0D, 1.0D);
					tessellator.draw();

					if(!renderAsPoint) {
						GL11.glEnable(GL11.GL_BLEND);
						
						// Draw a texture on top to simulate phase
						OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

						double phase = Math.abs(metric.phase);
						double sign = Math.signum(metric.phase);

						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glRotatef((float)sign * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
						
						if(phase > 0.95F) {
							mc.renderEngine.bindTexture(overlayNew);
						} else if(phase > 0.8F) {
							mc.renderEngine.bindTexture(overlayCrescent);
						} else if(phase > 0.5F) {
							mc.renderEngine.bindTexture(overlayHalf);
						} else {
							mc.renderEngine.bindTexture(overlayGibbous);
						}
						
						if(phase > 0.3F) {
							tessellator.startDrawingQuads();
							tessellator.addVertexWithUV(-size, 100.0D, -size, 0.0D, 0.0D);
							tessellator.addVertexWithUV(size, 100.0D, -size, 1.0D, 0.0D);
							tessellator.addVertexWithUV(size, 100.0D, size, 1.0D, 1.0D);
							tessellator.addVertexWithUV(-size, 100.0D, size, 0.0D, 1.0D);
							tessellator.draw();
						}


						GL11.glDisable(GL11.GL_TEXTURE_2D);
						
						// Draw another layer on top to blend with the atmosphere
						GL11.glColor4f(skyR - blendDarken, skyG - blendDarken, skyB - blendDarken, 1 - blendAmount);
						OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
	
						tessellator.startDrawingQuads();
						tessellator.addVertexWithUV(-size, 100.0D, -size, 0.0D, 0.0D);
						tessellator.addVertexWithUV(size, 100.0D, -size, 1.0D, 0.0D);
						tessellator.addVertexWithUV(size, 100.0D, size, 1.0D, 1.0D);
						tessellator.addVertexWithUV(-size, 100.0D, size, 0.0D, 1.0D);
						tessellator.draw();
	
						GL11.glEnable(GL11.GL_TEXTURE_2D);
					}

				}
				GL11.glPopMatrix();
			}

			GL11.glEnable(GL11.GL_BLEND);


			// Draw DIGAMMA STAR
			GL11.glPushMatrix();
			{

				OpenGlHelper.glBlendFunc(770, 1, 1, 0);

				float brightness = (float) Math.sin(celestialAngle * Math.PI);
				brightness *= brightness;
				GL11.glColor4f(brightness, brightness, brightness, brightness);
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(celestialAngle * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);

				mc.renderEngine.bindTexture(digammaStar);

				float digamma = HbmLivingProps.getDigamma(Minecraft.getMinecraft().thePlayer);
				float var12 = 1F * (1 + digamma * 0.25F);
				double dist = 100D - digamma * 2.5;

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-var12, dist, -var12, 0.0D, 0.0D);
				tessellator.addVertexWithUV(var12, dist, -var12, 0.0D, 1.0D);
				tessellator.addVertexWithUV(var12, dist, var12, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-var12, dist, var12, 1.0D, 0.0D);
				tessellator.draw();

			}
			GL11.glPopMatrix();

			GL11.glDisable(GL11.GL_TEXTURE_2D);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_FOG);

		}
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		double heightAboveHorizon = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();

		if(heightAboveHorizon < 0.0D) {
			GL11.glPushMatrix();
			{

				GL11.glTranslatef(0.0F, 12.0F, 0.0F);
				GL11.glCallList(glSkyList2);

			}
			GL11.glPopMatrix();

			float f8 = 1.0F;
			float f9 = -((float) (heightAboveHorizon + 65.0D));
			float opposite = -f8;

			tessellator.startDrawingQuads();
			tessellator.setColorRGBA_I(0, 255);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, opposite, f8);
			tessellator.addVertex(-f8, opposite, f8);
			tessellator.addVertex(-f8, opposite, -f8);
			tessellator.addVertex(f8, opposite, -f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(f8, opposite, -f8);
			tessellator.addVertex(f8, opposite, f8);
			tessellator.addVertex(f8, f9, f8);
			tessellator.addVertex(f8, f9, -f8);
			tessellator.addVertex(-f8, f9, -f8);
			tessellator.addVertex(-f8, f9, f8);
			tessellator.addVertex(-f8, opposite, f8);
			tessellator.addVertex(-f8, opposite, -f8);
			tessellator.addVertex(-f8, opposite, -f8);
			tessellator.addVertex(-f8, opposite, f8);
			tessellator.addVertex(f8, opposite, f8);
			tessellator.addVertex(f8, opposite, -f8);
			tessellator.draw();
		}

		if(world.provider.isSkyColored()) {
			GL11.glColor3f(skyR * 0.2F + 0.04F, skyG * 0.2F + 0.04F, skyB * 0.6F + 0.1F);
		} else {
			GL11.glColor3f(skyR, skyG, skyB);
		}

		GL11.glPushMatrix();
		{

			GL11.glTranslatef(0.0F, -((float) (heightAboveHorizon - 16.0D)), 0.0F);
			GL11.glCallList(glSkyList2);

		}
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);

	}
	
	public void renderSunset(float partialTicks, WorldClient world, Minecraft mc) {

	}
	
	// is just drawing a big cube with UVs prepared to draw a gradient
	private void renderSkyboxSide(Tessellator tessellator, int side) {
		double u = side % 3 / 3.0D;
		double v = side / 3 / 2.0D;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-100.0D, -100.0D, -100.0D, u, v);
		tessellator.addVertexWithUV(-100.0D, -100.0D, 100.0D, u, v + 0.5D);
		tessellator.addVertexWithUV(100.0D, -100.0D, 100.0D, u + 0.3333333333333333D, v + 0.5D);
		tessellator.addVertexWithUV(100.0D, -100.0D, -100.0D, u + 0.3333333333333333D, v);
		tessellator.draw();
	}

}