package com.hbm.dim.moon;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.ExperimentalCaveGenerator;
import com.hbm.dim.noise.MapGenGreg;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderMoon extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator();
	private MapGenGreg caveGenV3 = new MapGenGreg();

	public ChunkProviderMoon(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenV2 = (ExperimentalCaveGenerator) TerrainGen.getModdedMapGen(caveGenV2, CUSTOM);
		caveGenV3 = (MapGenGreg) TerrainGen.getModdedMapGen(caveGenV3, CAVE);

		stoneBlock = ModBlocks.moon_rock;
		seaBlock = ModBlocks.basalt;
		seaLevel = 64;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		// BEEG CAVES
		// this.caveGenV2.func_151539_a(this, worldObj, x, z, buffer.blocks);
		
		// NEW CAVES
		this.caveGenV3.func_151539_a(this, worldObj, x, z, buffer.blocks);
		return buffer;
	}

}
