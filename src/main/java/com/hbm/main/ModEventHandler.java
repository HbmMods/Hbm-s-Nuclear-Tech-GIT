package com.hbm.main;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.collect.Multimap;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.WorldConfig;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.EntityEffectHandler;
import com.hbm.handler.RadiationWorldHandler;
import com.hbm.handler.HTTPHandler;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.items.armor.ItemModRevive;
import com.hbm.items.special.ItemHot;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ArmorUtil;
import com.hbm.util.EnchantmentUtil;
import com.hbm.world.generator.TimedGenerator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class ModEventHandler
{	
	public static int meteorShower = 0;
	static Random rand = new Random();
	
	@SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		
        if(!event.player.worldObj.isRemote) {
        	event.player.addChatMessage(new ChatComponentText("Loaded world with Hbm's Nuclear Tech Mod " + RefStrings.VERSION + " for Minecraft 1.7.10!"));
        	
        	if(HTTPHandler.newVersion) {
        		event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "New version " + HTTPHandler.versionNumber + " is available!"));
        	}
        	
        	if(MobConfig.enableDucks && event.player instanceof EntityPlayerMP && !event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getBoolean("hasDucked"))
        		PacketDispatcher.wrapper.sendTo(new PlayerInformPacket("Press O to Duck!"), (EntityPlayerMP)event.player);
        }
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		
		EntityPlayer player = event.player;
		
		if(player.getUniqueID().toString().equals(Library.Dr_Nostalgia) && !player.worldObj.isRemote) {
			
			if(!player.inventory.hasItem(ModItems.hat))
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.hat));
			
			if(!player.inventory.hasItem(ModItems.beta))
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.beta));
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)  {
		
		if(event.entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entity;
			HbmPlayerProps.getData(player); //this already calls the register method if it's null so no further action required
		}
		
		if(event.entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) event.entity;
			HbmLivingProps.getData(living); //ditto
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		
		HbmLivingProps.setRadiation(event.entityLiving, 0);
		
		for(int i = 1; i < 5; i++) {
			
			ItemStack stack = event.entityLiving.getEquipmentInSlot(i);
			
			if(stack != null && stack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(stack)) {

				ItemStack revive = ArmorModHandler.pryMods(stack)[ArmorModHandler.extra];
				
				if(revive != null && revive.getItem() instanceof ItemModRevive) {
					
					revive.setItemDamage(revive.getItemDamage() + 1);
					
					if(revive.getItemDamage() >= revive.getMaxDamage()) {
						ArmorModHandler.removeMod(stack, ArmorModHandler.extra);
					} else {
						ArmorModHandler.applyMod(stack, revive);
					}
					
					event.entityLiving.setHealth(event.entityLiving.getMaxHealth());
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.resistance.id, 60, 99));
					event.setCanceled(true);
					return;
				}
			}
		}
		
		if(event.entity.worldObj.isRemote)
			return;
		
		if(GeneralConfig.enableCataclysm) {
			EntityBurningFOEQ foeq = new EntityBurningFOEQ(event.entity.worldObj);
			foeq.setPositionAndRotation(event.entity.posX, 500, event.entity.posZ, 0.0F, 0.0F);
			event.entity.worldObj.spawnEntityInWorld(foeq);
		}
		
		if(event.entity.getUniqueID().toString().equals(Library.HbMinecraft)) {
			event.entity.dropItem(ModItems.book_of_, 1);
		}
		
		if(event.entity instanceof EntityTaintedCreeper && event.source == ModDamageSource.boxcar) {
			
			for(Object o : event.entity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, event.entity.boundingBox.expand(50, 50, 50))) {
				EntityPlayer player = (EntityPlayer)o;
				player.triggerAchievement(MainRegistry.bobHidden);
			}
		}
		
		if(!event.entityLiving.worldObj.isRemote) {
			
			if(event.source instanceof EntityDamageSource && ((EntityDamageSource)event.source).getEntity() instanceof EntityPlayer) {
				
				if(event.entityLiving instanceof EntitySpider && event.entityLiving.getRNG().nextInt(500) == 0) {
					
					event.entityLiving.dropItem(ModItems.spider_milk, 1);
				}
				
				if(event.entityLiving instanceof EntityAnimal && event.entityLiving.getRNG().nextInt(500) == 0) {
					
					event.entityLiving.dropItem(ModItems.bandaid, 1);
				}
				
				if(event.entityLiving instanceof EntitySpider && event.entityLiving.getRNG().nextInt(1000) == 0) {
					
					event.entityLiving.dropItem(ModItems.heart_piece, 1);
				}
				
				if(event.entityLiving instanceof EntityCyberCrab && event.entityLiving.getRNG().nextInt(500) == 0) {
					
					event.entityLiving.dropItem(ModItems.wd40, 1);
				}
			}
		}
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
			if(rand.nextInt(1024) == 0)
				entity.setCurrentItemOrArmor(3, new ItemStack(ModItems.starmetal_plate, 1, world.rand.nextInt(ModItems.starmetal_plate.getMaxDamage())));
			
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
			if(rand.nextInt(512) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.stopsign));
			if(rand.nextInt(512) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.sopsign));
			if(rand.nextInt(512) == 0)
				entity.setCurrentItemOrArmor(0, new ItemStack(ModItems.chernobylsign));
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
	public void onItemToss(ItemTossEvent event) {
		
		ItemStack yeet = event.entityItem.getEntityItem();
		
		if(yeet.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(yeet)) {
			
			ItemStack[] mods = ArmorModHandler.pryMods(yeet);
			ItemStack cladding = mods[ArmorModHandler.cladding];
			
			if(cladding != null && cladding.getItem() == ModItems.cladding_obsidian) {
				
				try {
					ReflectionHelper.findField(Entity.class, "field_149500_a", "invulnerable").setBoolean(event.entityItem, true);
				} catch(Exception e) { }
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		
		ItemStack[] prevArmor = null;
		
		try {
			prevArmor = (ItemStack[]) ReflectionHelper.findField(EntityLivingBase.class, "field_82180_bT", "previousEquipment").get(event.entityLiving);
		} catch(Exception e) { }

		if(event.entityLiving instanceof EntityPlayer && prevArmor != null && event.entityLiving.getHeldItem() != null 
				&& (prevArmor[0] == null || prevArmor[0].getItem() != event.entityLiving.getHeldItem().getItem())
				&& event.entityLiving.getHeldItem().getItem() instanceof IEquipReceiver) {

			((IEquipReceiver)event.entityLiving.getHeldItem().getItem()).onEquip((EntityPlayer) event.entityLiving);
		}
		
		for(int i = 1; i < 5; i++) {
			
			ItemStack prev = prevArmor != null ? prevArmor[i] : null;
			ItemStack armor = event.entityLiving.getEquipmentInSlot(i);
			
			boolean reapply = prevArmor != null && !ItemStack.areItemStacksEqual(prev, armor);
			
			if(reapply) {
				
				if(prev != null && ArmorModHandler.hasMods(prev)) {
					
					for(ItemStack mod : ArmorModHandler.pryMods(prev)) {
						
						if(mod != null && mod.getItem() instanceof ItemArmorMod) {
							
							Multimap map = ((ItemArmorMod)mod.getItem()).getModifiers(prev);
							
							if(map != null)
								event.entityLiving.getAttributeMap().removeAttributeModifiers(map);
						}
					}
				}
			}
			
			if(armor != null && ArmorModHandler.hasMods(armor)) {
				
				for(ItemStack mod : ArmorModHandler.pryMods(armor)) {
					
					if(mod != null && mod.getItem() instanceof ItemArmorMod) {
						((ItemArmorMod)mod.getItem()).modUpdate(event.entityLiving, armor);
						
						if(reapply) {
							
							Multimap map = ((ItemArmorMod)mod.getItem()).getModifiers(armor);
							
							if(map != null)
								event.entityLiving.getAttributeMap().applyAttributeModifiers(map);
						}
					}
				}
			}
		}
		
		EntityEffectHandler.onUpdate(event.entityLiving);
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		
		/////
		//try {
		/////
		
		/// METEOR SHOWER START ///
		if(event.world != null && !event.world.isRemote && event.world.provider.isSurfaceWorld() && GeneralConfig.enableMeteorStrikes) {
			if(event.world.rand.nextInt(meteorShower > 0 ? WorldConfig.meteorShowerChance : WorldConfig.meteorStrikeChance) == 0) {
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
				if(meteorShower == 0 && GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("Ended meteor shower.");
			}
			
			if(event.world.rand.nextInt(WorldConfig.meteorStrikeChance * 100) == 0 && GeneralConfig.enableMeteorShowers) {
				meteorShower = 
						(int)(WorldConfig.meteorShowerDuration * 0.75 + 
								WorldConfig.meteorShowerDuration * 0.25 * event.world.rand.nextFloat());

				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("Started meteor shower! Duration: " + meteorShower);
			}
		}
		/// METEOR SHOWER END ///

		/// RADIATION STUFF START ///
		if(event.world != null && !event.world.isRemote && GeneralConfig.enableRads) {
			
			int thunder = AuxSavedData.getThunder(event.world);
			
			if(thunder > 0)
				AuxSavedData.setThunder(event.world, thunder - 1);
			
			if(!event.world.loadedEntityList.isEmpty()) {

				RadiationSavedData data = RadiationSavedData.getData(event.world);
				
				if(data.worldObj == null) {
					data.worldObj = event.world;
				}
				
				if(event.world.getTotalWorldTime() % 20 == 0 && event.phase == Phase.START) {
					data.updateSystem();
				}
				
				List<Object> oList = new ArrayList<Object>();
				oList.addAll(event.world.loadedEntityList);
				
				
				/**
				 *  REMOVE THIS V V V
				 */
				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {
						
						//effect for radiation
						EntityLivingBase entity = (EntityLivingBase) e;
						
						if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)
							continue;
						
						float eRad = HbmLivingProps.getRadiation(entity);
						
						if(entity instanceof EntityCreeper && eRad >= 200 && entity.getHealth() > 0) {
							
							if(event.world.rand.nextInt(3) == 0 ) {
								EntityNuclearCreeper creep = new EntityNuclearCreeper(event.world);
								creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
				        		
				        		if(!entity.isDead)
				        			if(!event.world.isRemote)
				        				event.world.spawnEntityInWorld(creep);
				        		entity.setDead();
							} else {
								entity.attackEntityFrom(ModDamageSource.radiation, 100F);
							}
							continue;
		        		
			        	} else if(entity instanceof EntityCow && !(entity instanceof EntityMooshroom) && eRad >= 50) {
			        		EntityMooshroom creep = new EntityMooshroom(event.world);
			        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

			        		if(!entity.isDead)
			        			if(!event.world.isRemote)
			        				event.world.spawnEntityInWorld(creep);
			        		entity.setDead();
							continue;
			        		
			        	} else if(entity instanceof EntityVillager && eRad >= 500) {
			        		EntityZombie creep = new EntityZombie(event.world);
			        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			        		
			        		if(!entity.isDead)
				        		if(!event.world.isRemote)
				        			event.world.spawnEntityInWorld(creep);
			        		entity.setDead();
							continue;
			        	} else if(entity.getClass().equals(EntityDuck.class) && eRad >= 200) {
			        		
			        		EntityQuackos quacc = new EntityQuackos(event.world);
			        		quacc.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			        		
			        		if(!entity.isDead && !event.world.isRemote)
				        		event.world.spawnEntityInWorld(quacc);
			        		
			        		entity.setDead();
							continue;
			        	}
						
						if(eRad < 200 || entity instanceof EntityNuclearCreeper ||
								entity instanceof EntityMooshroom ||
								entity instanceof EntityZombie ||
								entity instanceof EntitySkeleton ||
								entity instanceof EntityQuackos)
							continue;
						
						if(eRad > 2500)
							HbmLivingProps.setRadiation(entity, 2500);
						
						if(eRad >= 1000) {

							entity.attackEntityFrom(ModDamageSource.radiation, 1000F);
							HbmLivingProps.setRadiation(entity, 0);
							
							if(entity.getHealth() > 0) {
					        	entity.setHealth(0);
					        	entity.onDeath(ModDamageSource.radiation);
							}
				        	
				        	if(entity instanceof EntityPlayer)
				        		((EntityPlayer)entity).triggerAchievement(MainRegistry.achRadDeath);
				        	
						} else if(eRad >= 800) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 30, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 2));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 10 * 20, 2));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.poison.id, 3 * 20, 2));
				        	if(event.world.rand.nextInt(700) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.wither.id, 3 * 20, 1));
							
						} else if(eRad >= 600) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 30, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 2));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 10 * 20, 2));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.poison.id, 3 * 20, 1));
							
						} else if(eRad >= 400) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 30, 0));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 1));
				        	
						} else if(eRad >= 200) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 0));
				        	
				        	if(entity instanceof EntityPlayer)
				        		((EntityPlayer)entity).triggerAchievement(MainRegistry.achRadPoison);
						}
					}
				}
				/**
				 * REMOVE THIS ^ ^ ^
				 */
			}
		}
		/// RADIATION STUFF END ///
		
		if(event.phase == Phase.START) {
			RadiationWorldHandler.handleWorldDestruction(event.world);
			BossSpawnHandler.rollTheDice(event.world);
			TimedGenerator.automaton(event.world, 100);
		}
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		
		EntityLivingBase e = event.entityLiving;

		if(e instanceof EntityPlayer && ArmorUtil.checkArmor((EntityPlayer)e, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
			e.worldObj.playSoundAtEntity(e, "random.break", 5F, 1.0F + e.getRNG().nextFloat() * 0.5F);
			event.setCanceled(true);
		}
		
		ArmorFSB.handleAttack(event);
	}
	
	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event) {
		
		for(int i = 1; i < 5; i++) {
			
			ItemStack armor = event.entityLiving.getEquipmentInSlot(i);
			
			if(armor != null && ArmorModHandler.hasMods(armor)) {
				
				for(ItemStack mod : ArmorModHandler.pryMods(armor)) {
					
					if(mod != null && mod.getItem() instanceof ItemArmorMod) {
						((ItemArmorMod)mod.getItem()).modDamage(event, armor);
					}
				}
			}
		}
		
		ArmorFSB.handleHurt(event);
	}
	
	@SubscribeEvent
	public void onPlayerFall(PlayerFlyableFallEvent event) {
		
		ArmorFSB.handleFall(event.entityPlayer);
	}
	
	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event) {
		
		if(event.entityLiving instanceof EntityPlayer)
			ArmorFSB.handleJump((EntityPlayer) event.entityLiving);
	}
	
	@SubscribeEvent
	public void onEntityFall(LivingFallEvent event) {
		
		if(event.entityLiving instanceof EntityPlayer)
			ArmorFSB.handleFall((EntityPlayer) event.entityLiving);
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		ArmorFSB.handleTick(event);
		
		if(player.ticksExisted == 100 || player.ticksExisted == 200)
			CraftingManager.crumple();
		
		if(!player.worldObj.isRemote && event.phase == TickEvent.Phase.START) {
			
			/// GHOST FIX START ///
			
			if(!Float.isFinite(player.getHealth()) || !Float.isFinite(player.getAbsorptionAmount())) {
				player.addChatComponentMessage(new ChatComponentText("Your health has been restored!"));
				player.worldObj.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);
				player.setHealth(player.getMaxHealth());
				player.setAbsorptionAmount(0);
			}
			
			/// GHOST FIX END ///
			
			/// BETA HEALTH START ///
			if(player.inventory.hasItem(ModItems.beta)) {
				
				if(player.getFoodStats().getFoodLevel() > 10) {
					player.heal(player.getFoodStats().getFoodLevel() - 10);
				}
				
				if(player.getFoodStats().getFoodLevel() != 10) {
					
					// Why can't you be normal??
					try {
						Field food = ReflectionHelper.findField(FoodStats.class, "field_75127_a", "foodLevel");
						food.setInt(player.getFoodStats(), 10);
					} catch(Exception e) { }
				}
			}
			/// BETA HEALTH END ///

			/// PU RADIATION START ///
			
			if(player.getUniqueID().toString().equals(Library.Pu_238)) {
				
				List<EntityLivingBase> entities = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(3, 3, 3));
				
				for(EntityLivingBase e : entities) {
					
					if(e != player) {
						e.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 300, 2));
					}
				}
			}
			
			/// PU RADIATION END ///
		}

		//TODO: rewrite this so it doesn't look like shit
		if(player.worldObj.isRemote && event.phase == event.phase.START && !player.isInvisible() && !player.isSneaking()) {
			
			if(player.getUniqueID().toString().equals(Library.HbMinecraft)) {
				
				int i = player.ticksExisted * 3;
				
				Vec3 vec = Vec3.createVectorHelper(3, 0, 0);
				
				vec.rotateAroundY((float) (i * Math.PI / 180D));
				
				for(int k = 0; k < 5; k++) {
					
					vec.rotateAroundY((float) (1F * Math.PI / 180D));
					player.worldObj.spawnParticle("townaura", player.posX + vec.xCoord, player.posY + 1 + player.worldObj.rand.nextDouble() * 0.05, player.posZ + vec.zCoord, 0.0, 0.0, 0.0);
				}
			}
			
			if(player.getUniqueID().toString().equals(Library.Pu_238)) {
				
				Vec3 vec = Vec3.createVectorHelper(3 * rand.nextDouble(), 0, 0);
				
				vec.rotateAroundZ((float) (rand.nextDouble() * Math.PI));
				vec.rotateAroundY((float) (rand.nextDouble() * Math.PI * 2));
				
				player.worldObj.spawnParticle("townaura", player.posX + vec.xCoord, player.posY + 1 + vec.yCoord, player.posZ + vec.zCoord, 0.0, 0.0, 0.0);
			}
		}
	}
	
	@SubscribeEvent
    public void enteringChunk(EnteringChunk evt)
    {
        if(evt.entity instanceof EntityMissileBaseAdvanced)
        {
            ((EntityMissileBaseAdvanced)evt.entity).loadNeighboringChunks(evt.newChunkX, evt.newChunkZ);
        }
        
        if(evt.entity instanceof EntityMissileCustom)
        {
            ((EntityMissileCustom)evt.entity).loadNeighboringChunks(evt.newChunkX, evt.newChunkZ);
        }
    }
	
	@SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		
		NBTTagCompound data = new NBTTagCompound();
		HbmPlayerProps.getData(event.original).saveNBTData(data);
		HbmPlayerProps.getData(event.entityPlayer).loadNBTData(data);
    }
	
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
		if(item == Item.getItemFromBlock(ModBlocks.machine_difurnace_off)) {
			e.player.addStat(MainRegistry.bobMetalworks, 1);
		}
		if(item == Item.getItemFromBlock(ModBlocks.machine_assembler)) {
			e.player.addStat(MainRegistry.bobAssembly, 1);
		}
		if(item == Item.getItemFromBlock(ModBlocks.brick_concrete)) {
			e.player.addStat(MainRegistry.bobChemistry, 1);
		}
		if(item == Item.getItemFromBlock(ModBlocks.machine_boiler_electric_off)) {
			e.player.addStat(MainRegistry.bobOil, 1);
		}
		if(item == ModItems.ingot_uranium_fuel) {
			e.player.addStat(MainRegistry.bobNuclear, 1);
		}
	}
	
	@SubscribeEvent
	public void itemSmelted(PlayerEvent.ItemSmeltedEvent e) {
		
		if(!e.player.worldObj.isRemote && e.smelting.getItem() == Items.iron_ingot && e.player.getRNG().nextInt(64) == 0) {
			
			if(!e.player.inventory.addItemStackToInventory(new ItemStack(ModItems.lodestone)))
				e.player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.lodestone), false);
			else
				e.player.inventoryContainer.detectAndSendChanges();
		}
		
		if(!e.player.worldObj.isRemote && e.smelting.getItem() == ModItems.ingot_uranium && e.player.getRNG().nextInt(64) == 0) {
			
			if(!e.player.inventory.addItemStackToInventory(new ItemStack(ModItems.quartz_plutonium)))
				e.player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.quartz_plutonium), false);
			else
				e.player.inventoryContainer.detectAndSendChanges();
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		
		if(!(event.getPlayer() instanceof EntityPlayerMP))
			return;
		
		if(event.block == ModBlocks.stone_gneiss && !((EntityPlayerMP) event.getPlayer()).func_147099_x().hasAchievementUnlocked(MainRegistry.achStratum)) {
			event.getPlayer().triggerAchievement(MainRegistry.achStratum);
			event.setExpToDrop(500);
		}
	}
	
	private static final String hash = "41eb77f138ce350932e33b6b26b233df9aad0c0c80c6a49cb9a54ddd8fae3f83";
	
	@SubscribeEvent
	public void onClickSign(PlayerInteractEvent event) {

		int x = event.x;
		int y = event.y;
		int z = event.z;
		World world = event.world;
		
		if(!world.isRemote && event.action == Action.RIGHT_CLICK_BLOCK && world.getBlock(x, y, z) == Blocks.standing_sign) {
			
			TileEntitySign sign = (TileEntitySign)world.getTileEntity(x, y, z);
			
			String result = smoosh(sign.signText[0], sign.signText[1], sign.signText[2], sign.signText[3]);
			//System.out.println(result);
			
			if(result.equals(hash)) {
				world.func_147480_a(x, y, z, false);
				EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(ModItems.bobmazon_hidden));
				entityitem.delayBeforeCanPickup = 10;
				world.spawnEntityInWorld(entityitem);
			}
		}
		
	}
	
	private String smoosh(String s1, String s2, String s3, String s4) {
		
		Random rand = new Random();
		String s = "";

		byte[] b1 = s1.getBytes();
		byte[] b2 = s2.getBytes();
		byte[] b3 = s3.getBytes();
		byte[] b4 = s4.getBytes();
		
		if(b1.length == 0 || b2.length == 0 || b3.length == 0 || b4.length == 0)
			return "";
		
		s += s1;
		rand.setSeed(b1[0]);
		s += rand.nextInt(0xffffff);
		
		s += s2;
		rand.setSeed(rand.nextInt(0xffffff) + b2[0]);
		rand.setSeed(b2[0]);
		s += rand.nextInt(0xffffff);
		
		s += s3;
		rand.setSeed(rand.nextInt(0xffffff) + b3[0]);
		rand.setSeed(b3[0]);
		s += rand.nextInt(0xffffff);
		
		s += s4;
		rand.setSeed(rand.nextInt(0xffffff) + b4[0]);
		rand.setSeed(b4[0]);
		s += rand.nextInt(0xffffff);
		
		//System.out.println(s);
		
		return getHash(s);
	}
	
	private String getHash(String inp) {
		
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] bytes = sha256.digest(inp.getBytes());
			String str = "";
			
		    for(int b : bytes)
		      str = str + Integer.toString((b & 0xFF) + 256, 16).substring(1);
	    
		    return str;
		    
		} catch (NoSuchAlgorithmException e) { }
		
		return "";
	}
	
	@SubscribeEvent
	public void chatEvent(ServerChatEvent event) {
		
		EntityPlayerMP player = event.player;
		String message = event.message;
		
		//boolean conditions for the illiterate, edition 1
		//bellow you can see the header of an if-block. inside the brackets, there is a boolean statement.
		//that means nothing other than its value totaling either 'true' or 'false'
		//examples: 'true' would just mean true
		//'1 > 3' would equal false
		//'i < 10' would equal true if 'i' is smaller than 10, if equal or greater, it will result in false
		
		//let's start from the back:
		
		//this part means that the message's first character has to equal a '!': --------------------------+
		//                                                                                                 |
		//this is a logical AND operator: --------------------------------------------------------------+  |
		//                                                                                              |  |
		//this is a reference to a field in                                                             |  |
		//Library.java containing a reference UUID: ---------------------------------------+            |  |
		//                                                                                 |            |  |
		//this will compare said UUID with                                                 |            |  |
		//the string representation of the                                                 |            |  |
		//current player's UUID: -----------+                                              |            |  |
		//                                  |                                              |            |  |
		//another AND operator: ---------+  |                                              |            |  |
		//                               |  |                                              |            |  |
		//this is a reference to a       |  |                                              |            |  |
		//boolean called                 |  |                                              |            |  |
		//'enableDebugMode' which is     |  |                                              |            |  |
		//only set once by the mod's     |  |                                              |            |  |
		//config and is disabled by      |  |                                              |            |  |
		//default. "debug" is not a      |  |                                              |            |  |
		//substring of the message, nor  |  |                                              |            |  |
		//something that can be toggled  |  |                                              |            |  |
		//in any other way except for    |  |                                              |            |  |
		//the config file: |             |  |                                              |            |  |
		//                 V             V  V                                              V            V  V
		if(GeneralConfig.enableDebugMode && player.getUniqueID().toString().equals(Library.HbMinecraft) && message.startsWith("!")) {
			
			String[] msg = message.split(" ");
			
			String m = msg[0].substring(1, msg[0].length()).toLowerCase();
			
			if("gv".equals(m)) {
				
				int id = 0;
				int size = 1;
				int meta = 0;
				
				if(msg.length > 1 && NumberUtils.isNumber(msg[1])) {
					id = (int)(double)NumberUtils.createDouble(msg[1]);
				}
				
				if(msg.length > 2 && NumberUtils.isNumber(msg[2])) {
					size = (int)(double)NumberUtils.createDouble(msg[2]);
				}
				
				if(msg.length > 3 && NumberUtils.isNumber(msg[3])) {
					meta = (int)(double)NumberUtils.createDouble(msg[3]);
				}
				
				Item item = Item.getItemById(id);
				
				if(item != null && size > 0 && meta >= 0) {
					player.inventory.addItemStackToInventory(new ItemStack(item, size, meta));
				}
			}
			
			player.inventoryContainer.detectAndSendChanges();
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void anvilUpdateEvent(AnvilUpdateEvent event) {
		
		if(event.left.getItem() instanceof ItemGunBase && event.right.getItem() == Items.enchanted_book) {
			
			event.output = event.left.copy();

            Map mapright = EnchantmentHelper.getEnchantments(event.right);
            Iterator itr = mapright.keySet().iterator();

            while (itr.hasNext()) {
            	
            	int i = ((Integer)itr.next()).intValue();
            	int j = ((Integer)mapright.get(Integer.valueOf(i))).intValue();
            	Enchantment e = Enchantment.enchantmentsList[i];
            	
            	EnchantmentUtil.removeEnchantment(event.output, e);
            	EnchantmentUtil.addEnchantment(event.output, e, j);
            }
            
            event.cost = 10;
		}
		
		if(event.left.getItem() == ModItems.ingot_meteorite && event.right.getItem() == ModItems.ingot_meteorite &&
				event.left.stackSize == 1 && event.right.stackSize == 1) {
			
			double h1 = ItemHot.getHeat(event.left);
			double h2 = ItemHot.getHeat(event.right);
			
			if(h1 >= 0.5 && h2 >= 0.5) {
	            
				ItemStack out = new ItemStack(ModItems.ingot_meteorite_forged);
				ItemHot.heatUp(out, (h1 + h2) / 2D);
				event.output = out;
	            event.cost = 10;
			}
		}
		
		if(event.left.getItem() == ModItems.ingot_meteorite_forged && event.right.getItem() == ModItems.ingot_meteorite_forged &&
				event.left.stackSize == 1 && event.right.stackSize == 1) {
			
			double h1 = ItemHot.getHeat(event.left);
			double h2 = ItemHot.getHeat(event.right);
			
			if(h1 >= 0.5 && h2 >= 0.5) {
	            
				ItemStack out = new ItemStack(ModItems.blade_meteorite);
				ItemHot.heatUp(out, (h1 + h2) / 2D);
				event.output = out;
	            event.cost = 30;
			}
		}
		
		if(event.left.getItem() == ModItems.meteorite_sword_seared && event.right.getItem() == ModItems.ingot_meteorite_forged &&
				event.left.stackSize == 1 && event.right.stackSize == 1) {
			
			double h2 = ItemHot.getHeat(event.right);
			
			if(h2 >= 0.5) {
	            
				ItemStack out = new ItemStack(ModItems.meteorite_sword_reforged);
				event.output = out;
	            event.cost = 50;
			}
		}
		
		if(event.left.getItem() == ModItems.ingot_steel_dusted && event.right.getItem() == ModItems.ingot_steel_dusted &&
				event.left.stackSize ==  event.right.stackSize) {

			double h1 = ItemHot.getHeat(event.left);
			double h2 = ItemHot.getHeat(event.right);
			
			if(h2 >= 0.5) {

				int i1 = event.left.getItemDamage();
				int i2 = event.right.getItemDamage();
				
				int i3 = Math.min(i1, i2) + 1;
				
				boolean done = i3 >= 10;
	            
				ItemStack out = new ItemStack(done ? ModItems.ingot_chainsteel : ModItems.ingot_steel_dusted, event.left.stackSize, done ? 0 : i3);
				ItemHot.heatUp(out, done ? 1D : (h1 + h2) / 2D);
				event.output = out;
	            event.cost = event.left.stackSize;
			}
		}
	}
}
