package com.hbm.dim.minmus;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenCrater;
import com.hbm.dim.mapgen.MapGenVanillaCaves;

import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;

public class ChunkProviderMinmus extends ChunkProviderCelestial {
	
	private MapGenBase caveGenerator = new MapGenVanillaCaves(ModBlocks.minmus_stone).withLava(ModBlocks.minmus_smooth);

	private MapGenCrater smallCrater = new MapGenCrater(5);
	private MapGenCrater largeCrater = new MapGenCrater(96);

	public ChunkProviderMinmus(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);

		smallCrater.setSize(6, 24);
		largeCrater.setSize(64, 128);

		smallCrater.regolith = largeCrater.regolith = ModBlocks.minmus_regolith;
		smallCrater.rock = largeCrater.rock = ModBlocks.minmus_stone;
		
		stoneBlock = ModBlocks.minmus_stone;
		seaBlock = ModBlocks.minmus_smooth;
		seaLevel = 63;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		caveGenerator.func_151539_a(this, worldObj, x, z, buffer.blocks);
		smallCrater.func_151539_a(this, worldObj, x, z, buffer.blocks);
		largeCrater.func_151539_a(this, worldObj, x, z, buffer.blocks);
		
		return buffer;
	}

}
