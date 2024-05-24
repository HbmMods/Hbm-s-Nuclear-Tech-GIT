package com.hbm.dim.duna;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDuna extends WorldProviderCelestial {

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerDuna(worldObj);
	}

	@Override
	public String getDimensionName() {
		return "Duna";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderDuna(this.worldObj, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.duna_rock;
	}

}