package com.hbm.util;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMetalFence;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Because vanilla's implementation sucks.
 * 
 * @author hbm
 */
public class HitscanUtil {
	
	public static final CollisionHandler STANDARD_COLLISION = new CollisionHandler();
	public static final CollisionHandlerBullet BULLET_COLLISION = new CollisionHandlerBullet();

	public MovingObjectPosition hitscanBlocksCompat(World world, Vec3 start, Vec3 end, boolean collideFluids, boolean requiresPooledBB, boolean returnMissMOP) {
		return hitscanBlocks(world, start, end, 200, collideFluids, requiresPooledBB, returnMissMOP, STANDARD_COLLISION);
	}

	/**
	 * Improved version of World.func_147447_a (rayTraceBlocks).
	 * <br><br>
	 * The original code would step over each block that intersects with the delta vector by calculating block border intersects, instead of using
	 * fixed length steps (like how explosions would). This means the hitscan is way more accurate along diagonals. The "length" variable that's
	 * decremented on every iteration isn't actually the max length of the delta vector, but rather the max amount of blocks that can be intersected.
	 * This means that the effective range decreases depending on the orientation of the vector.
	 * <br>
	 * <br>Improvements over the original:
	 * <br>- General cleanup of the code, obfuscated values are now properly named
	 * <br>- The entire iteration process now uses a single Vec3NT instance instead of a Vec3 for every single traversed block
	 * <br>- Max iteration count is configurable
	 * <br>- Collision handler that can do special things depending on the position/block that's being collision checked
	 * <br>
	 * <br>Do note that the start vector is modified as the method runs.
	 * 
	 * @param world
	 * @param start
	 * @param end
	 * @param iterations the max amount of blocks that can be checked
	 * @param collideFluids whether the canCollideCheck uses the fluid check mode, allowing fluids to return MOP hits
	 * @param requiresBoundingBox whether the getCollisionBoundingBoxFromPool of the block must return a value, can be null otherwise, usually resulting in a miss MOP
	 * @param returnLastMOP whether the method should return the last calculated MOP (usually a miss) if iterations are depleted or the target is reached, returns null otherwise
	 * @param collisions the collision handler, STANDARD_COLLISION imitates vanilla handling
	 * @return a MovingObjectPosition defining the block hit if there is one, null if returnLastMOP is false, the last checked position with a MISS type if returnLastMOP is true, and always null if one of the vector positions is NaN
	 */
	public MovingObjectPosition hitscanBlocks(World world, Vec3 start, Vec3 end, int iterations, boolean collideFluids, boolean requiresBoundingBox, boolean returnLastMOP, CollisionHandler collisions) {
		if(Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord)) return null;
		if(Double.isNaN(end.xCoord) || Double.isNaN(end.yCoord) || Double.isNaN(end.zCoord)) return null;
				
		int eX = MathHelper.floor_double(end.xCoord);
		int eY = MathHelper.floor_double(end.yCoord);
		int eZ = MathHelper.floor_double(end.zCoord);
		int sX = MathHelper.floor_double(start.xCoord);
		int sY = MathHelper.floor_double(start.yCoord);
		int sZ = MathHelper.floor_double(start.zCoord);

		MovingObjectPosition mop = collisions.tryCollide(world, sX, sY, sZ, 0, start, end, requiresBoundingBox, collideFluids, false);
		if(mop != null) return mop;

		MovingObjectPosition lastMop = null;
		Vec3NT vec = new Vec3NT(0, 0, 0);

