package com.hbm.world.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent.ReplaceBiomeBlocks;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

public class MapGenChainloader extends MapGenBase {

	/**
	 * There is no way - with existing forge hooks - to add entirely new `MapGenBase` generators to existing dimensions (overworld, nether).
	 * So, in order to fix that (while not breaking mods like Greg's Caves), we chainload our own MapGenBase into the existing cave generator
	 */

	private MapGenBase parent;

	private List<MapGenBase> generators = new ArrayList<>();

	// These may be added to before OR after mapgen registration safely
	private static List<MapGenBase> overworldGenerators = new ArrayList<>();
	private static List<MapGenBase> netherGenerators = new ArrayList<>();

	// Hack to provide the current generating chunk's block metas to the generation function
	private static byte[] blockMetas;

	// Double hack to check we actually get the damn metas, if not we will have to do a quick bit of post gen!
	private static HashMap<ChunkCoordIntPair, byte[]> blockMetaMap = new HashMap<>();

	// Executes our chainloaded parent, and all our child generators
	@Override
	public void func_151539_a(IChunkProvider chunk, World world, int chunkX, int chunkZ, Block[] blocks) {
		parent.func_151539_a(chunk, world, chunkX, chunkZ, blocks);

		// Some mods may use vanilla gen events for added dimensions, so we guard against that here
		if(world.provider.dimensionId != 0 && world.provider.dimensionId != -1) return;

		for(MapGenBase generator : generators) {
			if(generator instanceof MapGenBaseMeta) ((MapGenBaseMeta)generator).setMetas(blockMetas);
			generator.func_151539_a(chunk, world, chunkX, chunkZ, blocks);
		}
	}

	public static void register() {
		MapGenEventHandler handler = new MapGenEventHandler();
		MinecraftForge.TERRAIN_GEN_BUS.register(handler);
		MinecraftForge.EVENT_BUS.register(handler);
	}

	public static void addOverworldGenerator(MapGenBase generator) {
		if(overworldGenerators.contains(generator)) return;
		overworldGenerators.add(generator);
	}

	public static void addNetherGenerator(MapGenBase generator) {
		if(netherGenerators.contains(generator)) return;
		netherGenerators.add(generator);
	}

	public static void repairBadGeneration(World world, int chunkX, int chunkZ) {
		ChunkCoordIntPair coords = new ChunkCoordIntPair(chunkX, chunkZ);
		if(MapGenChainloader.blockMetaMap.containsKey(coords)) {
			byte[] metas = MapGenChainloader.blockMetaMap.get(coords);
			for(int i = 0; i < metas.length; i++) {
				if(metas[i] == 0) continue;
				int y = i % 256;
				int xz = (i - y) / 256;
				int z = xz % 16;
				int x = (xz - z) / 16;

				world.setBlockMetadataWithNotify(chunkX * 16 + x, y, chunkZ * 16 + z, metas[i], 3);
			}

			MapGenChainloader.blockMetaMap.remove(coords);
		}
	}

	public static class MapGenEventHandler {

		// Register as late as possible to pick up any modded cave generators
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void addMapGenChainloader(InitMapGenEvent event) {
			if(!(event.newGen instanceof MapGenChainloader)) {
				if(event.type == EventType.CAVE) {
					MapGenChainloader loader = new MapGenChainloader();
					loader.parent = event.newGen;
					loader.generators = overworldGenerators;
					event.newGen = loader;
				} else if(event.type == EventType.NETHER_CAVE) {
					MapGenChainloader loader = new MapGenChainloader();
					loader.parent = event.newGen;
					loader.generators = netherGenerators;
					event.newGen = loader;
				}
			}
		}

		@SubscribeEvent
		public void storeLatestBlockMeta(ReplaceBiomeBlocks event) {
			if(event.metaArray.length == 256) {
				// a mod is using a deprecated forge hook! fuck!
				// we will store our own meta array and apply metas _after_ chunk gen has finished
				blockMetas = new byte[65536];
				blockMetaMap.put(new ChunkCoordIntPair(event.chunkX, event.chunkZ), blockMetas);
			} else {
				blockMetas = event.metaArray;
			}
		}

	}

}
