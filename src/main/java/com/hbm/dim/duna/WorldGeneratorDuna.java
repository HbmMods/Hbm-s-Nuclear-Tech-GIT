package com.hbm.dim.duna;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.GeneralConfig;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.world.feature.OilBubble;
import com.hbm.world.feature.OilBubbleDuna;
import com.hbm.world.feature.OreLayer3D;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGeneratorDuna implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.dunaDimension) {
			generateDuna(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	private void generateDuna(World world, Random rand, int i, int j) {
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 8, 1, 43, ModBlocks.moon_nickel, ModBlocks.moon_rock);
	///	//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 9, 4, 27, ModBlocks.moon_titanium, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn,  4, 4, 8, ModBlocks.moon_lithium, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.aluminiumSpawn,  6, 5, 40, ModBlocks.moon_aluminium, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.hematiteSpawn, 10, 4, 80, ModBlocks.moon_conglomerate, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 6, 5, 16, ModBlocks.moon_nickel);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 6, 5, 8, ModBlocks.moon_titanium);
		//new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.HEMATITE.ordinal());
		if(WorldConfig.dunaoilSpawn > 0 && rand.nextInt(WorldConfig.dunaoilSpawn) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubbleDuna.spawnOil(world, randPosX, randPosY, randPosZ, 10 + rand.nextInt(7));
		}
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 8, 1, 43, ModBlocks.duna_iron, ModBlocks.duna_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 9, 4, 27, ModBlocks.duna_zinc, ModBlocks.duna_rock);
	}
	
}