package com.hbm.dim.orbit;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.SkyProviderCelestial;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.SolarSystem.AstroMetric;
import com.hbm.lib.Library;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class SkyProviderOrbit extends SkyProviderCelestial {

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		Tessellator tessellator = Tessellator.instance;
		CelestialBody orbiting = ((WorldProviderOrbit)world.provider).getOrbitingBody();
		double altitude = 1_000_000; // higher than actual altitude to visually slow down orbiting
		float orbitalTilt = 80;

		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_BLEND);
		RenderHelper.disableStandardItemLighting();

		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

		
		float celestialAngle = world.getCelestialAngle(partialTicks);
		celestialAngle -= (float)SolarSystem.calculateSingleAngle(world, partialTicks, orbiting, altitude) / 360.0F;
		float celestialPhase = (1 - (celestialAngle + 0.5F) % 1) * 2 - 1;

		float starBrightness = (float)Library.smoothstep(Math.abs(celestialPhase), 0.6, 0.75);

		renderStars(partialTicks, world, mc, starBrightness, celestialAngle, orbitalTilt);

		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);

		GL11.glPushMatrix();
		{

			GL11.glRotatef(orbitalTilt, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(celestialAngle * 360.0F, 1.0F, 0.0F, 0.0F);

			double sunSize = SolarSystem.calculateSunSize(orbiting);
			double coronaSize = sunSize * (3 - Library.smoothstep(Math.abs(celestialPhase), 0.7, 0.8));

			renderSun(partialTicks, world, mc, sunSize, coronaSize, 1, 0);

			List<AstroMetric> metrics = SolarSystem.calculateMetricsFromPosition(world, partialTicks, orbiting, altitude);

			renderCelestials(partialTicks, world, mc, metrics, celestialAngle, orbiting, Vec3.createVectorHelper(0, 0, 0), 1, 1, true);
			
			GL11.glEnable(GL11.GL_BLEND);

			// digma balls
			renderDigamma(partialTicks, world, mc, celestialAngle);

		}
		GL11.glPopMatrix();

		double uvOffset = (((double)world.getWorldTime() + partialTicks) / 512) % 1;
		GL11.glPushMatrix();
		{

			GL11.glRotatef(orbitalTilt, 1.0F, 0.0F, 0.0F);

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_FOG);
			GL11.glEnable(GL11.GL_BLEND);

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	
			GL11.glColor4f(1, 1, 1, 1);
			mc.renderEngine.bindTexture(orbiting.texture);
			GL11.glRotated(180, 1, 0, 0);
			
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-160, 100.0D, -160, 0.0D + uvOffset, 0.0D);
			tessellator.addVertexWithUV(160, 100.0D, -160, 1.0D + uvOffset, 0.0D);
			tessellator.addVertexWithUV(160, 100.0D, 160, 1.0D + uvOffset, 1.0D);
			tessellator.addVertexWithUV(-160, 100.0D, 160, 0.0D + uvOffset, 1.0D);
			tessellator.draw();

			// GL11.glEnable(GL11.GL_BLEND);
			
			// // Draw a shader on top to render celestial phase
			// OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_DST_ALPHA, GL11.GL_ZERO);

			// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			planetShader.use();
			planetShader.setUniforms(celestialPhase, 0);
			
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-160, 100.0D, -160, 0.0D, 0.0D);
			tessellator.addVertexWithUV(160, 100.0D, -160, 1.0D, 0.0D);
			tessellator.addVertexWithUV(160, 100.0D, 160, 1.0D, 1.0D);
			tessellator.addVertexWithUV(-160, 100.0D, 160, 0.0D, 1.0D);
			tessellator.draw();

			planetShader.stop();

			// GL11.glEnable(GL11.GL_ALPHA_TEST);
			// GL11.glEnable(GL11.GL_FOG);
			// GL11.glDisable(GL11.GL_BLEND);

			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);

		}
		GL11.glPopMatrix();


		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
	}
	
}
