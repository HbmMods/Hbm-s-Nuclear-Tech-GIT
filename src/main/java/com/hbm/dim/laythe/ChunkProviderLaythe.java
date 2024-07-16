package com.hbm.dim.laythe;

import com.hbm.dim.ChunkProviderCelestial;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ChunkProviderLaythe extends ChunkProviderCelestial {

    public ChunkProviderLaythe(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);
        declamp = false;

        seaBlock = Blocks.water;
    }

}