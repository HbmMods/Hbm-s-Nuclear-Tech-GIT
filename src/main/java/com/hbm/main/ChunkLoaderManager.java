package com.hbm.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.handler.ThreeInts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.common.util.Constants;

public class ChunkLoaderManager {

	private static final String DATA_NAME = "ChunkData";
	
	public static void loadTicket(World world, Ticket ticket) {
		ChunkWorldSavedData savedData = getSavedData(world, ticket);

		for(ChunkCoordIntPair chunk : savedData.chunksForcedBy.values()) {
			ForgeChunkManager.forceChunk(ticket, chunk);
		}
	}

	private static ChunkWorldSavedData getSavedData(World world) {
		return getSavedData(world, null);
	}

	private static ChunkWorldSavedData getSavedData(World world, Ticket ticket) {
		ChunkWorldSavedData savedData = (ChunkWorldSavedData) world.perWorldStorage.loadData(ChunkWorldSavedData.class, DATA_NAME);

		if(savedData == null) {
			world.perWorldStorage.setData(DATA_NAME, new ChunkWorldSavedData(DATA_NAME));
			savedData = (ChunkWorldSavedData) world.perWorldStorage.loadData(ChunkWorldSavedData.class, DATA_NAME);
		}

		if(savedData.ticket == null) {
			if(ticket == null)
				ticket = ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.NORMAL);

			savedData.ticket = ticket;
		}

		return savedData;
	}

	// Accepts a REGULAR position and turns it into a forced chunk, and then associated with the block position
	// Only this block position can unforce a chunk
	public static void forceChunk(World world, int x, int y, int z) {
		forceChunk(world, x, y, z, getChunkPosition(x, z));
	}

	public static void forceChunk(World world, int x, int y, int z, ChunkCoordIntPair chunk) {
		ChunkWorldSavedData savedData = getSavedData(world);
		savedData.chunksForcedBy.put(new ThreeInts(x, y, z), chunk);
		savedData.markDirty();

		ForgeChunkManager.forceChunk(savedData.ticket, chunk);
	}

	public static void unforceChunk(World world, int x, int y, int z) {
		unforceChunk(world, x, y, z, getChunkPosition(x, z));
	}

	public static void unforceChunk(World world, int x, int y, int z, ChunkCoordIntPair chunk) {
		ChunkWorldSavedData savedData = getSavedData(world);
		savedData.chunksForcedBy.remove(new ThreeInts(x, y, z));
		savedData.markDirty();

		if(!savedData.chunksForcedBy.containsValue(chunk)) {
			ForgeChunkManager.unforceChunk(savedData.ticket, chunk);
		}
	}

	private static ChunkCoordIntPair getChunkPosition(int x, int z) {
		return new ChunkCoordIntPair(x >> 4, z >> 4);
	}

	public static class ChunkWorldSavedData extends WorldSavedData {

		public ChunkWorldSavedData(String name) {
			super(name);
		}

		public Map<ThreeInts, ChunkCoordIntPair> chunksForcedBy = new HashMap<>();
		public Ticket ticket;

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			chunksForcedBy = new HashMap<>();
			NBTTagList list = nbt.getTagList("chunks", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound tag = list.getCompoundTagAt(i);

				ThreeInts coords = new ThreeInts(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
				ChunkCoordIntPair chunk = new ChunkCoordIntPair(tag.getInteger("cx"), tag.getInteger("cz"));

				chunksForcedBy.put(coords, chunk);
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			NBTTagList list = new NBTTagList();
			for(Entry<ThreeInts, ChunkCoordIntPair> entry : chunksForcedBy.entrySet()) {
				ThreeInts coords = entry.getKey();
				ChunkCoordIntPair chunk = entry.getValue();

				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("x", coords.x);
				tag.setInteger("y", coords.y);
				tag.setInteger("z", coords.z);
				tag.setInteger("cx", chunk.chunkXPos);
				tag.setInteger("cz", chunk.chunkZPos);

				list.appendTag(tag);
			}
			nbt.setTag("chunks", list);
		}

	}

}
