package com.hbm.handler.pollution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;

public class PollutionHandler {
	
	public static final String fileName = "hbmpollution.dat";
	public static HashMap<World, PollutionPerWorld> perWorld = new HashMap();
	
	///////////////////////
	/// UTILITY METHODS ///
	///////////////////////
	public static void incrementPollution(World world, int x, int y, int z, PollutionType type, float amount) {
		PollutionPerWorld ppw = perWorld.get(world);
		if(ppw == null) return;
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x >> 6, z >> 6);
		PollutionData data = ppw.pollution.get(pos);
		if(data == null) {
			data = new PollutionData();
			ppw.pollution.put(pos, data);
		}
		data.pollution[type.ordinal()] = MathHelper.clamp_float(data.pollution[type.ordinal()] + amount, 0F, 10_000F);
	}
	
	public static void decrementPollution(World world, int x, int y, int z, PollutionType type, float amount) {
		incrementPollution(world, x, y, z, type, -amount);
	}
	
	public static void setPollution(World world, int x, int y, int z, PollutionType type, float amount) {
		PollutionPerWorld ppw = perWorld.get(world);
		if(ppw == null) return;
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x >> 6, z >> 6);
		PollutionData data = ppw.pollution.get(pos);
		if(data == null) {
			data = new PollutionData();
			ppw.pollution.put(pos, data);
		}
		data.pollution[type.ordinal()] = amount;
	}
	
	public static float getPollution(World world, int x, int y, int z, PollutionType type) {
		PollutionPerWorld ppw = perWorld.get(world);
		if(ppw == null) return 0F;
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x >> 6, z >> 6);
		PollutionData data = ppw.pollution.get(pos);
		if(data == null) return 0F;
		return data.pollution[type.ordinal()];
	}
	
	public static PollutionData getPollutionData(World world, int x, int y, int z) {
		PollutionPerWorld ppw = perWorld.get(world);
		if(ppw == null) return null;
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x >> 6, z >> 6);
		PollutionData data = ppw.pollution.get(pos);
		return data;
	}

	//////////////////////
	/// EVENT HANDLING ///
	//////////////////////
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
	
	//////////////////////////
	/// SYSTEM UPDATE LOOP ///
	//////////////////////////
	int eggTimer = 0;
	@SubscribeEvent
	public void updateSystem(TickEvent.ServerTickEvent event) {
		
		if(event.side == Side.SERVER && event.phase == Phase.END) {
			
			eggTimer++;
			if(eggTimer < 60) return;
			eggTimer = 0;
			
			// TBI
		}
	}

	//////////////////////
	/// DATA STRUCTURE ///
	//////////////////////
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
		public float[] pollution = new float[PollutionType.values().length];
		
		public static PollutionData fromNBT(NBTTagCompound nbt) {
			PollutionData data = new PollutionData();
			
			for(int i = 0; i < PollutionType.values().length; i++) {
				data.pollution[i] = nbt.getFloat(PollutionType.values()[i].name().toLowerCase(Locale.US));
			}
			
			return data;
		}
		
		public void toNBT(NBTTagCompound nbt) {
			for(int i = 0; i < PollutionType.values().length; i++) {
				nbt.setFloat(PollutionType.values()[i].name().toLowerCase(Locale.US), pollution[i]);
			}
		}
	}
	
	public static enum PollutionType {
		SOOT, POISON, HEAVYMETAL, FALLOUT;
	}
}
