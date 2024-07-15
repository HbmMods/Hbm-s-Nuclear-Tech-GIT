package com.hbm.dim.moon;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.ExperimentalCaveGenerator;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderMoon extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator();

	public ChunkProviderMoon(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenV2 = (ExperimentalCaveGenerator) TerrainGen.getModdedMapGen(caveGenV2, CUSTOM);	
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		// BEEG CAVES
		this.caveGenV2.func_151539_a(this, worldObj, x, z, buffer.blocks);
		
		return buffer;
	}

}
