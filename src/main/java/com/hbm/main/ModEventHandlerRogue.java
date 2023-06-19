package com.hbm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.handler.RogueWorldHandler;
import com.hbm.saveddata.RogueWorldSaveData;
import com.hbm.saveddata.TomSaveData;
import com.hbm.world.WorldProviderNTM;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.world.WorldEvent;

public class ModEventHandlerRogue {
	
	//////////////////////////////////////////
	private static Random rand = new Random();
	//////////////////////////////////////////
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {

		if(event.world != null && !event.world.isRemote && event.phase == Phase.START) {
			float escape = 1F / 1200F; 	// Two days to move an AU out.
			//float flyby = 1F / 96000F;		// One MC day between initial impact and total darkness.
			
			RogueWorldHandler.frostEffects(event.world);
			RogueWorldSaveData data = RogueWorldSaveData.forWorld(event.world);
			//data.atmosphere = 1F;
			//data.distance = 1F;
			
			if(data.star == true && data.rogue == false) {
				data.rtime ++;
				data.markDirty();
			}
			
			if(data.rtime >= 500 ) {//96000
				data.rogue = true;
				data.markDirty();
			}
			
			if(data.rogue = true && data.rtime >= 500 ) {
				data.distance += escape;
				data.temperature= ((int) ((5800 * Math.pow((1F/215F)/(2F* data.distance), 0.5) * Math.pow((1f-0.3f), 0.25)) * Math.max(1, (1.125d * Math.pow(data.atmosphere, 0.25)))))-273;
				data.markDirty();
			}						
			
			/*if(!event.world.loadedEntityList.isEmpty()) {
				
				List<Object> oList = new ArrayList<Object>();
				oList.addAll(event.world.loadedEntityList);
				
				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {
						EntityLivingBase entity = (EntityLivingBase) e;
						
						if(entity.worldObj.provider.dimensionId == 0 && data.fire > 0 && data.dust < 0.75f &&
								event.world.getSavedLightValue(EnumSkyBlock.Sky, (int) entity.posX, (int) entity.posY, (int) entity.posZ) > 7) {
							
							entity.setFire(5);
							entity.attackEntityFrom(DamageSource.onFire, 2);
						}
					}
				}
			}*/
		}
	}

	//data is always pooled out of the perWorld save data so resetting values isn't needed
	/*@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onUnload(WorldEvent.Unload event) {
		// We don't want Tom's impact data transferring between worlds.
		TomSaveData data = TomSaveData.forWorld(event.world);
		this.fire = 0;
		this.dust = 0;
		this.impact = false;
		data.fire = 0;
		data.dust = 0;
		data.impact = false;
	}*/

