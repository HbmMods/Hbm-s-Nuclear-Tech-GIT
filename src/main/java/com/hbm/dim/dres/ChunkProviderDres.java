package com.hbm.dim.dres;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenCrater;
import com.hbm.dim.mapgen.MapGenVanillaCaves;

import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderDres extends ChunkProviderCelestial {
	
	private MapGenBase caveGenerator = new MapGenVanillaCaves(ModBlocks.dres_rock);

	private MapGenCrater smallCrater = new MapGenCrater(6);
	private MapGenCrater largeCrater = new MapGenCrater(64);

	public ChunkProviderDres(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);

		smallCrater = (MapGenCrater) TerrainGen.getModdedMapGen(smallCrater, CUSTOM);
		smallCrater.setSize(8, 32);

		largeCrater = (MapGenCrater) TerrainGen.getModdedMapGen(largeCrater, CUSTOM);
		largeCrater.setSize(96, 128);

		smallCrater.regolith = largeCrater.regolith = ModBlocks.dres_rock;
		smallCrater.rock = largeCrater.rock = ModBlocks.dres_rock;

		stoneBlock = ModBlocks.dres_rock;
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
