package com.hbm.world;

import com.hbm.packet.BiomeSyncPacket;
import com.hbm.packet.PacketDispatcher;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

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

	public static void syncBiomeChange(World world, int x, int z) {
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		byte biome = chunk.getBiomeArray()[(z & 15) << 4 | (x & 15)];
		PacketDispatcher.wrapper.sendToAllAround(new BiomeSyncPacket(x, z, chunk.getBiomeArray()), new TargetPoint(world.provider.dimensionId, x, 128, z, 1024D));
	}
}
