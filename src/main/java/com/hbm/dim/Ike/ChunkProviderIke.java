package com.hbm.dim.Ike;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderIke implements IChunkProvider {
	/** RNG. */
	private Random rand;
	private NoiseGeneratorOctaves octave1;
	private NoiseGeneratorOctaves octave2;
	private NoiseGeneratorOctaves octave3;
	private NoiseGeneratorPerlin octave4;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen5;
	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	/** Reference to the World object. */
	private World worldObj;
	/** are map structures going to be generated (e.g. strongholds) */
	private final boolean mapFeaturesEnabled;
	private WorldType field_147435_p;
	private final double[] field_147434_q;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];
	private MapGenBase caveGenerator = new MapGenCaves();
	/** Holds Stronghold Generator */
	private MapGenStronghold strongholdGenerator = new MapGenStronghold();
	/** Holds Village Generator */
	private MapGenVillage villageGenerator = new MapGenVillage();
	/** Holds Mineshaft Generator */
	private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
	private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
	private int seaLevel;
	protected Block fillblock;
	

//	private MapGenCrater craterGenerator;
	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;
	double[] field_147427_d; //seriously mojang, this shit is fucking unreadable
	double[] field_147428_e;
	double[] field_147425_f;
	double[] field_147426_g;
	int[][] field_73219_j = new int[32][32];
	private static final String __OBFID = "CL_00000396";

	{
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
		scatteredFeatureGenerator = (MapGenScatteredFeature) TerrainGen.getModdedMapGen(scatteredFeatureGenerator, SCATTERED_FEATURE);
		
	}

	public ChunkProviderIke(World IkeWorld, long il, boolean ib)
	{
		this.worldObj = IkeWorld;
		this.mapFeaturesEnabled = ib;
		this.field_147435_p = IkeWorld.getWorldInfo().getTerrainType();
		this.rand = new Random(il);
		this.octave1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.octave2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.octave3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.octave4 = new NoiseGeneratorPerlin(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
		this.field_147434_q = new double[825];
		this.parabolicField = new float[25];

		for (int j = -2; j <= 2; ++j)
		{
			for (int k = -2; k <= 2; ++k)
			{
				float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}
		
		//DimensionProperties dimProperties = DimensionManager.getInstance().getDimensionProperties(p_i2006_1_.provider.dimensionId);
		
		//seaLevel = 0;
		//oceanBlock = dimProperties.getOceanBlock();
		
		fillblock = ModBlocks.ike_stone;
		
		NoiseGenerator[] noiseGens = {octave1, octave2, octave3, octave4, noiseGen5, noiseGen6, mobSpawnerNoise};
		noiseGens = TerrainGen.getModdedNoiseGenerators(IkeWorld, this.rand, noiseGens);
		this.octave1 = (NoiseGeneratorOctaves)noiseGens[0];
		this.octave2 = (NoiseGeneratorOctaves)noiseGens[1];
		this.octave3 = (NoiseGeneratorOctaves)noiseGens[2];
		this.octave4 = (NoiseGeneratorPerlin)noiseGens[3];
		this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
		this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
		this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];

		//TODO: may break on little planets
		//float atmDensity = ((WorldProviderPlanet)worldObj.provider).getAtmosphereDensity(0,0);

		//if(atmDensity < 0.75f)
		//	craterGenerator = new MapGenCrater( (int)(10 + 26 ));
		//else 
		//	craterGenerator = null;		
	}

	public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_)
	{
		int b0 = seaLevel;
		//TODO: may break for little planets
		this.biomesForGeneration = ((WorldChunkManagerHell)this.worldObj.getWorldChunkManager()).getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
		this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);

		for (int k = 0; k < 4; ++k)
		{
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1)
			{
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2)
				{
					double d0 = 0.125D;
					double d1 = this.field_147434_q[k1 + k2];
					double d2 = this.field_147434_q[l1 + k2];
					double d3 = this.field_147434_q[i2 + k2];
					double d4 = this.field_147434_q[j2 + k2];
					double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3)
						{
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3)
							{
								if ((d15 += d16) > 0.0D)
								{
									p_147424_3_[j3 += short1] = fillblock;
								}
								else if (k2 * 8 + l2 < b0)
								{
									p_147424_3_[j3 += short1] = Blocks.air;
								}
								else
								{
									p_147424_3_[j3 += short1] = null;
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

	public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147422_1_, p_147422_2_, p_147422_3_, p_147422_4_, p_147422_5_, this.worldObj);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return;

		double d0 = 0.03125D;
		this.stoneNoise = this.octave4.func_151599_a(this.stoneNoise, (double)(p_147422_1_ * 16), (double)(p_147422_2_ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for (int k = 0; k < 16; ++k)
		{
			for (int l = 0; l < 16; ++l)
			{
				BiomeGenBase biomegenbase = p_147422_5_[l + k * 16];
				biomegenbase.genTerrainBlocks(this.worldObj, this.rand, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + k, p_147422_2_ * 16 + l, this.stoneNoise[l + k * 16]);
			}
		}
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		return this.provideChunk(p_73158_1_, p_73158_2_);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	public BlockMetacoupling getChunkPrimer(int p_73154_1_, int p_73154_2_)
	{
		BlockMetacoupling ablock = new BlockMetacoupling();
		this.func_147424_a(p_73154_1_, p_73154_2_, ablock.ablock);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		this.replaceBlocksForBiome(p_73154_1_, p_73154_2_, ablock.ablock, ablock.abyte, this.biomesForGeneration);
		this.caveGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, ablock.ablock);
		//this.ravineGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, ablock.ablock);

		//if(this.craterGenerator != null)
		//	this.craterGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, ablock.ablock);

		if (this.mapFeaturesEnabled)
		{
			//TODO: structures
		}
		
		return ablock;
	}
	
	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	@Override
	public Chunk provideChunk(int x, int z)
	{
		this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);

		BlockMetacoupling ablock = getChunkPrimer(x, z);
		//ChunkExtendedBiome
		Chunk chunk = new Chunk(this.worldObj, ablock.ablock, ablock.abyte, x, z);
		
		//TODO: convert back to int
		byte[] abyte1 = chunk.getBiomeArray();

		for (int k = 0; k < abyte1.length; ++k)
		{
			abyte1[k] = (byte) this.biomesForGeneration[k].biomeID;
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_)
	{
		this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0D, 200.0D, 0.5D);
		this.field_147427_d = this.octave3.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.field_147428_e = this.octave1.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		this.field_147425_f = this.octave2.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);

		int l = 0;
		int i1 = 0;

		for (int j1 = 0; j1 < 5; ++j1)
		{
			for (int k1 = 0; k1 < 5; ++k1)
			{
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for (int l1 = -b0; l1 <= b0; ++l1)
				{
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = biomegenbase1.rootHeight;
						float f4 = biomegenbase1.heightVariation;

						float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

						if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
						{
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
				double d12 = this.field_147426_g[i1] / 8000.0D;

				if (d12 < 0.0D)
				{
					d12 = -d12 * 0.3D;
				}

				d12 = d12 * 3.0D - 2.0D;

				if (d12 < 0.0D)
				{
					d12 /= 2.0D;

					if (d12 < -1.0D)
					{
						d12 = -1.0D;
					}

					d12 /= 1.4D;
					d12 /= 2.0D;
				}
				else
				{
					if (d12 > 1.0D)
					{
						d12 = 1.0D;
					}

					d12 /= 8.0D;
				}

				++i1;
				double d13 = (double)f1;
				double d14 = (double)f;
				d13 += d12 * 0.2D;
				d13 = d13 * 8.5D / 8.0D;
				double d5 = 8.5D + d13 * 4.0D;

				for (int j2 = 0; j2 < 33; ++j2)
				{
					double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if (d6 < 0.0D)
					{
						d6 *= 4.0D;
					}

					double d7 = this.field_147428_e[l] / 512.0D;
					double d8 = this.field_147425_f[l] / 512.0D;
					double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
					double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

					if (j2 > 29)
					{
						double d11 = (double)((float)(j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					this.field_147434_q[l] = d10;
					++l;
				}
			}
		}
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		BlockFalling.fallInstantly = true;
		int k = p_73153_2_ * 16;
		int l = p_73153_3_ * 16;
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long i1 = this.rand.nextLong() / 2L * 2L + 1L;
		long j1 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)p_73153_2_ * i1 + (long)p_73153_3_ * j1 ^ this.worldObj.getSeed());
		boolean flag = false;

		//TODO: add registry for planets
		/*boolean populationFlag = ((WorldProviderMoon)worldObj.provider).getDimensionProperties(k, l).hasRivers();
		
		if(populationFlag )
			MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag));

		if (this.mapFeaturesEnabled)
		{
			//TODO: maybe features
		}*/


		biomegenbase.decorate(this.worldObj, this.rand, k, l);
		/*if (TerrainGen.populate(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag, ANIMALS))
		{
			SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, k + 8, l + 8, 16, 16, this.rand);
		}*/
		k += 8;
		l += 8;

		//If a planet is terraformed chenge upper blocks
		/*if(zmaster587.advancedRocketry.api.Configuration.allowTerraforming && worldObj.provider.getClass() == WorldProviderPlanet.class) {

			if(DimensionManager.getInstance().getDimensionProperties(worldObj.provider.dimensionId).isTerraformed()) {
				Chunk chunk = worldObj.getChunkFromChunkCoords(p_73153_2_, p_73153_3_);
				PlanetEventHandler.modifyChunk(worldObj, (WorldProviderPlanet)worldObj.provider, chunk);
			}
		}
		
		if (populationFlag)
			MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(p_73153_1_, worldObj, rand, p_73153_2_, p_73153_3_, flag));

		OreGenProperties oreGenProperties = DimensionManager.getInstance().getDimensionProperties(this.worldObj.provider.dimensionId).getOreGenProperties(this.worldObj);
		
		if(oreGenProperties != null) {
			for(OreEntry entry : oreGenProperties.getOreEntries()) {
				new CustomizableOreGen(entry).generate(rand, p_73153_2_, p_73153_3_, this.worldObj, this, this.worldObj.getChunkProvider());
			}
		}
		*/
		BlockFalling.fallInstantly = false;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}

	/**
	 * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
	 * unimplemented.
	 */
	public void saveExtraData() {}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
	 */
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave()
	{
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	public String makeString()
	{
		return "RandomLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	@Override
	public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
    {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return p_73155_1_ == EnumCreatureType.monster && this.scatteredFeatureGenerator.func_143030_a(p_73155_2_, p_73155_3_, p_73155_4_) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() : biomegenbase.getSpawnableList(p_73155_1_);
    }

	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
	{
		return null;
	}

	public int getLoadedChunkCount()
	{
		return 0;
	}

	public void recreateStructures(int p_82695_1_, int p_82695_2_)
	{
		if (this.mapFeaturesEnabled)
		{

		}
	}
	
	public static class BlockMetacoupling
	{
		public Block[] ablock = new Block[65536];
		public byte[] abyte = new byte[65536];
	}

}
