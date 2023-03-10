package com.hbm.dim.duna.GenLayerDuna;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerDuna extends GenLayer
{
    public GenLayerDuna(long l)
    {
        super(l);
    }

    public static GenLayer[] makeTheWorld(long l)
    {
    	GenLayer biomes = new GenLayerDunaBiomes(l);
    	 biomes = new GenLayerFuzzyZoom(2000L, biomes);
         biomes = new GenLayerZoom(2001L, biomes);
         biomes = new GenLayerDiversifyDuna(1000L, biomes);
         biomes = new GenLayerZoom(1000L, biomes);
         biomes = new GenLayerDiversifyDuna(1001L, biomes);
         biomes = new GenLayerZoom(1001L, biomes);
         biomes = new GenLayerDunaLowlands(1300L, biomes);
         biomes = new GenLayerZoom(1003L, biomes);
         biomes = new GenLayerSmooth(700L, biomes);
         biomes = new GenLayerZoom(1005L, biomes);
         biomes = new GenLayerSmooth(703L, biomes);
         biomes = new GenLayerFuzzyZoom(1000L, biomes);
         biomes = new GenLayerSmooth(705L, biomes);
         biomes = new GenLayerFuzzyZoom(1001L, biomes);
         biomes = new GenLayerSmooth(706L, biomes);
         biomes = new GenLayerFuzzyZoom(1002L, biomes);
         biomes = new GenLayerZoom(1006L, biomes);
         
         GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);
 
        biomes.initWorldGenSeed(l);
        genLayerVeronoiZoom.initWorldGenSeed(l);

        return new GenLayer[] { biomes, genLayerVeronoiZoom };
    }
}