		while(iterations-- >= 0) {

			if(Double.isNaN(start.xCoord) || Double.isNaN(start.yCoord) || Double.isNaN(start.zCoord)) return null;
			if(sX == eX && sY == eY && sZ == eZ) return returnLastMOP ? lastMop : null;

			boolean movesAlongX = true;
			boolean movesAlongY = true;
			boolean movesAlongZ = true;
			double subX = 999.0D;
			double subY = 999.0D;
			double subZ = 999.0D;

			if(eX > sX) {
				subX = sX + 1.0D;
			} else if(eX < sX) {
				subX = sX + 0.0D;
			} else {
				movesAlongX = false;
			}

			if(eY > sY) {
				subY = sY + 1.0D;
			} else if(eY < sY) {
				subY = sY + 0.0D;
			} else {
				movesAlongY = false;
			}

			if(eZ > sZ) {
				subZ = sZ + 1.0D;
			} else if(eZ < sZ) {
				subZ = sZ + 0.0D;
			} else {
				movesAlongZ = false;
			}

			double stepX = 999.0D;
			double stepY = 999.0D;
			double stepZ = 999.0D;
			double deltaX = end.xCoord - start.xCoord;
			double deltaY = end.yCoord - start.yCoord;
			double deltaZ = end.zCoord - start.zCoord;

			if(movesAlongX) stepX = (subX - start.xCoord) / deltaX;
			if(movesAlongY) stepY = (subY - start.yCoord) / deltaY;
			if(movesAlongZ) stepZ = (subZ - start.zCoord) / deltaZ;

			byte facing;

			// moves along X axis
			if(stepX < stepY && stepX < stepZ) {
				if(eX > sX) facing = 4;
				else facing = 5;

				start.xCoord = subX;
				start.yCoord += deltaY * stepX;
				start.zCoord += deltaZ * stepX;

				// moves along Y axis
			} else if(stepY < stepZ) {
				if(eY > sY) facing = 0;
				else facing = 1;

				start.xCoord += deltaX * stepY;
				start.yCoord = subY;
				start.zCoord += deltaZ * stepY;

				// moves along Z axis
			} else {
				if(eZ > sZ) facing = 2;
				else facing = 3;

				start.xCoord += deltaX * stepZ;
				start.yCoord += deltaY * stepZ;
				start.zCoord = subZ;
			}

			vec.setComponents(start.xCoord, start.yCoord, start.zCoord);
			sX = (int) (vec.xCoord = MathHelper.floor_double(start.xCoord));

			if(facing == 5) {
				--sX;
				++vec.xCoord;
			}

			sY = (int) (vec.yCoord = MathHelper.floor_double(start.yCoord));

			if(facing == 1) {
				--sY;
				++vec.yCoord;
			}

			sZ = (int) (vec.zCoord = MathHelper.floor_double(start.zCoord));

			if(facing == 3) {
				--sZ;
				++vec.zCoord;
			}

			mop = collisions.tryCollide(world, sX, sY, sZ, facing, start, end, requiresBoundingBox, collideFluids, true);

			if(mop != null) {
				if(mop.typeOfHit == mop.typeOfHit.BLOCK) return mop;
				lastMop = mop;
			}
		}

