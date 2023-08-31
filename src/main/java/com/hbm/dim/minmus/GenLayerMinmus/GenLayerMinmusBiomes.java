package com.hbm.dim.minmus.GenLayerMinmus;


import com.hbm.dim.minmus.biome.BiomeGenBaseMinmus;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerMinmusBiomes extends GenLayer
{
    private static final BiomeGenBase[] biomes = new BiomeGenBase[] {BiomeGenBaseMinmus.minmusCanyon, BiomeGenBaseMinmus.minmusPlains};

    public GenLayerMinmusBiomes(long l)
    {
        super(l);
    }

    @Override
    public int[] getInts(int x, int z, int width, int depth)
    {
        int[] dest = IntCache.getIntCache(width * depth);

        for (int k = 0; k < depth; ++k)
        {
            for (int i = 0; i < width; ++i)
            {
                initChunkSeed(x + i, z + k);
                dest[i + k * width] = biomes[nextInt(biomes.length)].biomeID;
            }
        }

        return dest;
    }
}
