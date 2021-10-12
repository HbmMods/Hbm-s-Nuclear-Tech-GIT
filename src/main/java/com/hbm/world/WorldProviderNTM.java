package com.hbm.world;

import com.hbm.main.ModEventHandler;
import com.hbm.saveddata.TomSaveData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IRenderHandler;

//import com.Pu238.chicxulubMod.ChicxulubWorldChunkManager;

public class WorldProviderNTM extends WorldProviderSurface {
	private float[] colorsSunriseSunset = new float[4];
	
	//float atmosphericDust;
	//float atmosphericDust = ModEventHandler.dust;
	public WorldProviderNTM() {	
	}
	
//    protected Class<? extends WorldChunkManager> getWorldChunkManagerClass()
//    {
//        return ChicxulubWorldChunkManager.class;
//    }
	
	@Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return super.calculateCelestialAngle(worldTime, partialTicks);
    }
	@Override
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
        float f2 = 0.4F;
        float f3 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) - 0.0F;
        float f4 = -0.0F;
        float dust = ModEventHandler.dust;

        if (f3 >= f4 - f2 && f3 <= f4 + f2)
        {
            float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
            f6 *= f6;
            this.colorsSunriseSunset[0] = (f5 * 0.3F + 0.7F) * (1-dust);
            this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + 0.2F) * (1-dust);
            this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + 0.2F) * (1-dust);
            this.colorsSunriseSunset[3] = f6 * (1-dust);
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }	
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return this.getStarBrightnessEclipse(par1);
    }

    @SideOnly(Side.CLIENT)
    public float getStarBrightnessEclipse(float par1)
    {
    	float starBr = worldObj.getStarBrightnessBody(par1);
    	float dust = ModEventHandler.dust;
        float f1 = worldObj.getCelestialAngle(par1);
        float f2 = 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.25F);

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        /*if (ChicxulubEventHandler.dayOfSolarEclipse && ChicxulubEventHandler.solarEclipse) {
            float starBr = worldObj.getStarBrightnessBody(par1);
            int eclTick = ChicxulubEventHandler.solarEclipseTick;
            if (eclTick >= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR) { //fading out
                eclTick -= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            } else {
                eclTick = ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR - eclTick;
            }
            float perc = ((float) eclTick) / ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            return starBr + 1 - 1 * (0.15F + (0.85F * perc));
        }*/ 
        
        /*if (ChicxulubEventHandler.dayOfAsteroidImpact && ChicxulubEventHandler.asteroidImpact) {            
            int eclTick = ChicxulubEventHandler.asteroidImpactTick;
            if (eclTick >= ChicxulubEventHandler.ASTEROID_IMPACT_HALF_DUR) { //fading out
                eclTick -= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            } else {
                eclTick = ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR - eclTick;
            }
            float perc = ((float) eclTick) / ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            return starBr + 1 - 1 * (0.15F + (0.85F * perc))*(1-dust);
        }*/         
        return starBr*(1-dust);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1) {
    	float dust = ModEventHandler.dust;
    	float sunBr = worldObj.getSunBrightnessFactor(par1);
            /*if (ChicxulubEventHandler.dayOfSolarEclipse && ChicxulubEventHandler.solarEclipse) {                
                int eclTick = ChicxulubEventHandler.solarEclipseTick;
                if (eclTick >= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR) { //fading out
                    eclTick -= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
                } else {
                    eclTick = ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR - eclTick;
                }
                float perc = ((float) eclTick) / ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
                return sunBr * (0.15F + (0.85F * perc))*(1-dust);
            }*/        
        return (sunBr*0.8F+0.2F)*(1-dust);
    }
	
    @Override
    public boolean isDaytime()
    {
    	float dust = ModEventHandler.dust;
        //if (ChicxulubEventHandler.dayOfSolarEclipse && ChicxulubEventHandler.solarEclipse) {
            //int eclTick = ChicxulubEventHandler.solarEclipseTick;
            if (dust >=0.75F) {
            	return false;//fading out
            }
            //return true;
        //}
        return super.isDaytime();
    }
    
	@Override
	public float getSunBrightnessFactor(float par1) {
		float dust = ModEventHandler.dust;
		float sunBr = worldObj.getSunBrightnessFactor(par1);
		/*if (ChicxulubEventHandler.dayOfSolarEclipse && ChicxulubEventHandler.solarEclipse) {                
                int eclTick = ChicxulubEventHandler.solarEclipseTick;
                if (eclTick >= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR) { //fading out
                    eclTick -= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
                } else {
                    eclTick = ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR - eclTick;
                }
                float perc = ((float) eclTick) / ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
                return sunBr * (0.15F + (0.85F * perc))*(1-dust);
            }*/
		float dimSun = sunBr*((0.8F+0.2F)*(1-dust));
        return dimSun;
	}

    /**
     * Return Vec3D with biome specific fog color
     */
    @SideOnly(Side.CLIENT)
    @Override
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
    {
    	Vec3 fog =super.getFogColor(p_76562_1_, p_76562_2_);
    	float dust = ModEventHandler.dust;
       /* float f2 = MathHelper.cos(p_76562_1_ * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }*/

        float f3 = (float) fog.xCoord;
        float f4 = (float) fog.yCoord*(1-(dust*0.5F));
        float f5 = (float) fog.zCoord*(1-dust);
        //f3 *= f2 * 0.94F + 0.06F;
        //f4 *= f2 * 0.94F + 0.06F;
        //f5 *= f2 * 0.91F + 0.09F;
        //f4 *= f4*(1-dust*0.5F);
        //f5 *= f5*(1-dust);
        return Vec3.createVectorHelper((double)f3*(1-dust), (double)f4*(1-dust), (double)f5*(1-dust));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {
        return this.getSkyColorEclipse(cameraEntity, partialTicks);
    }
  
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColorEclipse(Entity cameraEntity, float partialTicks)
    {
    	Vec3 sky = super.getSkyColor(cameraEntity, partialTicks);
    	float dust = ModEventHandler.dust;
        /*float f1 = worldObj.getCelestialAngle(p_72833_2_);
        float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        int i = MathHelper.floor_double(p_72833_1_.posX);
        int j = MathHelper.floor_double(p_72833_1_.posY);
        int k = MathHelper.floor_double(p_72833_1_.posZ);
        int l = ForgeHooksClient.getSkyBlendColour(worldObj, i, j, k);
        float f4 = (float)(l >> 16 & 255) / 255.0F;
        float f5 = (float)(l >> 8 & 255) / 255.0F;
        float f6 = (float)(l & 255) / 255.0F;
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float f7 = worldObj.getRainStrength(p_72833_2_);
        float f8;
        float f9;
       
        
        if (f7 > 0.0F)
        {
            f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            f9 = 1.0F - f7 * 0.75F;
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
            f6 = f6 * f9 + f8 * (1.0F - f9);
        }

        if (dust > 0.0F)
        {
        	f4 = f4*1;
        	f5 = f5*(1-dust*0.5F);
        	f6 = f6*(1-dust);
            /*f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            f9 = 1.0F - dust * 0.75F;
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
            f6 = f6 * f9 + f8 * (1.0F - f9);
        }
        
        f8 = worldObj.getWeightedThunderStrength(p_72833_2_);

        if (f8 > 0.0F)
        {
            f9 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
            float f10 = 1.0F - f8 * 0.75F;
            f4 = f4 * f10 + f9 * (1.0F - f10);
            f5 = f5 * f10 + f9 * (1.0F - f10);
            f6 = f6 * f10 + f9 * (1.0F - f10);
        }

        if (worldObj.lastLightningBolt > 0)
        {
            f9 = (float)worldObj.lastLightningBolt - p_72833_2_;

            if (f9 > 1.0F)
            {
                f9 = 1.0F;
            }

            f9 *= 0.45F;
            f4 = f4 * (1.0F - f9) + 0.8F * f9;
            f5 = f5 * (1.0F - f9) + 0.8F * f9;
            f6 = f6 * (1.0F - f9) + 1.0F * f9;
        }

        if (ChicxulubEventHandler.dayOfSolarEclipse && ChicxulubEventHandler.solarEclipse) {
            int eclTick = ChicxulubEventHandler.solarEclipseTick;
            if (eclTick >= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR) { //fading out
                eclTick -= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            } else {
                eclTick = ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR - eclTick;
            }
            float perc = ((float) eclTick) / ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            return Vec3.createVectorHelper((double)f4*(1-dust)*(0.15F+(0.85F*perc)), (double)f5*(1-dust)*(0.15F+(0.85F*perc)), (double)f6*(1-dust)*(0.15F+(0.85F*perc)));
        }*/
    	
        float f4 = (float) sky.xCoord;
        float f5 = (float) sky.yCoord*(1-(dust*0.5F));
        float f6 = (float) sky.zCoord*(1-dust);
        return Vec3.createVectorHelper((double)f4*(1-dust), (double)f5*(1-dust), (double)f6*(1-dust));
    }
    
    @SideOnly(Side.CLIENT)
    public Vec3 drawClouds(float partialTicks)
    {
        return this.drawCloudsEclipse(partialTicks);
    }

    @SideOnly(Side.CLIENT)
    public Vec3 drawCloudsEclipse(float partialTicks)
    {
    	Vec3 clouds = super.drawClouds(partialTicks);
    	float dust = ModEventHandler.dust;
       /* float f1 = worldObj.getCelestialAngle(p_72824_1_);
        float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        float f3 = (float)(16777215L >> 16 & 255L) / 255.0F;
        float f4 = (float)(16777215L >> 8 & 255L) / 255.0F;
        float f5 = (float)(16777215L & 255L) / 255.0F;
        float f6 = worldObj.getRainStrength(p_72824_1_);
        float f7;
        float f8;
        //float dust = ChicxulubWorldData.getData(worldObj).getDust();
        float dust = ChicxulubEventHandler.stratosphericDust;
        
        if (f6 > 0.0F)
        {
            f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
            f8 = 1.0F - f6 * 0.95F;
            f3 = f3 * f8 + f7 * (1.0F - f8);
            f4 = f4 * f8 + f7 * (1.0F - f8);
            f5 = f5 * f8 + f7 * (1.0F - f8);
        }

        f3 *= f2 * 0.9F + 0.1F;
        f4 *= f2 * 0.9F + 0.1F;
        f5 *= f2 * 0.85F + 0.15F;
        f7 = worldObj.getWeightedThunderStrength(p_72824_1_);

        if (f7 > 0.0F)
        {
            f8 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
            float f9 = 1.0F - f7 * 0.95F;
            f3 = f3 * f9 + f8 * (1.0F - f9);
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
        }
        if (ChicxulubEventHandler.dayOfSolarEclipse && ChicxulubEventHandler.solarEclipse) {
            int eclTick = ChicxulubEventHandler.solarEclipseTick;
            if (eclTick >= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR) { //fading out
                eclTick -= ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            } else {
                eclTick = ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR - eclTick;
            }
            float perc = ((float) eclTick) / ChicxulubEventHandler.SOLAR_ECLIPSE_HALF_DUR;
            return Vec3.createVectorHelper((double)f3*((0.15F+(0.85F*perc))*(1-dust)), (double)f4*((0.15F+(0.85F*perc))*(1-dust)), (double)f5*((0.15F+(0.85F*perc))*(1-dust)));
        }*/
        float f3 = (float) clouds.xCoord;
        float f4 = (float) clouds.yCoord;
        float f5 = (float) clouds.zCoord;
        return Vec3.createVectorHelper((double)f3*(1-dust), (double)f4*(1-dust), (double)f5*(1-dust));
    }
    
/*    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        float f2 = 1.0F - (worldObj.getSunlightRenderBrightnessFactor( par1)
        		* celestialHelper.getDispersionFactor(EnumRGBA.Alpha, par1));

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        return f2 * f2 * 0.5F;
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public void setSkyRenderer(IRenderHandler skyRenderer) {
    	for(Field field : skyRenderer.getClass().getDeclaredFields())
    		if(IRenderHandler.class.isAssignableFrom(field.getDeclaringClass()))
    			super.setSkyRenderer(skyRenderer);
    	if(skyRenderer instanceof NewSkyRenderer)
    		super.setSkyRenderer(skyRenderer);
    }*/
}