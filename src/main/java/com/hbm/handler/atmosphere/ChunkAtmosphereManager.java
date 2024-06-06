package com.hbm.handler.atmosphere;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkAtmosphereManager {
    
    public static ChunkAtmosphereHandler proxy = new ChunkAtmosphereHandler();

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        proxy.receiveWorldLoad(event);
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        proxy.receiveWorldUnload(event);
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        proxy.receiveBlockPlaced(event);
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        proxy.receiveBlockBroken(event);
    }

}
