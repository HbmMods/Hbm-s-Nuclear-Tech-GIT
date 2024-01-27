package com.hbm.dim.laythe.GenLayerLaythe;

import com.hbm.dim.dres.GenLayerDres.GenLayerDiversifyDres;
import com.hbm.dim.dres.GenLayerDres.GenLayerDresBasins;
import com.hbm.dim.dres.GenLayerDres.GenLayerDresBiomes;
import com.hbm.dim.dres.GenLayerDres.GenLayerDresPlains;
import com.hbm.dim.laythe.biome.BiomeGenBaseLaythe;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;

public abstract class GenLayerLaythe extends GenLayer
{
    public GenLayerLaythe(long l)
    {
        super(l);
    }

    public static GenLayer[] makeTheWorld(long l)
    {
    	GenLayer biomes = new GenLayerLaytheBiomes(l);
   	 biomes = new GenLayerFuzzyZoom(2000L, biomes);
        biomes = new GenLayerZoom(2001L, biomes);
        biomes = new GenLayerDiversifyLaythe(1000L, biomes);
        biomes = new GenLayerZoom(1000L, biomes);
        biomes = new GenLayerZoom(1001L, biomes);
        biomes = new GenLayerLaytheOceans(4000L, biomes);
        biomes = new GenLayerLaytheOceans(4000L, biomes);
        biomes = new GenLayerLaytheOceans(4000L, biomes);
        biomes = new GenLayerLaytheOceans(4000L, biomes);
        biomes = new GenLayerZoom(1003L, biomes);
        biomes = new GenLayerSmooth(700L, biomes);
        biomes = new GenLayerLaytheIslands(200L, biomes);

        biomes = new GenLayerZoom(1006L, biomes);
         
         GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);
 
        biomes.initWorldGenSeed(l);
        genLayerVeronoiZoom.initWorldGenSeed(l);

        return new GenLayer[] { biomes, genLayerVeronoiZoom };
    }
    @Override
    public int[] getInts(int x, int z, int width, int height) {
        int[] biomes = IntCache.getIntCache(width * height);
        
        // Example code for generating ocean biomes with adjusted size and frequency
        for (int i = 0; i < width * height; i++) {
            // Adjust biome size
            int biomeSize = 10; // Adjust this value to change biome size
            
            // Adjust biome frequency
            int biomeFrequency = 20; // Adjust this value to change biome frequency
            
            // Generate ocean biome
            if (i % biomeFrequency == 0) { // Adjust the condition for biome frequency
                for (int j = 0; j < biomeSize; j++) { // Adjust the size of the biome
                    biomes[i + j] = BiomeGenBaseLaythe.laytheOcean.biomeID;
                }
                i += biomeSize - 1; // Skip the next cells since they belong to the same biome
            } else {
                // Generate other biomes
                biomes[i] = BiomeGenBaseLaythe.laytheIsland.biomeID /* generate other biomes */;
            }
        }
        
        return biomes;
    }
}
