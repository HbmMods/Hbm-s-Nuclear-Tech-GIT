package com.hbm.dim;

import com.hbm.config.SpaceConfig;
import com.hbm.main.MainRegistry;
import com.hbm.util.AstronomyUtil;
import com.hbm.util.SkyColorManager;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderMoon extends WorldProvider {
    private float[] colorsSunriseSunset = new float[4];

	public void registerWorldChunkManager() {
		
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenMoon(111), dimensionId);
		this.dimensionId = SpaceConfig.moonDimension;
		this.hasNoSky = false;
	}

	@Override
	public void updateWeather()
	{
		this.worldObj.getWorldInfo().setRainTime(0);
		this.worldObj.getWorldInfo().setRaining(false);
		this.worldObj.getWorldInfo().setThunderTime(0);
		this.worldObj.getWorldInfo().setThundering(false);
		this.worldObj.rainingStrength = 0.0F;
		this.worldObj.thunderingStrength = 0.0F;
	}

	
	@Override
	public String getDimensionName() {
		return "Moon";
	}
	
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderMoon(this.worldObj, this.getSeed(), false);
    }
    
	public void renderClouds() {
		
	}
    
	   @SideOnly(Side.CLIENT)
	    public Vec3 getFogColor(float x, float y) {	    	
	    	NBTTagCompound tagger3 = MainRegistry.proxy.getPlanetaryTags(worldObj);
	        if (tagger3 != null) {
	            String traitKey = Hospitality.BREATHEABLE.toString();
	            if (tagger3.hasKey(traitKey)) {
	                float f = 1.0F - this.getStarBrightness(1.0F);
	                return Vec3.createVectorHelper(148.3D / 255 * f , 144.4D / 255* f, 242.7D/ 255 * f);
	            }
	        }
	      return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
	    }

	    public Vec3 getSkyColor(Entity camera, float partialTicks) {
	    	NBTTagCompound tagger3 = MainRegistry.proxy.getPlanetaryTags(worldObj);
	        if (tagger3 != null) {
	            String traitKey = Hospitality.BREATHEABLE.toString();
	            if (tagger3.hasKey(traitKey)) {
	                float f = 1.0F - this.getStarBrightness(1.0F);

	                return SkyColorManager.currentSkyColor = Vec3.createVectorHelper(92D / 255 * f , 83.4D / 255* f, 217.7D/ 255 * f);
	            }
	        }
	        return Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);    
	     }
	    
	    @SideOnly(Side.CLIENT)
	    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_) {
	        float f2 = 0.4F;
	        float f3 = MathHelper.cos(p_76560_1_ * (float)Math.PI * 2.0F) - 0.0F;
	        float f4 = -0.0F;
	    	NBTTagCompound tagger3 = MainRegistry.proxy.getPlanetaryTags(worldObj);
	        if (tagger3 != null) {
	            String traitKey = Hospitality.BREATHEABLE.toString();
	            if (tagger3.hasKey(traitKey)) {
	                if (f3 >= f4 - f2 && f3 <= f4 + f2)
	                {
	                    float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
	                    float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
	                    f6 *= f6;
	                    this.colorsSunriseSunset[0] = f5 * 0.2F + 0.0F;
	                    this.colorsSunriseSunset[1] = f5 * f5 * 0.6F + 0.1F;
	                    this.colorsSunriseSunset[2] = f5 * f5 * 0.2F + 0.0F;
	                    this.colorsSunriseSunset[3] = f6;
	                    return this.colorsSunriseSunset;
	                }
	                else
	                {
	                    return null;
	                }            	
	            }           
	        }
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
			//float starBr = worldObj.getStarBrightnessBody(par1);
	    	NBTTagCompound tagger3 = MainRegistry.proxy.getPlanetaryTags(worldObj);
	        if (tagger3 != null) {
	            String traitKey = Hospitality.BREATHEABLE.toString();
	            if (tagger3.hasKey(traitKey)) {
				float f1 = worldObj.getCelestialAngle(par1);
				float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

				if(f2 < 0.0F) {
					f2 = 0.0F;
				}

				if(f2 > 1.0F) {
					f2 = 1.0F;
				}
				return f2;
	           }
				
			}
		return 1F;
		
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
		return new SkyProviderMoon();
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