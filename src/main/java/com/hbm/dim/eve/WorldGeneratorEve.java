package com.hbm.dim.eve;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.eve.GenLayerEve.WorldGenElectricVolcano;
import com.hbm.dim.eve.GenLayerEve.WorldGenEveSpike;
import com.hbm.dim.eve.biome.BiomeGenBaseEve;
import com.hbm.world.feature.OilBubble;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorEve implements IWorldGenerator {

	WorldGenElectricVolcano volcano = new WorldGenElectricVolcano(30, 22, ModBlocks.eve_silt, ModBlocks.eve_rock);

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.eveDimension) {
			generateEve(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateEve(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);

		DungeonToolbox.generateOre(world, rand, i, j, 12,  8, 1, 33, ModBlocks.ore_niobium, meta, ModBlocks.eve_rock);
		DungeonToolbox.generateOre(world, rand, i, j, 8,  4, 5, 48, ModBlocks.ore_iodine, meta, ModBlocks.eve_rock);

		if(WorldConfig.eveGasSpawn > 0 && rand.nextInt(WorldConfig.eveGasSpawn) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubble.spawnOil(world, randPosX, randPosY, randPosZ, 10 + rand.nextInt(7), ModBlocks.ore_gas, meta, ModBlocks.eve_rock);
		}

		int x = i + rand.nextInt(16);
		int z = j + rand.nextInt(16);
		int y = world.getHeightValue(x, z);

		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		if(biome == BiomeGenBaseEve.eveSeismicPlains) {
			new WorldGenEveSpike().generate(world, rand, x, y, z);
		}

		if(rand.nextInt(100) == 0) {
			volcano.generate(world, rand, x, y, z);

		}
	}

}
