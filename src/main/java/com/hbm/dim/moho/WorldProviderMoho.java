package com.hbm.dim.moho;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderMoho extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenMoho(SpaceConfig.mohoBiome), dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Moho";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderMoho(this.worldObj, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.moho_stone;
	}

}