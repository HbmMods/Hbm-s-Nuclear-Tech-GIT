package com.hbm.dim.eve.GenLayerEve;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerEve extends GenLayer
{
    public GenLayerEve(long l)
    {
        super(l);
    }

    public static GenLayer[] makeTheWorld(long l)
    {
    	GenLayer biomes = new GenLayerEveBiomes(l);
    	 biomes = new GenLayerFuzzyZoom(2000L, biomes);
         biomes = new GenLayerZoom(2001L, biomes);
         biomes = new GenLayerDiversifyEve(1000L, biomes);
         biomes = new GenLayerZoom(1000L, biomes);
         biomes = new GenLayerDiversifyEve(1001L, biomes);
         biomes = new GenLayerZoom(1001L, biomes);
         biomes = new GenLayerEveOceans(1300L, biomes);
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
