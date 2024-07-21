package com.hbm.dim.eve;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.ChunkProviderCelestial.BlockMetaBuffer;
import com.hbm.dim.moho.MapgenRavineButBased;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

public class ChunkProviderEve extends ChunkProviderCelestial {

	
    public ChunkProviderEve(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);

        stoneBlock = ModBlocks.eve_rock;
        seaBlock = ModBlocks.mercury_block;

        declamp = false;
    }
	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		// how many times do I gotta say BEEEEG
		return buffer;
	}
}