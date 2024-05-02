package com.hbm.dim.eve.GenLayerEve;

import com.hbm.dim.dres.GenLayerDres.GenLayerEveMountains;

import net.minecraft.world.biome.BiomeGenRiver;
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
         biomes = new GenLayerEveSeismicPlains(1006L, biomes);
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
         //biomes = new GenLayerEveRiver(1002L, biomes);

         GenLayer renlayerweek = new GenLayerRiverInit(100L, biomes);
         GenLayer genlayercreek6 = new GenLayerRiver(1L, renlayerweek);
         GenLayer genlayercreek7 = new GenLayerSmooth(1000L, genlayercreek6);
         GenLayer genlayercreekfinal = new GenLayerEveRiver(100L, biomes, genlayercreek7);
         GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, genlayercreek7);
         genLayerVeronoiZoom = new GenLayerRiver(l, biomes);
         
         genlayercreekfinal.initWorldGenSeed(l);
         genLayerVeronoiZoom.initWorldGenSeed(l);

        biomes.initWorldGenSeed(l);
        genLayerVeronoiZoom.initWorldGenSeed(l);
        /*         GenLayer seismicPlains = new GenLayerEveSeismicPlains(l, biomes);
     	 GenLayer sbiomes = new GenLayerEveMountains(l, biomes);

         GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);
         biomes = new GenLayerRiver(1002L, sbiomes);
	*/
        
        
        return new GenLayer[] { genlayercreekfinal, genLayerVeronoiZoom, genlayercreekfinal };
    }
}
