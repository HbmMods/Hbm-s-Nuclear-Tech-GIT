package com.hbm.main;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class ChunkLoaderManager {

	private static Map<Integer, Ticket> tickets = new HashMap<>();
	
	public static void loadTicket(Ticket ticket) {
		tickets.put(ticket.world.provider.dimensionId, ticket);
	}

	// Accepts a REGULAR position and turns it into a ChunkCoordIntPair
	public static void forceChunk(World world, int x, int z) {
		forceChunk(world, getChunkPosition(x, z));
	}

	public static void forceChunk(World world, ChunkCoordIntPair chunk) {
		Ticket ticket = getTicket(world);
		ForgeChunkManager.forceChunk(ticket, chunk);
	}

	public static void unforceChunk(World world, int x, int z) {
		unforceChunk(world, getChunkPosition(x, z));
	}

	public static void unforceChunk(World world, ChunkCoordIntPair chunk) {
		Ticket ticket = getTicket(world);
		ForgeChunkManager.unforceChunk(ticket, chunk);
	}

	private static Ticket getTicket(World world) {
		if(!tickets.containsKey(world.provider.dimensionId)) {
			Ticket ticket = ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.NORMAL);
			tickets.put(world.provider.dimensionId, ticket);
			return ticket;
		}

		return tickets.get(world.provider.dimensionId);
	}

	private static ChunkCoordIntPair getChunkPosition(int x, int z) {
		return new ChunkCoordIntPair(x >> 4, z >> 4);
	}

}
