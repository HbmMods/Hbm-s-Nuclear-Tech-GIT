package com.hbm.world;

import com.hbm.handler.ImpactWorldHandler;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderSurface;

public class WorldProviderNTM extends WorldProviderSurface {
	
	private float[] colorsSunriseSunset = new float[4];

	public WorldProviderNTM() {
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return super.calculateCelestialAngle(worldTime, partialTicks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		float f2 = 0.4F;
		float f3 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) - 0.0F;
		float f4 = -0.0F;
		float dust = MainRegistry.proxy.getImpactDust(worldObj);

		if(f3 >= f4 - f2 && f3 <= f4 + f2) {
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float) Math.PI)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = (f5 * 0.3F + 0.7F) * (1 - dust);
			this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + 0.2F) * (1 - dust);
			this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + 0.2F) * (1 - dust);
			this.colorsSunriseSunset[3] = f6 * (1 - dust);
			return this.colorsSunriseSunset;
		} else {
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		float starBr = worldObj.getStarBrightnessBody(par1);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float f1 = worldObj.getCelestialAngle(par1);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if(f2 < 0.0F) {
			f2 = 0.0F;
		}

		if(f2 > 1.0F) {
			f2 = 1.0F;
		}
		return starBr * (1 - dust);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		float dust = ImpactWorldHandler.getDustForClient(MainRegistry.proxy.me().worldObj);
		float sunBr = worldObj.getSunBrightnessFactor(par1);
		return (sunBr * 0.8F + 0.2F) * (1 - dust);
	}

	@Override
	public boolean isDaytime() {
		float dust = MainRegistry.proxy.getImpactDust(worldObj);

		if(dust >= 0.75F) {
			return false;
		}
		return super.isDaytime();
	}

	@Override
	public float getSunBrightnessFactor(float par1) {
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float sunBr = worldObj.getSunBrightnessFactor(par1);
		float dimSun = sunBr * (1 - dust);
		return dimSun;
	}

	/**
	 * Return Vec3D with biome specific fog color
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
		Vec3 fog = super.getFogColor(p_76562_1_, p_76562_2_);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float fire = MainRegistry.proxy.getImpactFire(worldObj);

		float f3 = (float) fog.xCoord;
		float f4 = (float) fog.yCoord * (1 - (dust * 0.5F));
		float f5 = (float) fog.zCoord * (1 - dust);

		if(fire > 0) {
			return Vec3.createVectorHelper((double) f3 * (Math.max((1 - (dust * 2)), 0)), (double) f4 * (Math.max((1 - (dust * 2)), 0)), (double) f5 * (Math.max((1 - (dust * 2)), 0)));
		}
		return Vec3.createVectorHelper((double) f3 * (1 - dust), (double) f4 * (1 - dust), (double) f5 * (1 - dust));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
		Vec3 sky = super.getSkyColor(cameraEntity, partialTicks);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float fire = MainRegistry.proxy.getImpactFire(worldObj);

		float f4;
		float f5;
		float f6;

		if(fire > 0) {
			f4 = (float) (sky.xCoord * 1.3f);
			f5 = (float) sky.yCoord * ((Math.max((1 - (dust * 1.4f)), 0)));
			f6 = (float) sky.zCoord * ((Math.max((1 - (dust * 4)), 0)));
		} else {
			f4 = (float) sky.xCoord;
			f5 = (float) sky.yCoord * (1 - (dust * 0.5F));
			f6 = (float) sky.zCoord * (1 - dust);
		}

		return Vec3.createVectorHelper((double) f4 * (fire + (1 - dust)), (double) f5 * (fire + (1 - dust)), (double) f6 * (fire + (1 - dust)));
	}

	@SideOnly(Side.CLIENT)
	public Vec3 drawClouds(float partialTicks) {
		Vec3 clouds = super.drawClouds(partialTicks);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);;
		float f3 = (float) clouds.xCoord;
		float f4 = (float) clouds.yCoord;
		float f5 = (float) clouds.zCoord;
		return Vec3.createVectorHelper((double) f3 * (1 - dust), (double) f4 * (1 - dust), (double) f5 * (1 - dust));
	}
}