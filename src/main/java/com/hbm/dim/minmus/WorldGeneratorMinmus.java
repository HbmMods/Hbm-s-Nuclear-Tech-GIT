package com.hbm.dim.minmus;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.GeneralConfig;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.main.MainRegistry;
import com.hbm.world.feature.OreLayer3D;
import com.hbm.world.feature.Sellafield;
import com.hbm.world.generator.DungeonToolbox;


import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

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