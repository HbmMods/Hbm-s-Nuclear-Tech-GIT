package com.hbm.dim;

import static net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.GeneralConfig;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.world.feature.OreLayer3D;
import com.hbm.world.gen.MapGenNTMFeatures;
import com.hbm.world.generator.DungeonToolbox;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.world.WorldEvent;

public class WorldGeneratorMoon implements IWorldGenerator {
	static ExperimentalCaveGenerator gen = new ExperimentalCaveGenerator();
	
	private final Random rand = new Random(); //A central random, used to cleanly generate our stuff without affecting vanilla or modded seeds.
	private boolean hasPopulationEvent = false; // Does the given chunkGenerator have a population event? If not (flatlands), default to using generate.

	/** Inits all MapGen upon the loading of a new world. Hopefully clears out structureMaps and structureData when a different world is loaded. */
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event) {
		gen = (ExperimentalCaveGenerator) getModdedMapGen(new ExperimentalCaveGenerator(), EventType.CUSTOM);
		hasPopulationEvent = false;
	}
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.dimensionId == SpaceConfig.moonDimension) {
			generateMoon(world, random, chunkX * 16, chunkZ * 16); 
		}
	}
	private void generateMoon(World world, Random rand, int i, int j) {
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 8, 1, 43, ModBlocks.moon_nickel, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 12, 4, 27, ModBlocks.moon_titanium, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn,  4, 4, 8, ModBlocks.moon_lithium, ModBlocks.moon_rock);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.aluminiumSpawn,  6, 5, 40, ModBlocks.moon_aluminium, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.hematiteSpawn, 10, 4, 80, ModBlocks.moon_conglomerate, ModBlocks.moon_rock);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.nickelSpawn, 6, 5, 16, ModBlocks.moon_nickel);
		//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 6, 5, 8, ModBlocks.moon_titanium);
		//new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.HEMATITE.ordinal());
		
		//gen.func_151538_a(world, rand, i, j, rand, Blocks.air, ModBlocks.moon_rock);

	}
}