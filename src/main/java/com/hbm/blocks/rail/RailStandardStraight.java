package com.hbm.blocks.rail;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardStraight extends BlockDummyable implements IRailNTM {

	public RailStandardStraight(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 2, 2, 0, 0};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, 0, 0, 0, 0, new RailLeaveInfo());
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailLeaveInfo info) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, motionX, motionY, motionZ, speed, info);
	}
	
	/* Very simple function determining the snapping position and adding the motion value to it, if desired. */
	public Vec3 snapAndMove(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailLeaveInfo info) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return Vec3.createVectorHelper(trainX, trainY, trainZ);
		int cX = pos[0];
		int cY = pos[1];
		int cZ = pos[2];
		int meta = world.getBlockMetadata(cX, cY, cZ) - this.offset;
		ForgeDirection dir = ForgeDirection.getOrientation(meta);

		Vec3 vec = Vec3.createVectorHelper(trainX, trainY, trainZ);
		
		if(speed == 0) {
			return vec;
		}
		
		if(dir == Library.POS_X || dir == Library.NEG_X) {
			double targetX = trainX;
			if(motionX > 0) {
				targetX += speed;
			} else {
				targetX -= speed;
			}
			vec.xCoord = MathHelper.clamp_double(targetX, x - 2, x + 3);
			vec.yCoord = y;
			vec.zCoord = z + 0.5;
			info.dist(Math.abs(targetX - vec.xCoord));
			info.pos(new BlockPos(vec.xCoord + (motionX > 0 ? 1 : -1), y, z));
		} else {
			double targetZ = trainZ;
			if(motionZ > 0) {
				targetZ += speed;
			} else {
				targetZ -= speed;
			}
			vec.xCoord = x + 0.5;
			vec.yCoord = y;
			vec.zCoord = MathHelper.clamp_double(targetZ, z - 2, z + 3);
			info.dist(Math.abs(targetZ - vec.zCoord));
			info.pos(new BlockPos(x, y, vec.zCoord + (motionZ > 0 ? 1 : -1)));
		}
		
		return vec;
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}
}
