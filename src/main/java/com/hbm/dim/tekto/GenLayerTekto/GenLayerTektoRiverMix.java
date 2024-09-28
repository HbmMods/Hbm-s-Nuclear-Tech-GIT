package com.hbm.dim.tekto.GenLayerTekto;

import com.hbm.dim.eve.biome.BiomeGenBaseEve;
import com.hbm.dim.tekto.biome.BiomeGenBaseTekto;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerTektoRiverMix extends GenLayerRiverMix {

	private GenLayer biomePatternGeneratorChain;
	private GenLayer riverPatternGeneratorChain;

	public GenLayerTektoRiverMix(long seed, GenLayer biomePatternGeneratorChain, GenLayer riverPatternGeneratorChain) {
		super(seed, biomePatternGeneratorChain, riverPatternGeneratorChain);

		this.biomePatternGeneratorChain = biomePatternGeneratorChain;
		this.riverPatternGeneratorChain = riverPatternGeneratorChain;
	}

	@Override
	public void initWorldGenSeed(long seed) {
		this.biomePatternGeneratorChain.initWorldGenSeed(seed);
		this.riverPatternGeneratorChain.initWorldGenSeed(seed);
		super.initWorldGenSeed(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int length) {
		int[] inputBiomeIds = this.biomePatternGeneratorChain.getInts(x, z, width, length);
		int[] riverBiomeIds = this.riverPatternGeneratorChain.getInts(x, z, width, length);
		int[] outputBiomeIds = IntCache.getIntCache(width * length);
		//what if instead of constantly copying like this we just set the ids in the constructor?????
		//later
		for(int i1 = 0; i1 < width * length; ++i1) {
			if(inputBiomeIds[i1] != BiomeGenBaseTekto.polyvinylPlains.biomeID) {
				if(riverBiomeIds[i1] == BiomeGenBase.river.biomeID) {
					if(BiomeGenBaseEve.getBiomeGenArray()[inputBiomeIds[i1]] != null) {
						outputBiomeIds[i1] = BiomeGenBaseTekto.tetrachloricRiver.biomeID;
					} else if(inputBiomeIds[i1] != BiomeGenBase.mushroomIsland.biomeID && inputBiomeIds[i1] != BiomeGenBase.mushroomIslandShore.biomeID) {
						outputBiomeIds[i1] = riverBiomeIds[i1] & 255;
					} else {
						outputBiomeIds[i1] = BiomeGenBase.mushroomIslandShore.biomeID;
					}
				} else {
					outputBiomeIds[i1] = inputBiomeIds[i1];
				}
			} else {
				outputBiomeIds[i1] = inputBiomeIds[i1];
			}
		}

		return outputBiomeIds;
	}
}