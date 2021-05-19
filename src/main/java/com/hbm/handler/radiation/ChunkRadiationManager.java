package com.hbm.handler.radiation;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkRadiationManager {
	
	public static ChunkRadiationHandler proxy;

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
	
	@SubscribeEvent
	public void updateSystem(TickEvent.ServerTickEvent event) {
		
	}
}
