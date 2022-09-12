package com.hbm.world.worldgen;

import java.util.Random;

import com.hbm.config.StructureConfig;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

public class NTMWorldGenerator implements IWorldGenerator {
	
	private MapGenNTMFeatures NTMFeatureGenerator = new MapGenNTMFeatures();
	
	{
		NTMFeatureGenerator = (MapGenNTMFeatures) TerrainGen.getModdedMapGen(NTMFeatureGenerator, EventType.CUSTOM);
	}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, rand, chunkGenerator, chunkX, chunkZ); break;
		case 0:
			generateSurface(world, rand, chunkGenerator, chunkX, chunkZ); break;
		case 1:
			generateEnd(world, rand, chunkGenerator, chunkX, chunkZ); break;
		}
	}

	private void generateNether(World world, Random rand, IChunkProvider chunkGenerator, int chunkX, int chunkZ) { }
	
	private void generateSurface(World world, Random rand, IChunkProvider chunkGenerator, int chunkX, int chunkZ) {
		Block[] ablock = new Block[65536];
		
		//WorldConfig.enableStructures
		/** Spawns structure starts. Utilizes canSpawnStructureAtCoords() + if else checks in Start constructor */
		if(StructureConfig.enableStructures) {
			this.NTMFeatureGenerator.func_151539_a(chunkGenerator, world, chunkX, chunkZ, ablock);
		}
		
		/** Actually generates structures in a given chunk. */
		if(StructureConfig.enableStructures) {
			this.NTMFeatureGenerator.generateStructuresInChunk(world, rand, chunkX, chunkZ);
		}
	}

	private void generateEnd(World world, Random rand, IChunkProvider chunkGenerator, int chunkX, int chunkZ) { }
}
