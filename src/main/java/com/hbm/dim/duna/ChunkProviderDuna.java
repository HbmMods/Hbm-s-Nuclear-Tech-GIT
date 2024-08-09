package com.hbm.dim.duna;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.duna.biome.BiomeGenBaseDuna;
import com.hbm.dim.mapgen.ExperimentalCaveGenerator;

import net.minecraft.world.World;
public class ChunkProviderDuna extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator(2, 40, 8.0F);

    public ChunkProviderDuna(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);
		stoneBlock = ModBlocks.duna_rock;

        caveGenV2.lavaBlock = ModBlocks.basalt;
        caveGenV2.stoneBlock = ModBlocks.duna_rock;
    }

    @Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

        if(biomesForGeneration[0] == BiomeGenBaseDuna.dunaLowlands) {
            // BEEG CAVES UNDER THE CANYONS
            this.caveGenV2.func_151539_a(this, worldObj, x, z, buffer.blocks);
        }
		
		return buffer;
	}

}