package com.hbm.handler.neutron;

import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.util.fauxpointtwelve.BlockPos;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// General neutron handler because I didn't feel like having each handler class have its own interaction function.
public class NeutronHandler {

	private static int ticks = 0;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if(event.phase != TickEvent.Phase.START)
			return;

		// Remove `StreamWorld` objects if they have no streams.
		{ // aflghdkljghlkbhfjkghgilurbhlkfjghkffdjgn
			List<World> toRemove = new ArrayList<>();
			NeutronNodeWorld.streamWorlds.forEach((world, streamWorld) -> {
				if (streamWorld.streams.isEmpty())
					toRemove.add(world);
			});

			for (World world : toRemove) {
				NeutronNodeWorld.streamWorlds.remove(world);
			}
		}

		for (Map.Entry<World, NeutronNodeWorld.StreamWorld> world : NeutronNodeWorld.streamWorlds.entrySet()) {

			// Gamerule caching because this apparently is kinda slow?
			// meh, good enough
			RBMKNeutronHandler.reflectorEfficiency = RBMKDials.getReflectorEfficiency(world.getKey());
			RBMKNeutronHandler.absorberEfficiency = RBMKDials.getAbsorberEfficiency(world.getKey());
			RBMKNeutronHandler.moderatorEfficiency = RBMKDials.getModeratorEfficiency(world.getKey());

			// I hate this.
			// this broke everything because it was ONE OFF
			// IT'S NOT THE TOTAL HEIGHT IT'S THE AMOUNT OF BLOCKS ABOVE AAAAAAAAAAAAA
			RBMKNeutronHandler.columnHeight = RBMKDials.getColumnHeight(world.getKey()) + 1;
			RBMKNeutronHandler.fluxRange = RBMKDials.getFluxRange(world.getKey());

			for (NeutronStream stream : world.getValue().streams) {
				stream.runStreamInteraction(world.getKey());
			}
			world.getValue().removeAllStreams();
		}

		// Freshen the node cache every `cacheTime` ticks to prevent huge RAM usage from idle nodes.
		int cacheTime = 20;
		if (ticks >= cacheTime) {
			ticks = 0;
			List<BlockPos> toRemove = new ArrayList<>();
			for (NeutronNode cachedNode : NeutronNodeWorld.nodeCache.values()) {
				if (cachedNode.type == NeutronStream.NeutronType.RBMK) {
					RBMKNeutronHandler.RBMKNeutronNode node = (RBMKNeutronHandler.RBMKNeutronNode) cachedNode;
					toRemove.addAll(node.checkNode());
				}
					/* TODO: actually do this and uncache pile nodes
					if (cachedNode.type == NeutronStream.NeutronType.PILE) {
						PileNeutronNode node = (PileNeutronNode) cachedNode;
						toRemove.addAll(node.checkNode());
					}
					*/
			}

			toRemove.forEach(NeutronNodeWorld::removeNode);

		}
		ticks++;
	}
}
