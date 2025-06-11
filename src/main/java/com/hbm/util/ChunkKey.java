package com.hbm.util;

import net.minecraft.world.ChunkCoordIntPair;

import java.util.Objects;

/**
 * Unique identifier for sub-chunks.
 * @author mlbv
 */
public class ChunkKey {
	public final ChunkCoordIntPair pos;
	public final int subY;

	public ChunkKey(int cx, int cz, int sy) {
		this.pos = new ChunkCoordIntPair(cx, cz);
		this.subY = sy;
	}

	public ChunkKey(ChunkCoordIntPair pos, int sy) {
		this.pos = pos;
		this.subY = sy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ChunkKey)) return false;
		ChunkKey k = (ChunkKey) o;
		return subY == k.subY && pos.equals(k.pos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pos.chunkXPos, pos.chunkZPos, subY);
	}
}
