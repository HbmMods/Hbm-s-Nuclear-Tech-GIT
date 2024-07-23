package com.hbm.dim.minmus;

import java.util.Random;

import com.hbm.config.SpaceConfig;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorMinmus implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.minmusDimension) {
			generateMinmus(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateMinmus(World world, Random rand, int i, int j) {

	}

}