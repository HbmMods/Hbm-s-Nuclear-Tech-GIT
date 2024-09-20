package com.hbm.dim.moho;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockVolcano;
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
		
		// More basalt ores!
		DungeonToolbox.generateOre(world, rand, i, j, 16, 6, 16, 64, ModBlocks.ore_basalt, 0, ModBlocks.basalt);
		DungeonToolbox.generateOre(world, rand, i, j, 12, 8, 8, 32, ModBlocks.ore_basalt, 1, ModBlocks.basalt);
		DungeonToolbox.generateOre(world, rand, i, j, 8, 9, 8, 48, ModBlocks.ore_basalt, 2, ModBlocks.basalt);
		DungeonToolbox.generateOre(world, rand, i, j, 2, 4, 0, 24, ModBlocks.ore_basalt, 3, ModBlocks.basalt);
		DungeonToolbox.generateOre(world, rand, i, j, 8, 10, 16, 64, ModBlocks.ore_basalt, 4, ModBlocks.basalt);

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

		// Kick the volcanoes into action, and fix SOME floating lava
		// a full fix for floating lava would cause infinite cascades so we uh, don't
		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				for(int y = 32; y < 128; y++) {
					int ox = i + x;
					int oz = j + z;
					Block b = world.getBlock(ox, y, oz);

					if(b == Blocks.lava && world.getBlock(ox, y - 1, oz) == Blocks.air) {
						world.setBlock(ox, y - 1, oz, Blocks.flowing_lava, 0, 0);
						world.markBlockForUpdate(ox, y - 1, oz);
					} else if(b == ModBlocks.volcano_core) {
						world.setBlock(ox, y, oz, ModBlocks.volcano_core, BlockVolcano.META_STATIC_EXTINGUISHING, 0);
						world.markBlockForUpdate(ox, y, oz);
					}
				}
			}
		}
	}

}