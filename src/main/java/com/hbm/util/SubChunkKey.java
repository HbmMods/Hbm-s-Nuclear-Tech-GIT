package com.hbm.util;

import net.minecraft.world.ChunkCoordIntPair;

/**
 * Unique identifier for sub-chunks.
 * @author mlbv
 */
public class SubChunkKey {

	private int chunkXPos;
	private int chunkZPos;
	private int subY;
	private int hash;

	public SubChunkKey(int cx, int cz, int sy) {
		this.update(cx, cz, sy);
	}

	public SubChunkKey(ChunkCoordIntPair pos, int sy) {
		this.update(pos.chunkXPos, pos.chunkZPos, sy);
	}

	public SubChunkKey update(int cx, int cz, int sy) {
		this.chunkXPos = cx;
		this.chunkZPos = cz;
		this.subY = sy;
		int result = subY;
		result = 31 * result + cx;
		result = 31 * result + cz;
		this.hash = result;
		return this;
	}

	@Override
	public final int hashCode() {
		return this.hash;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SubChunkKey)) return false;
		SubChunkKey k = (SubChunkKey) o;
		return this.subY == k.subY && this.chunkXPos == k.chunkXPos && this.chunkZPos == k.chunkZPos;
	}

	public int getSubY() {
		return subY;
	}

	public int getChunkXPos() {
		return chunkXPos;
	}

	public int getChunkZPos() {
		return chunkZPos;
	}

	public ChunkCoordIntPair getPos() {
		return new ChunkCoordIntPair(this.chunkXPos, this.chunkZPos);
	}
}
