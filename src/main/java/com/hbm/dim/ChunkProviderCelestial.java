package com.hbm.dim;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.TerrainGen;

public abstract class ChunkProviderCelestial implements IChunkProvider {

	/**
	 * This class creates chunks, filling them with blocks via various noise funcs
	 * The variables directly below modify the generation parameters
	 */

	// Default block fills
	protected Block stoneBlock;
	protected Block seaBlock; // Doesn't have to be liquid, if you want like, basalt seas
	protected int seaLevel;

	// Noise frequency
	protected Vec3 firstOrderFreq = Vec3.createVectorHelper(684.412D, 684.412D, 684.412D);
	protected Vec3 secondOrderFreq = Vec3.createVectorHelper(684.412D, 684.412D, 684.412D);
	protected Vec3 thirdOrderFreq = Vec3.createVectorHelper(8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
	protected Vec3 heightOrderFreq = Vec3.createVectorHelper(200.0D, 200.0D, 0.5D);

	// Embiggenify
	protected boolean amplified = false;
	
	//round
	protected boolean reclamp = true;
	

	// Now for the regular stuff, changing these won't change gen, just break things
	protected World worldObj;
	protected final boolean mapFeaturesEnabled;
	protected Random rand;
	
	// Generation buffers and the like, no need to modify or have visibility to these
	private NoiseGeneratorOctaves firstOrder;
	private NoiseGeneratorOctaves secondOrder;
	private NoiseGeneratorOctaves thirdOrder;
	private NoiseGeneratorPerlin perlin;
	private NoiseGeneratorOctaves heightOrder;

	protected BiomeGenBase[] biomesForGeneration;
	private final double[] terrainBuffer;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];

	private double[] firstOrderBuffer;
	private double[] secondOrderBuffer;
	private double[] thirdOrderBuffer;
	private double[] heightOrderBuffer;

