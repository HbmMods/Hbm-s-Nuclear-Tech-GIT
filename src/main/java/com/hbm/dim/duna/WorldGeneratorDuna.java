package com.hbm.dim.duna;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.WorldTypeTeleport;
import com.hbm.main.ResourceManager;
import com.hbm.world.feature.OilBubbleDuna;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorDuna implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.dunaDimension) {
			generateDuna(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateDuna(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);

		if(WorldConfig.dunaoilSpawn > 0 && rand.nextInt(WorldConfig.dunaoilSpawn) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubbleDuna.spawnOil(world, randPosX, randPosY, randPosZ, 10 + rand.nextInt(7));
		}

        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn, 8, 16, 16, ModBlocks.ore_asbestos, meta, ModBlocks.duna_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 8, 1, 43, ModBlocks.ore_iron, meta, ModBlocks.duna_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 9, 4, 27, ModBlocks.ore_zinc, meta, ModBlocks.duna_rock);


		if(i == 0 && j == 0 && world.getWorldInfo().getTerrainType() == WorldTypeTeleport.martian) {
			int x = 0;
			int z = 0;
			int y = world.getHeightValue(x, z) - 1;

			ResourceManager.martian.build(world, x, y, z);
		}
	}
	
}