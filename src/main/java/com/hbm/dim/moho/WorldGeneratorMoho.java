package com.hbm.dim.moho;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
		int meta = CelestialBody.getMeta(world);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.mineralSpawn, 10, 12, 32, ModBlocks.ore_mineral, meta, ModBlocks.moho_stone);

        DungeonToolbox.generateOre(world, rand, i, j, 14, 12, 5, 30, ModBlocks.ore_glowstone, meta, ModBlocks.moho_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherPhosphorusSpawn, 6, 8, 64, ModBlocks.ore_fire, meta, ModBlocks.moho_stone);
		DungeonToolbox.generateOre(world, rand, i, j, 8, 4, 0, 24, ModBlocks.ore_australium, meta, ModBlocks.moho_stone);

        DungeonToolbox.generateOre(world, rand, i, j, 1, 12, 8, 32, ModBlocks.ore_shale, meta, ModBlocks.moho_stone);

        DungeonToolbox.generateOre(world, rand, i, j, 10, 32, 0, 128, ModBlocks.basalt, 0, ModBlocks.moho_stone);

		for(int k = 0; k < 2; k++){
			int x = i + rand.nextInt(16);
			int z = j + rand.nextInt(16);
			int d = 16 + rand.nextInt(96);

			for(int y = d - 5; y <= d; y++) {
				Block b = world.getBlock(x, y, z);
				if(world.getBlock(x, y + 1, z) == Blocks.air && (b == ModBlocks.moho_stone || b == ModBlocks.moho_regolith)) {
					world.setBlock(x, y, z, ModBlocks.geysir_nether);
					world.setBlock(x+1, y, z, Blocks.netherrack);
					world.setBlock(x-1, y, z, Blocks.netherrack);
					world.setBlock(x, y, z+1, Blocks.netherrack);
					world.setBlock(x, y, z-1, Blocks.netherrack);
					world.setBlock(x+1, y-1, z, Blocks.netherrack);
					world.setBlock(x-1, y-1, z, Blocks.netherrack);
					world.setBlock(x, y-1, z+1, Blocks.netherrack);
					world.setBlock(x, y-1, z-1, Blocks.netherrack);
				}
			}
		}
	}

}