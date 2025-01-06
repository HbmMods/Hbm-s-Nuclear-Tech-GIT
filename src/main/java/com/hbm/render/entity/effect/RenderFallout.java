package com.hbm.render.entity.effect;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFallout extends Render {

	private static final ResourceLocation falloutTexture = new ResourceLocation(RefStrings.MODID, "textures/entity/fallout.png");
	private Random random = new Random();
	float[] rainXCoords;
	float[] rainYCoords;
	long lastTime = System.nanoTime();

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

		if(entity instanceof EntityFalloutRain)
			this.render((EntityFalloutRain) entity, x, y, z, f0, f1);

	}

	public void render(EntityFalloutRain entity, double x, double y, double z, float f0, float f1) {

		Minecraft mc = Minecraft.getMinecraft();

		EntityLivingBase entitylivingbase = mc.renderViewEntity;
		Vec3 vector = Vec3.createVectorHelper(entitylivingbase.posX - entity.posX, entitylivingbase.posY - entity.posY, entitylivingbase.posZ - entity.posZ);

		if(vector.lengthVector() <= entity.getScale()) {
			long time = System.nanoTime();
			float t = (time - lastTime) / 50_000_000;
			if(t <= 1.0F)
				renderRainSnow(t);
			else
				renderRainSnow(1.0F);

			lastTime = time;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.falloutTexture;
	}

	protected void renderRainSnow(float interp) {

		Minecraft mc = Minecraft.getMinecraft();
		int timer = mc.thePlayer.ticksExisted;

		float intensity = 1F;

		if(intensity > 0.0F) {

			if(this.rainXCoords == null) {
				this.rainXCoords = new float[1024];
				this.rainYCoords = new float[1024];

				for(int i = 0; i < 32; ++i) {
					for(int j = 0; j < 32; ++j) {
						float f2 = j - 16;
						float f3 = i - 16;
						float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
						this.rainXCoords[i << 5 | j] = -f3 / f4;
						this.rainYCoords[i << 5 | j] = f2 / f4;
					}
				}
			}

			WorldClient worldclient = mc.theWorld;
			EntityLivingBase camera = mc.renderViewEntity;

			int playerX = MathHelper.floor_double(camera.posX);
			int playerY = MathHelper.floor_double(camera.posY);
			int playerZ = MathHelper.floor_double(camera.posZ);
			double dX = camera.lastTickPosX + (camera.posX - camera.lastTickPosX) * interp;
			double dY = camera.lastTickPosY + (camera.posY - camera.lastTickPosY) * interp;
			double dZ = camera.lastTickPosZ + (camera.posZ - camera.lastTickPosZ) * interp;

			int playerHeight = MathHelper.floor_double(dY);
			byte renderLayerCount = 5;
			byte layer = -1;

			if(mc.gameSettings.fancyGraphics) renderLayerCount = 10;

			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			for(int layerZ = playerZ - renderLayerCount; layerZ <= playerZ + renderLayerCount; ++layerZ) {
				for(int layerX = playerX - renderLayerCount; layerX <= playerX + renderLayerCount; ++layerX) {
					int rainCoord = (layerZ - playerZ + 16) * 32 + layerX - playerX + 16;
					float rainCoordX = this.rainXCoords[rainCoord] * 0.5F;
					float rainCoordY = this.rainYCoords[rainCoord] * 0.5F;

					int rainHeight = worldclient.getPrecipitationHeight(layerX, layerZ);
					int minHeight = playerY - renderLayerCount;
					int maxHeight = playerY + renderLayerCount;

					if(minHeight < rainHeight) minHeight = rainHeight;
					if(maxHeight < rainHeight) maxHeight = rainHeight;
					
					int layerY = rainHeight;
					if(rainHeight < playerHeight) layerY = playerHeight;

					if(minHeight != maxHeight) {
						this.random.setSeed(layerX * layerX * 3121 + layerX * 45238971 ^ layerZ * layerZ * 418711 + layerZ * 13761);

						if(layer != 1) {
							if(layer >= 0) {
								tessellator.draw();
							}

							layer = 1;
							mc.getTextureManager().bindTexture(RenderFallout.falloutTexture);
							tessellator.startDrawingQuads();
						}

						float fallSpeed = 1.0F;
						float swayLoop = ((timer & 511) + interp) / 512.0F;
						float fallVariation = 0.4F + this.random.nextFloat() * 0.2F;
						float swayVariation = this.random.nextFloat();
						double distX = layerX + 0.5F - camera.posX;
						double distZ = layerZ + 0.5F - camera.posZ;
						float intensityMod = MathHelper.sqrt_double(distX * distX + distZ * distZ) / renderLayerCount;
						float colorMod = 1.0F;
						
						tessellator.setBrightness((worldclient.getLightBrightnessForSkyBlocks(layerX, layerY, layerZ, 0) * 3 + 15728880) / 4);
						tessellator.setColorRGBA_F(colorMod, colorMod, colorMod, ((1.0F - intensityMod * intensityMod) * 0.3F + 0.5F) * intensity);
						tessellator.setTranslation(-dX * 1.0D, -dY * 1.0D, -dZ * 1.0D);
						tessellator.addVertexWithUV(layerX - rainCoordX + 0.5D, minHeight, layerZ - rainCoordY + 0.5D, 0.0F * fallSpeed + fallVariation, minHeight * fallSpeed / 4.0F + swayLoop * fallSpeed + swayVariation);
						tessellator.addVertexWithUV(layerX + rainCoordX + 0.5D, minHeight, layerZ + rainCoordY + 0.5D, 1.0F * fallSpeed + fallVariation, minHeight * fallSpeed / 4.0F + swayLoop * fallSpeed + swayVariation);
						tessellator.addVertexWithUV(layerX + rainCoordX + 0.5D, maxHeight, layerZ + rainCoordY + 0.5D, 1.0F * fallSpeed + fallVariation, maxHeight * fallSpeed / 4.0F + swayLoop * fallSpeed + swayVariation);
						tessellator.addVertexWithUV(layerX - rainCoordX + 0.5D, maxHeight, layerZ - rainCoordY + 0.5D, 0.0F * fallSpeed + fallVariation, maxHeight * fallSpeed / 4.0F + swayLoop * fallSpeed + swayVariation);
						tessellator.setTranslation(0.0D, 0.0D, 0.0D);
					}
				}
			}

			if(layer >= 0) {
				tessellator.draw();
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		}
	}
}
