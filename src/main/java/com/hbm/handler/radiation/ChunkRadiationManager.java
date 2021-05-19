package com.hbm.handler.radiation;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkRadiationManager {
	
	public static ChunkRadiationHandler proxy = new ChunkRadiationHandlerSimple();

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		proxy.receiveWorldLoad(event);
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		proxy.receiveWorldUnload(event);
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkDataEvent.Load event) {
		proxy.receiveChunkLoad(event);
	}
	
	@SubscribeEvent
	public void onChunkSave(ChunkDataEvent.Save event) {
		proxy.receiveChunkSave(event);
	}
	
	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload event) {
		proxy.receiveChunkUnload(event);
	}
	
	int eggTimer = 0;
	
	@SubscribeEvent
	public void updateSystem(TickEvent.ServerTickEvent event) {
		
		if(event.side == Side.SERVER && event.phase == Phase.END) {
			
			eggTimer++;
			
			if(eggTimer >= 20) {
				proxy.updateSystem();
				eggTimer = 0;
			}
		}
	}
}
