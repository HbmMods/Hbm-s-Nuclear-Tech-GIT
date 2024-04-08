package api.hbm.energymk2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * The "Nodespace" is an intermediate, "ethereal" layer of abstraction that tracks nodes (i.e. cables) even when they are no longer loaded, allowing continued operation even when unloaded
 * @author hbm
 *
 */
public class Nodespace {
	
	/** Contains all "NodeWorld" instances, i.e. lists of nodes existing per world */
	public static HashMap<World, NodeWorld> worlds = new HashMap();
	public static Set<PowerNetMK2> activePowerNets = new HashSet();
	
	public static PowerNode getNode(World world, int x, int y, int z) {
		NodeWorld nodeWorld = worlds.get(world);
		if(nodeWorld != null) return nodeWorld.nodes.get(new BlockPos(x, y, z));
		return null;
	}
	
	public static void createNode(World world, PowerNode node) {
		NodeWorld nodeWorld = worlds.get(world);
		if(nodeWorld == null) {
			nodeWorld = new NodeWorld();
			worlds.put(world, nodeWorld);
		}
		nodeWorld.pushNode(node);
	}
	
	public static void destroyNode(World world, int x, int y, int z) {
		PowerNode node = getNode(world, x, y, z);
		if(node != null) {
			worlds.get(world).popNode(node);
		}
	}
	
	/** Goes over each node and manages connections */
	public static void updateNodespace() {
		
		for(World world : MinecraftServer.getServer().worldServers) {
			NodeWorld nodes = worlds.get(world);
			
			for(Entry<BlockPos, PowerNode> entry : nodes.nodes.entrySet()) {
				PowerNode node = entry.getValue();
				if(!node.hasValidNet() || node.recentlyChanged) {
					checkNodeConnection(world, node);
					node.recentlyChanged = false;
				}
				
				/*if(node.hasValidNet()) {
					
					for(BlockPos pos : node.positions) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "marker");
						data.setInteger("color", node.net.hashCode() % 0xffffff);
						data.setInteger("expires", 250);
						data.setDouble("dist", 50D);
						data.setString("label", "" + node.net.links.size());
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.getX(), pos.getY(), pos.getZ()), new TargetPoint(world.provider.dimensionId, pos.getX(), pos.getY(), pos.getZ(), 50));
					}
				}*/
			}
		}
		
		updatePowerNets();
	}
	
	private static void updatePowerNets() {
		
		for(PowerNetMK2 net : activePowerNets) {
			net.transferPower();
		}
	}
	
	/** Goes over each connection point of the given node, tries to find neighbor nodes and to join networks with them */
	private static void checkNodeConnection(World world, PowerNode node) {
		
		for(DirPos con : node.connections) {
			
			PowerNode conNode = getNode(world, con.getX(), con.getY(), con.getZ()); // get whatever neighbor node intersects with that connection
			
			if(conNode != null) { // if there is a node at that place
				
				if(conNode.hasValidNet() && conNode.net == node.net) continue; // if the net is valid and both nodes have the same net, skip
				
				for(DirPos revCon : conNode.connections) { // check if neighbor node also has a valid reverse connection
					
					// god i hope i didn't fuck this up my brain is hurting already
					if(revCon.getX() - revCon.getDir().offsetX == con.getX() && revCon.getY() - revCon.getDir().offsetY == con.getY() && revCon.getZ() - revCon.getDir().offsetZ == con.getZ() && revCon.getDir() == con.getDir().getOpposite()) {
						connectToNode(node, conNode);
						break;
					}
				}
			}
		}
		
		if(node.net == null || !node.net.isValid()) new PowerNetMK2().joinLink(node);
	}
	
	/** Links two nodes with different or potentially no networks */
	private static void connectToNode(PowerNode origin, PowerNode connection) {
		
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
	
	public static class NodeWorld {

		/** Contains a map showing where each node is, a node is every spot that a cable exists at.
		 * Instead of the old proxy system, things like substation now create multiple nodes at their connection points */
		public static HashMap<BlockPos, PowerNode> nodes = new HashMap();
		
		/** Adds a node at all its positions to the nodespace */
		public void pushNode(PowerNode node) {
			for(BlockPos pos : node.positions) {
				nodes.put(pos, node);
			}
		}
		
		/** Removes the specified node from all positions from nodespace */
		public void popNode(PowerNode node) {
			if(node.net != null) node.net.destroy();
			for(BlockPos pos : node.positions) {
				nodes.remove(pos);
				node.expired = true;
			}
		}
		
		/** Grabs the node at one position, then removes it from all positions it occupies */
		public void popNode(BlockPos pos) {
			PowerNode node = nodes.get(pos);
			if(node != null) popNode(node);
		}
	}
	
	public static class PowerNode {
		
		public BlockPos[] positions;
		public DirPos[] connections;
		public PowerNetMK2 net;
		public boolean expired = false;
		/**
		 * Okay so here's the deal: The code has shit idiot brain fungus. I don't know why. I re-tested every part involved several times.
		 * I don't know why. But for some reason, during neighbor checks, on certain arbitrary fucking places, the joining operation just fails.
		 * Disallowing nodes to create new networks fixed the problem completely, which is hardly surprising since they wouldn't be able to make
		 * a new net anyway and they will re-check neighbors until a net is found, so the solution is tautological in nature. So I tried limiting
		 * creation of new networks. Didn't work. So what's there left to do? Hand out a mark to any node that has changed networks, and let those
		 * recently modified nodes do another re-check. This creates a second layer of redundant operations, and in theory doubles (in practice,
		 * it might be an extra 20% due to break-off section sizes) the amount of CPU time needed for re-building the networks after joining or
		 * breaking, but it seems to allow those parts to connect back to their neighbor nets as they are supposed to. I am not proud of this solution,
		 * this issue shouldn't exist to begin with and I am going fucking insane but it is what it is.
		 */
		public boolean recentlyChanged = true;
		
		public PowerNode(BlockPos... positions) {
			this.positions = positions;
		}
		
		public PowerNode setConnections(DirPos... connections) {
			this.connections = connections;
			return this;
		}
		
		public boolean hasValidNet() {
			return this.net != null && this.net.isValid();
		}
		
		public void setNet(PowerNetMK2 net) {
			this.net = net;
			this.recentlyChanged = true;
		}
	}
}
