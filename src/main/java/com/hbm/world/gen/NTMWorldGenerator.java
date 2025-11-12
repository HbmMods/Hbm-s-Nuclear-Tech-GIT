package com.hbm.world.gen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.StructureConfig;
import com.hbm.main.StructureManager;
import com.hbm.world.gen.component.BunkerComponents.BunkerStart;
import com.hbm.world.gen.component.Component.CrabSpawners;
import com.hbm.world.gen.component.Component.GreenOoze;
import com.hbm.world.gen.component.Component.MeteorBricks;
import com.hbm.world.gen.component.Component.SupplyCrates;
import com.hbm.world.gen.nbt.JigsawPiece;
import com.hbm.world.gen.nbt.JigsawPool;
import com.hbm.world.gen.nbt.NBTStructure;
import com.hbm.world.gen.nbt.SpawnCondition;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureComponent.BlockSelector;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.world.WorldEvent;

public class NTMWorldGenerator implements IWorldGenerator {

	boolean regTest = false;

	public NTMWorldGenerator() {
		final List<BiomeGenBase> invalidBiomes = Arrays.asList(new BiomeGenBase[] {BiomeGenBase.ocean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.deepOcean});
		final List<BiomeGenBase> oceanBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.ocean, BiomeGenBase.deepOcean });
		final List<BiomeGenBase> beachBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.beach, BiomeGenBase.stoneBeach, BiomeGenBase.coldBeach });
		final List<BiomeGenBase> lighthouseBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.beach, BiomeGenBase.stoneBeach, BiomeGenBase.coldBeach });

		/// SPIRE ///
		NBTStructure.registerStructure(0, new SpawnCondition("spire") {{
			canSpawn = biome -> biome.heightVariation <= 0.05F && !invalidBiomes.contains(biome);
			structure = new JigsawPiece("spire", StructureManager.spire, -1);
			spawnWeight = 2;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("features") {{
			canSpawn = biome -> !invalidBiomes.contains(biome);
			start = d -> new MapGenNTMFeatures.Start(d.getW(), d.getX(), d.getY(), d.getZ());
			spawnWeight = 14 * 4;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("bunker") {{
			canSpawn = biome -> !invalidBiomes.contains(biome);
			start = d -> new BunkerStart(d.getW(), d.getX(), d.getY(), d.getZ());
			spawnWeight = 1 * 4;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("vertibird") {{
			canSpawn = biome -> !biome.canSpawnLightningBolt() && biome.temperature >= 2F;
			structure = new JigsawPiece("vertibird", StructureManager.vertibird, -3);
			spawnWeight = 3 * 4;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("crashed_vertibird") {{
			canSpawn = biome -> !biome.canSpawnLightningBolt() && biome.temperature >= 2F;
			structure = new JigsawPiece("crashed_vertibird", StructureManager.crashed_vertibird, -10);
			spawnWeight = 3 * 4;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("aircraft_carrier") {{
			canSpawn = oceanBiomes::contains;
			structure = new JigsawPiece("aircraft_carrier", StructureManager.aircraft_carrier, -6);
			maxHeight = 42;
			spawnWeight = 1;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("oil_rig") {{
			canSpawn = biome -> biome == BiomeGenBase.deepOcean;
			structure = new JigsawPiece("oil_rig", StructureManager.oil_rig, -20);
			maxHeight = 12;
			minHeight = 11;
			spawnWeight = 2;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("lighthouse") {{
			canSpawn = lighthouseBiomes::contains;
			structure = new JigsawPiece("lighthouse", StructureManager.lighthouse, -40);
			maxHeight = 29;
			minHeight = 28;
			spawnWeight = 2;
		}});

		NBTStructure.registerStructure(0, new SpawnCondition("beached_patrol") {{
			canSpawn = beachBiomes::contains;
			structure = new JigsawPiece("beached_patrol", StructureManager.beached_patrol, -5);
			minHeight = 58;
			maxHeight = 67;
			spawnWeight = 8;
		}});

		NBTStructure.registerNullWeight(0, 2, oceanBiomes::contains); //why the fuck did this change

		NBTStructure.registerStructure(0, new SpawnCondition("dish") {{
			canSpawn = biome -> biome == BiomeGenBase.plains;
			structure = new JigsawPiece("dish", StructureManager.dish, -10);
			minHeight = 53;
			maxHeight = 65;
			spawnWeight = 1;
		}});

		NBTStructure.registerNullWeight(0, 2, biome -> biome == BiomeGenBase.plains);
		NBTStructure.registerNullWeight(0, 2, oceanBiomes::contains);

		Map<Block, BlockSelector> bricks = new HashMap<Block, BlockSelector>() {{
			put(ModBlocks.meteor_brick, new MeteorBricks());
		}};
		Map<Block, BlockSelector> crates = new HashMap<Block, BlockSelector>() {{
			put(ModBlocks.meteor_brick, new MeteorBricks());
			put(ModBlocks.crate, new SupplyCrates());
			put(ModBlocks.meteor_spawner, new CrabSpawners());
		}};
		Map<Block, BlockSelector> ooze = new HashMap<Block, BlockSelector>() {{
			put(ModBlocks.meteor_brick, new MeteorBricks());
			put(ModBlocks.concrete_colored, new GreenOoze());
		}};

		NBTStructure.registerStructure(0, new SpawnCondition("meteor_dungeon") {{
			minHeight = 32;
			maxHeight = 32;
			sizeLimit = 128;
			canSpawn = biome -> biome.rootHeight >= 0;
			startPool = "start";
			pools = new HashMap<String, JigsawPool>() {{
				put("start", new JigsawPool() {{
					add(new JigsawPiece("meteor_core", StructureManager.meteor_core) {{ blockTable = bricks; }}, 1);
				}});
				put("spike", new JigsawPool() {{
					add(new JigsawPiece("meteor_spike", StructureManager.meteor_spike) {{ heightOffset = -3; conformToTerrain = true; }}, 1);
				}});
				put("default", new JigsawPool() {{
					add(new JigsawPiece("meteor_corner", StructureManager.meteor_corner) {{ blockTable = bricks; }}, 2);
					add(new JigsawPiece("meteor_t", StructureManager.meteor_t) {{ blockTable = bricks; }}, 3);
					add(new JigsawPiece("meteor_stairs", StructureManager.meteor_stairs) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_base_thru", StructureManager.meteor_room_base_thru) {{ blockTable = bricks; }}, 3);
					add(new JigsawPiece("meteor_room_base_end", StructureManager.meteor_room_base_end) {{ blockTable = bricks; }}, 4);
					fallback = "fallback";
				}});
				put("10room", new JigsawPool() {{
					add(new JigsawPiece("meteor_room_basic", StructureManager.meteor_room_basic) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_balcony", StructureManager.meteor_room_balcony) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_dragon", StructureManager.meteor_room_dragon) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_ladder", StructureManager.meteor_room_ladder) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_ooze", StructureManager.meteor_room_ooze) {{ blockTable = ooze; }}, 1);
					add(new JigsawPiece("meteor_room_split", StructureManager.meteor_room_split) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_stairs", StructureManager.meteor_room_stairs) {{ blockTable = bricks; }}, 1);
					add(new JigsawPiece("meteor_room_triple", StructureManager.meteor_room_triple) {{ blockTable = bricks; }}, 1);
					fallback = "roomback";
				}});
				put("3x3loot", new JigsawPool() {{
					add(new JigsawPiece("meteor_3_bale", StructureManager.meteor_3_bale), 1);
					add(new JigsawPiece("meteor_3_blank", StructureManager.meteor_3_blank), 1);
					add(new JigsawPiece("meteor_3_block", StructureManager.meteor_3_block), 1);
					add(new JigsawPiece("meteor_3_crab", StructureManager.meteor_3_crab), 1);
					add(new JigsawPiece("meteor_3_crab_tesla", StructureManager.meteor_3_crab_tesla), 1);
					add(new JigsawPiece("meteor_3_crate", StructureManager.meteor_3_crate), 1);
					add(new JigsawPiece("meteor_3_dirt", StructureManager.meteor_3_dirt), 1);
					add(new JigsawPiece("meteor_3_lead", StructureManager.meteor_3_lead), 1);
					add(new JigsawPiece("meteor_3_ooze", StructureManager.meteor_3_ooze), 1);
					add(new JigsawPiece("meteor_3_pillar", StructureManager.meteor_3_pillar), 1);
					add(new JigsawPiece("meteor_3_star", StructureManager.meteor_3_star), 1);
					add(new JigsawPiece("meteor_3_tesla", StructureManager.meteor_3_tesla), 1);
					add(new JigsawPiece("meteor_3_book", StructureManager.meteor_3_book), 1);
					add(new JigsawPiece("meteor_3_mku", StructureManager.meteor_3_mku), 1);
					add(new JigsawPiece("meteor_3_statue", StructureManager.meteor_3_statue), 1);
					add(new JigsawPiece("meteor_3_glow", StructureManager.meteor_3_glow), 1);
					fallback = "3x3loot"; // generate loot even if we're at the size limit
				}});
				put("headloot", new JigsawPool() {{
					add(new JigsawPiece("meteor_dragon_chest", StructureManager.meteor_dragon_chest) {{ blockTable = crates; }}, 1);
					add(new JigsawPiece("meteor_dragon_tesla", StructureManager.meteor_dragon_tesla) {{ blockTable = crates; }}, 1);
					add(new JigsawPiece("meteor_dragon_trap", StructureManager.meteor_dragon_trap) {{ blockTable = crates; }}, 1);
					add(new JigsawPiece("meteor_dragon_crate_crab", StructureManager.meteor_dragon_crate_crab) {{ blockTable = crates; }}, 1);
					fallback = "headback";
				}});
				put("fallback", new JigsawPool() {{
					add(new JigsawPiece("meteor_fallback", StructureManager.meteor_fallback) {{ blockTable = bricks; }}, 1);
				}});
				put("roomback", new JigsawPool() {{
					add(new JigsawPiece("meteor_room_fallback", StructureManager.meteor_room_fallback) {{ blockTable = bricks; }}, 1);
				}});
				put("headback", new JigsawPool() {{
					add(new JigsawPiece("meteor_loot_fallback", StructureManager.meteor_dragon_fallback) {{ blockTable = crates; }}, 1);
				}});
			}};
		}});
	}

	private NBTStructure.GenStructure nbtGen = new NBTStructure.GenStructure();

	private final Random rand = new Random(); //A central random, used to cleanly generate our stuff without affecting vanilla or modded seeds.

	/** Inits all MapGen upon the loading of a new world. Hopefully clears out structureMaps and structureData when a different world is loaded. */
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event) {
		nbtGen = (NBTStructure.GenStructure) TerrainGen.getModdedMapGen(new NBTStructure.GenStructure(), EventType.CUSTOM);

		hasPopulationEvent = false;
	}

	/** Called upon the initial population of a chunk. Called in the pre-population event first; called again if pre-population didn't occur (flatland) */
	private void setRandomSeed(World world, int chunkX, int chunkZ) {
		rand.setSeed(world.getSeed() + world.provider.dimensionId);
		final long i = rand.nextLong() / 2L * 2L + 1L;
		final long j = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long)chunkX * i + (long)chunkZ * j ^ world.getSeed());
	}

	/*
	 * Pre-population Events / Structure Generation
	 * Used to generate structures without unnecessary intrusion by biome decoration, like trees.
	 */

	private boolean hasPopulationEvent = false; // Does the given chunkGenerator have a population event? If not (flatlands), default to using generate.

	@SubscribeEvent
	public void generateStructures(PopulateChunkEvent.Pre event) {
		hasPopulationEvent = true;

		if(StructureConfig.enableStructures == 0) return;
		if(StructureConfig.enableStructures == 2 && !event.world.getWorldInfo().isMapFeaturesEnabled()) return;

		setRandomSeed(event.world, event.chunkX, event.chunkZ); //Set random for population down the line.

		nbtGen.generateStructures(event.world, rand, event.chunkProvider, event.chunkX, event.chunkZ);
	}

	/*
	 * Post-Vanilla / Modded Generation
	 * Used to generate features that don't care about intrusions (ores, craters, caves, etc.)
	 */

	@Override
	public void generate(Random unusedRandom, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(hasPopulationEvent) return; //If we've failed to generate any structures (flatlands)

		if(StructureConfig.enableStructures == 0) return;
		if(StructureConfig.enableStructures == 2 && !world.getWorldInfo().isMapFeaturesEnabled()) return;

		setRandomSeed(world, chunkX, chunkZ); //Reset the random seed to compensate

		nbtGen.generateStructures(world, rand, chunkProvider, chunkX, chunkZ);
	}

	public SpawnCondition getStructureAt(World world, int chunkX, int chunkZ) {
		if(StructureConfig.enableStructures == 0) return null;
		if(StructureConfig.enableStructures == 2 && !world.getWorldInfo().isMapFeaturesEnabled()) return null;

		setRandomSeed(world, chunkX, chunkZ);

		return nbtGen.getStructureAt(world, chunkX, chunkZ);
	}

}
