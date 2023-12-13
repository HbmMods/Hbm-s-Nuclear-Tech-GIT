/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.eve;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.dim.eve.GenLayerEve.GenLayerEve;
import com.hbm.dim.eve.biome.BiomeGenBaseEve;

//import com.hbm.dim.duna.GenLayerDuna.GenLayerDuna;
//import com.hbm.dim.duna.biome.BiomeGenBaseDuna;

//import stevekung.mods.moreplanets.planets.fronos.worldgen.biome.BiomeGenBaseFronos;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerEve extends WorldChunkManager
{
	private GenLayer unzoomedBiomes;
	private GenLayer zoomedBiomes;
	private BiomeCache myBiomeCache;
	private List<BiomeGenBase> myBiomesToSpawnIn;

	protected WorldChunkManagerEve()
	{
		this.myBiomeCache = new BiomeCache(this);
		this.myBiomesToSpawnIn = new ArrayList();
		this.myBiomesToSpawnIn.add(BiomeGenBaseEve.evePlains);
		this.myBiomesToSpawnIn.add(BiomeGenBaseEve.eveOcean);
		this.myBiomesToSpawnIn.add(BiomeGenBaseEve.eveMountains);
		this.myBiomesToSpawnIn.add(BiomeGenBaseEve.SeismicPlains);

	}

	public WorldChunkManagerEve(long seed, WorldType type)
	{
		this();
		GenLayer[] genLayers = GenLayerEve.makeTheWorld(seed);
		this.unzoomedBiomes = genLayers[0];
		this.zoomedBiomes = genLayers[1];
	}

	public WorldChunkManagerEve(World world)
	{
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
	}

	@Override
	public List getBiomesToSpawnIn()
	{
		return this.myBiomesToSpawnIn;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int par1, int par2)
	{
		BiomeGenBase biome = this.myBiomeCache.getBiomeGenAt(par1, par2);

		if (biome == null)
		{
			return BiomeGenBaseEve.evePlains;
		}
		return biome;
	}

	@Override
	public float[] getRainfall(float[] par1, int x, int z, int width, int depth)
	{
		IntCache.resetIntCache();
		int[] aint = this.zoomedBiomes.getInts(x, z, width, depth);

		if (par1 == null || par1.length < width * depth)
		{
			par1 = new float[width * depth];
		}
		for (int i1 = 0; i1 < width * depth; ++i1)
		{
			float f = BiomeGenBase.getBiome(aint[i1]).getIntRainfall() / 65536.0F;

			if (f > 1.0F)
			{
				f = 1.0F;
			}
			par1[i1] = f;
		}
		return par1;
	}

	@Override
	public float getTemperatureAtHeight(float par1, int par2)
	{
		return par1;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int x, int z, int length, int width)
	{
		int[] arrayOfInts = this.unzoomedBiomes.getInts(x, z, length, width);

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < length * width)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[length * width];
		}
		for (int i = 0; i < length * width; i++)
		{
			if (arrayOfInts[i] >= 0)
			{
				par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(arrayOfInts[i]);
			}
			else
			{
				par1ArrayOfBiomeGenBase[i] = BiomeGenBaseEve.evePlains;
			}
		}
		return par1ArrayOfBiomeGenBase;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int x, int y, int width, int length, boolean cacheFlag)
	{
		IntCache.resetIntCache();
		
		int[] ai = this.zoomedBiomes.getInts(x, y, width, length);

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < width * length)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[width * length];
		}
		if (cacheFlag && width == 16 && length == 16 && (x & 0xF) == 0 && (y & 0xF) == 0)
		{
			BiomeGenBase[] abiomegenbase = this.myBiomeCache.getCachedBiomes(x, y);
			System.arraycopy(abiomegenbase, 0, par1ArrayOfBiomeGenBase, 0, width * length);
			return par1ArrayOfBiomeGenBase;
		}
		for (int i = 0; i < width * length; i++)
		{
			if (ai[i] >= 0)
			{
				par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(ai[i]);
			}
			else
			{
				par1ArrayOfBiomeGenBase[i] = BiomeGenBaseEve.evePlains;
			}
		}
		return par1ArrayOfBiomeGenBase;
	}

	@Override
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
	{
		int i = par1 - par3 >> 2;
		int j = par2 - par3 >> 2;
		int k = par1 + par3 >> 2;
		int l = par2 + par3 >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;
		int[] ai = this.unzoomedBiomes.getInts(i, j, i1, j1);

		for (int k1 = 0; k1 < i1 * j1; k1++)
		{
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(ai[k1]);

			if (!par4List.contains(biomegenbase))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
	{
		int i = par1 - par3 >> 2;
		int j = par2 - par3 >> 2;
		int k = par1 + par3 >> 2;
		int l = par2 + par3 >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;
		int[] ai = this.unzoomedBiomes.getInts(i, j, i1, j1);
		ChunkPosition chunkposition = null;
		int k1 = 0;

		for (int l1 = 0; l1 < ai.length; l1++)
		{
			int i2 = i + l1 % i1 << 2;
			int j2 = j + l1 / i1 << 2;
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(ai[l1]);

			if (par4List.contains(biomegenbase) && (chunkposition == null || par5Random.nextInt(k1 + 1) == 0))
			{
				chunkposition = new ChunkPosition(i2, 0, j2);
				k1++;
			}
		}
		return chunkposition;
	}

	@Override
	public void cleanupCache()
	{
		this.myBiomeCache.cleanupCache();
	}

}