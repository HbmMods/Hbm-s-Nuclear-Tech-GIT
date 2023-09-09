/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.eve.biome;

import java.util.Random;

import com.hbm.config.SpaceConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBaseEve extends BiomeGenBase
{
    public static final BiomeGenBase evePlains = new BiomeGenEve(SpaceConfig.eveBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase eveOcean = new BiomeGenEveOcean(SpaceConfig.eveOceanBiome).setTemperatureRainfall(-1.0F, 0.0F);
    public static final BiomeGenBase eveMountains = new BiomeGenEveMountains(SpaceConfig.eveMountainsBiome).setTemperatureRainfall(-1.0F, 0.0F);
    
    public BiomeGenBaseEve(int id)
    {
        super(id);
    }
}