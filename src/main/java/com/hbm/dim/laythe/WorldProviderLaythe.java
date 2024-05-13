package com.hbm.dim.laythe;

import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderLaythe extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerLaythe(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Laythe";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderLaythe(this.worldObj, this.getSeed(), false);
	}

}