package com.hbm.dim.eve;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;

import net.minecraft.world.World;

public class ChunkProviderEve extends ChunkProviderCelestial {

    public ChunkProviderEve(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);

        stoneBlock = ModBlocks.eve_rock;
        seaBlock = ModBlocks.mercury_block;

        declamp = false;
    }

}