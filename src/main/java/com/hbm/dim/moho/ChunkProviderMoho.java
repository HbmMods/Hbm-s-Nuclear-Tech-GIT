package com.hbm.dim.moho;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.ExperimentalCaveGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderMoho extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator(1, 52, 10.0F);
	private MapgenRavineButBased rgen = new MapgenRavineButBased();

	public ChunkProviderMoho(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenV2 = (ExperimentalCaveGenerator) TerrainGen.getModdedMapGen(caveGenV2, CUSTOM);
		rgen = (MapgenRavineButBased) TerrainGen.getModdedMapGen(rgen, RAVINE);

		caveGenV2.stoneBlock = ModBlocks.moho_stone;
		rgen.stoneBlock = ModBlocks.moho_stone;
		stoneBlock = ModBlocks.moho_stone;
		seaBlock = Blocks.lava;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		// how many times do I gotta say BEEEEG
		this.caveGenV2.func_151539_a(this, worldObj, x, z, buffer.blocks);
		this.rgen.func_151539_a(this, worldObj, x, z, buffer.blocks);
		return buffer;
	}

}
