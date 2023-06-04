package com.hbm.handler.pollution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;

public class PollutionHandler {
	
	public static final String fileName = "hbmpollution.dat";
	public static HashMap<World, PollutionPerWorld> perWorld = new HashMap();

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if(!event.world.isRemote) {
			WorldServer world = (WorldServer) event.world;
			String dirPath = getDataDir(world);

			try {
				File pollutionFile = new File(dirPath, fileName);

				if(pollutionFile != null) {
					
					if(pollutionFile.exists()) {
						FileInputStream io = new FileInputStream(pollutionFile);
						NBTTagCompound data = CompressedStreamTools.readCompressed(io);
						io.close();
						perWorld.put(event.world, new PollutionPerWorld(data));
					} else {
						perWorld.put(event.world, new PollutionPerWorld());
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		if(!event.world.isRemote) perWorld.remove(event.world);
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		if(!event.world.isRemote) {
			WorldServer world = (WorldServer) event.world;
			String dirPath = getDataDir(world);

			try {
				File pollutionFile = new File(dirPath, fileName);
				if(!pollutionFile.exists()) pollutionFile.createNewFile();
				NBTTagCompound data = perWorld.get(world).writeToNBT();
				CompressedStreamTools.writeCompressed(data, new FileOutputStream(pollutionFile));
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String getDataDir(WorldServer world) {
		String dir = world.getSaveHandler().getWorldDirectory().getAbsolutePath();
		
		if(world.provider.dimensionId != 0) {
			dir += File.separator + "DIM" + world.provider.dimensionId;
		}
		
		dir += File.separator + "data";
		
		return dir;
	}

	public static class PollutionPerWorld {
		public HashMap<ChunkCoordIntPair, PollutionData> pollution = new HashMap();
		
		public PollutionPerWorld() { }
		
		public PollutionPerWorld(NBTTagCompound data) {
			
			NBTTagList list = data.getTagList("entries", 10);
			
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound nbt = list.getCompoundTagAt(i);
				int chunkX = nbt.getInteger("chunkX");
				int chunkZ = nbt.getInteger("chunkZ");
				pollution.put(new ChunkCoordIntPair(chunkX, chunkZ), PollutionData.fromNBT(nbt));
			}
		}
		
		public NBTTagCompound writeToNBT() {
			
			NBTTagCompound data = new NBTTagCompound();
			
			NBTTagList list = new NBTTagList();
			
			for(Entry<ChunkCoordIntPair, PollutionData> entry : pollution.entrySet()) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("chunkX", entry.getKey().chunkXPos);
				nbt.setInteger("chunkZ", entry.getKey().chunkZPos);
				entry.getValue().toNBT(nbt);
				list.appendTag(nbt);
			}
			
			data.setTag("entries", list);
			
			return data;
		}
	}
	
	public static class PollutionData {
		float soot;
		float poison;
		float heavyMetal;
		
		public static PollutionData fromNBT(NBTTagCompound nbt) {
			PollutionData data = new PollutionData();
			data.soot = nbt.getFloat("soot");
			data.poison = nbt.getFloat("poison");
			data.heavyMetal = nbt.getFloat("heavyMetal");
			return data;
		}
		
		public void toNBT(NBTTagCompound nbt) {
			nbt.setFloat("soot", soot);
			nbt.setFloat("poison", poison);
			nbt.setFloat("heavyMetal", heavyMetal);
		}
	}
}
