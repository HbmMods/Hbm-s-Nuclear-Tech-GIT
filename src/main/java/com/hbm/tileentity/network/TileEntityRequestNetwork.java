package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

/**
 * i can see it clearly
 * this simple idea, this concept of 4 individually acting objects performing basic tasks
 * it is all spiraling out of control
 * in a giant mess of nested generics, magic numbers and static global variables
 * may god have mercy on my soul
 * 
 * @author hbm
 *
 */
public class TileEntityRequestNetwork extends TileEntity {
	
	public static HashMap<World, HashMap<ChunkCoordIntPair, Set<BlockPos>>> activeWaypoints = new HashMap();
	public static HashMap<BlockPos, Long> lastActive = new HashMap();
	public static long lastWipe = 0;

	public Set<BlockPos> reachableNodes = new HashSet();
	public Set<BlockPos> knownNodes = new HashSet();
	public static final int maxRange = 24;
	public static final int maxAge = 2_000;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				BlockPos pos = getCoord();
				push(worldObj, pos);
				
				for(BlockPos known : knownNodes) {
					ParticleUtil.spawnDebugLine(worldObj,
							pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							(known.getX()  - pos.getX()) / 2D, (known.getY() - pos.getY()) / 2D, (known.getZ() - pos.getZ()) / 2D,
							reachableNodes.contains(known) ? 0x00ff00 : 0xff0000);
				}
				
				//rescan known nodes
				if(worldObj.getTotalWorldTime() % 40 == 0 && knownNodes.size() > 0) {
					
					BlockPos node = (BlockPos) new ArrayList(knownNodes).get(knownNodes.size() > 1 ? worldObj.rand.nextInt(knownNodes.size() - 1) : 0);
					
					if(node != null) {
						
						Long timestamp = lastActive.get(node);
						
						if(timestamp == null || timestamp < System.currentTimeMillis() - maxAge) {
							knownNodes.remove(node);
							reachableNodes.remove(node);
							lastActive.remove(node);
						} else if(!hasPath(worldObj, pos, node)) {
							reachableNodes.remove(node);
						} else {
							reachableNodes.add(node);
						}
					}
					
				//discover new nodes
				} else {

					Set<BlockPos> nodes = getAllLocalNodes(worldObj, pos.getX(), pos.getZ());
					
					for(BlockPos node : nodes) {
						
						if(!knownNodes.contains(node) && !node.equals(pos)) {
							knownNodes.add(node);
							if(hasPath(worldObj, pos, node)) reachableNodes.add(node);
							break;
						}
					}
				}
			}
		}
	}
	
	public BlockPos getCoord() {
		return new BlockPos(xCoord, yCoord + 1, zCoord);
	}
	
	/**
	 * Performs a bidirectional scan to see if the nodes have line of sight
	 * @param world
	 * @param pos1
	 * @param pos2
	 * @return
	 */
	public static boolean hasPath(World world, BlockPos pos1, BlockPos pos2) {
		Vec3 vec1 = Vec3.createVectorHelper(pos1.getX() + 0.5, pos1.getY() + 0.5, pos1.getZ() + 0.5);
		Vec3 vec2 = Vec3.createVectorHelper(pos2.getX() + 0.5, pos2.getY() + 0.5, pos2.getZ() + 0.5);
		Vec3 vec3 = vec1.subtract(vec2);
		if(vec3.lengthVector() > maxRange) return false;
		//for some fucking reason beyond any human comprehension, this function will randomly yield incorrect results but only from one side
		//therefore we just run the stupid fucking thing twice and then compare the results
		//thanks for this marvelous piece of programming, mojang
		MovingObjectPosition mop0 = world.func_147447_a(vec1, vec2, false, true, false);
		MovingObjectPosition mop2 = world.func_147447_a(vec2, vec1, false, true, false);
		return (mop0 == null || mop0.typeOfHit == mop0.typeOfHit.MISS) && (mop2 == null || mop2.typeOfHit == mop2.typeOfHit.MISS);
	}
	
	/**
	 * Adds the position to that chunk's node list.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void push(World world, BlockPos pos) {
		
		HashMap<ChunkCoordIntPair, Set<BlockPos>> coordMap = activeWaypoints.get(world);
		
		if(coordMap == null) {
			coordMap = new HashMap();
			activeWaypoints.put(world, coordMap);
		}
		
		ChunkCoordIntPair chunkPos = new ChunkCoordIntPair(pos.getX() >> 4, pos.getZ() >> 4);
		Set<BlockPos> posList = coordMap.get(chunkPos);
		
		if(posList == null) {
			posList = new HashSet();
			coordMap.put(chunkPos, posList);
		}
		
		posList.add(pos);
		
		lastActive.put(pos, System.currentTimeMillis());
	}
	
	/**
	 * Gets all active nodes in a 5x5 chunk area, centered around the given position.
	 * Used for finding neighbors to check connections to.
	 * @param world
	 * @param x
	 * @param z
	 * @return
	 */
	public static Set<BlockPos> getAllLocalNodes(World world, int x, int z) {
		
		Set<BlockPos> nodes = new HashSet();

		x >>= 4;
		z >>= 4;
		
		HashMap<ChunkCoordIntPair, Set<BlockPos>> coordMap = activeWaypoints.get(world);
		
		if(coordMap == null) return nodes;
		
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				
				Set<BlockPos> posList = coordMap.get(new ChunkCoordIntPair(x + i, z + j));
				
				if(posList != null) for(BlockPos node : posList) {
					Long timestamp = lastActive.get(node);
					
					if(timestamp != null && timestamp >= System.currentTimeMillis() - maxAge) {
						nodes.add(node);
					}
				}
			}
		}
		
		return nodes;
	}

}
