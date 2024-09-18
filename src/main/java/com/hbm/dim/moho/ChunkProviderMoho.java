package com.hbm.dim.moho;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.ExperimentalCaveGenerator;
import com.hbm.dim.mapgen.MapGenCrater;
import com.hbm.dim.mapgen.MapGenVolcano;
import com.hbm.dim.mapgen.MapgenRavineButBased;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ChunkProviderMoho extends ChunkProviderCelestial {

	private ExperimentalCaveGenerator caveGenV2 = new ExperimentalCaveGenerator(1, 52, 10.0F);
	private MapgenRavineButBased rgen = new MapgenRavineButBased();

	private MapGenCrater smallCrater = new MapGenCrater(6);
	private MapGenCrater largeCrater = new MapGenCrater(64);
	private MapGenVolcano volcano = new MapGenVolcano(72);

	public ChunkProviderMoho(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);

		smallCrater.setSize(8, 32);
		largeCrater.setSize(96, 128);
		volcano.setSize(64, 128);

		smallCrater.regolith = largeCrater.regolith = ModBlocks.moho_regolith;
		smallCrater.rock = largeCrater.rock = ModBlocks.moho_stone;

		caveGenV2.stoneBlock = ModBlocks.moho_stone;
		rgen.stoneBlock = ModBlocks.moho_stone;
		stoneBlock = ModBlocks.moho_stone;
		seaBlock = Blocks.lava;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		// how many times do I gotta say BEEEEG
		caveGenV2.func_151539_a(this, worldObj, x, z, buffer.blocks);
		rgen.func_151539_a(this, worldObj, x, z, buffer.blocks);
		smallCrater.func_151539_a(this, worldObj, x, z, buffer.blocks);
		largeCrater.func_151539_a(this, worldObj, x, z, buffer.blocks);
		volcano.func_151539_a(this, worldObj, x, z, buffer.blocks);

		return buffer;
	}

}
