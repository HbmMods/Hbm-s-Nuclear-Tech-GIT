package com.hbm.world;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.BiomeSyncPacket;
import com.hbm.util.Compat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.chunkio.ChunkIOExecutor;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class WorldUtil {

	private static final MethodHandle getBiomeShortHandle;

	static {
		if(Loader.isModLoaded(Compat.MOD_EIDS)) {
			try {
				MethodHandles.Lookup lookup = MethodHandles.publicLookup();
				MethodType methodType = MethodType.methodType(short[].class);
				getBiomeShortHandle = lookup.findVirtual(Chunk.class, "getBiomeShortArray", methodType);
			} catch(Exception e) {
				throw new AssertionError();
			}
		} else {
			getBiomeShortHandle = null;
		}
	}

	public static void setBiome(World world, int x, int z, BiomeGenBase biome) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		if(Loader.isModLoaded(Compat.MOD_EIDS)) {
			short[] array = getBiomeShortArray(chunk);
			array[(z & 15) << 4 | x & 15] = (short) biome.biomeID;
		} else {
			chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)] = (byte)(biome.biomeID & 255);
		}
		chunk.isModified = true;
	}

	public static void syncBiomeChange(World world, int x, int z) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		if(Loader.isModLoaded(Compat.MOD_EIDS)) {
			PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(x >> 4, z >> 4, getBiomeShortArray(chunk)), new TargetPoint(world.provider.dimensionId, x, 128, z, 1024D));
		} else {
			PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(x >> 4, z >> 4, chunk.getBiomeArray()), new TargetPoint(world.provider.dimensionId, x, 128, z, 1024D));
		}
	}

	public static void syncBiomeChangeBlock(World world, int x, int z) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		if(Loader.isModLoaded(Compat.MOD_EIDS)) {
			short biome = getBiomeShortArray(chunk)[(z & 15) << 4 | (x & 15)];
			PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(x, z, biome), new TargetPoint(world.provider.dimensionId, x, 128, z, 1024D));
		} else {
			byte biome = chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)];
			PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(x, z, biome), new TargetPoint(world.provider.dimensionId, x, 128, z, 1024D));
		}
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
		if(Loader.isModLoaded(Compat.MOD_EIDS)) {
			PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(coord.chunkXPos, coord.chunkZPos, getBiomeShortArray(chunk)), new TargetPoint(world.provider.dimensionId, coord.getCenterXPos(), 128, coord.getCenterZPosition() /* who named you? */, 1024D));
		} else {
			PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(coord.chunkXPos, coord.chunkZPos, chunk.getBiomeArray()), new TargetPoint(world.provider.dimensionId, coord.getCenterXPos(), 128, coord.getCenterZPosition() /* who named you? */, 1024D));
		}
	}

	public static short[] getBiomeShortArray(Chunk chunk) {
		try {
			return (short[]) getBiomeShortHandle.invokeExact(chunk);
		} catch(Throwable ex) {
			throw new AssertionError();
		}
	}

	/**Chunkloads the chunk the entity is going to spawn in and then spawns it
	 * @param entity The entity to be spawned**/

	/*fun fact: this is based off of joinEntityInSurroundings in World
	  however, since mojang is staffed by field mice, that function is client side only and half-baked
	 */
	public static void loadAndSpawnEntityInWorld(Entity entity) {

		World world = entity.worldObj;
		int chunkX = MathHelper.floor_double(entity.posX / 16.0D);
		int chunkZ = MathHelper.floor_double(entity.posZ / 16.0D);
		byte loadRadius = 2;

		for (int k = chunkX - loadRadius; k <= chunkX + loadRadius; ++k)
		{
			for (int l = chunkZ - loadRadius; l <= chunkZ + loadRadius; ++l)
			{
				world.getChunkFromChunkCoords(k, l);
			}
		}

		if (!world.loadedEntityList.contains(entity))
		{
			if (!MinecraftForge.EVENT_BUS.post(new EntityJoinWorldEvent(entity, world)))
			{
				world.getChunkFromChunkCoords(chunkX, chunkZ).addEntity(entity);
				world.loadedEntityList.add(entity);
				world.onEntityAdded(entity);
			}
		}
	}
	
	public static Chunk provideChunk(WorldServer world, int chunkX, int chunkZ) {
		try {
			ChunkProviderServer provider = world.theChunkProviderServer;
			Chunk chunk = (Chunk) provider.loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
			if(chunk != null) return chunk;
			return loadChunk(world, provider, chunkX, chunkZ);
		} catch(Throwable x) {
			return null;
		}
	}

	private static Chunk loadChunk(WorldServer world, ChunkProviderServer provider, int chunkX, int chunkZ) {
		long chunkCoord = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
		provider.chunksToUnload.remove(Long.valueOf(chunkCoord));
		Chunk chunk = (Chunk) provider.loadedChunkHashMap.getValueByKey(chunkCoord);
		AnvilChunkLoader loader = null;

		if(provider.currentChunkLoader instanceof AnvilChunkLoader) {
			loader = (AnvilChunkLoader) provider.currentChunkLoader;
		}

		if(chunk == null && loader != null && loader.chunkExists(world, chunkX, chunkZ)) {
			chunk = ChunkIOExecutor.syncChunkLoad(world, loader, provider, chunkX, chunkZ);
		}

		return chunk;
	}
}
