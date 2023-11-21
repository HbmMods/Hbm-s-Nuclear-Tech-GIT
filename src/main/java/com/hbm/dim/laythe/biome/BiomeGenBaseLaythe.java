/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.laythe.biome;

import java.util.Random;

import com.hbm.config.SpaceConfig;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBaseLaythe extends BiomeGenBase
{
    public static final BiomeGenBase laytheIsland = new BiomeGenLaythe(SpaceConfig.laytheBiome).setTemperatureRainfall(0.2F, 0.2F);
    public static final BiomeGenBase laytheOcean = new BiomeGenLaytheOcean(SpaceConfig.laytheOceanBiome).setTemperatureRainfall(0.2F, 0.2F);
    
    public BiomeGenBaseLaythe(int id)
    {
        super(id);
    }
}