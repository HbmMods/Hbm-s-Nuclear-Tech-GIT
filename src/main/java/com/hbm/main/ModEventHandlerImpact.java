package com.hbm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.handler.ImpactWorldHandler;
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
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumSkyBlock;
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

public class ModEventHandlerImpact {
	
	//////////////////////////////////////////
	private static Random rand = new Random();
	//////////////////////////////////////////
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {

		if(event.world != null && !event.world.isRemote && event.phase == Phase.START) {
			float settle = 1F / 14400000F; 	// 600 days to completely clear all dust.
			float cool = 1F / 24000F;		// One MC day between initial impact and total darkness.
			
			ImpactWorldHandler.impactEffects(event.world);
			TomSaveData data = TomSaveData.forWorld(event.world);
			
			if(data.dust > 0 && data.fire == 0) {
				data.dust = Math.max(0, data.dust - settle);
				data.markDirty();
			}
			
			if(data.fire > 0) {
				data.fire = Math.max(0, (data.fire - cool));
				data.dust = Math.min(1, (data.dust + cool));
				data.markDirty();
			}
			
			
			if(data.time > 0) {
				data.time--;
				if(data.time==data.dtime)
				{
					EntityTom tom = new EntityTom(event.world);
					tom.setPosition(data.x + 0.5, 600, data.z + 0.5);
					event.world.spawnEntityInWorld(tom);
					IChunkProvider provider = event.world.getChunkProvider();
					provider.loadChunk(data.x >> 4, data.z >> 4);
				}
				data.markDirty();
			}
			
			if(!event.world.loadedEntityList.isEmpty()) {
				
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
			}
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
		
		TomSaveData data = TomSaveData.forWorld(event.world);
		
		if(data.impact) {
			if(!(event.entity instanceof EntityPlayer) && event.entity instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) event.entity;
				if(event.world.provider.dimensionId == 0) {
					if(event.entity.height >= 0.85f || event.entity.width >= 0.85f && event.entity.ticksExisted < 20 && !(event.entity instanceof EntityWaterMob) && !living.isChild()) {
						event.setCanceled(true);
					}
				}
				if(event.entity instanceof EntityWaterMob && event.entity.ticksExisted < 20) {
					Random rand = new Random();
					if(rand.nextInt(9) != 0) {
						event.setCanceled(true);
					}
				}
			}
		}
		if(!(event.entity instanceof EntityPlayer) && event.entity instanceof EntityDuck) {
			double range = 2D;
				
			List<EntityLivingBase> entities = event.world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(event.entity.posX, event.entity.posY, event.entity.posZ, event.entity.posX, event.entity.posY, event.entity.posZ).expand(range, range, range));
			if(data.impact)
			{
				event.setCanceled(false);
				return;
			}
			for(EntityLivingBase e : entities) {
				if(e instanceof EntityPlayer || e instanceof EntityDuck || data.impact)
				{
					event.setCanceled(false);
					return;
				}
			}
			//if(entities.size()==0 && !data.impact)
			//{
			//	event.setCanceled(true);	
			//}
		}		
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
		
		TomSaveData data = TomSaveData.getLastCachedOrNull();
		
		if(data == null || event.biome == null) {
			return;
		}
		
		if(data.impact) {
			if(mat == Material.wood || mat == Material.glass || b == Blocks.ladder || b instanceof BlockCrops ||
					b == Blocks.chest || b instanceof BlockDoor || mat == Material.cloth || mat == Material.water || b == Blocks.stone_slab) {
				event.replacement = Blocks.air;
				
			} else if(b == Blocks.cobblestone || b == Blocks.stonebrick) {
				if(rand.nextInt(3) == 1) {
					event.replacement = Blocks.gravel;
				}
			} else if(b == Blocks.sandstone) {
				if(rand.nextInt(3) == 1) {
					event.replacement = Blocks.sand;
				}
			} else if(b == Blocks.farmland) {
				event.replacement = Blocks.dirt;
			}
		}
		
