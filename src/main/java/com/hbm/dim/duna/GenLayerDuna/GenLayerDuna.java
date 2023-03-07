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
         //biomes = new GenLayerCarboniferousIceEdgeSea(3L, biomes);
         biomes = new GenLayerZoom(1001L, biomes);
        // biomes = new GenLayerEdiacaranDeepOcean(1100L, biomes);
         biomes = new GenLayerDunaLowlands(1300L, biomes);
         biomes = new GenLayerZoom(1003L, biomes);
         //biomes = new GenLayerCarboniferousIceEdge(2L, biomes);
         biomes = new GenLayerSmooth(700L, biomes);
         biomes = new GenLayerZoom(1005L, biomes);
         //biomes = new GenLayerCarboniferousIceSpikes(1000L, biomes);
         biomes = new GenLayerSmooth(703L, biomes);
         biomes = new GenLayerFuzzyZoom(1000L, biomes);
         //biomes = new GenLayerEdiacaranBeach(1050L, biomes);
         biomes = new GenLayerSmooth(705L, biomes);
         biomes = new GenLayerFuzzyZoom(1001L, biomes);
         biomes = new GenLayerSmooth(706L, biomes);
         biomes = new GenLayerFuzzyZoom(1002L, biomes);
         biomes = new GenLayerZoom(1006L, biomes);
         
         GenLayer genlayercreek = new GenLayerRiverInit(100L, biomes);
         GenLayer genlayercreek2 = GenLayerZoom.magnify(1000L, genlayercreek, 1);
         GenLayer genlayercreek3 = GenLayerZoom.magnify(1000L, genlayercreek2, 2);
         GenLayer genlayercreek4 = GenLayerZoom.magnify(1000L, genlayercreek3, 2);
         GenLayer genlayercreek5 = GenLayerZoom.magnify(1000L, genlayercreek4, 2);
         GenLayer genlayercreek6 = new GenLayerRiver(1L, genlayercreek5);
         GenLayer genlayercreek7 = new GenLayerSmooth(1000L, genlayercreek6);
         GenLayer genlayercreekfinal = new GenLayerDunaRiver(100L, biomes, genlayercreek7);

         
         GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);
    	
        /*GenLayer biomes = new GenLayerDunaBiomes(l);
        biomes = new GenLayerFuzzyZoom(2000L, biomes);
        biomes = new GenLayerZoom(1000L, biomes);
//        biomes = new GenLayerVenusSurround(500L, biomes);
        biomes = new GenLayerZoom(1001L, biomes);
        biomes = new GenLayerZoom(1002L, biomes);
        biomes = new GenLayerZoom(1003L, biomes);
        GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);*/
        biomes.initWorldGenSeed(l);
        genLayerVeronoiZoom.initWorldGenSeed(l);

        return new GenLayer[] { biomes, genLayerVeronoiZoom };
    }
}
