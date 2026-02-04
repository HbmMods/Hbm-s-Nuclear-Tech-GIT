package com.hbm.util.fauxpointtwelve;

/** BlockPos + dimension */
public class DimPos extends BlockPos {

	protected int dim;
	
	public DimPos(int x, int y, int z, int dim) {
		super(x, y, z);
		this.dim = dim;
	}
	
	public int getDim() {
		return this.dim;
	}
	@Override
	public int hashCode() {
		return getIdentity(this.getX(), this.getY(), this.getZ(), this.getDim());
	}
	
	public static int getIdentity(int x, int y, int z, int dim) {
		return ((y + dim * HASH_PRIME) + z * HASH_PRIME) * HASH_PRIME + x; // yeah fuck it why not
	}

	@Override
	public boolean equals(Object toCompare) {
		if(this == toCompare) {
			return true;
		} else if(!(toCompare instanceof DimPos)) {
			return false;
		} else {
			DimPos pos = (DimPos) toCompare;
			if(this.getX() != pos.getX()) return false;
			if(this.getY() != pos.getY()) return false;
			if(this.getZ() != pos.getZ()) return false;
			return this.getDim() == pos.getDim();
		}
	}
	
	@Override
	public DimPos clone() {
		try {
			return (DimPos) super.clone();
		} catch(Exception x) { }
		return null;
	}
}