		if(event.replacement != null) {
			event.setResult(Result.DENY);
		}
	}

	
	@SubscribeEvent
	public void postImpactGeneration(BiomeEvent event) {
		/// Disables post-impact surface replacement for superflat worlds
		/// because they are retarded and crash with a NullPointerException if
		/// you try to look for biome-specific blocks.
		TomSaveData data = TomSaveData.getLastCachedOrNull(); //despite forcing the data, we cannot rule out canceling events or custom firing shenanigans 
		if(data != null && event.biome != null) {
			if(event.biome.topBlock != null) {
				if(event.biome.topBlock == Blocks.grass) {
					if(data.impact && (data.dust > 0 || data.fire > 0)) {
						event.biome.topBlock = ModBlocks.impact_dirt;
					} else {
						event.biome.topBlock = Blocks.grass;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void postImpactDecoration(DecorateBiomeEvent.Decorate event) {
		
		TomSaveData data = TomSaveData.forWorld(event.world);
		
		if(data.impact) {
			EventType type = event.type;
			
			if(data.dust > 0 || data.fire > 0) {
				if(type == event.type.TREE || type == event.type.BIG_SHROOM || type == event.type.GRASS || type == event.type.REED || type == event.type.FLOWERS || type == event.type.DEAD_BUSH
						|| type == event.type.CACTUS || type == event.type.PUMPKIN || type == event.type.LILYPAD) {
					event.setResult(Result.DENY);
				}
				
			} else if(data.dust == 0 && data.fire == 0) {
				if(type == event.type.TREE || type == event.type.BIG_SHROOM || type == event.type.CACTUS) {
					Random rand = new Random();
					if(rand.nextInt(4) == 0) {
						event.setResult(Result.DEFAULT);
					} else {
						event.setResult(Result.DENY);
					}
				}
				
				if(type == event.type.GRASS || type == event.type.REED) {
					event.setResult(Result.DEFAULT);
				}
			}
			
		} else {
			event.setResult(Result.DEFAULT);
		}
	}

	@SubscribeEvent
	public void populateChunkPre(PopulateChunkEvent.Pre event) {
		TomSaveData.forWorld(event.world); /* forces the data to be cached so it is accurate by the time ModEventHandlerImpact#modifyVillageGen is called. */
	}

	@SubscribeEvent
	public void populateChunkPost(PopulateChunkEvent.Post event) {
		
		TomSaveData data = TomSaveData.forWorld(event.world);
		
		if(data.impact) {
			Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX, event.chunkZ);
			
			for(ExtendedBlockStorage storage : chunk.getBlockStorageArray()) {
				
				if(storage != null) {
					
					for(int x = 0; x < 16; ++x) {
						for(int y = 0; y < 16; ++y) {
							for(int z = 0; z < 16; ++z) {
								
								if(data.dust > 0.25 || data.fire > 0) {
									if(storage.getBlockByExtId(x, y, z) == Blocks.grass) {
										storage.func_150818_a(x, y, z, ModBlocks.impact_dirt);
									} else if(storage.getBlockByExtId(x, y, z) instanceof BlockLog) {
										storage.func_150818_a(x, y, z, Blocks.air);
									} else if(storage.getBlockByExtId(x, y, z) instanceof BlockLeaves) {
										storage.func_150818_a(x, y, z, Blocks.air);
									} else if(storage.getBlockByExtId(x, y, z).getMaterial() == Material.leaves) {
										storage.func_150818_a(x, y, z, Blocks.air);
									} else if(storage.getBlockByExtId(x, y, z).getMaterial() == Material.plants) {
										storage.func_150818_a(x, y, z, Blocks.air);
									} else if(storage.getBlockByExtId(x, y, z) instanceof BlockBush) {
										storage.func_150818_a(x, y, z, Blocks.air);
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
