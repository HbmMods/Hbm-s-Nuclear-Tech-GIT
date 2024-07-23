package com.hbm.dim.laythe;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenGreg;
import com.hbm.entity.mob.EntityCreeperFlesh;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderLaythe extends ChunkProviderCelestial {

	private MapGenGreg caveGenV3 = new MapGenGreg();

	private List<SpawnListEntry> spawnedOfFlesh = new ArrayList<SpawnListEntry>();

	public ChunkProviderLaythe(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		caveGenV3 = (MapGenGreg) TerrainGen.getModdedMapGen(caveGenV3, CAVE);

		seaBlock = Blocks.water;

		spawnedOfFlesh.add(new SpawnListEntry(EntityCreeperFlesh.class, 10, 4, 4));
	}
	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);
		
		// NEW CAVES
		this.caveGenV3.func_151539_a(this, worldObj, x, z, buffer.blocks);

		return buffer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
		if(creatureType == EnumCreatureType.monster && worldObj.getBlock(x, y - 1, z) == ModBlocks.tumor)
			return spawnedOfFlesh;

		return super.getPossibleCreatures(creatureType, x, y, z);
	}

}