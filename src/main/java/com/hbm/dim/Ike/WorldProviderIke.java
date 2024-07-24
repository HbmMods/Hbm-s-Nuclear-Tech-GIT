package com.hbm.dim.Ike;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderIke extends WorldProviderCelestial {
	
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenIke(SpaceConfig.ikeBiome), dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Ike";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderIke(this.worldObj, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.ike_stone;
	}

	@Override
	public boolean updateLightmap(int[] lightmap) {
		for(int i = 0; i < 256; i++) {
			float sun = getSunBrightness(1.0F) - 0.1F;
			float sky = lightBrightnessTable[i / 16];
			float duna = Math.max(sky - sun, 0);

			int[] color = unpackColor(lightmap[i]);

			color[0] += duna * 20;
			if(color[0] > 255) color[0] = 255;

			lightmap[i] = packColor(color);
		}
		return true;
	}

}