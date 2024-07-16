package com.hbm.dim.moho;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderMoho extends ChunkProviderCelestial {
	
	private MapGenBase caveGenerator = new MapGenCaves();

	public ChunkProviderMoho(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
		
		stoneBlock = ModBlocks.moho_stone;
		seaBlock = Blocks.lava;
		seaLevel = 48;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		this.caveGenerator.func_151539_a(this, this.worldObj, x, z, buffer.blocks);
		
		return buffer;
	}

}
