package com.hbm.dim;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerCelestial extends WorldChunkManager {
	
	private GenLayer biomeLayer;
	private GenLayer biomeDetailLayer;

	private BiomeCache biomeCache;
	
	public WorldChunkManagerCelestial(BiomeGenLayers layers) {
		biomeCache = new BiomeCache(this);
		this.biomeLayer = layers.biomeLayer;
		this.biomeDetailLayer = layers.biomeDetailLayer;
	}
    
    // getBiomesToSpawnIn is never called for celestial bodies, so fuck that noise lmao

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int z) {
		return biomeCache.getBiomeGenAt(x, z);
	}

	@Override
	public float[] getRainfall(float[] values, int x, int z, int width, int length) {
		IntCache.resetIntCache();

        if(values == null || values.length < width * length) {
            values = new float[width * length];
        }

        int[] biomeIds = biomeDetailLayer.getInts(x, z, width, length);

        for(int i = 0; i < width * length; ++i) {
			float f = (float)BiomeGenBase.getBiome(biomeIds[i]).getIntRainfall() / 65536.0F;

			if(f > 1.0F) f = 1.0F;

			values[i] = f;
        }

        return values;
	}

	@SideOnly(Side.CLIENT)
	@Override
    public float getTemperatureAtHeight(float temperature, int y) {
        return temperature;
    }

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int length) {
        IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * length) {
            biomes = new BiomeGenBase[width * length];
        }

        int[] biomeIds = biomeLayer.getInts(x, z, width, length);

		for(int i = 0; i < width * length; ++i) {
			biomes[i] = BiomeGenBase.getBiome(biomeIds[i]);
		}

		return biomes;
    }

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int x, int z, int width, int length, boolean flag) {
		IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * length) {
            biomes = new BiomeGenBase[width * length];
        }

        if(flag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
            BiomeGenBase[] cachedBiomes = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(cachedBiomes, 0, biomes, 0, width * length);
            return biomes;
        } else {
            int[] biomeIds = biomeDetailLayer.getInts(x, z, width, length);

            for(int i = 0; i < width * length; ++i) {
                biomes[i] = BiomeGenBase.getBiome(biomeIds[i]);
            }

            return biomes;
        }
	}

	// Used by structures, generally, to find if a given chunk contains any of the specified biomes
	@SuppressWarnings("rawtypes")
	@Override
	public boolean areBiomesViable(int x, int z, int size, List biomes) {
        IntCache.resetIntCache();

        int l = x - size >> 2;
        int i1 = z - size >> 2;
        int j1 = x + size >> 2;
        int k1 = z + size >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.biomeLayer.getInts(l, i1, l1, i2);

		for(int j2 = 0; j2 < l1 * i2; ++j2) {
			BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(aint[j2]);

			if(!biomes.contains(biomeGenBase)) {
				return false;
			}
		}

		return true;
    }

	// Finds a chunk in a given area that has one of the specified biomes
	@SuppressWarnings("rawtypes")
	@Override
    public ChunkPosition findBiomePosition(int x, int z, int size, List biomes, Random rand) {
        IntCache.resetIntCache();

        int l = x - size >> 2;
        int i1 = z - size >> 2;
        int j1 = x + size >> 2;
        int k1 = z + size >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.biomeLayer.getInts(l, i1, l1, i2);
        ChunkPosition chunkposition = null;
        int j2 = 0;

        for(int k2 = 0; k2 < l1 * i2; ++k2) {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k2]);

            if(biomes.contains(biomegenbase) && (chunkposition == null || rand.nextInt(j2 + 1) == 0)) {
                chunkposition = new ChunkPosition(l2, 0, i3);
                ++j2;
            }
        }

        return chunkposition;
    }

	public void cleanupCache() {
        biomeCache.cleanupCache();
    }

    public static class BiomeGenLayers {

        private GenLayer biomeLayer;
        private GenLayer biomeDetailLayer;

        public BiomeGenLayers(GenLayer biomeLayer, GenLayer biomeDetailLayer, long seed) {
            this.biomeLayer = biomeLayer;
            this.biomeDetailLayer = biomeDetailLayer;

            biomeLayer.initWorldGenSeed(seed);
            biomeDetailLayer.initWorldGenSeed(seed);
        }

    }

}
