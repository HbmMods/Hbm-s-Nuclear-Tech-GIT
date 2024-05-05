package com.hbm.dim.eve;

import com.hbm.config.WorldConfig;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.main.MainRegistry;
import com.hbm.util.AstronomyUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderEve extends WorldProvider {
    private float[] colorsSunriseSunset = new float[4];

	public void registerWorldChunkManager() {
		
		this.worldChunkMgr = new WorldChunkManagerEve(worldObj);
		//this.dimensionId = WorldConfig.dunaDimension;
		//this.hasNoSky = false;
	}

	@Override
	public String getDimensionName() {
		return "Eve";
	}
	
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderEve(this.worldObj, this.getSeed(), false);
    }
    
	public void renderClouds() {
	}
    
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float x, float y) {
        float f = this.getSunBrightnessFactor(1.0F);
      return Vec3.createVectorHelper(53F / 255F * f, 32F / 255F * f, 74F / 255F * f);
    }
    
    public Vec3 getSkyColor(Entity camera, float partialTicks) {
        float f = this.getSunBrightnessFactor(1.0F);
      return Vec3.createVectorHelper(92 / 255.0F * f, 54 / 255.0F * f, 131 / 255.0F * f);
    }
    
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
    {
        float f2 = 0.4F;
        float f3 = MathHelper.cos((p_76560_1_) * (float)Math.PI * 2.0F) - 0.0F;
        float f4 = -0.0F;

        if (f3 >= f4 - f2 && f3 <= f4 + f2)
        {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
            f6 *= f6;
            this.colorsSunriseSunset[0] = f5 * 0.01F;
            this.colorsSunriseSunset[1] = f5 * f5 * 0.9F + 0.3F;
            this.colorsSunriseSunset[2] = f5 * f5;
            this.colorsSunriseSunset[3] = f6;
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }
    }

    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }

    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }
	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		return 0;
	}
    public boolean canRespawnHere()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
		return -100;
	}

	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderEve();
	}
	
    public long getDayLength()
    {
    	return (long) (40000);
    }
    
    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        par1 = this.getWorldTime();
        int j = (int) (par1 % this.getDayLength());
        float f1 = (j + par3) / this.getDayLength() - 0.25F;

        if (f1 < 0.0F)
        {
            ++f1;
        }

        if (f1 > 1.0F)
        {
            --f1;
        }

        float f2 = f1;
        f1 = 0.5F - MathHelper.cos(f1 * 3.1415927F) / 2.0F;
        return f2 + (f1 - f2) / 3.0F;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		float sunBr = worldObj.getSunBrightnessFactor(par1);
		return (sunBr * 0.3F);
	}
}