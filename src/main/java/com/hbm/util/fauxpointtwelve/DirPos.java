package com.hbm.util.fauxpointtwelve;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class DirPos extends BlockPos {
	
	protected ForgeDirection dir;

	public DirPos(int x, int y, int z, ForgeDirection dir) {
		super(x, y, z);
		this.dir = dir;
	}

	public DirPos(TileEntity te, ForgeDirection dir) {
		super(te);
		this.dir = dir;
	}

	public DirPos(double x, double y, double z, ForgeDirection dir) {
		super(x, y, z);
		this.dir = dir;
	}
	
	@Override
	public DirPos rotate(Rotation rotationIn) {
		switch(rotationIn) {
		case NONE:
		default: return this;
		case CLOCKWISE_90: return new DirPos(-this.getZ(), this.getY(), this.getX(), this.getDir().getRotation(ForgeDirection.UP));
		case CLOCKWISE_180: return new DirPos(-this.getX(), this.getY(), -this.getZ(), this.getDir().getOpposite());
		case COUNTERCLOCKWISE_90: return new DirPos(this.getZ(), this.getY(), -this.getX(), this.getDir().getRotation(ForgeDirection.DOWN));
		}
	}
	
	public DirPos flip() {
		return new DirPos(this.getX(), this.getY(), this.getZ(), this.getDir().getOpposite());
	}
	
	/** Useful for the opposing piece of a connector */
	public DirPos flipWithOffset() {
		ForgeDirection dir = this.getDir();
		return new DirPos(this.getX() + dir.offsetX, this.getY() + dir.offsetY, this.getZ() + dir.offsetZ, dir.getOpposite());
	}
	
	public ForgeDirection getDir() { return this.dir; }
	public BlockPos toPos() { return new BlockPos(x, y, z); }
	
	@Override
	public int hashCode() {
		return DimPos.getIdentity(this.getX(), this.getY(), this.getZ(), this.getDir().ordinal());
	}

	@Override
	public boolean equals(Object toCompare) {
		if(this == toCompare) {
			return true;
		} else if(!(toCompare instanceof BlockPos)) {
			return false;
		} else {
			DirPos pos = (DirPos) toCompare;
			if(this.getX() != pos.getX()) {
				return false;
			} else if(this.getY() != pos.getY()) {
				return false;
			} else if(this.getZ() != pos.getZ()) {
				return false;
			} else {
				return this.getDir() == pos.getDir();
			}
		}
	}
}
