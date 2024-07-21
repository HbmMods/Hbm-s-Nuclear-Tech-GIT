package com.hbm.dim.moon;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenCrater;
import com.hbm.dim.mapgen.MapGenGreg;
import com.hbm.dim.moho.MapgenRavineButBased;

import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderMoon extends ChunkProviderCelestial {

	private MapGenGreg caveGenV3 = new MapGenGreg();
	private MapgenRavineButBased rgen = new MapgenRavineButBased();

	private MapGenCrater smallCrater = new MapGenCrater(6);
	private MapGenCrater largeCrater = new MapGenCrater(64);

	public ChunkProviderMoon(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenV3 = (MapGenGreg) TerrainGen.getModdedMapGen(caveGenV3, CAVE);
		caveGenV3.stoneBlock = ModBlocks.moon_rock;
		rgen = (MapgenRavineButBased) TerrainGen.getModdedMapGen(rgen, RAVINE);
		rgen.stoneBlock = ModBlocks.moon_rock;

		smallCrater = (MapGenCrater) TerrainGen.getModdedMapGen(smallCrater, CUSTOM);
		smallCrater.setSize(8, 32);

		largeCrater = (MapGenCrater) TerrainGen.getModdedMapGen(largeCrater, CUSTOM);
		largeCrater.setSize(96, 128);

		smallCrater.regolith = largeCrater.regolith = ModBlocks.basalt;
		smallCrater.rock = largeCrater.rock = ModBlocks.moon_rock;

		stoneBlock = ModBlocks.moon_rock;
		seaBlock = ModBlocks.basalt;
		seaLevel = 64;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);
		
		// NEW CAVES
		caveGenV3.func_151539_a(this, worldObj, x, z, buffer.blocks);
		rgen.func_151539_a(this, worldObj, x, z, buffer.blocks);
		smallCrater.func_151539_a(this, worldObj, x, z, buffer.blocks);
		largeCrater.func_151539_a(this, worldObj, x, z, buffer.blocks);

		return buffer;
	}

}
