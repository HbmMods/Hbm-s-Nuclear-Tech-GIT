package com.hbm.blocks.rail;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ParticleUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailWaypointSystem extends BlockDummyable implements IRailNTM {
	
	public List<RailDef> railDefs = new ArrayList();
	
	public BlockRailWaypointSystem(Material mat) {
		super(mat);
	}
	
	/** Whether the train at position FROM can move towards the waypoint TO along the supplied railDef, also supplies world and core position */
	public boolean canCross(World world, int x, int y, int z, Vec3 from, Vec3 to, RailDef def) {
		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.125F, 1F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public Vec3 getSnappingPos(World world, int x, int y, int z, double trainX, double trainY, double trainZ) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, 0, 0, 0, 0, new RailContext());
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info, MoveContext context) {
		return snapAndMove(world, x, y, z, trainX, trainY, trainZ, motionX, motionY, motionZ, speed, info);
	}

	public Vec3 snapAndMove(World world, int x, int y, int z, double trainX, double trainY, double trainZ, double motionX, double motionY, double motionZ, double speed, RailContext info) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return Vec3.createVectorHelper(trainX, trainY, trainZ);
		int cX = pos[0];
		int cY = pos[1];
		int cZ = pos[2];
		int meta = world.getBlockMetadata(cX, cY, cZ);
		double moveAngle = Math.atan2(motionX, motionZ) * 180D / Math.PI + 90;
		Vec3 trainPos = Vec3.createVectorHelper(trainX, trainY, trainZ);
		
		//convert nodes to a list of links with in-world positions
		Vec3 train = Vec3.createVectorHelper(trainX, trainY, trainZ);
		Vec3 core = Vec3.createVectorHelper(cX + 0.5, cY, cZ + 0.5);
		List<List<Pair<Vec3[], RailDef>>> links = new ArrayList();
		
		for(RailDef def : railDefs) {
			List<Pair<Vec3[], RailDef>> linkList = new ArrayList();
			links.add(linkList);
			
			for(int i = 0; i < def.nodes.size() - 1; i++) {
				Vec3 vec1 = getPositionFromNode(world, x, y, z, core, def.nodes.get(i), meta);
				Vec3 vec2 = getPositionFromNode(world, x, y, z, core, def.nodes.get(i + 1), meta);
				ParticleUtil.spawnDroneLine(world, vec1.xCoord, vec1.yCoord, vec1.zCoord, vec2.xCoord - vec1.xCoord, vec2.yCoord - vec1.yCoord, vec2.zCoord - vec1.zCoord, 0xff0000);
				linkList.add(new Pair<Vec3[], RailDef>(new Vec3[] {vec1, vec2}, def));
			}
		}
		
		//find closest node
		Pair<Vec3[], RailDef> closest = null;
		Vec3 startingPos = null;
		/** closest chain of link definitions */
		List<Pair<Vec3[], RailDef>> cDef = null;
		double angularDiff = 0;
		double linkAngle = 0;
		double dist = Double.MAX_VALUE;
		/** direction */
		boolean d = true;
		
		for(List<Pair<Vec3[], RailDef>> chain : links) {
			for(Pair<Vec3[], RailDef> link : chain) {
				Vec3[] array = link.getKey();
				Vec3 point = getClosestPointOnLink(array[0], array[1], train);
				
				if(point != null) {
					Vec3 delta = point.subtract(train);
					double length = delta.lengthVector();
					
					if(!canCross(world, cX, cY, cZ, trainPos, point, link.getValue())) continue;
					
					linkAngle = EntityRailCarBase.generateYaw(array[1], array[0]);
					angularDiff = BobMathUtil.angularDifference(linkAngle, -moveAngle);
					if(angularDiff < -180) { angularDiff += 180; linkAngle += 180; d = false; }
					if(angularDiff > 0) { angularDiff -= 180; linkAngle -= 180; d = false; }
					
					if(length < dist) {
						closest = link;
						startingPos = point;
						cDef = chain;
						dist = length;
					}
				}
			}
		}
		
		if(closest == null) {
			return Vec3.createVectorHelper(trainX, trainY, trainZ);
		}
		
		double distRemaining = speed;
		boolean engaged = false;
		Vec3 currentPos = startingPos;
		for(int i = d ? 0 : cDef.size() - 1; d ? (i < cDef.size()) : (i >= 0); i += d ? 1 : -1) {
			
			Pair<Vec3[], RailDef> link = cDef.get(i);
			Vec3[] array = link.getKey();
			
			if(!engaged) {
				if(link == closest) {
					engaged = true;
				} else {
					continue;
				}
			}
			
			Vec3 nextNode = array[d?1:0];
			Vec3 delta = nextNode.subtract(currentPos);
			
			if(!canCross(world, cX, cY, cZ, currentPos, nextNode, link.getValue())) break;
			
			double len = delta.lengthVector();
			if(len >= distRemaining) {
				info.overshoot = 0;
				double newYaw = EntityRailCarBase.generateYaw(nextNode, currentPos);
				if(Math.abs(BobMathUtil.angularDifference(newYaw, moveAngle)) < 45) info.yaw = (float) newYaw;
				else info.yaw = (float) moveAngle;
				delta.normalize();
				return Vec3.createVectorHelper(currentPos.xCoord - delta.xCoord * distRemaining / len, currentPos.yCoord - delta.yCoord * distRemaining / len, currentPos.zCoord - delta.zCoord * distRemaining / len);
			}
			
			distRemaining -= len;
			currentPos = nextNode;
		}

		info.overshoot = distRemaining;
		info.pos = new BlockPos(currentPos.xCoord, currentPos.yCoord, currentPos.zCoord);
		
		return currentPos;
	}
	
	public Vec3 getClosestPointOnLink(Vec3 pointA, Vec3 pointB, Vec3 pointP) {
		Vec3 ap = Vec3.createVectorHelper(pointP.xCoord - pointA.xCoord, 0, pointP.zCoord - pointA.zCoord);
		Vec3 ab = Vec3.createVectorHelper(pointB.xCoord - pointA.xCoord, 0, pointB.zCoord - pointA.zCoord);
		
		double magAB = ab.xCoord * ab.xCoord + ab.zCoord * ab.zCoord;
		double dotProd = ap.xCoord * ab.xCoord + ap.zCoord * ab.zCoord;
		double dist = dotProd / magAB;
		
		if(dist < 0) return pointA;
		if(dist > 1) return pointB;
		if(dist < 0 || dist > 1) return null;
		
		return Vec3.createVectorHelper(pointA.xCoord + ab.xCoord * dist, pointA.yCoord + (pointB.yCoord - pointA.yCoord) * dist, pointA.zCoord + ab.zCoord * dist);
	}
	
	/** Creates the in-world position for a node based on the node itself and the core position */
	public Vec3 getPositionFromNode(World world, int x, int y, int z, Vec3 core, Vec3 node, int meta) {
		float rotation = 0;
		if(meta == 12) rotation = 90F / 180F * (float) Math.PI;
		if(meta == 14) rotation = 180F / 180F * (float) Math.PI;
		if(meta == 13) rotation = 270F / 180F * (float) Math.PI;
		Vec3 copy = Vec3.createVectorHelper(node.xCoord, node.yCoord, node.zCoord);
		copy.rotateAroundY(rotation);
		return core.addVector(copy.xCoord, copy.yCoord, copy.zCoord);
	}
	
	public class RailDef {
		String name;
		public List<Vec3> nodes = new ArrayList();
		
		public RailDef(String name) {
			this.name = name;
		}
	}
}
