package com.hbm.blocks.rail;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardCurve extends BlockDummyable implements IRailNTM {

	public RailStandardCurve() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 0;
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
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		List<String> context = new ArrayList();
		context.add("=========================");
		
		boolean debug = Math.abs(speed) < 2;
		
		double turnRadius = 4.5D;

		Vec3 vec = Vec3.createVectorHelper(trainX, trainY, trainZ);
		double axisX = cX + 0.5  + dir.offsetX * 0.5 + rot.offsetX * turnRadius;
		double axisZ = cZ + 0.5  + dir.offsetZ * 0.5 + rot.offsetZ * turnRadius;
		
		Vec3 dist = Vec3.createVectorHelper(vec.xCoord - axisX, 0, vec.zCoord - axisZ);
		dist = dist.normalize();
		dist.xCoord *= turnRadius;
		dist.zCoord *= turnRadius;
		
		if(speed == 0) {
			info.dist(0).pos(new BlockPos(x, y, z));
			return Vec3.createVectorHelper(axisX + dist.xCoord, y, axisZ + dist.zCoord);
		}
		
		context.add("Speed: " + speed);
		
		double angleDeg = -Math.atan(dist.zCoord / dist.xCoord) * 180D / Math.PI;
		double length90Deg = turnRadius * Math.PI / 2D;
		double angularChange = speed / length90Deg * 90D;
		
		context.add("angleDeg: " + angleDeg);
		context.add("length90Deg: " + length90Deg);
		context.add("angularChange: " + angularChange);
		
		ForgeDirection moveDir = ForgeDirection.UNKNOWN;
		
		if(Math.abs(motionX) > Math.abs(motionZ)) {
			moveDir = motionX > 0 ? Library.POS_X : Library.NEG_X;
		} else {
			moveDir = motionZ > 0 ? Library.POS_Z : Library.NEG_Z;
		}
		
		context.add("moveDir: " + moveDir);
		
		if(moveDir == dir || moveDir == rot) {
			angularChange *= -1;
		}
		
		context.add("angularChange: " + angularChange);
		
		double effAngle = angleDeg + angularChange;
		
		context.add("effAngle: " + effAngle);
		
		if(effAngle > 90) {
			double angleOvershoot = effAngle - 90D;
			double lengthOvershoot = angleOvershoot * length90Deg / 90D;
			context.add("angleOvershoot: " + angleOvershoot);
			context.add("lengthOvershoot: " + lengthOvershoot);
			info.dist(lengthOvershoot).pos(new BlockPos(cX - dir.offsetX * 4 + rot.offsetX * 5, y, cZ - dir.offsetZ * 4 + rot.offsetZ * 5));
			if(debug) for(String s : context) System.out.println(s);
			return Vec3.createVectorHelper(axisX - dir.offsetX * turnRadius + rot.offsetX * turnRadius, y, axisZ - dir.offsetZ * turnRadius + rot.offsetZ * turnRadius);
		}
		
		if(effAngle < 0) {
			double angleOvershoot = -effAngle;
			double lengthOvershoot = angleOvershoot * length90Deg / 90D;
			context.add("angleOvershoot: " + angleOvershoot);
			context.add("lengthOvershoot: " + lengthOvershoot);
			info.dist(lengthOvershoot).pos(new BlockPos(cX + dir.offsetX , y, cZ + dir.offsetZ));
			ParticleUtil.spawnGasFlame(world, axisX + 0.5 + dir.offsetX * 0.5, y, axisZ * 0.5 + dir.offsetZ * 0.5, 0, 0.2, 0);
			if(debug) for(String s : context) System.out.println(s);
			return Vec3.createVectorHelper(axisX + 0.5 + dir.offsetX * 0.5, y, axisZ * 0.5 + dir.offsetZ * 0.5);
		}
		
		double radianChange = angularChange * Math.PI / 180D;
		dist.rotateAroundY((float) radianChange);
		
		context.add("radianChange: " + radianChange);
		if(debug) for(String s : context) System.out.println(s);
		return Vec3.createVectorHelper(axisX + dist.xCoord, y, axisZ + dist.zCoord);
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 4, 0, 4, 0};
	}

	@Override
	public int getOffset() {
		return 0;
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
}
