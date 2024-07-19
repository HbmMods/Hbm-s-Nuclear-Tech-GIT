package com.hbm.dim.moon;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.moho.MapgenRavineButBased;
import com.hbm.dim.noise.MapGenGreg;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderMoon extends ChunkProviderCelestial {

	private MapGenGreg caveGenV3 = new MapGenGreg();
	private MapgenRavineButBased rgen = new MapgenRavineButBased();

	public ChunkProviderMoon(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenV3 = (MapGenGreg) TerrainGen.getModdedMapGen(caveGenV3, CAVE);
		caveGenV3.stoneBlock = ModBlocks.moon_rock;
		rgen = (MapgenRavineButBased) TerrainGen.getModdedMapGen(rgen, RAVINE);
		rgen.stoneBlock = ModBlocks.moon_rock;

		stoneBlock = ModBlocks.moon_rock;
		seaBlock = ModBlocks.basalt;
		seaLevel = 64;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);
		
		// NEW CAVES
		this.caveGenV3.func_151539_a(this, worldObj, x, z, buffer.blocks);
		this.rgen.func_151539_a(this, worldObj, x, z, buffer.blocks);

		return buffer;
	}

}
