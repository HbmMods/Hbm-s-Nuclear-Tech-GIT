package api.hbm.energymk2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
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
			markNeigbors(world, node);
		}
	}
	
	/** Grabs all neighbor nodes from the given node's connection points and removes them from the network entirely, forcing a hard reconnect */
	private static void markNeigbors(World world, PowerNode node) {
		
		for(DirPos con : node.connections) {
			PowerNode conNode = getNode(world, con.getX(), con.getY(), con.getZ());
			if(conNode != null && conNode.hasValidNet()) conNode.net.leaveLink(conNode);
		}
	}
	
	/** Goes over each node and manages connections */
	public static void updateNodespace() {
		
		for(World world : MinecraftServer.getServer().worldServers) {
			NodeWorld nodes = worlds.get(world);
			
			for(Entry<BlockPos, PowerNode> entry : nodes.nodes.entrySet()) {
				PowerNode node = entry.getValue();
				if(!node.hasValidNet()) {
					checkNodeConnection(world, node);
				}
				
				if(node.hasValidNet()) {
					
					for(BlockPos pos : node.positions) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "marker");
						data.setInteger("color", 0x00ff00);
						data.setInteger("expires", 250);
						data.setDouble("dist", 15D);
						data.setString("label", "" + node.net.hashCode());
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.getX(), pos.getY(), pos.getZ()), new TargetPoint(world.provider.dimensionId, pos.getX(), pos.getY(), pos.getZ(), 50));
					}
				}
			}
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
			origin.net.joinNetworks(connection.net);
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
	}
}
