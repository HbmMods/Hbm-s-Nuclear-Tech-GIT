package com.hbm.handler.radiation;

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public abstract class ChunkRadiationHandler {

	/**
	 * Updates the radiation system, i.e. all worlds.
	 * Doesn't need parameters because it governs the ENTIRE system.
	 */
	public abstract void updateSystem();
	public abstract float getRadiation(World world, int x, int y, int z);
	public abstract void setRadiation(World world, int x, int y, int z, float rad);
	public abstract void incrementRad(World world, int x, int y, int z, float rad);
	public abstract void decrementRad(World world, int x, int y, int z, float rad);
	public abstract void clearSystem(World world);

	/*
	 * Proxy'd event handlers
	 */
	public void receiveWorldLoad(WorldEvent.Load event) { }
	public void receiveWorldUnload(WorldEvent.Unload event) { }
	public void receiveWorldTick(TickEvent.ServerTickEvent event) { }
	
	public void receiveChunkLoad(ChunkDataEvent.Load event) { }
	public void receiveChunkSave(ChunkDataEvent.Save event) { }
	public void receiveChunkUnload(ChunkEvent.Unload event) { }
	
	public void handleWorldDestruction() { }
}