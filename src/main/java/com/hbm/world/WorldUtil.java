package com.hbm.world;

import com.hbm.packet.BiomeSyncPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class WorldUtil {

	public static void setBiome(World world, int x, int z, BiomeGenBase biome) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)] = (byte)(biome.biomeID & 255);
	}
	
	public static void syncBiomeChange(World world, Chunk chunk) {
		/* "let's not make all this valuable information accessible, at all, hehe hoho huehue" -mojank, probably */
		/*if(!(world instanceof WorldServer)) return;
		WorldServer server = (WorldServer) world;
		PlayerManager man = server.getPlayerManager();
		Method getOrCreateChunkWatcher = ReflectionHelper.findMethod(PlayerManager.class, man, new String[] {"getOrCreateChunkWatcher"}, int.class, int.class, boolean.class);
		int x = chunk.getChunkCoordIntPair().chunkXPos;
		int z = chunk.getChunkCoordIntPair().chunkZPos;
		PlayerManager.PlayerInstance playerinstance = (PlayerInstance) getOrCreateChunkWatcher.invoke(man, x, z, false);*/
		
		/* this sucks ass */
		ChunkCoordIntPair coord = chunk.getChunkCoordIntPair();
		PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(coord.chunkXPos, coord.chunkZPos, chunk.getBiomeArray()), new TargetPoint(world.provider.dimensionId, coord.getCenterXPos(), 128, coord.getCenterZPosition() /* who named you? */, 1024D));
	}

	public static void syncBiomeChange(World world, int x, int z) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		byte biome = chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)];
		PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(x, z, chunk.getBiomeArray()), new TargetPoint(world.provider.dimensionId, x, 128, z, 1024D));
	}
}
