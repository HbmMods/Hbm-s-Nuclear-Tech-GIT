package com.hbm.dim.duna.GenLayerDuna;

import com.hbm.dim.eve.GenLayerEve.GenLayerEveBiomes;
import com.hbm.dim.eve.GenLayerEve.GenLayerEveRiver;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
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
        GenLayer genlayer = new GenLayerIsland(1L);

    	GenLayerRiverInit genlayerRiverInit = new GenLayerRiverInit(100L, genlayer);
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
         
         GenLayer genlayerVoronoiZoom = new GenLayerVoronoiZoom(10L, biomes);

         GenLayer genlayerRiverZoom = new GenLayerZoom(1000L, biomes);
         GenLayer genlayerRiver = new GenLayerRiver(1004L, genlayerRiverZoom); // Your custom river layer
         genlayerRiver = new GenLayerZoom(105L, genlayerRiver);

         GenLayer genlayerRiverMix = new GenLayerRiverMix(100L, biomes, genlayerRiver);
         GenLayer genlayerVoronoiZoomRiver = new GenLayerVoronoiZoom(101L, genlayerRiverMix);

         
         return new GenLayer[]{genlayerRiverMix, genlayerVoronoiZoom, genlayerRiverMix, genlayerVoronoiZoomRiver, biomes};
}
}