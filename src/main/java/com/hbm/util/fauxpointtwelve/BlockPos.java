package com.hbm.util.fauxpointtwelve;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Adjusted code from MC 1.12 (com.minecraft.util.math.BlockPos)
 */
public class BlockPos {

	private final int x;
	private final int y;
	private final int z;

	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPos(TileEntity te) {
		this(te.xCoord, te.yCoord, te.zCoord);
	}
	
	public BlockPos(double x, double y, double z) {
		this((int)MathHelper.floor_double(x), (int)MathHelper.floor_double(y), (int)MathHelper.floor_double(z));
	}
	
	public BlockPos add(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0 ? this : new BlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
	}
	
	public BlockPos add(double x, double y, double z) {
		return x == 0.0D && y == 0.0D && z == 0.0D ? this : new BlockPos((double) this.getX() + x, (double) this.getY() + y, (double) this.getZ() + z);
	}
	
	public BlockPos add(BlockPos vec) {
		return this.add(vec.getX(), vec.getY(), vec.getZ());
	}
	
	public BlockPos rotate(Rotation rotationIn) {
		switch(rotationIn) {
		case NONE:
		default: return this;
		case CLOCKWISE_90: return new BlockPos(-this.getZ(), this.getY(), this.getX());
		case CLOCKWISE_180: return new BlockPos(-this.getX(), this.getY(), -this.getZ());
		case COUNTERCLOCKWISE_90: return new BlockPos(this.getZ(), this.getY(), -this.getX());
		}
	}

	public BlockPos offset(ForgeDirection dir) {
		return this.offset(dir, 1);
	}

	public BlockPos offset(ForgeDirection dir, int distance) {
		return new BlockPos(x + dir.offsetX * distance, y + dir.offsetY * distance, z + dir.offsetZ * distance);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	/** 1.12 vanilla implementation */
	@Override
	public int hashCode() {
		return (this.getY() + this.getZ() * 31) * 31 + this.getX();
	}

	@Override
	public boolean equals(Object toCompare) {
		if(this == toCompare) {
			return true;
		} else if(!(toCompare instanceof BlockPos)) {
			return false;
		} else {
			BlockPos pos = (BlockPos) toCompare;
			if(this.getX() != pos.getX()) {
				return false;
			} else if(this.getY() != pos.getY()) {
				return false;
			} else {
				return this.getZ() == pos.getZ();
			}
		}
	}
}
