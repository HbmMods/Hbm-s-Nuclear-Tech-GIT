package com.hbm.dim.moon;

import static net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.ExperimentalCaveGenerator;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.world.WorldEvent;

public class WorldGeneratorMoon implements IWorldGenerator {

	static ExperimentalCaveGenerator gen = new ExperimentalCaveGenerator();
	
	// private final Random rand = new Random(); //A central random, used to cleanly generate our stuff without affecting vanilla or modded seeds.
	// private boolean hasPopulationEvent = false; // Does the given chunkGenerator have a population event? If not (flatlands), default to using generate.

	/** Inits all MapGen upon the loading of a new world. Hopefully clears out structureMaps and structureData when a different world is loaded. */
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event) {
		gen = (ExperimentalCaveGenerator) getModdedMapGen(new ExperimentalCaveGenerator(), EventType.CUSTOM);
		// hasPopulationEvent = false;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.moonDimension) {
			generateMoon(world, random, chunkX * 16, chunkZ * 16); 
		}
	}

	private void generateMoon(World world, Random rand, int i, int j) {
		int meta = CelestialBody.getMeta(world);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.mineralSpawn, 10, 12, 32, ModBlocks.ore_mineral, meta, ModBlocks.moon_rock);

		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 8, 1, 43, ModBlocks.ore_nickel, meta, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 12, 4, 27, ModBlocks.ore_titanium, meta, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn,  6, 4, 8, ModBlocks.ore_lithium, meta, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.aluminiumSpawn,  6, 5, 40, ModBlocks.ore_aluminium, meta, ModBlocks.moon_rock);
        DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.fluoriteSpawn, 4, 5, 45, ModBlocks.ore_fluorite, meta, ModBlocks.moon_rock);
        DungeonToolbox.generateOre(world, rand, i, j, 10, 13, 5, 64, ModBlocks.ore_quartz, meta, ModBlocks.moon_rock);
	}
}