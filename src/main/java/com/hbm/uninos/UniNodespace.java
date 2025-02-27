package com.hbm.uninos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class UniNodespace {
	
	public static HashMap<World, UniNodeWorld> worlds = new HashMap();
	public static Set<NodeNet> activeNodeNets = new HashSet<>();
	
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
	
	public static void updateNodespace() {
		
		for(World world : MinecraftServer.getServer().worldServers) {
			UniNodeWorld nodeWorld = worlds.get(world);

			if(nodeWorld == null) continue;
			
			for(Entry<Pair<BlockPos, INetworkProvider>, GenNode> entry : nodeWorld.nodes.entrySet()) {
				GenNode node = entry.getValue();
				INetworkProvider provider = entry.getKey().getValue();
				if(!node.hasValidNet() || node.recentlyChanged) {
					checkNodeConnection(world, node, provider);
					node.recentlyChanged = false;
				}
			}
		}
		
		updateNetworks();
	}
	
	private static void updateNetworks() {

		for(NodeNet net : activeNodeNets) net.resetTrackers(); //reset has to be done before everything else
		for(NodeNet net : activeNodeNets) net.update();
	}
	
	/** Goes over each connection point of the given node, tries to find neighbor nodes and to join networks with them */
	private static void checkNodeConnection(World world, GenNode node, INetworkProvider provider) {
		
		for(DirPos con : node.connections) {
			GenNode conNode = getNode(world, con.getX(), con.getY(), con.getZ(), provider); // get whatever neighbor node intersects with that connection
			if(conNode != null) { // if there is a node at that place
				if(conNode.hasValidNet() && conNode.net == node.net) continue; // if the net is valid and both nodes have the same net, skip
				if(checkConnection(conNode, con, false)) {
					connectToNode(node, conNode);
				}
			}
		}
		
		if(node.net == null || !node.net.isValid()) provider.provideNetwork().joinLink(node);
	}
	
	/** Checks if the node can be connected to given the DirPos, skipSideCheck will ignore the DirPos' direction value */
	public static boolean checkConnection(GenNode connectsTo, DirPos connectFrom, boolean skipSideCheck) {
		for(DirPos revCon : connectsTo.connections) {
			if(revCon.getX() - revCon.getDir().offsetX == connectFrom.getX() && revCon.getY() - revCon.getDir().offsetY == connectFrom.getY() && revCon.getZ() - revCon.getDir().offsetZ == connectFrom.getZ() && (revCon.getDir() == connectFrom.getDir().getOpposite() || skipSideCheck)) {
				return true;
			}
		}
		return false;
	}
	
	/** Links two nodes with different or potentially no networks */
	private static void connectToNode(GenNode origin, GenNode connection) {
		
		if(origin.hasValidNet() && connection.hasValidNet()) { // both nodes have nets, but the nets are different (previous assumption), join networks
			if(origin.net.links.size() > connection.net.links.size()) {
				origin.net.joinNetworks(connection.net);
			} else {
				connection.net.joinNetworks(origin.net);
			}
		} else if(!origin.hasValidNet() && connection.hasValidNet()) { // origin has no net, connection does, have origin join connection's net
			connection.net.joinLink(origin);
		} else if(origin.hasValidNet() && !connection.hasValidNet()) { // ...and vice versa
			origin.net.joinLink(connection);
		}
	}
	
	public static class UniNodeWorld {
		
		public HashMap<Pair<BlockPos, INetworkProvider>, GenNode> nodes = new HashMap();
		
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
