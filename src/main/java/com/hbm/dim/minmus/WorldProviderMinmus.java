package com.hbm.dim.minmus;

import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderMinmus extends WorldProviderCelestial {
	
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerMinmus(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Minmus";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderMinmus(this.worldObj, this.getSeed(), false);
	}

}