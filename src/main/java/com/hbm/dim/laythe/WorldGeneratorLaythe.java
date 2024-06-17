package com.hbm.dim.laythe;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorLaythe implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if(world.provider.dimensionId == SpaceConfig.laytheDimension) {
            generateLaythe(world, random, chunkX * 16, chunkZ * 16);
        }
    }

	private void generateLaythe(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);

        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn, 4, 16, 16, ModBlocks.ore_asbestos, meta);
    }
    
}
