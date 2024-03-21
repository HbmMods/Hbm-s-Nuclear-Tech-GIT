package api.hbm.energymk2;

import java.util.HashMap;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.world.World;

/**
 * The "Nodespace" is an intermediate, "ethereal" layer of abstraction that tracks nodes (i.e. cables) even when they are no longer loaded, allowing continued operation even when unloaded
 * @author hbm
 *
 */
public class Nodespace {
	
	/** Contains all "NodeWorld" instances, i.e. lists of nodes existing per world */
	public static HashMap<World, NodeWorld> worlds = new HashMap();
	
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
			for(BlockPos pos : node.positions) {
				nodes.remove(pos);
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
		
		public PowerNode(BlockPos... positions) {
			this.positions = positions;
		}
		
		public PowerNode setConnections(DirPos... connections) {
			this.connections = connections;
			return this;
		}
	}
}
