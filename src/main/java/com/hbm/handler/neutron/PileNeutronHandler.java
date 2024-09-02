package com.hbm.handler.neutron;

import com.hbm.tileentity.machine.pile.TileEntityPileBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PileNeutronHandler {

	public static class PileNeutronNode extends NeutronNode {

		public PileNeutronNode(TileEntityPileBase tile) {
			super(tile, NeutronStream.NeutronType.RBMK);
		}

	}

	// The big one!! Runs all interactions for neutrons.
	public static void runAllInteractions() {

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

			for (NeutronStream stream : world.getValue().streams) {
				stream.runStreamInteraction(world.getKey());
			}
			world.getValue().removeAllStreams();
		}
	}
}
