package com.hbm.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A snapshot of a 16×16×16 sub-chunk.
 * @Author mlbv
 */
public class SubChunkSnapshot {
	/**
	 * A sub-chunk that contains only air.
	 */
	public static final SubChunkSnapshot EMPTY = new SubChunkSnapshot(new Block[]{Blocks.air}, null);
	private final Block[] palette;
	private final short[] data;

	private SubChunkSnapshot(Block[] p, short[] d) {
		this.palette = p;
		this.data = d;
	}

	/**
	 * Creates a SubChunkSnapshot from a loaded chunk.
	 *
	 * @param world
	 *        The World instance from which to retrieve the chunk.
	 * @param cpos
	 *        The ChunkCoordIntPair identifying the chunk coordinates (x, z).
	 * @param subY
	 *        The vertical sub-chunk index (0–15) within the chunk.
	 * @return
	 *        A SubChunkSnapshot containing the palette and block data for the sub-chunk,
	 *        or SubChunkSnapshot.EMPTY if the region is unloaded or contains only air.
	 */
	public static SubChunkSnapshot getSnapshot(World world, ChunkCoordIntPair cpos, int subY) {
		if (!world.getChunkProvider().chunkExists(cpos.chunkXPos, cpos.chunkZPos)) {
			return SubChunkSnapshot.EMPTY;
		}
		return getOrLoadSnapshot(world, cpos, subY);
	}

	/**
	 * Creates a SubChunkSnapshot.
	 *
	 * @param world
	 *        The World instance from which to retrieve the chunk.
	 * @param cpos
	 *        The ChunkCoordIntPair identifying the chunk coordinates (x, z).
	 * @param subY
	 *        The vertical sub-chunk index (0–15) within the chunk.
	 * @return
	 *        A SubChunkSnapshot containing the palette and block data for the sub-chunk,
	 *        or SubChunkSnapshot.EMPTY if the region contains only air.
	 */
	public static SubChunkSnapshot getOrLoadSnapshot(World world, ChunkCoordIntPair cpos, int subY){
		Chunk chunk = world.getChunkFromChunkCoords(cpos.chunkXPos, cpos.chunkZPos);
		ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[subY];
		if (ebs == null || ebs.isEmpty()) return SubChunkSnapshot.EMPTY;

		short[] data = new short[16 * 16 * 16];
		List<Block> palette = new ArrayList<>();
		palette.add(Blocks.air);
		Map<Block, Short> idxMap = new HashMap<>();
		idxMap.put(Blocks.air, (short) 0);
		boolean allAir = true;

		for (int ly = 0; ly < 16; ly++) {
			for (int lz = 0; lz < 16; lz++) {
				for (int lx = 0; lx < 16; lx++) {
					Block block = ebs.getBlockByExtId(lx, ly, lz);
					int idx;
					if (block == Blocks.air) {
						idx = 0;
					} else {
						allAir = false;
						Short e = idxMap.get(block);
						if (e == null) {
							idxMap.put(block, (short) palette.size());
							palette.add(block);
							idx = palette.size() - 1;
						} else {
							idx = e;
						}
					}
					data[(ly << 8) | (lz << 4) | lx] = (short) idx;
				}
			}
		}
		if (allAir) return SubChunkSnapshot.EMPTY;
		return new SubChunkSnapshot(palette.toArray(new Block[0]), data);
	}

	/**
	 * Retrieves the Block at the specified local coordinates within this sub-chunk snapshot.
	 *
	 * @param x
	 *        The local x-coordinate within the sub-chunk (0–15).
	 * @param y
	 *        The local y-coordinate within the sub-chunk (0–15).
	 * @param z
	 *        The local z-coordinate within the sub-chunk (0–15).
	 * @return
	 *        The Block instance at the given position.
	 */
	public Block getBlock(int x, int y, int z) {
		if (this == EMPTY || data == null) return Blocks.air;
		short idx = data[(y << 8) | (z << 4) | x];
		return (idx >= 0 && idx < palette.length) ? palette[idx] : Blocks.air;
	}
}
