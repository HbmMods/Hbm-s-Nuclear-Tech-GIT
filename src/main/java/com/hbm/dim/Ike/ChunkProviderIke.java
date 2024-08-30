package com.hbm.dim.Ike;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenTiltedSpires;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;

public class ChunkProviderIke extends ChunkProviderCelestial {
	
	private MapGenBase caveGenerator = new MapGenCaves();
	private MapGenTiltedSpires spires = new MapGenTiltedSpires(6, 6, 0F);

	public ChunkProviderIke(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);

		spires.rock = ModBlocks.ike_stone;
		spires.regolith = ModBlocks.ike_regolith;
		spires.mid = 86;

		stoneBlock = ModBlocks.ike_stone;
	}

	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);

		spires.func_151539_a(this, worldObj, x, z, buffer.blocks);
		caveGenerator.func_151539_a(this, worldObj, x, z, buffer.blocks);
		
		return buffer;
	}

	// man fuck Ike, why you gotta be spawning shit again
	@SuppressWarnings("rawtypes")
	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        return null;
	}

}
