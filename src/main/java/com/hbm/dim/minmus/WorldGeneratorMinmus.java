package com.hbm.dim.minmus;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;

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
		int meta = CelestialBody.getMeta(world);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.malachiteSpawn, 16, 6, 40, ModBlocks.stone_resource, EnumStoneType.MALACHITE.ordinal(), ModBlocks.minmus_stone);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn * 3, 12, 8, 56, ModBlocks.ore_copper, meta, ModBlocks.minmus_stone);
	}

}