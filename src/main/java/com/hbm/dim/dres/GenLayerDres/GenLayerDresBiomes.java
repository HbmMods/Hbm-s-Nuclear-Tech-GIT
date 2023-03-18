package com.hbm.dim.dres.GenLayerDres;

import com.hbm.dim.dres.biome.BiomeGenBaseDres;
import com.hbm.dim.duna.biome.BiomeGenBaseDuna;
import com.hbm.dim.eve.biome.BiomeGenBaseEve;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDresBiomes extends GenLayer
{
    private static final BiomeGenBase[] biomes = new BiomeGenBase[] {BiomeGenBaseDres.dresCanyon, BiomeGenBaseDres.dresPlains};

    public GenLayerDresBiomes(long l)
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