	public ChunkProviderCelestial(World world, long seed, boolean hasMapFeatures) {
		this.worldObj = world;
		this.mapFeaturesEnabled = hasMapFeatures;

		this.rand = new Random(seed);
		this.firstOrder = new NoiseGeneratorOctaves(this.rand, 16);
		this.secondOrder = new NoiseGeneratorOctaves(this.rand, 16);
		this.thirdOrder = new NoiseGeneratorOctaves(this.rand, 8);
		this.perlin = new NoiseGeneratorPerlin(this.rand, 4);
		this.heightOrder = new NoiseGeneratorOctaves(this.rand, 16);

		this.terrainBuffer = new double[825];
		this.parabolicField = new float[25];

		for(int j = -2; j <= 2; ++j) {
			for(int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt_float((float) (j * j + k * k) + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}

		stoneBlock = Blocks.stone;
		seaBlock = Blocks.air;
		seaLevel = 63;

		NoiseGenerator[] noiseGens = { firstOrder, secondOrder, thirdOrder, perlin, heightOrder };
		noiseGens = TerrainGen.getModdedNoiseGenerators(world, this.rand, noiseGens);
		this.firstOrder = (NoiseGeneratorOctaves) noiseGens[0];
		this.secondOrder = (NoiseGeneratorOctaves) noiseGens[1];
		this.thirdOrder = (NoiseGeneratorOctaves) noiseGens[2];
		this.perlin = (NoiseGeneratorPerlin) noiseGens[3];
		this.heightOrder = (NoiseGeneratorOctaves) noiseGens[4];
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	protected BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = new BlockMetaBuffer();
		generateBlocks(x, z, buffer.blocks);
		biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x * 16, z * 16, 16, 16);
		replaceBlocksForBiome(x, z, buffer.blocks, buffer.metas, biomesForGeneration);
		return buffer;
	}

	// Fills in the chunk with stone, water, and air
	protected void generateBlocks(int x, int z, Block[] blocks) {
		biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		generateNoiseField(x * 4, 0, z * 4);

		for(int k = 0; k < 4; ++k) {
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for(int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for(int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = terrainBuffer[k1 + k2];
					double d2 = terrainBuffer[l1 + k2];
					double d3 = terrainBuffer[i2 + k2];
					double d4 = terrainBuffer[j2 + k2];
					double d5 = (terrainBuffer[k1 + k2 + 1] - d1) * d0;
					double d6 = (terrainBuffer[l1 + k2 + 1] - d2) * d0;
					double d7 = (terrainBuffer[i2 + k2 + 1] - d3) * d0;
					double d8 = (terrainBuffer[j2 + k2 + 1] - d4) * d0;

					for(int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for(int i3 = 0; i3 < 4; ++i3) {
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for(int k3 = 0; k3 < 4; ++k3) {
								if((d15 += d16) > 0.0D) {
									blocks[j3 += short1] = stoneBlock;
								} else if(k2 * 8 + l2 < seaLevel) {
									blocks[j3 += short1] = seaBlock;
								} else {
									blocks[j3 += short1] = Blocks.air;
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	// Fills up a buffer with "chances for this block to be stone" using 3D noise and biome specific information
	protected void generateNoiseField(int x, int y, int z) {
		firstOrderBuffer = firstOrder.generateNoiseOctaves(firstOrderBuffer, x, y, z, 5, 33, 5, firstOrderFreq.xCoord, firstOrderFreq.yCoord, firstOrderFreq.zCoord);
		secondOrderBuffer = secondOrder.generateNoiseOctaves(secondOrderBuffer, x, y, z, 5, 33, 5, secondOrderFreq.xCoord, secondOrderFreq.yCoord, secondOrderFreq.zCoord);
		thirdOrderBuffer = thirdOrder.generateNoiseOctaves(thirdOrderBuffer, x, y, z, 5, 33, 5, thirdOrderFreq.xCoord, thirdOrderFreq.yCoord, thirdOrderFreq.zCoord);
		heightOrderBuffer = heightOrder.generateNoiseOctaves(heightOrderBuffer, x, z, 5, 5, heightOrderFreq.xCoord, heightOrderFreq.yCoord, heightOrderFreq.zCoord);

		int l = 0;
		int i1 = 0;

		for(int j1 = 0; j1 < 5; ++j1) {
			for(int k1 = 0; k1 < 5; ++k1) {
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for(int l1 = -b0; l1 <= b0; ++l1) {
					for(int i2 = -b0; i2 <= b0; ++i2) {
						BiomeGenBase biomegenbase1 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = biomegenbase1.rootHeight;
						float f4 = biomegenbase1.heightVariation;

                        if(amplified && f3 > 0.0F) {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

						float f5 = parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

						if(biomegenbase1.rootHeight > biomegenbase.rootHeight) {
							f5 /= 2.0F;
						}

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d12 = heightOrderBuffer[i1] / 8000.0D;

				if(d12 < 0.0D) {
					d12 = -d12 * 0.3D;
				}

				d12 = d12 * 3.0D - 2.0D;

				if(d12 < 0.0D) {
					d12 /= 2.0D;

					if(d12 < -1.0D) {
						d12 = -1.0D;
					}

					d12 /= 1.4D;
					d12 /= 2.0D;
				} else {
					if(d12 > 1.0D) {
						d12 = 1.0D;
					}

					d12 /= 8.0D;
				}

				++i1;
				double d13 = (double) f1;
				double d14 = (double) f;
				d13 += d12 * 0.2D;
				d13 = d13 * 8.5D / 8.0D;
				double d5 = 8.5D + d13 * 4.0D;

				for(int j2 = 0; j2 < 33; ++j2) {
					double d6 = ((double) j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if(d6 < 0.0D) {
						d6 *= 4.0D;
					}

					double d7 = firstOrderBuffer[l] / 512.0D; 
					double d8 = secondOrderBuffer[l] / 512.0D;
					double d9 = (thirdOrderBuffer[l] / 10.0D + 1.0D) / 2.0D;
					//srry, there has to be a better way to smooth out things, we got the perlin tools to do so but i have no idea how to invoke those tools here.
					//maybe sometime soon...?
					double d10 = reclamp ? MathHelper.denormalizeClamp(d7, d8, d9) - d6 : d8 - d6;
					if(j2 > 29) {
						double d11 = (double) ((float) (j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					terrainBuffer[l] = d10;
					++l;
				}
			}
		}
	}

	protected void replaceBlocksForBiome(int x, int z, Block[] blocks, byte[] metas, BiomeGenBase[] biomes) {
		double d0 = 0.03125D;
		stoneNoise = perlin.func_151599_a(stoneNoise, (double) (x * 16), (double) (z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for(int k = 0; k < 16; ++k) {
			for(int l = 0; l < 16; ++l) {
				BiomeGenBase biomegenbase = biomes[l + k * 16];
				biomegenbase.genTerrainBlocks(worldObj, rand, blocks, metas, x * 16 + k, z * 16 + l, stoneNoise[l + k * 16]);
			}
		}
	}

	/**
	 * Checks to see if a chunk exists at x, z
	 */
	@Override
	public boolean chunkExists(int x, int z) {
		return true;
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);

		BlockMetaBuffer ablock = getChunkPrimer(x, z);
		Chunk chunk = new Chunk(worldObj, ablock.blocks, ablock.metas, x, z);

		byte[] abyte1 = chunk.getBiomeArray();

		for(int k = 0; k < abyte1.length; ++k) {
			abyte1[k] = (byte) biomesForGeneration[k].biomeID;
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	@Override
	public Chunk loadChunk(int x, int z) {
		return provideChunk(x, z);
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(IChunkProvider provider, int x, int z) {
		BlockFalling.fallInstantly = true;

		int k = x * 16;
		int l = z * 16;
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(k + 16, l + 16);
		rand.setSeed(worldObj.getSeed());
		long i1 = rand.nextLong() / 2L * 2L + 1L;
		long j1 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long) x * i1 + (long) z * j1 ^ worldObj.getSeed());

		biomegenbase.decorate(worldObj, rand, k, l);

		BlockFalling.fallInstantly = false;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If passed
	 * false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	@Override
	public boolean saveChunks(boolean combined, IProgressUpdate progress) {
		return true;
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to
	 * unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "RandomLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given
	 * location.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(x, z);
        return biomegenbase.getSpawnableList(creatureType);
	}

	/**
	 * I have no fucking clue, just return null
	 */
	@Override
	public ChunkPosition func_147416_a(World world, String shitfuck, int x, int y, int z) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int x, int z) {

	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave,
	 * only during world unload. Currently
	 * unimplemented.
	 */
	@Override
	public void saveExtraData() {}

	public static class BlockMetaBuffer {
		public Block[] blocks = new Block[65536];
		public byte[] metas = new byte[65536];
	}

}
