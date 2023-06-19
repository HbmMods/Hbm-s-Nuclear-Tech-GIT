package com.hbm.world;

import com.hbm.handler.ImpactWorldHandler;
import com.hbm.handler.RogueWorldHandler;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerRogue;
import com.hbm.saveddata.RogueWorldSaveData;
import com.hbm.saveddata.TomSaveData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;

public class WorldProviderNTM extends WorldProviderSurface {
	
	private float[] colorsSunriseSunset = new float[4];
//    public WorldChunkManagerNTM worldChunkMgr;
	public WorldProviderNTM() {
	}
	/*@Override
    public void registerWorldChunkManager()
    {
		this.worldChunkMgr = new WorldChunkManagerNTM();
    }*/
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return super.calculateCelestialAngle(worldTime, partialTicks);
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		RogueWorldSaveData data = RogueWorldSaveData.forWorld(worldObj);
		return data.temperature >= 0 ? super.canDoRainSnowIce(chunk) : false;
	}
	@Override
	public void updateWeather() {
		RogueWorldSaveData data = RogueWorldSaveData.forWorld(worldObj);
		if(data.temperature >= 0)
			super.updateWeather();
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
			this.colorsSunriseSunset[0] = (f5 * 0.3F + 0.7F)* Math.min(1, ModEventHandlerRogue.getSolarBrightnessClient(worldObj)) * MainRegistry.proxy.getAtmosphere(worldObj) * (1 - dust);
			this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + 0.2F)*  Math.min(1, ModEventHandlerRogue.getSolarBrightnessClient(worldObj)) * MainRegistry.proxy.getAtmosphere(worldObj) * (1 - dust);
			this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + 0.2F)*  Math.min(1, ModEventHandlerRogue.getSolarBrightnessClient(worldObj)) * MainRegistry.proxy.getAtmosphere(worldObj) * (1 - dust);
			this.colorsSunriseSunset[3] = f6*  Math.min(1, ModEventHandlerRogue.getSolarBrightnessClient(worldObj)) * MainRegistry.proxy.getAtmosphere(worldObj) * (1 - dust);
			System.out.println( ModEventHandlerRogue.getSolarBrightnessClient(worldObj));
			System.out.println(colorsSunriseSunset);
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
		//float distance = 1-MainRegistry.proxy.getDistance(worldObj);
		float f1 = worldObj.getCelestialAngle(par1);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if(f2 < Math.max(0,1-ModEventHandlerRogue.getSolarBrightnessClient(worldObj))) {
			f2 = Math.max(0,1-ModEventHandlerRogue.getSolarBrightnessClient(worldObj));
		}

		if(f2 > 1.0F) {
			f2 = 1.0F;
		}
		return f2 * (1 - dust)*0.5f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		float dust = ImpactWorldHandler.getDustForClient(MainRegistry.proxy.me().worldObj);
		float sunBr = worldObj.getSunBrightnessFactor(par1);
		return ((sunBr * 0.8F + 0.2F) * (1 - dust))*ModEventHandlerRogue.getSolarBrightnessClient(worldObj);
	}
	

	@Override
	public boolean isDaytime() {
		float dust = MainRegistry.proxy.getImpactDust(worldObj);

		if(dust >= 0.75F) {
			return false;
		}else{
		return super.isDaytime();
	}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightnessFactor(float par1) {
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float sunBr = worldObj.getSunBrightnessFactor(par1);
		float dimSun = (sunBr * (1 - dust))*ModEventHandlerRogue.getSolarBrightnessClient(worldObj);
		return dimSun;
	}

	/**
	 * Return Vec3D with biome specific fog color
	 */
	
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
		Vec3 fog = super.getFogColor(p_76562_1_, p_76562_2_);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);
		float fire = MainRegistry.proxy.getImpactFire(worldObj);
		boolean impact = MainRegistry.proxy.getImpact(worldObj);
		float f3;
		float f4;
		float f5;
		if(impact && fire == 0)
		{
	        float f2 = MathHelper.cos(p_76562_1_ * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

	        if (f2 < 0.0F)
	        {
	            f2 = 0.0F;
	        }

	        if (f2 > 1.0F)
	        {
	            f2 = 1.0F;
	        }

	        f3 = 1.0F;
	        f4 = 0.80392157F* (1 - (dust * 0.5F));;
	        f5 = 0.80392157F * (1 - dust);
	        f3 *= f2 * 0.94F + 0.06F;
	        f4 *= f2 * 0.94F + 0.06F;
	        f5 *= f2 * 0.91F + 0.09F;
			return Vec3.createVectorHelper((double) f3 * (1 - dust), (double) f4 * (1 - dust), (double) f5 * (1 - dust));
		}
		f3 = (float) fog.xCoord;
		f4 = (float) fog.yCoord * (1 - (dust * 0.5F));
		f5 = (float) fog.zCoord * (1 - dust);
		if(fire > 0) {
			return Vec3.createVectorHelper((double) f3 * (Math.max((1 - (dust * 2)), 0)), (double) f4 * (Math.max((1 - (dust * 2)), 0)), (double) f5 * (Math.max((1 - (dust * 2)), 0)));
		}
		return Vec3.createVectorHelper((double) f3*ModEventHandlerRogue.getSolarBrightnessClient(worldObj) * (1 - dust), (double) f4*ModEventHandlerRogue.getSolarBrightnessClient(worldObj) * (1 - dust), (double) f5 * (1 - dust)*ModEventHandlerRogue.getSolarBrightnessClient(worldObj));
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

		return Vec3.createVectorHelper((double) f4*ModEventHandlerRogue.getSolarBrightnessClient(worldObj) * (fire + (1 - dust)), (double) f5*ModEventHandlerRogue.getSolarBrightnessClient(worldObj) * (fire + (1 - dust)), (double) f6*ModEventHandlerRogue.getSolarBrightnessClient(worldObj) * (fire + (1 - dust)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 drawClouds(float partialTicks) {
		Vec3 clouds = super.drawClouds(partialTicks);
		float dust = MainRegistry.proxy.getImpactDust(worldObj);;
		float f3 = (float) clouds.xCoord;
		float f4 = (float) clouds.yCoord;
		float f5 = (float) clouds.zCoord;
		return Vec3.createVectorHelper((double) f3 * (1 - dust)*ModEventHandlerRogue.getSolarBrightnessClient(worldObj), (double) f4 * (1 - dust)*ModEventHandlerRogue.getSolarBrightnessClient(worldObj), (double) f5 * (1 - dust)*ModEventHandlerRogue.getSolarBrightnessClient(worldObj));
	}
	/*@Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
        BiomeGenBase biomegenbase = this.getBiomeGenForCoords(x, z);
        float f = biomegenbase.getFloatTemperature(x, y, z);
        TomSaveData data = TomSaveData.forWorld(worldObj);
        float t = 0;
        if(data.impact)
        {
        	t=1.15F;
        }
        else
        {
        	t=0.15F;
        }
        if (f > t)
        {
            return false;
        }
        else
        {
            if (y >= 0 && y < 256 && worldObj.getSavedLightValue(EnumSkyBlock.Block, x, y, z) < 10)
            {
                Block block = worldObj.getBlock(x, y, z);

                if ((block == Blocks.water || block == Blocks.flowing_water) && worldObj.getBlockMetadata(x, y, z) == 0)
                {
                    if (!byWater)
                    {
                        return true;
                    }

                    boolean flag1 = true;

                    if (flag1 && worldObj.getBlock(x - 1, y, z).getMaterial() != Material.water)
                    {
                        flag1 = false;
                    }

                    if (flag1 && worldObj.getBlock(x + 1, y, z).getMaterial() != Material.water)
                    {
                        flag1 = false;
                    }

                    if (flag1 && worldObj.getBlock(x, y, z - 1).getMaterial() != Material.water)
                    {
                        flag1 = false;
                    }

                    if (flag1 && worldObj.getBlock(x, y, z + 1).getMaterial() != Material.water)
                    {
                        flag1 = false;
                    }

                    if (!flag1)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }*/
	/*@Override
    public boolean canSnowAt(int x, int y, int z, boolean checkLight)
    {
        BiomeGenBase biomegenbase = this.getBiomeGenForCoords(x, z);
        float f = biomegenbase.getFloatTemperature(x, y, z);

        if (f > 1.15F)
        {
            return false;
        }
        else if (!checkLight)
        {
            return true;
        }
        else
        {
            if (y >= 0 && y < 256 && worldObj.getSavedLightValue(EnumSkyBlock.Block, x, y, z) < 10)
            {
                Block block = worldObj.getBlock(x, y, z);

                if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(worldObj, x, y, z))
                {
                    return true;
                }
            }

            return false;
        }
    }*/
}