package com.hbm.dim.orbit;

import java.util.List;

import com.hbm.config.SpaceConfig;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderOrbit implements IChunkProvider {

	protected World worldObj;

	public ChunkProviderOrbit(World world) {
		this.worldObj = world;
	}
	
	/**
	 * Checks to see if a chunk exists at x, z
	 */
	@Override
	public boolean chunkExists(int x, int z) {
		return true;
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		Chunk chunk = new Chunk(worldObj, new Block[65536], new byte[65536], x, z);

		byte[] abyte1 = chunk.getBiomeArray();

		for(int k = 0; k < abyte1.length; ++k) {
			abyte1[k] = (byte)SpaceConfig.orbitBiome;
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	@Override
	public Chunk loadChunk(int x, int z) {
		return provideChunk(x, z);
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(IChunkProvider provider, int x, int z) {
		
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If passed
	 * false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	@Override
	public boolean saveChunks(boolean combined, IProgressUpdate progress) {
		return true;
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to
	 * unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "RandomLevelSource";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given
	 * location.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
		return null;
	}

	/**
	 * I have no fucking clue, just return null
	 */
	@Override
	public ChunkPosition func_147416_a(World world, String shitfuck, int x, int y, int z) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void recreateStructures(int x, int z) {

	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave,
	 * only during world unload. Currently
	 * unimplemented.
	 */
	@Override
	public void saveExtraData() {}

}
