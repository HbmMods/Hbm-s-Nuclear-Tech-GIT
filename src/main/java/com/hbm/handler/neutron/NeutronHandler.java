package com.hbm.handler.neutron;

import com.hbm.tileentity.machine.rbmk.RBMKDials;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;

import java.util.Map;


// General neutron handler because I didn't feel like having each handler class have its own interaction function.
public class NeutronHandler {

	private static int ticks = 0;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if(event.phase != TickEvent.Phase.START)
			return;

		// Freshen the node cache every `cacheTime` ticks to prevent huge RAM usage from idle nodes.
		int cacheTime = 20;
		boolean cacheClear = ticks >= cacheTime;
		if(cacheClear) ticks = 0;
		ticks++;

		// Remove `StreamWorld` objects if they have no streams.
		NeutronNodeWorld.removeEmptyWorlds();

		for(Map.Entry<World, NeutronNodeWorld.StreamWorld> world : NeutronNodeWorld.streamWorlds.entrySet()) {

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

			world.getValue().runStreamInteractions(world.getKey());
			world.getValue().removeAllStreams();

			if(cacheClear) world.getValue().cleanNodes();
		}
	}
}
