package com.hbm.dim.dres;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDres extends WorldProviderCelestial {
	
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerDres(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Dres";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderDres(this.worldObj, this.getSeed(), false);
	}
	// sorry mellow...
	@Override
	public Block getStone() {
		return ModBlocks.dres_rock;
	}

}