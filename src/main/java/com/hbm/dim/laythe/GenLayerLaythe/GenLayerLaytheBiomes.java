package com.hbm.dim.laythe.GenLayerLaythe;


import com.hbm.dim.laythe.biome.BiomeGenBaseLaythe;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerLaytheBiomes extends GenLayer
{
    private static final BiomeGenBase[] biomes = new BiomeGenBase[] { BiomeGenBaseLaythe.laytheIsland, BiomeGenBaseLaythe.laytheOcean};

    public GenLayerLaytheBiomes(long l)
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
