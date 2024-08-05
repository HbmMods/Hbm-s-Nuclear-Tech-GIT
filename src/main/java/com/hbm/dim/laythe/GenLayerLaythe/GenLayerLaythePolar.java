package com.hbm.dim.laythe.GenLayerLaythe;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.laythe.biome.BiomeGenBaseLaythe;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerLaythePolar extends GenLayer {

    public GenLayerLaythePolar(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
    }


    public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
    {
        int i1 = p_75904_1_ - 1;
        int j1 = p_75904_2_ - 1;
        int k1 = p_75904_3_ + 2;
        int l1 = p_75904_4_ + 2;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);

        for (int i2 = 0; i2 < p_75904_4_; ++i2)
        {
            for (int j2 = 0; j2 < p_75904_3_; ++j2)
            {
                int k2 = aint[j2 + 0 + (i2 + 0) * k1];
                int l2 = aint[j2 + 2 + (i2 + 0) * k1];
                int i3 = aint[j2 + 0 + (i2 + 2) * k1];
                int j3 = aint[j2 + 2 + (i2 + 2) * k1];
                int k3 = aint[j2 + 1 + (i2 + 1) * k1];
                this.initChunkSeed((long)(j2 + p_75904_1_), (long)(i2 + p_75904_2_));
                if (k3 == SpaceConfig.laytheOceanBiome && k2 == SpaceConfig.laytheOceanBiome && l2 == SpaceConfig.laytheOceanBiome && i3 == SpaceConfig.laytheOceanBiome && j3 == SpaceConfig.laytheOceanBiome)
                {
                    aint1[j2 + i2 * p_75904_3_] = BiomeGenBaseLaythe.laythePolar.biomeID;
                }
                else
                {
                    aint1[j2 + i2 * p_75904_3_] = k3;
                }
            }
        }

        return aint1;
    }
}

