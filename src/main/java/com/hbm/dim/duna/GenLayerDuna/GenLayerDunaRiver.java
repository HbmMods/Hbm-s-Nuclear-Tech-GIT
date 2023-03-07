package com.hbm.dim.duna.GenLayerDuna;

import com.hbm.dim.duna.BiomeGenBaseDuna;

//import net.lepidodendron.util.EnumBiomeTypeEdiacaran;
//import net.lepidodendron.world.biome.cambrian.BiomeCambrian;
//import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
//import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
//import net.pnediacaran.world.biome.precambrian.ediacaran.BiomeEdiacaran;
//import net.pncambrian.world.biome.cambrian.BiomeCambrianBiome;
//import net.pnediacaran.world.biome.precambrian.ediacaran.BiomeEdiacaranBiome;

public class GenLayerDunaRiver extends GenLayer
{
    private final GenLayer biomePatternGeneratorChain;
    private final GenLayer riverPatternGeneratorChain;

    //Creeks to use:
   /* public Biome EDIACARAN_CREEK_COAST = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_creek_coastal"));
    public int EDIACARAN_CREEK_COAST_ID = Biome.getIdForBiome(EDIACARAN_CREEK_COAST);
    public Biome EDIACARAN_CREEK = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_creek"));
    public int EDIACARAN_CREEK_ID =  Biome.getIdForBiome(EDIACARAN_CREEK);

    //Biomes to exclude for rivers:
    public Biome EDIACARAN_OCEAN = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_trench"));
    public int EDIACARAN_OCEAN_ID =  Biome.getIdForBiome(EDIACARAN_OCEAN);
    public Biome EDIACARAN_OCEAN_SHORE = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:precambrian_sea"));
    public int EDIACARAN_OCEAN_SHORE_ID =  Biome.getIdForBiome(EDIACARAN_OCEAN_SHORE);
    public Biome EDIACARAN_HILLY = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_hills"));
    public int EDIACARAN_HILLY_ID =  Biome.getIdForBiome(EDIACARAN_HILLY);
    public Biome EDIACARAN_CRAGGY = Biome.REGISTRY.getObject(new ResourceLocation("lepidodendron:ediacaran_crags"));
    public int EDIACARAN_CRAGGY_ID =  Biome.getIdForBiome(EDIACARAN_CRAGGY);*/

    public GenLayerDunaRiver(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_)
    {
        super(p_i2129_1_);
        this.biomePatternGeneratorChain = p_i2129_3_;
        this.riverPatternGeneratorChain = p_i2129_4_;
    }

    public void initWorldGenSeed(long seed)
    {
        this.biomePatternGeneratorChain.initWorldGenSeed(seed);
        this.riverPatternGeneratorChain.initWorldGenSeed(seed);
        super.initWorldGenSeed(seed);
    }

    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] aint = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint1 = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaWidth * areaHeight; ++i)
        {
            if (aint1[i] == BiomeGenBase.river.biomeID)
            {
                //Exclude rivers here:
                if (aint[i] == BiomeGenBaseDuna.dunaLowlands.biomeID
                        || aint[i] == BiomeGenBaseDuna.dunaPolar.biomeID
                )
                {
                    aint2[i] = aint[i];
                }
                else {
                    //Add the rivers we want:
                    BiomeGenBase biome = BiomeGenBase.getBiome(aint[i]);
                    if (biome instanceof BiomeGenBaseDuna) {
                        if (biome == BiomeGenBaseDuna.dunaPlains) {
                            aint2[i] = BiomeGenBaseDuna.dunaPlains.biomeID;
                        }
                        else {
                            aint2[i] = aint[i];
                        }
                    }
                    else {
                        aint2[i] = aint[i];
                    }
                }
            }
            else
            {
                aint2[i] = aint[i];
            }

        }

        return aint2;
    }
}