	@SubscribeEvent
	public void extinction(EntityJoinWorldEvent event) {
		
		RogueWorldSaveData data = RogueWorldSaveData.forWorld(event.world);
		
		if(data.rogue == true) {
			if(!(event.entity instanceof EntityPlayer) && event.entity instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) event.entity;
				if(event.world.provider.dimensionId == 0 && data.temperature <- 20) {// && getTemperatureAtDepth((int) event.entity.posY, event.world)>-20
					if(event.entity.height >= 0.85f || event.entity.width >= 0.85f && event.entity.ticksExisted < 20 ) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
    public static float getTemperatureAtDepth(int Y, World world) 
    {
		RogueWorldSaveData data = RogueWorldSaveData.forWorld(world);
    	int voidTemp = 50;
    	int bedrockLevel = -5;
    	int seaLevel = 64;
    	
    	return data.temperature;
    	//return (int) (voidTemp-(voidTemp-data.temperature)*((bedrockLevel+Math.min(seaLevel, Y)/(seaLevel+bedrockLevel))));
    }

    public static float getSolarBrightness(World world) {
    	RogueWorldSaveData data = RogueWorldSaveData.forWorld(world);
    	return (float) (1/Math.max(1,Math.pow(data.distance, 2)));
    }

    public static float getPlanetaryLightLevelMultiplier(World world) {
		double log2Multiplier = (Math.log10(getSolarBrightness(world))/Math.log10(4));
		//Returns the brightness visible to the eye, compared to the actual flux - this is a factor of ~1.5x for every 2x increase in luminosity
        //This is used for planetary light levels, as those would be eyesight based unlike the stellar brightness or similar
		return (float) Math.pow(1.5, log2Multiplier);
	}

    public static float getSolarBrightnessClient(World world) {    	
    	return (float) (1/Math.max(1,Math.pow(MainRegistry.proxy.getDistance(world), 1)));
    }

    public static float getPlanetaryLightLevelMultiplierClient(World world) {
		double log2Multiplier = (Math.log10(getSolarBrightnessClient(world))/Math.log10(4));
		return (float) Math.pow(1.5, log2Multiplier);
	}
    
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLoad(WorldEvent.Load event) {
		
		if(GeneralConfig.enableImpactWorldProvider) {
			DimensionManager.unregisterProviderType(0);
			DimensionManager.registerProviderType(0, WorldProviderNTM.class, true);
		}
	}

	@SubscribeEvent
	public void modifyVillageGen(BiomeEvent.GetVillageBlockID event) {
		Block b = event.original;
		Material mat = event.original.getMaterial();
		
		RogueWorldSaveData data = RogueWorldSaveData.getLastCachedOrNull();
		
		if(data != null && event.biome != null) {
			if(data.rogue && data.temperature > -50) {
			if(mat == Material.water) {
				event.replacement = Blocks.packed_ice;
				
			} else if(mat == Material.wood) {
				if(rand.nextInt(3) == 1) {
					event.replacement = ModBlocks.frozen_log;
				}
			} else if(b == Blocks.farmland) {
				if(rand.nextInt(3) == 1) {
					event.replacement = ModBlocks.frozen_farmland;
				}
			} else if(b == Blocks.planks) {
				event.replacement = ModBlocks.frozen_planks;
			}
		}
		
		if(event.replacement != null) {
			event.setResult(Result.DENY);
		}
	}
	}

	
	@SubscribeEvent
	public void postRogueGeneration(BiomeEvent event) {
		/// Disables post-impact surface replacement for superflat worlds
		/// because they are retarded and crash with a NullPointerException if
		/// you try to look for biome-specific blocks.
		RogueWorldSaveData data = RogueWorldSaveData.getLastCachedOrNull(); //despite forcing the data, we cannot rule out canceling events or custom firing shenanigans 
		if(data != null && event.biome != null) {
			if(event.biome.topBlock != null) {
				if(event.biome.topBlock == Blocks.grass) {
					if(data.rogue && (data.temperature > -20)) {
						event.biome.topBlock = ModBlocks.frozen_grass;
					} else {
						event.biome.topBlock = Blocks.grass;
					}
				}
			if(event.biome.fillerBlock != null) {
				if(event.biome.fillerBlock == Blocks.dirt) {
					if(data.rogue && (data.temperature > -20)) {
						event.biome.fillerBlock = ModBlocks.frozen_dirt;
					} else {
						event.biome.fillerBlock = Blocks.dirt;
					}
				}
			}

			}
		}
	}

	@SubscribeEvent
	public void postImpactDecoration(DecorateBiomeEvent.Decorate event) {
		
		RogueWorldSaveData data = RogueWorldSaveData.forWorld(event.world);
		
		if(data.rogue) {
			EventType type = event.type;
				
				if(type == event.type.GRASS || type == event.type.REED) {
					event.setResult(Result.DENY);
				}
				if(type == event.type.LAKE) {
					event.setResult(Result.DENY);
				}
			
		} else {
			event.setResult(Result.DEFAULT);
		}
		
	}

	@SubscribeEvent
	public void populateChunkPre(PopulateChunkEvent.Pre event) {
		RogueWorldSaveData.forWorld(event.world); /* forces the data to be cached so it is accurate by the time ModEventHandlerImpact#modifyVillageGen is called. */
	}
	
	@SubscribeEvent
	public void populateChunkPost(PopulateChunkEvent.Post event) {
		
		RogueWorldSaveData data = RogueWorldSaveData.forWorld(event.world);
		
		if(data.rogue) {
			Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
			
			for(ExtendedBlockStorage storage : chunk.getBlockStorageArray()) {
				
				if(storage != null) {
					
					for(int x = 0; x < 16; ++x) {
						for(int y = 0; y < 16; ++y) {
							for(int z = 0; z < 16; ++z) {
							
								if(data.temperature <- 50) {
									if(storage.getBlockByExtId(x, y, z) == Blocks.grass) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_grass);
									} else if(storage.getBlockByExtId(x, y, z) == Blocks.log) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_log);
									} else if(storage.getBlockByExtId(x, y, z) instanceof BlockLog) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_log);
									} else if(storage.getBlockByExtId(x, y, z) instanceof BlockLeaves) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_leaves);
									} else if(storage.getBlockByExtId(x, y, z)== Blocks.leaves) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_leaves);
									} else if(storage.getBlockByExtId(x, y, z) instanceof BlockDirt) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_dirt);
									} else if(storage.getBlockByExtId(x, y, z) == Blocks.water) {
										storage.func_150818_a(x, y, z, ModBlocks.cold_ice);
									} else if(storage.getBlockByExtId(x, y, z) == Blocks.flowing_water) {
										storage.func_150818_a(x, y, z, ModBlocks.cold_ice);
									} else if(storage.getBlockByExtId(x, y, z)== Blocks.gravel) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_gravel);
									} else if(storage.getBlockByExtId(x, y, z)== Blocks.sand) {
										storage.func_150818_a(x, y, z, ModBlocks.frozen_sand);
							}
						
						}
					}
				}
			}
		}
	}
}
}
}

