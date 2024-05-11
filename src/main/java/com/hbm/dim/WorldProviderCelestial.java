package com.hbm.dim;

import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.inventory.fluid.Fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.IRenderHandler;

public abstract class WorldProviderCelestial extends WorldProvider {

	@Override
	public abstract void registerWorldChunkManager();

	@Override
	public void updateWeather() {
		if(CelestialBody.hasTrait(worldObj, CBT_Atmosphere.class)) {
			super.updateWeather();
			return;
		}

		this.worldObj.getWorldInfo().setRainTime(0);
		this.worldObj.getWorldInfo().setRaining(false);
		this.worldObj.getWorldInfo().setThunderTime(0);
		this.worldObj.getWorldInfo().setThundering(false);
		this.worldObj.rainingStrength = 0.0F;
		this.worldObj.thunderingStrength = 0.0F;
	}
    
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float x, float y) {
		CBT_Atmosphere atmosphere = CelestialBody.getTrait(worldObj, CBT_Atmosphere.class);

		// The cold hard vacuum of space
		if(atmosphere == null) return Vec3.createVectorHelper(0, 0, 0);

		float f = this.getSunBrightnessFactor(1.0F);

		Vec3 color;
		if(atmosphere.fluid == Fluids.EVEAIR) {
			color = Vec3.createVectorHelper(53F / 255F * f, 32F / 255F * f, 74F / 255F * f);
		} else if(atmosphere.fluid == Fluids.CARBONDIOXIDE) {
			color = Vec3.createVectorHelper(212F / 255F * f, 112F / 255F * f, 78F / 255F * f);
		} else {
			// Default to regular ol' overworld
			color = super.getFogColor(x, y);
		}

		// Fog intensity remains high to simulate a thin looking atmosphere on low pressure planets

		return color;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity camera, float partialTicks) {
		CBT_Atmosphere atmosphere = CelestialBody.getTrait(worldObj, CBT_Atmosphere.class);

		// The cold hard vacuum of space
		if(atmosphere == null) return Vec3.createVectorHelper(0, 0, 0);

		float f = this.getSunBrightnessFactor(1.0F);

		Vec3 color;
		if(atmosphere.fluid == Fluids.EVEAIR) {
			color = Vec3.createVectorHelper(92 / 255.0F * f, 54 / 255.0F * f, 131 / 255.0F * f);
		} else if(atmosphere.fluid == Fluids.CARBONDIOXIDE) {
			color = Vec3.createVectorHelper(125 / 255.0F * f, 69 / 255.0F * f, 48 / 255.0F * f);
		} else {
			// Default to regular ol' overworld
			color = super.getSkyColor(camera, partialTicks);
		}

		// Lower pressure sky renders thinner
		color.xCoord *= atmosphere.pressure;
		color.yCoord *= atmosphere.pressure;
		color.zCoord *= atmosphere.pressure;

		return color;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		CBT_Atmosphere atmosphere = CelestialBody.getTrait(worldObj, CBT_Atmosphere.class);
		if(atmosphere == null) return null;

		float[] colors = super.calcSunriseSunsetColors(par1, par2);
		if(colors == null) return null;

		// Mars IRL has inverted blue sunsets, which look cool as
		// So carbon dioxide rich atmospheres will do the same
		// for now, it's just a swizzle between red and blue
		if(atmosphere.fluid == Fluids.CARBONDIOXIDE) {
			float tmp = colors[0];
			colors[0] = colors[2];
			colors[2] = tmp;
		} else if (atmosphere.fluid == Fluids.EVEAIR) {
			float f2 = 0.4F;
			float f3 = MathHelper.cos((par1) * (float)Math.PI * 2.0F) - 0.0F;
			float f4 = -0.0F;
	
			if (f3 >= f4 - f2 && f3 <= f4 + f2) {
				float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
				float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
				f6 *= f6;
				colors[0] = f5 * 0.01F;
				colors[1] = f5 * f5 * 0.9F + 0.3F;
				colors[2] = f5 * f5;
				colors[3] = f6;
			}
		}

		return colors;
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		if(CelestialBody.hasTrait(worldObj, CBT_Atmosphere.class))
			return super.canDoLightning(chunk);

		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		if(CelestialBody.hasTrait(worldObj, CBT_Atmosphere.class))
			return super.canDoRainSnowIce(chunk);

		return false;
	}

	// Stars do not show up during the day in a vacuum, common misconception:
	// The reason stars aren't visible during the day on Earth isn't because of the sky,
	// the sky is ALWAYS there. The reason they aren't visible is because the Sun is too bright!
	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		// Stars become visible during the day beyond the orbit of Duna
		// And are fully visible during the day beyond the orbit of Jool
		float distanceStart = 20_000_000;
		float distanceEnd = 80_000_000;

		float semiMajorAxisKm = CelestialBody.getSemiMajorAxis(worldObj);
		float distanceFactor = MathHelper.clamp_float((semiMajorAxisKm - distanceStart) / (distanceEnd - distanceStart), 0F, 1F);

		// Vacuum still increases star brightness tho
		float starBrightness = super.getStarBrightness(par1);
		if (!CelestialBody.hasTrait(worldObj, CBT_Atmosphere.class))
			starBrightness *= 2F;

		return MathHelper.clamp_float(starBrightness, distanceFactor, 1F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		CBT_Atmosphere atmosphere = CelestialBody.getTrait(worldObj, CBT_Atmosphere.class);
		float sunBrightness = super.getSunBrightness(par1);

		if(atmosphere == null) return sunBrightness;

		if(atmosphere.fluid == Fluids.EVEAIR) {
			return sunBrightness *= 0.3F;
		}

		return sunBrightness;
	}

	@Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
		CBT_Atmosphere atmosphere = CelestialBody.getTrait(worldObj, CBT_Atmosphere.class);

		if(atmosphere == null || atmosphere.pressure < 0.5F) return -100;
		
		return super.getCloudHeight();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderCelestial();
	}

    protected long getDayLength() {
		return CelestialBody.getRotationalPeriod(worldObj);
    }
    
    @Override
    public float calculateCelestialAngle(long worldTime, float timeOffset) {
        int j = (int) (worldTime % this.getDayLength());
        float f1 = (j + timeOffset) / this.getDayLength() - 0.25F;

        if(f1 < 0.0F) {
            ++f1;
        }

        if(f1 > 1.0F) {
            --f1;
        }

        float f2 = f1;
        f1 = 0.5F - MathHelper.cos(f1 * 3.1415927F) / 2.0F;
        return f2 + (f1 - f2) / 3.0F;
    }

}