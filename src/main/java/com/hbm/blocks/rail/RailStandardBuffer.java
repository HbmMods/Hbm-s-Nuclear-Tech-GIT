package com.hbm.blocks.rail;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RailStandardBuffer extends BlockDummyable implements IRailNTM {

	public RailStandardBuffer() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 2, 2, 1, 0};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
	}

	@Override
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, 0, 0, 0, 0, new RailContext(), new MoveContext(RailCheckType.OTHER, 0));
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info, MoveContext context) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, motionX, motionY, motionZ, speed, info, context);
	}
	
	/* Very simple function determining the snapping position and adding the motion value to it, if desired. */
	public Vec3 snapAndMove(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info, MoveContext context) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return Vec3.createVectorHelper(trainX, trainY, trainZ);
		int cX = pos[0];
		int cY = pos[1];
		int cZ = pos[2];
		int meta = world.getBlockMetadata(cX, cY, cZ) - this.offset;
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		Vec3 vec = Vec3.createVectorHelper(trainX, trainY, trainZ);
		
		if(speed == 0) {
			//return vec;
		}
		
		if(dir == Library.POS_X || dir == Library.NEG_X) {
			double targetX = trainX;
			if(motionX > 0) {
				targetX += speed;
				info.yaw(-90F);
			} else {
				targetX -= speed;
				info.yaw(90F);
			}
			vec.xCoord = MathHelper.clamp_double(targetX, cX - 2, cX + 3);
			vec.yCoord = y + 0.1875;
			vec.zCoord = cZ + 0.5 + rot.offsetZ * 0.5;

			double nX = (dir == Library.POS_X ? -1 - context.collisionBogieDistance : 2);
			double pX = (dir == Library.NEG_X ? 0 - context.collisionBogieDistance : 3);
			double buffer = MathHelper.clamp_double(targetX, cX - nX, cX + pX);
			
			if(buffer != vec.xCoord) {
				context.collision = true;
				context.overshoot = Math.abs(buffer - vec.xCoord);
				vec.xCoord = buffer;
				return vec;
			}
			
			info.dist(Math.abs(targetX - vec.xCoord) * Math.signum(speed));
			info.pos(new BlockPos(cX + (motionX * speed > 0 ? 3 : -3), y, cZ));
		} else {
			double targetZ = trainZ;
			if(motionZ > 0) {
				targetZ += speed;
				info.yaw(0F);
			} else {
				targetZ -= speed;
				info.yaw(180F);
			}
			vec.xCoord = cX + 0.5 + rot.offsetX * 0.5;
			vec.yCoord = y + 0.1875;
			vec.zCoord = MathHelper.clamp_double(targetZ, cZ - 2,cZ + 3);

			double nZ = (dir == Library.POS_Z ? -1 - context.collisionBogieDistance : 2);
			double pZ = (dir == Library.NEG_Z ? 0 - context.collisionBogieDistance : 3);
			double buffer = MathHelper.clamp_double(targetZ, cZ - nZ, cZ + pZ);
			
			if(buffer != vec.xCoord) {
				context.collision = true;
				context.overshoot = Math.abs(buffer - vec.zCoord);
				vec.zCoord = buffer;
				return vec;
			}
			
			info.dist(Math.abs(targetZ - vec.zCoord) * Math.signum(speed));
			info.pos(new BlockPos(cX, y, cZ + (motionZ * speed > 0 ? 3 : -3)));
		}
		
		return vec;
	}

	@Override
	public TrackGauge getGauge(World world, int x, int y, int z) {
		return TrackGauge.STANDARD;
	}
}
