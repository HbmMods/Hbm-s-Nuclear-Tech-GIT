package com.hbm.dim.moon;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderMoon extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenMoon(SpaceConfig.moonBiome), dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Moon";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderMoon(this.worldObj, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.moon_rock;
	}

}
