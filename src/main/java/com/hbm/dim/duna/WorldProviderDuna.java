package com.hbm.dim.duna;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.WorldChunkManagerCelestial;
import com.hbm.dim.WorldProviderCelestial;
import com.hbm.dim.WorldTypeTeleport;
import com.hbm.dim.WorldChunkManagerCelestial.BiomeGenLayers;
import com.hbm.dim.duna.GenLayerDuna.GenLayerDiversifyDuna;
import com.hbm.dim.duna.GenLayerDuna.GenLayerDunaBiomes;
import com.hbm.dim.duna.GenLayerDuna.GenLayerDunaLowlands;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldProviderDuna extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerCelestial(createBiomeGenerators(worldObj.getSeed()));
	}

	@Override
	public String getDimensionName() {
		return "Duna";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderDuna(this.worldObj, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.duna_rock;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		// BRING
		//  HIM
		// HOMIE
		if(worldObj.getWorldInfo().getTerrainType() == WorldTypeTeleport.martian)
			return dimensionId;

		return super.getRespawnDimension(player);
	}

	private static BiomeGenLayers createBiomeGenerators(long seed) {
		GenLayer biomes = new GenLayerDunaBiomes(seed);
		
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

		return new BiomeGenLayers(genlayerRiverMix, genlayerVoronoiZoom, seed);
	}

}