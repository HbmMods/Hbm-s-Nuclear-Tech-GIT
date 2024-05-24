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

}