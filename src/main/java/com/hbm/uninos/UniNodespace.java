package com.hbm.uninos;

import java.util.HashMap;

import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.world.World;

public class UniNodespace {
	
	public static HashMap<World, UniNodeWorld> worlds = new HashMap();
	
	public static GenNode getNode(World world, int x, int y, int z, INetworkProvider type) {
		UniNodeWorld nodeWorld = worlds.get(world);
		if(nodeWorld != null) return nodeWorld.nodes.get(new Pair(new BlockPos(x, y, z), type));
		return null;
	}
	
	public static void createNode(World world, GenNode node) {
		UniNodeWorld nodeWorld = worlds.get(world);
		if(nodeWorld == null) {
			nodeWorld = new UniNodeWorld();
			worlds.put(world, nodeWorld);
		}
		nodeWorld.pushNode(node);
	}
	
	public static void destroyNode(World world, int x, int y, int z, INetworkProvider type) {
		GenNode node = getNode(world, x, y, z, type);
		if(node != null) {
			worlds.get(world).popNode(node);
		}
	}
	
	public static class UniNodeWorld {
		
		public HashMap<Pair<BlockPos, INetworkProvider>, GenNode<INetworkProvider>> nodes = new HashMap();
		
		/** Adds a node at all its positions to the nodespace */
		public void pushNode(GenNode node) {
			for(BlockPos pos : node.positions) {
				nodes.put(new Pair(pos, node.networkProvider), node);
			}
		}
		
		/** Removes the specified node from all positions from nodespace */
		public void popNode(GenNode node) {
			if(node.net != null) node.net.destroy();
			for(BlockPos pos : node.positions) {
				nodes.remove(new Pair(pos, node.networkProvider));
			}
			node.expired = true;
		}
	}
}
