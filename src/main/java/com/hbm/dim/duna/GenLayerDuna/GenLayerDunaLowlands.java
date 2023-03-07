package com.hbm.dim.duna.GenLayerDuna;

import com.hbm.dim.duna.BiomeGenBaseDuna;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDunaLowlands extends GenLayer
{

   /* public Biome EDIACARAN_OCEAN = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_sea"));
    public  int EDIACARAN_OCEAN_ID =  Biome.getIdForBiome(EDIACARAN_OCEAN);
    public  Biome EDIACARAN_OCEAN_SHORE = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_shallow_sea"));
    public  int EDIACARAN_OCEAN_SHORE_ID =  Biome.getIdForBiome(EDIACARAN_OCEAN_SHORE);
    public  Biome EDIACARAN_LAND = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_biome"));
    public  int EDIACARAN_LAND_ID =  Biome.getIdForBiome(EDIACARAN_LAND);
    public  Biome EDIACARAN_HILLS = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_hills"));
    public  int EDIACARAN_HILLS_ID =  Biome.getIdForBiome(EDIACARAN_HILLS);
    public  Biome EDIACARAN_BEACH = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_beach"));
    public  int EDIACARAN_BEACH_ID =  Biome.getIdForBiome(EDIACARAN_BEACH);*/


    public GenLayerDunaLowlands(long seed, GenLayer genLayer)
    {
        super(seed);
        this.parent = genLayer;
    }

    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        return this.getIntsOcean(areaX, areaY, areaWidth, areaHeight);
    }

    private int[] getIntsOcean(int p_151626_1_, int p_151626_2_, int p_151626_3_, int p_151626_4_)
    {
        int i = p_151626_1_ - 1;
        int j = p_151626_2_ - 1;
        int k = 1 + p_151626_3_ + 1;
        int l = 1 + p_151626_4_ + 1;
        int[] aint = this.parent.getInts(i, j, k, l);
        int[] aint1 = IntCache.getIntCache(p_151626_3_ * p_151626_4_);

        for (int i1 = 0; i1 < p_151626_4_; ++i1)
        {
            for (int j1 = 0; j1 < p_151626_3_; ++j1)
            {
                this.initChunkSeed((long)(j1 + p_151626_1_), (long)(i1 + p_151626_2_));
                int k1 = aint[j1 + 1 + (i1 + 1) * k];

                if (k1 == BiomeGenBaseDuna.dunaLowlands.biomeID)
                {
                    int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
                    int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
                    int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
                    int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
                    boolean flag = (
                        (l1 == BiomeGenBaseDuna.dunaPlains.biomeID/* || l1 == EDIACARAN_HILLS_ID || l1 == EDIACARAN_BEACH_ID*/)
                        || (i2 == BiomeGenBaseDuna.dunaPlains.biomeID/* || i2 == EDIACARAN_HILLS_ID || i2 == EDIACARAN_BEACH_ID*/)
                        || (j2 == BiomeGenBaseDuna.dunaPlains.biomeID/* || j2 == EDIACARAN_HILLS_ID || j2 == EDIACARAN_BEACH_ID*/)
                        || (k2 == BiomeGenBaseDuna.dunaPlains.biomeID/* || k2 == EDIACARAN_HILLS_ID || k2 == EDIACARAN_BEACH_ID*/)
                    );
                    if (flag)
                    {
                        k1 = BiomeGenBaseDuna.dunaLowlands.biomeID;
                    }
                }

                aint1[j1 + i1 * p_151626_3_] = k1;
            }
        }

        return aint1;
    }
    
}
