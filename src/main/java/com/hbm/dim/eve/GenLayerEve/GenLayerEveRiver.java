package com.hbm.dim.eve.GenLayerEve;

import com.hbm.dim.duna.biome.BiomeGenBaseDuna;
import com.hbm.dim.eve.biome.BiomeGenBaseEve;
import com.hbm.dim.eve.biome.BiomeGenEve;
import com.hbm.dim.eve.biome.BiomeGenEveMountains;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class GenLayerEveRiver extends GenLayer {
    private final GenLayer biomePatternGeneratorChain;
    private final GenLayer riverPatternGeneratorChain;

    public GenLayerEveRiver(long seed, GenLayer biomeGenLayer, GenLayer riverGenLayer) {
        super(seed);
        this.biomePatternGeneratorChain = biomeGenLayer;
        this.riverPatternGeneratorChain = riverGenLayer;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] biomeArray = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] riverArray = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] resultArray = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaWidth * areaHeight; ++i) {
                // Check if the biome is an ocean biome
                if (biomeArray[i] == BiomeGenBaseEve.eveOcean.biomeID) {
                    resultArray[i] = biomeArray[i]; // Exclude rivers in ocean biomes
                } else {
                    // Check if the biome is a mountain biome
                    BiomeGenBase biome = BiomeGenBaseEve.getBiome(biomeArray[i]);
                    if (biome instanceof BiomeGenEve) {
                        resultArray[i] = BiomeGenBaseEve.eveRiver.biomeID; // Set river biome
                    } else {
                        resultArray[i] = biomeArray[i]; // Otherwise keep original biome
                    }
                }
        }

        return resultArray;
    }

}
