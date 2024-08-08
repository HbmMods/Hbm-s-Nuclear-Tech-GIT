package com.hbm.dim.Ike;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorIke implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.ikeDimension) {
			generateIke(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateIke(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);

		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn, 8, 3, 22, ModBlocks.ore_asbestos, meta, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn, 9, 4, 27, ModBlocks.ore_copper, meta, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.ironClusterSpawn,  8, 1, 33, ModBlocks.ore_iron, meta, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn,  6, 4, 8, ModBlocks.ore_lithium, meta, ModBlocks.ike_stone);
		DungeonToolbox.generateOre(world, rand, i, j, 2, 4, 15, 40, ModBlocks.ore_coltan, meta, ModBlocks.ike_stone);
		
		//okay okay okay, lets say on duna you DO make solvent, this is now awesome because you can now make gallium arsenide to then head to
		//dres and the likes :)
	
		
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.mineralSpawn, 10, 12, 32, ModBlocks.ore_mineral, meta, ModBlocks.moho_stone);

	}
}