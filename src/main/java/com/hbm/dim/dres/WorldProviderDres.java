package com.hbm.dim.dres;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.WorldChunkManagerCelestial.BiomeGenLayers;
import com.hbm.dim.WorldChunkManagerCelestial;
import com.hbm.dim.WorldProviderCelestial;
import com.hbm.dim.dres.GenLayerDres.GenLayerDiversifyDres;
import com.hbm.dim.dres.GenLayerDres.GenLayerDresBasins;
import com.hbm.dim.dres.GenLayerDres.GenLayerDresBiomes;
import com.hbm.dim.dres.GenLayerDres.GenLayerDresPlains;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldProviderDres extends WorldProviderCelestial {
	
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerCelestial(createBiomeGenerators(worldObj.getSeed()));
	}

	@Override
	public String getDimensionName() {
		return "Dres";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderDres(this.worldObj, this.getSeed(), false);
	}
	
	// sorry mellow...
	// OOH I AM FOR REAL
	// NEVER MEANT TO MAKE YOUR DAUGHTER CRY
	@Override
	public Block getStone() {
		return ModBlocks.dres_rock;
	}

	private static BiomeGenLayers createBiomeGenerators(long seed) {
		GenLayer biomes = new GenLayerDresBiomes(seed);

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

		biomes = new GenLayerZoom(1006L, biomes);
		 
		GenLayer genLayerVeronoiZoom = new GenLayerVoronoiZoom(10L, biomes);

		return new BiomeGenLayers(biomes, genLayerVeronoiZoom, seed);
	}

}