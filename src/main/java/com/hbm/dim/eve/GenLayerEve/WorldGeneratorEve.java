package com.hbm.dim.eve.GenLayerEve;

import java.util.Random;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.Ike.WorldGenEveSpike;
import com.hbm.dim.eve.biome.BiomeGenBaseEve;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorEve implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {

        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        int y = world.getHeightValue(x, z);
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        if(biome == BiomeGenBaseEve.SeismicPlains)
        new WorldGenEveSpike().generate(world, random, x, y, z);
	}
		
	

}
