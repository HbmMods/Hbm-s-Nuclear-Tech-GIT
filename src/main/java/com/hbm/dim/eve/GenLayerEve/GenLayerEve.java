package com.hbm.dim.eve.GenLayerEve;

import com.hbm.dim.dres.GenLayerDres.GenLayerEveMountains;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerEve extends GenLayer
{
    public GenLayerEve(long l)
    {
        super(l);
    }

    public static GenLayer[] makeTheWorld(long seed, WorldType worldType) {
        GenLayer genlayer = new GenLayerIsland(1L);

        GenLayerRiverInit genlayerRiverInit = new GenLayerRiverInit(100L, genlayer);
        GenLayer genlayerBiomes = new GenLayerEveBiomes(seed); // Your custom biome layer
        genlayerBiomes = new GenLayerZoom(1000L, genlayerBiomes);
        genlayerBiomes = new GenLayerZoom(1001L, genlayerBiomes);
        genlayerBiomes = new GenLayerZoom(1002L, genlayerBiomes);
        genlayerBiomes = new GenLayerZoom(1003L, genlayerBiomes);
        genlayerBiomes = new GenLayerZoom(1004L, genlayerBiomes);
        genlayerBiomes = new GenLayerZoom(1005L, genlayerBiomes);

        GenLayer genlayerVoronoiZoom = new GenLayerVoronoiZoom(10L, genlayerBiomes);

        GenLayer genlayerRiverZoom = new GenLayerZoom(1000L, genlayerBiomes);
        GenLayer genlayerRiver = new GenLayerRiver(1001L, genlayerRiverZoom); // Your custom river layer
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerRiver);

        GenLayer genlayerRiverMix = new GenLayerRiverMix(100L, genlayerBiomes, genlayerRiver);
        GenLayer genlayerVoronoiZoomRiver = new GenLayerVoronoiZoom(101L, genlayerRiverMix);
        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerBiomes);
        GenLayerEveRiverMix genlayerrivermix = new GenLayerEveRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayerVoronoiZoom genlayervoronoizoom = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        
        return new GenLayer[]{genlayerrivermix, genlayervoronoizoom, genlayerrivermix, genlayerVoronoiZoomRiver, };
    }
}
