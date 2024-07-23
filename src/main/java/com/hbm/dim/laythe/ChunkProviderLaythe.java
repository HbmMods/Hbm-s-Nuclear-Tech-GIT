package com.hbm.dim.laythe;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;

import java.util.List;

import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.mapgen.MapGenGreg;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderLaythe extends ChunkProviderCelestial {
	private MapGenGreg caveGenV3 = new MapGenGreg();

    public ChunkProviderLaythe(World world, long seed, boolean hasMapFeatures) {
        super(world, seed, hasMapFeatures);
        declamp = false;
		caveGenV3 = (MapGenGreg) TerrainGen.getModdedMapGen(caveGenV3, CAVE);

        seaBlock = Blocks.water;
    }
	@Override
	public BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buffer = super.getChunkPrimer(x, z);
		
		// NEW CAVES
		this.caveGenV3.func_151539_a(this, worldObj, x, z, buffer.blocks);

		return buffer;
	}
	
	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(x, z);
        return biomegenbase.getSpawnableList(creatureType);
	}

}