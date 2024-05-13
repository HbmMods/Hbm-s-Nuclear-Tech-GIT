package com.hbm.dim;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.extprop.HbmLivingProps;

public class SkyProviderCelestial extends IRenderHandler {
	
	private static final ResourceLocation planetTexture = new ResourceLocation("hbm:textures/misc/space/planet.png");
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
		CelestialBody currentBody = CelestialBody.getBody(world);
		CelestialBody parentBody = currentBody.parent != SolarSystem.kerbol ? currentBody.parent : null;

		boolean hasAtmosphere = currentBody.hasTrait(CBT_Atmosphere.class);

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

		if(starBrightness > 0.0F) {
			GL11.glPushMatrix();
			{
				GL11.glRotatef(currentBody.axialTilt, 1.0F, 0.0F, 0.0F);

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

			GL11.glRotatef(currentBody.axialTilt, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(celestialAngle * 360.0F, 1.0F, 0.0F, 0.0F);

			float sunSize = SolarSystem.calculateSunSize(currentBody);
			float coronaSize = sunSize * (hasAtmosphere ? 2 : 3);

			// Some blanking to conceal the stars
			{
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
			{

				mc.renderEngine.bindTexture(SolarSystem.kerbol.texture);

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-sunSize, 100.0D, -sunSize, 0.0D, 0.0D);
				tessellator.addVertexWithUV(sunSize, 100.0D, -sunSize, 1.0D, 0.0D);
				tessellator.addVertexWithUV(sunSize, 100.0D, sunSize, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-sunSize, 100.0D, sunSize, 0.0D, 1.0D);
				tessellator.draw();
			}

			// Draw a big ol' spiky flare! Less so when there is an atmosphere
			{
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

			// Draw each of the planets
			for(CelestialBody planet : SolarSystem.kerbol.satellites) {

				if(planet == currentBody || planet == currentBody.parent)
					continue;

				GL11.glPushMatrix();
				{

					float planetSize = 1F;
					float planetAngle = SolarSystem.calculatePlanetAngle(world.getWorldTime(), partialTicks, parentBody != null ? parentBody : currentBody, planet);

					if((parentBody != null ? parentBody : currentBody).semiMajorAxisKm > planet.semiMajorAxisKm) {
						GL11.glRotatef(celestialAngle * -360.0F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
					}

					GL11.glColor4d(planet.color[0], planet.color[1], planet.color[2], 1);
					GL11.glRotatef(planetAngle * -360.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(planet.axialTilt, 0.0F, 1.0F, 0.0F);

					mc.renderEngine.bindTexture(planetTexture);

					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-planetSize, -100.0D, planetSize, 0.0D, 0.0D);
					tessellator.addVertexWithUV(planetSize, -100.0D, planetSize, 1.0D, 0.0D);
					tessellator.addVertexWithUV(planetSize, -100.0D, -planetSize, 1.0D, 1.0D);
					tessellator.addVertexWithUV(-planetSize, -100.0D, -planetSize, 0.0D, 1.0D);
					tessellator.draw();

				}
				GL11.glPopMatrix();
			}
			
			GL11.glDisable(GL11.GL_BLEND);

			// Draw any moons (either our parents or our own)
			for(CelestialBody moon : (parentBody != null ? parentBody.satellites : currentBody.satellites)) {

				if(moon == currentBody)
					continue;

				GL11.glPushMatrix();
				{

					float moonSize = SolarSystem.calculateBodySize(moon, moon);
					float moonAngle = SolarSystem.calculatePlanetAngle(world.getWorldTime(), partialTicks, currentBody, moon);

					if(currentBody.semiMajorAxisKm > moon.semiMajorAxisKm) {
						GL11.glRotatef(celestialAngle * -360.0F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
					}

					GL11.glColor4d(moon.color[0], moon.color[1], moon.color[2], 1);
					GL11.glRotatef(moonAngle * -360.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(moon.axialTilt, 0.0F, 1.0F, 0.0F);

					mc.renderEngine.bindTexture(moon.texture);

					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-moonSize, -100.0D, moonSize, 0.0D, 0.0D);
					tessellator.addVertexWithUV(moonSize, -100.0D, moonSize, 1.0D, 0.0D);
					tessellator.addVertexWithUV(moonSize, -100.0D, -moonSize, 1.0D, 1.0D);
					tessellator.addVertexWithUV(-moonSize, -100.0D, -moonSize, 0.0D, 1.0D);
					tessellator.draw();
					
				}
				GL11.glPopMatrix();

			}

			// Draw the parent planet if we're a moon
			if(parentBody != null) {
				GL11.glPushMatrix();
				{

					GL11.glColor4d(1, 1, 1, 1);
					GL11.glRotatef(celestialAngle * -360.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(90.0F + parentBody.axialTilt, 0.0F, 1.0F, 0.0F);
	
					float parentSize = SolarSystem.calculateBodySize(parentBody, currentBody);
	
					mc.renderEngine.bindTexture(parentBody.texture);

					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-parentSize, 100.0D, -parentSize, 0.0D, 0.0D);
					tessellator.addVertexWithUV(parentSize, 100.0D, -parentSize, 1.0D, 0.0D);
					tessellator.addVertexWithUV(parentSize, 100.0D, parentSize, 1.0D, 1.0D);
					tessellator.addVertexWithUV(-parentSize, 100.0D, parentSize, 0.0D, 1.0D);
					tessellator.draw();
					
				}
				GL11.glPopMatrix();
			}
					
			GL11.glEnable(GL11.GL_BLEND);


			// Draw DIGAMMA STAR
			GL11.glPushMatrix();
			{

				OpenGlHelper.glBlendFunc(770, 1, 1, 0);

				float brightness = (float) Math.sin(world.getCelestialAngle(partialTicks) * Math.PI);
				brightness *= brightness;
				GL11.glColor4f(brightness, brightness, brightness, brightness);
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
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