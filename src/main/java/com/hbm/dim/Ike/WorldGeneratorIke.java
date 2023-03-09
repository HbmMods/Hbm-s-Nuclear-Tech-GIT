package com.hbm.dim.Ike;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WorldConfig;
import com.hbm.world.feature.OreLayer3D;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGeneratorIke implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case 17:
			generateIke(world, random, chunkX * 16, chunkZ * 16); break;
		}
	}
	private void generateIke(World world, Random rand, int i, int j) {
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn, 4, 3, 22, ModBlocks.ike_asbestos, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn, 9, 4, 27, ModBlocks.ike_copper, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.ironClusterSpawn,  8, 1, 33, ModBlocks.ike_iron, ModBlocks.ike_stone);
	}
}