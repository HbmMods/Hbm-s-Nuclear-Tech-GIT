package com.hbm.world.feature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class OreLayer3D {

	public static int counter = 0;
	public int id;

	long lastSeed;
	NoiseGeneratorPerlin noiseX;
	NoiseGeneratorPerlin noiseY;
	NoiseGeneratorPerlin noiseZ;
	double[][] cacheX;
	double[][] cacheZ;

	double scaleH;
	double scaleV;
	double threshold;

	Block block;
	int meta;
	int dim = 0;

	Map<Integer, Set<ChunkCoordIntPair>> alreadyDecorated = new HashMap<>();

	public OreLayer3D(Block block, int meta) {
		this.block = block;
		this.meta = meta;
		MinecraftForge.EVENT_BUS.register(this);
		this.id = counter;
		counter++;
	}

	public OreLayer3D setDimension(int dim) {
		this.dim = dim;
		return this;
	}

	public OreLayer3D setScaleH(double scale) {
		this.scaleH = scale;
		return this;
	}

	public OreLayer3D setScaleV(double scale) {
		this.scaleV = scale;
		return this;
	}

	public OreLayer3D setThreshold(double threshold) {
		this.threshold = threshold;
		return this;
	}

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {
		World world = event.world;
		int cx = event.chunkX;
		int cz = event.chunkZ;

		if(world.provider == null || world.provider.dimensionId != this.dim) return;

		ChunkCoordIntPair chunkPos = new ChunkCoordIntPair(cx, cz);
		Set<ChunkCoordIntPair> decoratedChunks = alreadyDecorated.computeIfAbsent(world.provider.dimensionId, n -> new HashSet<>());

		// Stop early if we've already generated this chunk in this dimension
		if(decoratedChunks.contains(chunkPos)) return;
		decoratedChunks.add(chunkPos);

		// refresh caches on first run and if the world seed changes
		if(this.noiseX == null || world.getSeed() != lastSeed) {
			this.noiseX = new NoiseGeneratorPerlin(new Random(world.getSeed() + 101 + id), 4);
			this.noiseY = new NoiseGeneratorPerlin(new Random(world.getSeed() + 102 + id), 4);
			this.noiseZ = new NoiseGeneratorPerlin(new Random(world.getSeed() + 103 + id), 4);
			cacheX = new double[16][65];
			cacheZ = new double[16][65];
			lastSeed = world.getSeed();
		}

		for(int o = 0; o < 16; o++) {
			for(int y = 64; y > 5; y--) {
				cacheX[o][y] = this.noiseX.func_151601_a(y * scaleV, (cz + 8 + o) * scaleH);
				cacheZ[o][y] = this.noiseZ.func_151601_a((cx + 8 + o) * scaleH, y * scaleV);
			}
		}

		for(int ox = 0; ox < 16; ox++) {
			int x = cx + 8 + ox;

			for(int oz = 0; oz < 16; oz++) {
				int z = cz + 8 + oz;

				double ny = this.noiseY.func_151601_a(x * scaleH, z * scaleH);

				for(int y = 64; y > 5; y--) {
					double nx = cacheX[oz][y];
					double nz = cacheX[ox][y];

					if(nx * ny * nz > threshold) {
						Block target = world.getBlock(x, y, z);

						if(target.isNormalCube() && target.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
							world.setBlock(x, y, z, block, meta, 2);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		alreadyDecorated.put(event.world.provider.dimensionId, new HashSet<>());
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		alreadyDecorated.remove(event.world.provider.dimensionId);
	}

}
