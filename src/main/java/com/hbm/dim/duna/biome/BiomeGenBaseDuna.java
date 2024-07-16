
package com.hbm.dim.duna.biome;

import com.hbm.config.SpaceConfig;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBaseDuna extends BiomeGenBase
{
    public static final BiomeGenBase dunaPlains = new BiomeGenDuna(SpaceConfig.dunaBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaLowlands = new BiomeGenDunaLowlands(SpaceConfig.dunaLowlandsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaPolar = new BiomeGenDunaPolar(SpaceConfig.dunaPolarBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaHills = new BiomeGenDunaHills(SpaceConfig.dunaHillsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase dunaPolarHills = new BiomeGenDunaPolarHills(SpaceConfig.dunaPolarHillsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    
    public BiomeGenBaseDuna(int id)
    {
        super(id);
    }
}