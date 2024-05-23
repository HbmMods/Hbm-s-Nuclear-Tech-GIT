package com.hbm.dim.eve;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderEve extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerEve(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Eve";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderEve(this.worldObj, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.eve_rock;
	}

}