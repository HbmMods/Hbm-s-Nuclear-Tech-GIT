package com.hbm.dim.Ike;

import com.hbm.config.SpaceConfig;
import com.hbm.config.SpaceConfig;
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

public class WorldProviderIke extends WorldProvider {
	
	public void registerWorldChunkManager() {
		
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenIke(SpaceConfig.ikeBiome), dimensionId);
		this.dimensionId = SpaceConfig.ikeDimension;
		this.hasNoSky = false;
	}

	@Override
	public String getDimensionName() {
		return "Ike";
	}
	
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderIke(this.worldObj, this.getSeed(), false);
    }
    
	public void renderClouds() {
	}
    
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float x, float y) {
      return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
    }
    
    public Vec3 getSkyColor(Entity camera, float partialTicks) {
      return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
    }
    
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_) {
    	return null;
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
		/*float starBr = worldObj.getStarBrightnessBody(par1);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float f1 = worldObj.getCelestialAngle(par1);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if(f2 < 0.2F) {
			f2 = 0.2F;
		}

		if(f2 > 1.0F) {
			f2 = 1.0F;
		}*/
		return 1f;
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
		return new SkyProviderIke();
	}
	
    public long getDayLength()
    {
    	return (long) (AstronomyUtil.MunP*24000);
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


}