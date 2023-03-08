package com.hbm.world;

import com.hbm.dim.duna.BiomeGenBaseDuna;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class ModBiomes
{
    public static void init()
    {
        BiomeDictionary.registerBiomeType(BiomeGenBaseDuna.dunaPlains, Type.COLD, Type.DRY, Type.DEAD);
        BiomeDictionary.registerBiomeType(BiomeGenBaseDuna.dunaLowlands, Type.COLD, Type.DRY, Type.DEAD);
        BiomeDictionary.registerBiomeType(BiomeGenBaseDuna.dunaPolar, Type.COLD, Type.DRY, Type.DEAD, Type.SNOWY);
        BiomeDictionary.registerBiomeType(BiomeGenBaseDuna.dunaRiver, Type.COLD, Type.DRY, Type.DEAD);
    }
}