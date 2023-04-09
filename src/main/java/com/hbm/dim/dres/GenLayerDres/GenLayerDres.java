package com.hbm.dim.dres.GenLayerDres;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerDres extends GenLayer
{
    public GenLayerDres(long l)
    {
        super(l);
    }

    public static GenLayer[] makeTheWorld(long l)
    {
    	GenLayer biomes = new GenLayerDresBiomes(l);
    	 biomes = new GenLayerFuzzyZoom(2000L, biomes);
         biomes = new GenLayerZoom(2001L, biomes);
         biomes = new GenLayerDiversifyDres(1000L, biomes);
         biomes = new GenLayerZoom(1000L, biomes);
         biomes = new GenLayerDiversifyDres(1001L, biomes);
         biomes = new GenLayerZoom(1001L, biomes);
         biomes = new GenLayerDresBasins(3000L, biomes);
         biomes = new GenLayerZoom(1003L, biomes);
         biomes = new GenLayerSmooth(700L, biomes);
         biomes = new GenLayerDresPlains(200L, biomes);

         //biomes = new GenLayerZoom(1005L, biomes);
         //biomes = new GenLayerSmooth(703L, biomes);
        // biomes = new GenLayerFuzzyZoom(1000L, biomes);
        // biomes = new GenLayerSmooth(705L, biomes);
        // biomes = new GenLayerFuzzyZoom(1001L, biomes);
       //  biomes = new GenLayerSmooth(706L, biomes);
       //  biomes = new GenLayerFuzzyZoom(1002L, biomes);
         biomes = new GenLayerZoom(1006L, biomes);
         
         GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);
 
        biomes.initWorldGenSeed(l);
        genLayerVeronoiZoom.initWorldGenSeed(l);

        return new GenLayer[] { biomes, genLayerVeronoiZoom };
    }
}
