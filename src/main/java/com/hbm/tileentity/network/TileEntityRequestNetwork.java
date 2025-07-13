package com.hbm.tileentity.network;

import java.util.HashMap;
import java.util.Iterator;

import com.hbm.interfaces.NotableComments;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.util.HashedSet;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

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
@NotableComments
public abstract class TileEntityRequestNetwork extends TileEntityLoadedBase {

	public HashedSet<PathNode> reachableNodes = new HashedSet();
	public HashedSet<PathNode> knownNodes = new HashedSet();
	public static final int maxRange = 24;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				BlockPos pos = getCoord();

				PathNode newNode = createNode(pos);
				if(this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) newNode.active = false;
				// push new node
				push(worldObj, newNode);

				// remove known nodes that no longer exist
				// since we can assume a sane number of nodes to exist at any given time, we can run this check in full every second
				Iterator<PathNode> it = knownNodes.iterator();
				HashedSet<PathNode> localNodes = this.getAllLocalNodes(worldObj, xCoord, zCoord, 2); // this bit may spiral into multiple nested hashtable lookups but it's limited to only a few chunks so it shouldn't be an issue
				localNodes.remove(pos);

				while(it.hasNext()) {
					PathNode node = it.next();
					if(!localNodes.contains(node)) {
						reachableNodes.remove(node);
						it.remove();
					}
				}

				// draw debug crap
				for(PathNode known : knownNodes) {
					if(reachableNodes.contains(known)) ParticleUtil.spawnDroneLine(worldObj,
							pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
							(known.pos.getX()  - pos.getX()) / 2D, (known.pos.getY() - pos.getY()) / 2D, (known.pos.getZ() - pos.getZ()) / 2D,
							reachableNodes.contains(known) ? 0x00ff00 : 0xff0000);
				}

				/*NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "debug");
				data.setInteger("color", 0x0000ff);
				data.setFloat("scale", 1.5F);
				data.setString("text", knownNodes.size() + " / " + reachableNodes.size() + " / " + localNodes.size());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
				*/

				//both following checks run the `hasPath` function which is costly, so it only runs one op at a time

				//rescan known nodes
				for(PathNode known : knownNodes) {

					if(!hasPath(worldObj, pos, known.pos)) {
						reachableNodes.remove(known);
					} else {
						reachableNodes.add(known);
					}
				}

				//discover new nodes
				int newNodeLimit = 5;
				for(PathNode node : localNodes) {

					if(!knownNodes.contains(node) && !node.equals(pos)) {
						newNodeLimit--;
						knownNodes.add(node);
						if(hasPath(worldObj, pos, node.pos)) reachableNodes.add(node);
					}

					if(newNodeLimit <= 0) break;
				}
			}
		}
	}

	public abstract PathNode createNode(BlockPos pos);

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
	 */
	public static void push(World world, PathNode node) {

		HashMap<ChunkCoordIntPair, HashedSet<PathNode>> coordMap = RequestNetwork.activeWaypoints.get(world);

		if(coordMap == null) {
			coordMap = new HashMap();
			RequestNetwork.activeWaypoints.put(world, coordMap);
		}

		ChunkCoordIntPair chunkPos = new ChunkCoordIntPair(node.pos.getX() >> 4, node.pos.getZ() >> 4);
		HashedSet<PathNode> posList = coordMap.get(chunkPos);

		if(posList == null) {
			posList = new HashedSet();
			coordMap.put(chunkPos, posList);
		}

		posList.add(node);
	}

	/**
	 * Gets all active nodes in a 5x5 chunk area, centered around the given position.
	 * Used for finding neighbors to check connections to.
	 * @param world
	 * @param x
	 * @param z
	 * @return
	 */
	public static HashedSet<PathNode> getAllLocalNodes(World world, int x, int z, int range) {

		HashedSet<PathNode> nodes = new HashedSet();
		HashedSet<BlockPos> pos = new HashedSet();

		x >>= 4;
		z >>= 4;

		HashMap<ChunkCoordIntPair, HashedSet<PathNode>> coordMap = RequestNetwork.activeWaypoints.get(world);

		if(coordMap == null) return nodes;

		for(int i = -range; i <= range; i++) {
			for(int j = -range; j <= range; j++) {

				HashedSet<PathNode> nodeList = coordMap.get(new ChunkCoordIntPair(x + i, z + j));

				if(nodeList != null) for(PathNode node : nodeList) {
					if(!pos.contains(node.pos)) {
						nodes.add(node);
						//pos.add(node.pos);
					}
				}
			}
		}

		return nodes;
	}
}
