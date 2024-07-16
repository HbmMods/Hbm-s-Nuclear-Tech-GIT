package com.hbm.dim.duna;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;

import net.minecraft.world.World;
public class ChunkProviderDuna extends ChunkProviderCelestial {

    public ChunkProviderDuna(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);
		stoneBlock = ModBlocks.duna_rock;
    }

}