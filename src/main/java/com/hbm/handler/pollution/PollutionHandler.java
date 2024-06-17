package com.hbm.handler.pollution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.UUID;

import com.hbm.config.MobConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.entity.mob.glyphid.EntityGlyphidDigger;
import com.hbm.entity.mob.glyphid.EntityGlyphidScout;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.WorldEvent;

public class PollutionHandler {
	
	public static final String fileName = "hbmpollution.dat";
	public static HashMap<World, PollutionPerWorld> perWorld = new HashMap();
	
	/** Baserate of soot generation for a furnace-equivalent machine per second */
	public static final float SOOT_PER_SECOND = 1F / 25F;
	/** Baserate of heavy metal generation, balanced around the soot values of combustion engines */
	public static final float HEAVY_METAL_PER_SECOND = 1F / 50F;
	/** Baserate for poison when spilled */
	public static final float POISON_PER_SECOND = 1F / 50F;
	public static Vec3 targetCoords;

	///////////////////////
	/// UTILITY METHODS ///
	///////////////////////
	public static void incrementPollution(World world, int x, int y, int z, PollutionType type, float amount) {
		
		if(!RadiationConfig.enablePollution) return;
		
		PollutionPerWorld ppw = perWorld.get(world);
		if(ppw == null) return;
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x >> 6, z >> 6);
		PollutionData data = ppw.pollution.get(pos);
		if(data == null) {
			data = new PollutionData();
			ppw.pollution.put(pos, data);
		}
		data.pollution[type.ordinal()] = MathHelper.clamp_float((float) (data.pollution[type.ordinal()] + amount * MobConfig.pollutionMult), 0F, 10_000F);
	}
	
	public static void decrementPollution(World world, int x, int y, int z, PollutionType type, float amount) {
		incrementPollution(world, x, y, z, type, -amount);
	}
	
	public static void setPollution(World world, int x, int y, int z, PollutionType type, float amount) {
		
		if(!RadiationConfig.enablePollution) return;
		
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
		
		if(!RadiationConfig.enablePollution) return 0;
		
		PollutionPerWorld ppw = perWorld.get(world);
		if(ppw == null) return 0F;
		ChunkCoordIntPair pos = new ChunkCoordIntPair(x >> 6, z >> 6);
		PollutionData data = ppw.pollution.get(pos);
		if(data == null) return 0F;
		return data.pollution[type.ordinal()];
	}
	
	public static PollutionData getPollutionData(World world, int x, int y, int z) {
		
		if(!RadiationConfig.enablePollution) return null;
		
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
		if(!event.world.isRemote && RadiationConfig.enablePollution) {
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
			File pollutionFile = new File(dirPath, fileName);

			try {
				if(!pollutionFile.getParentFile().exists()) pollutionFile.getParentFile().mkdirs();
				if(!pollutionFile.exists()) pollutionFile.createNewFile();
				PollutionPerWorld ppw = perWorld.get(world);
				if(ppw != null) {
					NBTTagCompound data = ppw.writeToNBT();
					CompressedStreamTools.writeCompressed(data, new FileOutputStream(pollutionFile));
				}
			} catch(Exception ex) {
				System.out.println("Failed to write " + pollutionFile.getAbsolutePath());
				ex.printStackTrace();
			}
		}
	}
	
	public String getDataDir(WorldServer world) {
		String dir = world.getSaveHandler().getWorldDirectory().getAbsolutePath();
		// Crucible and probably Thermos provide dimId by themselves
		String dimId = File.separator + "DIM" + world.provider.dimensionId;
		if(world.provider.dimensionId != 0 && !dir.endsWith(dimId)) {
			dir += dimId;
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
			
			handleWorldDestruction();

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
					int P = PollutionType.POISON.ordinal();
					
					/* CALCULATION */
					if(data.pollution[S] > 10) {
						pollutionForNeightbors[S] = (float) (data.pollution[S] * 0.05F);
						data.pollution[S] *= 0.8F;
					}

					data.pollution[S] *= 0.99F;
					data.pollution[H] *= 0.9995F;
					
					if(data.pollution[P] > 10) {
						pollutionForNeightbors[P] = data.pollution[P] * 0.025F;
						data.pollution[P] *= 0.9F;
					} else {
						data.pollution[P] *= 0.995F;
					}

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

	protected static final float DESTRUCTION_THRESHOLD = 15F;
	protected static final int DESTRUCTION_COUNT = 5;
	
	protected static void handleWorldDestruction() {
		
		for(Entry<World, PollutionPerWorld> entry : perWorld.entrySet()) {
			
			World world = entry.getKey();
			WorldServer serv = (WorldServer) world;
			ChunkProviderServer provider = (ChunkProviderServer) serv.getChunkProvider();
			
			for(Entry<ChunkCoordIntPair, PollutionData> pollution : entry.getValue().pollution.entrySet()) {
				
				float poison = pollution.getValue().pollution[PollutionType.POISON.ordinal()];
				if(poison < DESTRUCTION_THRESHOLD) continue;
				
				ChunkCoordIntPair entryPos = pollution.getKey();
				
				for(int i = 0; i < DESTRUCTION_COUNT; i++) {
					int x = (entryPos.chunkXPos << 6) + world.rand.nextInt(64);
					int z = (entryPos.chunkZPos << 6) + world.rand.nextInt(64);
					
					if(provider.chunkExists(x >> 4, z >> 4)) {
						int y = world.getHeightValue(x, z) - world.rand.nextInt(3) + 1;
						Block b = world.getBlock(x, y, z);
						
						if(b == Blocks.grass || (b == Blocks.dirt && world.getBlockMetadata(x, y, z) == 0)) {
							world.setBlock(x, y, z, Blocks.dirt, 1, 3);
						} else if(b == Blocks.tallgrass || b.getMaterial() == Material.leaves || b.getMaterial() == Material.plants) {
							world.setBlock(x, y, z, Blocks.air);
						}
					}
				}
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

	public static final UUID maxHealth = UUID.fromString("25462f6c-2cb2-4ca8-9b47-3a011cc61207");
	public static final UUID attackDamage = UUID.fromString("8f442d7c-d03f-49f6-a040-249ae742eed9");
	
	@SubscribeEvent
	public void decorateMob(LivingSpawnEvent event) {
		
		if(!RadiationConfig.enablePollution) return;
		
		World world = event.world;
		if(world.isRemote) return;
		EntityLivingBase living = event.entityLiving;
		
		PollutionData data = getPollutionData(world, (int) Math.floor(event.x), (int) Math.floor(event.y), (int) Math.floor(event.z));
		if(data == null) return;
		
		if(living instanceof IMob && !(living instanceof EntityGlyphid)) {
			
			if(data.pollution[PollutionType.SOOT.ordinal()] > RadiationConfig.buffMobThreshold) {
				if(living.getEntityAttribute(SharedMonsterAttributes.maxHealth) != null && living.getEntityAttribute(SharedMonsterAttributes.maxHealth).getModifier(maxHealth) == null) living.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier(maxHealth, "Soot Anger Health Increase", 1D, 1));
				if(living.getEntityAttribute(SharedMonsterAttributes.attackDamage) != null && living.getEntityAttribute(SharedMonsterAttributes.attackDamage).getModifier(attackDamage) == null) living.getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(new AttributeModifier(attackDamage, "Soot Anger Damage Increase", 1.5D, 1));
				living.heal(living.getMaxHealth());
			}
		}
	}
	///RAMPANT MODE STUFFS///

	@SubscribeEvent
	public void rampantTargetSetter(PlayerSleepInBedEvent event){
		if (MobConfig.rampantGlyphidGuidance) targetCoords = Vec3.createVectorHelper(event.x, event.y, event.z);
	}

	@SubscribeEvent
	public void rampantScoutPopulator(WorldEvent.PotentialSpawns event){
		
		if(MobConfig.rampantNaturalScoutSpawn && !event.world.isRemote && event.world.provider.dimensionId == 0 && event.world.canBlockSeeTheSky(event.x, event.y, event.z) && !event.isCanceled()) {

					if (event.world.rand.nextInt(MobConfig.rampantScoutSpawnChance) == 0) {

						float soot = PollutionHandler.getPollution(event.world, event.x, event.y, event.z, PollutionType.SOOT);

						if (soot >= MobConfig.rampantScoutSpawnThresh) {
							EntityGlyphidScout scout = new EntityGlyphidScout(event.world);
							scout.setLocationAndAngles(event.x, event.y, event.z, event.world.rand.nextFloat() * 360.0F, 0.0F);
							if(scout.isValidLightLevel()) {
								//escort for the scout, which can also deal with obstacles
								EntityGlyphidDigger digger = new EntityGlyphidDigger(event.world);
								scout.setLocationAndAngles(event.x, event.y, event.z, event.world.rand.nextFloat() * 360.0F, 0.0F);
								digger.setLocationAndAngles(event.x, event.y, event.z, event.world.rand.nextFloat() * 360.0F, 0.0F);
								if(scout.getCanSpawnHere()) event.world.spawnEntityInWorld(scout);
								if(digger.getCanSpawnHere()) event.world.spawnEntityInWorld(digger);
							}
						}
					}
				}

	}

}
