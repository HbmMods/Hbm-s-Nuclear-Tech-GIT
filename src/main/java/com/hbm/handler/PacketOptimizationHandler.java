package com.hbm.handler;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.TEDataRequestPacket;
import com.hbm.tileentity.TileEntityLoadedBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.world.ChunkEvent;

import java.util.Map;

/**
 * General handler for packet optimizations.
 * @author BallOfEnergy01
 */
public class PacketOptimizationHandler {

	/**
	 * Fired on the server when a client enters a chunk.
	 * <p>
	 * --- SERVER ONLY ---
	 * @param event ChunkEvent.Load event.
	 */
	public static void onChunkEntered(EntityEvent.EnteringChunk event) {
		World world = event.entity.worldObj;
		if (!world.isRemote && event.entity instanceof EntityPlayerMP) {
			// Send data for every surrounding chunk *taking into account previous chunks*.
			// I promise this works...

			boolean movedOnXAxis = event.newChunkX != event.oldChunkX;

			// Precalculates the previous chunks.
			// This system operates like the below diagram:
			/*
			- I I X -
			- I P O -
			- I I X -
			 */
			// `I` is included chunks, meaning chunks that are to be force-sent to the player.
			// `P` is the player, `O` is the old chunk (not to be included in the sent data, as it would have already been sent when
			// the chunk was originally entered), and `X` are excluded chunks. These chunks also would have already been sent.
			// This is an extremely primitive but easy-to-implement form of caching for the chunk data, though it
			// isn't smart enough to last any more than a single chunk entry.
			int firstChunkX = movedOnXAxis ? event.oldChunkX : event.oldChunkX + 1;
			int firstChunkZ = movedOnXAxis ? event.oldChunkZ + 1 : event.oldChunkZ;
			int secondChunkX = movedOnXAxis ? event.oldChunkX : event.oldChunkX - 1;
			int secondChunkZ = movedOnXAxis ? event.oldChunkZ - 1 : event.oldChunkZ;

			for (byte xOff = -1; xOff <= 1; xOff++) {
				for (byte zOff = -1; zOff <= 1; zOff++) {
					int xCoord = event.newChunkX + xOff;
					int zCoord = event.newChunkZ + zOff;
					if (xCoord == event.oldChunkX && zCoord == event.oldChunkZ)
						continue;
					if (xCoord == firstChunkX && zCoord == firstChunkZ)
						continue;
					if (xCoord == secondChunkX && zCoord == secondChunkZ)
						continue;
					forceSendChunk((EntityPlayerMP) event.entity, xCoord, zCoord);
				}
			}
		}
	}

	/**
	 * Forces a chunk's (NTM) TEs to be sent to a player.
	 * <p>
	 * This is called by both the `TEDataRequestPacket` handler and on the server for chunk edge-case detection.
	 * @param player Player to force-send data to.
	 * @param x X coordinate of the chunk.
	 * @param z Z coordinate of the chunk.
	 */
	public static void forceSendChunk(EntityPlayerMP player, int x, int z) {

		int distanceSq = getDistanceSq(x, z, player.chunkCoordX, player.chunkCoordZ);
		int maxDistance = MinecraftServer.getServer().getConfigurationManager().getViewDistance() + 2;
		// The client shouldn't be able to request this far out.
		if (distanceSq > maxDistance * maxDistance)
			return;

		Chunk requestedChunk = player.worldObj.getChunkFromChunkCoords(x, z);
		@SuppressWarnings("unchecked") Map<ChunkCoordinates, TileEntity> teMap = requestedChunk.chunkTileEntityMap;
		for (TileEntity tileEntity : teMap.values()) {
			if (!(tileEntity instanceof TileEntityLoadedBase))
				continue;
			((TileEntityLoadedBase) tileEntity).playerNeedsData(player);
			//MainRegistry.logger.info("{} requested TE data for chunk ({}, {}).", player.getCommandSenderName(), x, z);
		}
	}

	/**
	 * Get squared distance between two points. Used because mojank is stupid.
	 * @param x1 x1
	 * @param z1 z1
	 * @param x2 x2
	 * @param z2 z2
	 * @return Squared distance between points.
	 */
	private static int getDistanceSq(int x1, int z1, int x2, int z2) {
		int dx = x1 - x2;
		int dz = z1 - z2;
		return dx * dx + dz * dz;
	}

	/**
	 * Fired on a client when a new chunk is loaded.
	 * <p>
	 * --- CLIENT ONLY ---
	 * @param event ChunkEvent.Load event.
	 */
	public static void onChunkLoad(ChunkEvent.Load event) {
		if (event.world.isRemote) {
			Chunk chunk = event.getChunk();
			PacketDispatcher.wrapper.sendToServer(new TEDataRequestPacket(chunk.xPosition, chunk.zPosition));
		}
	}
}
