package com.hbm.dim.moho;

import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.config.SpaceConfig;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorMoho implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.mohoDimension) {
			generateMoho(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	private void generateMoho(World world, Random rand, int i, int j) {
		if (SpaceConfig.ikecfreq > 0 && rand.nextInt(SpaceConfig.ikecfreq) == 0) {
			
			for (int a = 0; a < 1; a++) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
				double r = rand.nextInt(15) + 10;
				
				if(rand.nextInt(50) == 0)
					r = 50;

				new CraterMoho().generate(world, x, z, r, r * 0.35D);

				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned crater at " + x + " " + z);
			}
		}
		
	}
}