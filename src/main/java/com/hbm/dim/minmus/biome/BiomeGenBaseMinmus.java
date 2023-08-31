

package com.hbm.dim.minmus.biome;

import java.util.Random;

import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBaseMinmus extends BiomeGenBase
{
    public static final BiomeGenBase minmusPlains = new BiomeGenMinmusHills(SpaceConfig.minmusBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase minmusCanyon = new BiomeGenMinmusBasin(SpaceConfig.minmusBasins).setTemperatureRainfall(-1.0F, 0.0F);
    
    
    public BiomeGenBaseMinmus(int id)
    {
        super(id);
    }
}