package com.hbm.handler;

import net.minecraftforge.common.util.ForgeDirection;

public class ThreeInts implements Comparable<ThreeInts> {
	
	public int x;
	public int y;
	public int z;
	
	public ThreeInts(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThreeInts other = (ThreeInts) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public int compareTo(ThreeInts o) {
		return equals(o) ? 0 : 1;
	}
	
	/**
	 * @param dx x offset
	 * @param dy y offset
	 * @param dz z offset
	 * @return a new object containing the coordinates of that offset
	 */
	public ThreeInts getPositionAtOffset(int dx, int dy, int dz) {
		return new ThreeInts(dx + x, dy + y, dz + z);
	}

	public ThreeInts getPositionAtOffset(ForgeDirection dir) {
		return new ThreeInts(dir.offsetX + x, dir.offsetY + y, dir.offsetZ + z);
	}

	public double getDistanceSquared(ThreeInts to) {
		int ox = x - to.x, oy = y - to.y, oz = z - to.z;
		return ox * ox + oy * oy + oz * oz;
	}
	
	public double getDistance(ThreeInts to) {
		return Math.sqrt(getDistanceSquared(to));
	}

}
