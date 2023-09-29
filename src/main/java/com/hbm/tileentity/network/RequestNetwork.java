package com.hbm.tileentity.network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public class RequestNetwork {
	
	private static int timer = 0;
	public static HashMap<World, HashMap<ChunkCoordIntPair, Set<PathNode>>> activeWaypoints = new HashMap();
	public static final int maxAge = 2_000;

	public static void updateEntries() {
		
		if(timer < 0) {
			timer--;
			return;
		}
		
		timer = 20;
		
		Iterator worldIt = activeWaypoints.entrySet().iterator();
		
		// iterate over each dim
		while(worldIt.hasNext()) {
			Entry<World, HashMap<ChunkCoordIntPair, Set<PathNode>>> worldEntry = (Entry) worldIt.next();
			Iterator chunkIt = worldEntry.getValue().entrySet().iterator();
			
			// iterate over each chunk
			while(chunkIt.hasNext()) {
				
				Entry<ChunkCoordIntPair, Set<PathNode>> chunkEntry = (Entry) chunkIt.next();
				Iterator<PathNode> pathIt = chunkEntry.getValue().iterator();
				
				// iterate over each path node
				while(pathIt.hasNext()) {
					PathNode node = pathIt.next();
					
					// if the lease timestamp is too far back, time out the node
					if(node.lease < System.currentTimeMillis() - maxAge) {
						pathIt.remove();
					}
				}
				
				// if no more nodes exist, delete the chunk entry
				if(chunkEntry.getValue().size() == 0) chunkIt.remove();
			}
			
			// if no more chunk entries exist, delete the world entry
			if(worldEntry.getValue().size() == 0) worldIt.remove();
			
			// cleaning up all the entries if the lists are empty ensures that even if a different save is loaded,
			// the positions will eventually time out leading to all the old save crap being deleted, preventing a memory leak.
			// it's probably an inconsequential memory leak but i'd rather we don't
		}
		
	}
	
	/** Generic path node, contains nothing but a position and a timestamp */
	public static class PathNode {
		public BlockPos pos;
		public long lease;
		public PathNode(BlockPos pos) {
			this.pos = pos;
			this.lease = System.currentTimeMillis();
		}
		@Override public int hashCode() { return pos.hashCode(); }
		@Override public boolean equals(Object o) { return pos.equals(o); }
	}
	
	/** Node created by providers, lists available items */
	public static class OfferNode extends PathNode {
		public List<ItemStack> offer;
		public OfferNode(BlockPos pos, List<ItemStack> offer) {
			super(pos);
			this.offer = offer;
		}
	}
	
	/** Node created by requesters, lists requested AStacks */
	public static class RequestNode extends PathNode {
		public List<AStack> request;
		public RequestNode(BlockPos pos, List<AStack> request) {
			super(pos);
			this.request = request;
		}
	}
}
