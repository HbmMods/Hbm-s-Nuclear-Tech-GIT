package api.hbm.energymk2;

import com.hbm.interfaces.NotableComments;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PowerNetProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.world.World;

/**
 * The dead fucking corpse of nodespace MK1.
 * A fantastic proof of concept, but ultimately it was killed for being just not that versatile.
 * This class is mostly just a compatibility husk that should allow uninodespace to slide into the mod with as much lubrication as it deserves.
 * 
 * @author hbm
 */
public class Nodespace {
	
	public static final PowerNetProvider THE_POWER_PROVIDER = new PowerNetProvider();
	
	@Deprecated public static PowerNode getNode(World world, int x, int y, int z) {
		return (PowerNode) UniNodespace.getNode(world, x, y, z, THE_POWER_PROVIDER);
	}
	
	@Deprecated public static void createNode(World world, PowerNode node) {
		UniNodespace.createNode(world, node);
	}
	
	@Deprecated public static void destroyNode(World world, int x, int y, int z) {
		UniNodespace.destroyNode(world, x, y, z, THE_POWER_PROVIDER);
	}

	@NotableComments
	public static class PowerNode extends GenNode<PowerNetMK2> {
		
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
		
		public PowerNode(BlockPos... positions) {
			super(THE_POWER_PROVIDER, positions);
			this.positions = positions;
		}
		
		@Override
		public PowerNode setConnections(DirPos... connections) {
			super.setConnections(connections);
			return this;
		}
	}
}
