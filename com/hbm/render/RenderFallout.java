package com.hbm.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.EntityFalloutRain;
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
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;

public class RenderFallout extends Render {

	private Minecraft mc;
	private Random random = new Random();
	float[] rainXCoords;
	float[] rainYCoords;
	private int rendererUpdateCount;
	long lastTime = System.nanoTime();
	private static final ResourceLocation falloutTexture = new ResourceLocation(RefStrings.MODID,
			"textures/entity/fallout.png");

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {

		if (p_76986_1_ instanceof EntityFalloutRain)
			this.render((EntityFalloutRain) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);

	}

	public void render(EntityFalloutRain p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_) {
		
		this.mc = Minecraft.getMinecraft();

		EntityLivingBase entitylivingbase = this.mc.renderViewEntity;
		Vec3 vector = Vec3.createVectorHelper(entitylivingbase.posX - p_76986_1_.posX,
				entitylivingbase.posY - p_76986_1_.posY, entitylivingbase.posZ - p_76986_1_.posZ);
		
		double d = vector.lengthVector();
		
		if (d <= p_76986_1_.getScale()) {
			rendererUpdateCount++;
			long time = System.nanoTime();
			float t = (time - lastTime) / 50000000;
			if (t <= 1.0F)
				renderRainSnow(t);
			else
				renderRainSnow(1.0F);

			lastTime = time;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

	protected void renderRainSnow(float p_78474_1_) {

		IRenderHandler renderer = null;
		if ((renderer = this.mc.theWorld.provider.getWeatherRenderer()) != null) {
			renderer.render(p_78474_1_, this.mc.theWorld, mc);
			return;
		}

		// float f1 = this.mc.theWorld.getRainStrength(p_78474_1_);

		float f1 = 1;

		if (f1 > 0.0F) {
			// this.enableLightmap((double)p_78474_1_);

			if (this.rainXCoords == null) {
				this.rainXCoords = new float[1024];
				this.rainYCoords = new float[1024];

				for (int i = 0; i < 32; ++i) {
					for (int j = 0; j < 32; ++j) {
						float f2 = (float) (j - 16);
						float f3 = (float) (i - 16);
						float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
						this.rainXCoords[i << 5 | j] = -f3 / f4;
						this.rainYCoords[i << 5 | j] = f2 / f4;
					}
				}
			}

			EntityLivingBase entitylivingbase = this.mc.renderViewEntity;
			WorldClient worldclient = this.mc.theWorld;
			int k2 = MathHelper.floor_double(entitylivingbase.posX);
			int l2 = MathHelper.floor_double(entitylivingbase.posY);
			int i3 = MathHelper.floor_double(entitylivingbase.posZ);
			Tessellator tessellator = Tessellator.instance;
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			double d0 = entitylivingbase.lastTickPosX
					+ (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double) p_78474_1_;
			double d1 = entitylivingbase.lastTickPosY
					+ (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double) p_78474_1_;
			double d2 = entitylivingbase.lastTickPosZ
					+ (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double) p_78474_1_;
			int k = MathHelper.floor_double(d1);
			byte b0 = 5;

			if (this.mc.gameSettings.fancyGraphics) {
				b0 = 10;
			}

			boolean flag = false;
			byte b1 = -1;
			float f5 = (float) this.rendererUpdateCount + p_78474_1_;

			if (this.mc.gameSettings.fancyGraphics) {
				b0 = 10;
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			flag = false;

			for (int l = i3 - b0; l <= i3 + b0; ++l) {
				for (int i1 = k2 - b0; i1 <= k2 + b0; ++i1) {
					int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
					float f6 = this.rainXCoords[j1] * 0.5F;
					float f7 = this.rainYCoords[j1] * 0.5F;
					BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(i1, l);

					if (true) {
						int k1 = worldclient.getPrecipitationHeight(i1, l);
						int l1 = l2 - b0;
						int i2 = l2 + b0;

						if (l1 < k1) {
							l1 = k1;
						}

						if (i2 < k1) {
							i2 = k1;
						}

						float f8 = 1.0F;
						int j2 = k1;

						if (k1 < k) {
							j2 = k;
						}

						if (l1 != i2) {
							this.random.setSeed((long) (i1 * i1 * 3121 + i1 * 45238971 ^ l * l * 418711 + l * 13761));
							float f9 = biomegenbase.getFloatTemperature(i1, l1, l);
							float f10;
							double d4;

							/*
							 * if (false) { if (b1 != 0) { if (b1 >= 0) {
							 * tessellator.draw(); }
							 * 
							 * b1 = 0;
							 * this.mc.getTextureManager().bindTexture(this.
							 * falloutTexture); tessellator.startDrawingQuads();
							 * //System.out.println("Called!"); }
							 * 
							 * f10 = ((float)(this.rendererUpdateCount + i1 * i1
							 * * 3121 + i1 * 45238971 + l * l * 418711 + l *
							 * 13761 & 31) + p_78474_1_) / 32.0F * (3.0F +
							 * this.random.nextFloat()); double d3 =
							 * (double)((float)i1 + 0.5F) -
							 * entitylivingbase.posX; d4 = (double)((float)l +
							 * 0.5F) - entitylivingbase.posZ; float f12 =
							 * MathHelper.sqrt_double(d3 * d3 + d4 * d4) /
							 * (float)b0; float f13 = 1.0F;
							 * tessellator.setBrightness(worldclient.
							 * getLightBrightnessForSkyBlocks(i1, j2, l, 0));
							 * tessellator.setColorRGBA_F(f13, f13, f13, ((1.0F
							 * - f12 * f12) * 0.5F + 0.5F) * f1);
							 * tessellator.setTranslation(-d0 * 1.0D, -d1 *
							 * 1.0D, -d2 * 1.0D);
							 * tessellator.addVertexWithUV((double)((float)i1 -
							 * f6) + 0.5D, (double)l1, (double)((float)l - f7) +
							 * 0.5D, (double)(0.0F * f8), (double)((float)l1 *
							 * f8 / 4.0F + f10 * f8));
							 * tessellator.addVertexWithUV((double)((float)i1 +
							 * f6) + 0.5D, (double)l1, (double)((float)l + f7) +
							 * 0.5D, (double)(1.0F * f8), (double)((float)l1 *
							 * f8 / 4.0F + f10 * f8));
							 * tessellator.addVertexWithUV((double)((float)i1 +
							 * f6) + 0.5D, (double)i2, (double)((float)l + f7) +
							 * 0.5D, (double)(1.0F * f8), (double)((float)i2 *
							 * f8 / 4.0F + f10 * f8));
							 * tessellator.addVertexWithUV((double)((float)i1 -
							 * f6) + 0.5D, (double)i2, (double)((float)l - f7) +
							 * 0.5D, (double)(0.0F * f8), (double)((float)i2 *
							 * f8 / 4.0F + f10 * f8));
							 * tessellator.setTranslation(0.0D, 0.0D, 0.0D); }
							 * else
							 */
							{
								if (b1 != 1) {
									if (b1 >= 0) {
										tessellator.draw();
									}

									b1 = 1;
									this.mc.getTextureManager().bindTexture(this.falloutTexture);
									tessellator.startDrawingQuads();
								}

								f10 = ((float) (this.rendererUpdateCount & 511) + p_78474_1_) / 512.0F;
								float f16 = this.random.nextFloat() + f5 * 0.01F * (float) this.random.nextGaussian();
								float f11 = this.random.nextFloat() + f5 * (float) this.random.nextGaussian() * 0.001F;
								d4 = (double) ((float) i1 + 0.5F) - entitylivingbase.posX;
								double d5 = (double) ((float) l + 0.5F) - entitylivingbase.posZ;
								float f14 = MathHelper.sqrt_double(d4 * d4 + d5 * d5) / (float) b0;
								float f15 = 1.0F;
								tessellator.setBrightness(
										(worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 15728880) / 4);
								tessellator.setColorRGBA_F(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * f1);
								tessellator.setTranslation(-d0 * 1.0D, -d1 * 1.0D, -d2 * 1.0D);
								tessellator.addVertexWithUV((double) ((float) i1 - f6) + 0.5D, (double) l1,
										(double) ((float) l - f7) + 0.5D, (double) (0.0F * f8 + f16),
										(double) ((float) l1 * f8 / 4.0F + f10 * f8 + f11));
								tessellator.addVertexWithUV((double) ((float) i1 + f6) + 0.5D, (double) l1,
										(double) ((float) l + f7) + 0.5D, (double) (1.0F * f8 + f16),
										(double) ((float) l1 * f8 / 4.0F + f10 * f8 + f11));
								tessellator.addVertexWithUV((double) ((float) i1 + f6) + 0.5D, (double) i2,
										(double) ((float) l + f7) + 0.5D, (double) (1.0F * f8 + f16),
										(double) ((float) i2 * f8 / 4.0F + f10 * f8 + f11));
								tessellator.addVertexWithUV((double) ((float) i1 - f6) + 0.5D, (double) i2,
										(double) ((float) l - f7) + 0.5D, (double) (0.0F * f8 + f16),
										(double) ((float) i2 * f8 / 4.0F + f10 * f8 + f11));
								tessellator.setTranslation(0.0D, 0.0D, 0.0D);
							}
						}
					}
				}
			}

			if (b1 >= 0) {
				tessellator.draw();
				// System.out.println("Fired!");
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			// this.disableLightmap((double)p_78474_1_);
		}
	}

	/*
	 * public void enableLightmap(double p_78463_1_) {
	 * OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	 * GL11.glMatrixMode(GL11.GL_TEXTURE); GL11.glLoadIdentity(); float f =
	 * 0.00390625F; GL11.glScalef(f, f, f); GL11.glTranslatef(8.0F, 8.0F, 8.0F);
	 * GL11.glMatrixMode(GL11.GL_MODELVIEW);
	 * this.mc.getTextureManager().bindTexture(this.locationLightMap);
	 * GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
	 * GL11.GL_LINEAR); GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
	 * GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	 * GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
	 * GL11.GL_LINEAR); GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
	 * GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	 * GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
	 * GL11.GL_CLAMP); GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
	 * GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP); GL11.glColor4f(1.0F, 1.0F, 1.0F,
	 * 1.0F); GL11.glEnable(GL11.GL_TEXTURE_2D);
	 * OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit); }
	 */

}
