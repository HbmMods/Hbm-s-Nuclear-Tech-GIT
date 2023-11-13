package com.hbm.dim.Ike;

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

public class WorldGeneratorIke implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.ikeDimension) {

			generateIke(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	private void generateIke(World world, Random rand, int i, int j) {
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn, 4, 3, 22, ModBlocks.ike_asbestos, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn, 9, 4, 27, ModBlocks.ike_copper, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.ironClusterSpawn,  8, 1, 33, ModBlocks.ike_iron, ModBlocks.ike_stone);
		
		if (SpaceConfig.ikecfreq > 0 && rand.nextInt(SpaceConfig.ikecfreq) == 0) {
			
			for (int a = 0; a < 1; a++) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
				double r = rand.nextInt(15) + 10;
				
				if(rand.nextInt(50) == 0)
					r = 50;

				new Crater().generate(world, x, z, r, r * 0.35D, ModBlocks.ike_regolith, ModBlocks.ike_stone);

				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned crater at " + x + " " + z);
			}
		}
		
	}
}