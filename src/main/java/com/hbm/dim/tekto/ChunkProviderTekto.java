package com.hbm.dim.tekto;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;

import net.minecraft.world.World;


public class ChunkProviderTekto extends ChunkProviderCelestial {
	
	public ChunkProviderTekto(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		reclamp = false;
		stoneBlock = ModBlocks.basalt;
		seaBlock = ModBlocks.sulfuric_acid_block;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		// how many times do I gotta say BEEEEG
		return buffer;
	}
}