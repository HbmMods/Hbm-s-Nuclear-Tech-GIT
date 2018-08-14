package com.hbm.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class ModEventHandler
{	
	public static boolean showMessage = true;
	public static int meteorShower = 0;
	static Random rand = new Random();
	
	@SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if(showMessage)
        {
        	event.player.addChatMessage(new ChatComponentText("Loaded world with Hbm's Nuclear Tech Mod " + RefStrings.VERSION + " for Minecraft 1.7.10!"));
        }
        
        showMessage = !showMessage;
	}
	
	@SubscribeEvent
	public void spawnMob(LivingSpawnEvent event) {
		EntityLivingBase entity = event.entityLiving;
		World world = event.world;

		if(entity instanceof EntityZombie) {
			if(rand.nextInt(64) == 0)
				entity.setCurrentItemOrArmor(4, new ItemStack(ModItems.gas_mask_m65, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(4, new ItemStack(ModItems.gas_mask, 1, world.rand.nextInt(100)));
			if(rand.nextInt(256) == 0)
				entity.setCurrentItemOrArmor(4, new ItemStack(ModItems.mask_of_infamy, 1, world.rand.nextInt(100)));
			
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.pipe_lead, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.reer_graar, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.pipe_rusty, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.crowbar, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.geiger_counter, 1));
			if(rand.nextInt(128) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.steel_pickaxe, 1, world.rand.nextInt(300)));
		}
		if(entity instanceof EntitySkeleton) {
			if(rand.nextInt(16) == 0) {
				entity.setCurrentItemOrArmor(4, new ItemStack(ModItems.gas_mask_m65, 1, world.rand.nextInt(100)));
				
				if(rand.nextInt(32) == 0) {
					entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.syringe_poison));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		
		/////
		//try {
		/////
		
		if(event.world != null && !event.world.isRemote && event.world.provider.isSurfaceWorld() && MainRegistry.enableMeteorStrikes) {
			if(event.world.rand.nextInt(meteorShower > 0 ? MainRegistry.meteorShowerChance : MainRegistry.meteorStrikeChance) == 0) {
				if(!event.world.playerEntities.isEmpty()) {
					EntityPlayer p = (EntityPlayer)event.world.playerEntities.get(event.world.rand.nextInt(event.world.playerEntities.size()));
					
					if(p != null && p.dimension == 0) {
						EntityMeteor meteor = new EntityMeteor(event.world);
						meteor.posX = p.posX + event.world.rand.nextInt(201) - 100;
						meteor.posY = 384;
						meteor.posZ = p.posZ + event.world.rand.nextInt(201) - 100;
						meteor.motionX = event.world.rand.nextDouble() - 0.5;
						meteor.motionY = -2.5;
						meteor.motionZ = event.world.rand.nextDouble() - 0.5;
						event.world.spawnEntityInWorld(meteor);
					}
				}
			}
			
			if(meteorShower > 0) {
				meteorShower--;
				if(meteorShower == 0)
					MainRegistry.logger.info("Ended meteor shower.");
			}
			
			if(event.world.rand.nextInt(MainRegistry.meteorStrikeChance * 100) == 0 && MainRegistry.enableMeteorShowers) {
				meteorShower = 
						(int)(MainRegistry.meteorShowerDuration * 0.75 + 
								MainRegistry.meteorShowerDuration * 0.25 * event.world.rand.nextFloat());
				MainRegistry.logger.info("Started meteor shower! Duration: " + meteorShower);
			}
		}
		
		if(event.world != null && !event.world.isRemote) {
			if(!event.world.loadedEntityList.isEmpty()) {
				
				List<Object> oList = new ArrayList<Object>();
				oList.addAll(event.world.loadedEntityList);
				
				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {
						EntityLivingBase entity = (EntityLivingBase) e;
						PotionEffect effect = entity.getActivePotionEffect(HbmPotion.radiation);
						
						if(effect != null && !entity.isDead && entity.getHealth() > 0) {
							
							if(entity instanceof EntityCreeper) {
								
								if(event.world.rand.nextInt(5) == 0 ) {
									EntityNuclearCreeper creep = new EntityNuclearCreeper(event.world);
									creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
					        		
					        		if(!entity.isDead)
					        			if(!event.world.isRemote)
					        				event.world.spawnEntityInWorld(creep);
					        		entity.setDead();
								} else {
									entity.attackEntityFrom(ModDamageSource.radiation, 100F);
								}
			        		
				        	} else if(entity instanceof EntityCow) {
				        		EntityMooshroom creep = new EntityMooshroom(event.world);
				        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

				        		if(!entity.isDead)
				        			if(!event.world.isRemote)
				        				event.world.spawnEntityInWorld(creep);
				        		entity.setDead();
				        		
				        	} else if(entity instanceof EntityVillager) {
				        		EntityZombie creep = new EntityZombie(event.world);
				        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
				        		
				        		if(!entity.isDead)
					        		if(!event.world.isRemote)
					        			event.world.spawnEntityInWorld(creep);
				        		entity.setDead();
				        		
				        	} else if(!(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie)) {
							
								int level = effect.getAmplifier();
						        
						        if(level > 14) {
						        	if(event.world.rand.nextInt(100) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
						        	if(event.world.rand.nextInt(300) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 3));
						        	if(event.world.rand.nextInt(300) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 3));
						        	if(event.world.rand.nextInt(300) == 0)
						        		entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5 * 20, 2));
						        	if(event.world.rand.nextInt(500) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.wither.id, 3 * 20, 4));
						        } else if(level > 9) {
						        	if(event.world.rand.nextInt(150) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
						        	if(event.world.rand.nextInt(400) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 3));
						        	if(event.world.rand.nextInt(400) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 3));
						        	if(event.world.rand.nextInt(400) == 0)
						           		entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5 * 20, 2));
						        } else if(level > 4) {
						        	if(event.world.rand.nextInt(300) == 0)
						            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
						        	if(event.world.rand.nextInt(500) == 0)
						            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 1));
						        	if(event.world.rand.nextInt(500) == 0)
						            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 1));
								}
				        	}
						}
					}
				}
			}
		}
		
		//////////////////////
		/*} catch(Exception x) {
			
			MainRegistry.logger.error("Ouchie, something has happened in the NTM world tick event.");
		}*/
		//////////////////////
	}
	
	@SubscribeEvent
    public void enteringChunk(EnteringChunk evt)
    {
        if(evt.entity instanceof EntityMissileBaseAdvanced)
        {
            ((EntityMissileBaseAdvanced)evt.entity).loadNeighboringChunks(evt.newChunkX, evt.newChunkZ);
        }
    }
	
	/*@SubscribeEvent
	public void itemSmelted(PlayerEvent.ItemSmeltedEvent e) {
		if(e.smelting.getItem().equals(ModItems.ingot_titanium)) {
			e.player.addStat(MainRegistry.achievementGetTitanium, 1);
		}
	}*/
	
	@SubscribeEvent
	public void itemCrafted(PlayerEvent.ItemCraftedEvent e) {
		
		Item item = e.crafting.getItem();

		if(item == ModItems.gun_mp40) {
			e.player.addStat(MainRegistry.achFreytag, 1);
		}
		if(item == ModItems.piston_selenium || item == ModItems.gun_b92) {
			e.player.addStat(MainRegistry.achSelenium, 1);
		}
		if(item == ModItems.battery_potatos) {
			e.player.addStat(MainRegistry.achPotato, 1);
		}
		if(item == ModItems.gun_revolver_pip) {
			e.player.addStat(MainRegistry.achC44, 1);
		}
	}
	
	/*@SubscribeEvent
	public void itemCollected(PlayerEvent.ItemPickupEvent e) {
		if(e.pickedUp.getEntityItem().equals(ModItems.nothing)) {
			//e.player.addStat(MainRegistry.achievementGetAmblygonite, 1);
		}
	}*/
}