		return returnLastMOP ? lastMop : null;
	}
	
	/**
	 * Copy of Block.collisionRayTrace, cleaned up, with custom AABB dimensions so we can simulate hitscan with our own dimensions.
	 * Example: If we want bullets to go through fences but not the fence posts, simply plug the fence post dimensions into this
	 * method and use it instead of the fence's collisionRayTrace.
	 */
	public static MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Block block, Vec3 start, Vec3 end, double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
		
		start = start.addVector(-x, -y, -z);
		end = end.addVector(-x, -y, -z);

		//TODO: decrease object allocations
		Vec3 negX = start.getIntermediateWithXValue(end, minX);
		Vec3 posX = start.getIntermediateWithXValue(end, maxX);
		Vec3 negY = start.getIntermediateWithYValue(end, minY);
		Vec3 posY = start.getIntermediateWithYValue(end, maxY);
		Vec3 negZ = start.getIntermediateWithZValue(end, minZ);
		Vec3 posZ = start.getIntermediateWithZValue(end, maxZ);

		if(!isVecInsideYZBounds(negX, minY, maxY, minZ, maxZ)) negX = null;
		if(!isVecInsideYZBounds(posX, minY, maxY, minZ, maxZ)) posX = null;
		if(!isVecInsideXZBounds(negY, minX, maxX, minZ, maxZ)) negY = null;
		if(!isVecInsideXZBounds(posY, minX, maxX, minZ, maxZ)) posY = null;
		if(!isVecInsideXYBounds(negZ, minX, maxX, minY, maxY)) negZ = null;
		if(!isVecInsideXYBounds(posZ, minX, maxX, minY, maxY)) posZ = null;

		Vec3 impact = null;

		if(negX != null && (impact == null || start.squareDistanceTo(negX) < start.squareDistanceTo(impact))) impact = negX;
		if(posX != null && (impact == null || start.squareDistanceTo(posX) < start.squareDistanceTo(impact))) impact = posX;
		if(negY != null && (impact == null || start.squareDistanceTo(negY) < start.squareDistanceTo(impact))) impact = negY;
		if(posY != null && (impact == null || start.squareDistanceTo(posY) < start.squareDistanceTo(impact))) impact = posY;
		if(negZ != null && (impact == null || start.squareDistanceTo(negZ) < start.squareDistanceTo(impact))) impact = negZ;
		if(posZ != null && (impact == null || start.squareDistanceTo(posZ) < start.squareDistanceTo(impact))) impact = posZ;

		if(impact == null) {
			return null;
			
		} else {
			
			byte facing = -1;
			if(impact == negX) facing = 4;
			if(impact == posX) facing = 5;
			if(impact == negY) facing = 0;
			if(impact == posY) facing = 1;
			if(impact == negZ) facing = 2;
			if(impact == posZ) facing = 3;

			return new MovingObjectPosition(x, y, z, facing, impact.addVector(x, y, z));
		}
	}

	public static boolean isVecInsideYZBounds(Vec3 vec, double minY, double maxY, double minZ, double maxZ) {
		return vec == null ? false : vec.yCoord >= minY && vec.yCoord <= maxY && vec.zCoord >= minZ && vec.zCoord <= maxZ;
	}

	public static boolean isVecInsideXZBounds(Vec3 vec, double minX, double maxX, double minZ, double maxZ) {
		return vec == null ? false : vec.xCoord >= minX && vec.xCoord <= maxX && vec.zCoord >= minZ && vec.zCoord <= maxZ;
	}

	public static boolean isVecInsideXYBounds(Vec3 vec, double minX, double maxX, double minY, double maxY) {
		return vec == null ? false : vec.xCoord >= minX && vec.xCoord <= maxX && vec.yCoord >= minY && vec.yCoord <= maxY;
	}
	
	public static class CollisionHandler {
		
		public MovingObjectPosition tryCollide(World world, int x, int y, int z, int facing, Vec3 start, Vec3 end, boolean requiresBoundingBox, boolean collideFluids, boolean returnMiss) {

			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);

			if(!requiresBoundingBox || block.getCollisionBoundingBoxFromPool(world, x, y, z) != null) {
				if(block.canCollideCheck(meta, collideFluids)) {
					return block.collisionRayTrace(world, x, y, z, start, end);
				} else {
					return returnMiss ? new MovingObjectPosition(x, y, z, facing, start, false) : null;
				}
			}
			
			return null;
		}
	}
	
	public static class CollisionHandlerBullet extends CollisionHandler {
		
		@Override
		public MovingObjectPosition tryCollide(World world, int x, int y, int z, int facing, Vec3 start, Vec3 end, boolean requiresBoundingBox, boolean collideFluids, boolean returnMiss) {

			Block block = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			boolean canCollide = true;
			
			if(block == Blocks.iron_bars) canCollide = false;
			if(block == ModBlocks.fence_metal && !((BlockMetalFence) ModBlocks.fence_metal).hasPost(world, x, y, z, meta)) canCollide = false;

			if(!requiresBoundingBox || block.getCollisionBoundingBoxFromPool(world, x, y, z) != null) {
				if(canCollide && block.canCollideCheck(meta, collideFluids)) {
					return handleSpecial(world, x, y, z, start, end, block, meta); 
				} else {
					return returnMiss ? new MovingObjectPosition(x, y, z, facing, start, false) : null;
				}
			}
			
			return null;
		}
		
		public MovingObjectPosition handleSpecial(World world, int x, int y, int z, Vec3 start, Vec3 end, Block block, int meta) {
			// all fences with a 0.25x0.25 post
			if(block == Blocks.fence ||
					block == Blocks.nether_brick_fence ||
					block == ModBlocks.fence_metal) return collisionRayTrace(world, x, y, z, block, start, end, 0.375, 0, 0.375, 0.625, 1, 0.625);
			
			return block.collisionRayTrace(world, x, y, z, start, end);
		}
	}
}
