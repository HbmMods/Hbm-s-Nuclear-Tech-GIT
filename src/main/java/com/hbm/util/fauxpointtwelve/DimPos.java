package com.hbm.util.fauxpointtwelve;

public class DimPos extends BlockPos {
	
	private int dimension;

	public DimPos(double x, double y, double z, int dim) {
		super(x, y, z);
		this.dimension = dim;
	}

	public DimPos(int x, int y, int z, int dim) {
		super(x, y, z);
		this.dimension = dim;
	}
	
	public int getDim() {
		return this.dimension;
	}
	@Override
	public int hashCode() {
		return getDimIdentity(this.getX(), this.getY(), this.getZ(), this.getDim());
	}
	
	public static int getDimIdentity(int x, int y, int z, int dim) {
		return ((y + dim * 27644437) + z * 27644437) * 27644437 + x;
	}
}
