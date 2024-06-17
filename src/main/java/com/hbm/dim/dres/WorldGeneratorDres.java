package com.hbm.dim.dres;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.main.MainRegistry;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorDres implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.dresDimension) {
			generateDres(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateDres(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);

		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.cobaltSpawn, 4, 3, 22, ModBlocks.ore_cobalt, meta, ModBlocks.dres_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn, 9, 4, 27, ModBlocks.ore_iron, meta, ModBlocks.dres_rock);
		DungeonToolbox.generateOre(world, rand, i, j, 12,  8, 1, 33, ModBlocks.ore_niobium, meta, ModBlocks.dres_rock);
		
		if (SpaceConfig.drescfreq > 0 && rand.nextInt(SpaceConfig.drescfreq) == 0) {
			
			for (int a = 0; a < 1; a++) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
				double r = rand.nextInt(15) + 10;
				
				if(rand.nextInt(50) == 0)
					r = 50;

				new CraterDres().generate(world, x, z, r, r * 0.35D);

				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned crater at " + x + " " + z);
			}
		}
		
	}
}