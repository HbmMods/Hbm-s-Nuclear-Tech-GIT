package com.hbm.world.test;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGenTest implements IWorldGenerator {
	
	static MapGenTest gen = new MapGenTest();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		gen.func_151539_a(chunkGenerator, world, chunkX, chunkZ, new Block[0]);
		gen.generateStructuresInChunk(world, random, chunkX, chunkZ);
	}
}

