package com.hbm.dim;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WorldConfig;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorMoon implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case 15:
			generateMoon(world, random, chunkX * 16, chunkZ * 16); break;
		}
	}
	private void generateMoon(World world, Random rand, int i, int j) {
		if(WorldConfig.overworldOre) {
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 6, 5, 16, ModBlocks.moon_nickel);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 6, 5, 8, ModBlocks.moon_titanium);
		}
	}
}