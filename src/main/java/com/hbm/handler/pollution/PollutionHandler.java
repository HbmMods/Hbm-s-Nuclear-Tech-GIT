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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;

public class PollutionHandler {
	
	public static final String fileName = "hbmpollution.dat";
	public static HashMap<World, PollutionPerWorld> perWorld = new HashMap();
	
	/** Baserate of soot generation for a furnace-equivalent machine per second */
	public static final float SOOT_PER_SECOND = 1F / 25F;
	
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
				if(!pollutionFile.getParentFile().exists()) pollutionFile.getParentFile().mkdirs();
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
			
			for(Entry<World, PollutionPerWorld> entry : perWorld.entrySet()) {
				HashMap<ChunkCoordIntPair, PollutionData> newPollution = new HashMap();
				
				for(Entry<ChunkCoordIntPair, PollutionData> chunk : entry.getValue().pollution.entrySet()) {
					int x = chunk.getKey().chunkXPos;
					int z = chunk.getKey().chunkZPos;
					PollutionData data = chunk.getValue();
					
					float[] pollutionForNeightbors = new float[PollutionType.values().length];
					int S = PollutionType.SOOT.ordinal();
					int H = PollutionType.HEAVYMETAL.ordinal();
					
					/* CALCULATION */
					if(data.pollution[S] > 15) {
						pollutionForNeightbors[S] = data.pollution[S] * 0.05F;
						data.pollution[S] *= 0.8F;
					} else {
						data.pollution[S] *= 0.99F;
					}
					
					data.pollution[H] *= 0.999F;
					
					/* SPREADING */
					//apply new data to self
					PollutionData newData = newPollution.get(chunk.getKey());
					if(newData == null) newData = new PollutionData();
					
					boolean shouldPut = false;
					for(int i = 0; i < newData.pollution.length; i++) {
						newData.pollution[i] += data.pollution[i];
						if(newData.pollution[i] > 0) shouldPut = true;
					}
					if(shouldPut) newPollution.put(chunk.getKey(), newData);
					
					//apply neighbor data to neighboring chunks
					int[][] offsets = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
					for(int[] offset : offsets) {
						ChunkCoordIntPair offPos = new ChunkCoordIntPair(x + offset[0], z + offset[1]);
						PollutionData offsetData = newPollution.get(offPos);
						if(offsetData == null) offsetData = new PollutionData();
						
						shouldPut = false;
						for(int i = 0; i < offsetData.pollution.length; i++) {
							offsetData.pollution[i] += pollutionForNeightbors[i];
							if(offsetData.pollution[i] > 0) shouldPut = true;
						}
						if(shouldPut) newPollution.put(offPos, offsetData);
					}
				}
				
				entry.getValue().pollution.clear();
				entry.getValue().pollution.putAll(newPollution);
			}
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
	
	///////////////////
	/// MOB EFFECTS ///
	///////////////////

	
	@SubscribeEvent
	public void decorateMob(LivingSpawnEvent event) {
		
		World world = event.world;
		if(world.isRemote) return;
		EntityLivingBase living = event.entityLiving;
		
		PollutionData data = getPollutionData(world, (int) Math.floor(event.x), (int) Math.floor(event.y), (int) Math.floor(event.z));
		if(data == null) return;
		
		if(living instanceof IMob) {
			
			if(data.pollution[PollutionType.SOOT.ordinal()] > 15) {
				if(living.getEntityAttribute(SharedMonsterAttributes.maxHealth) != null) living.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Soot Anger Health Increase", 2D, 1));
				if(living.getEntityAttribute(SharedMonsterAttributes.attackDamage) != null) living.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(new AttributeModifier("Soot Anger Damage Increase", 1.5D, 1));
			}
		}
	}
}
