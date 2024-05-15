package com.hbm.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.Level;

import com.google.common.collect.Multimap;
import com.hbm.blocks.IStepTickReceiver;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockAshes;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityCreeperNuclear;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.entity.mob.EntityCreeperTainted;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.EntityEffectHandler;
import com.hbm.hazard.HazardSystem;
import com.hbm.interfaces.IBomb;
import com.hbm.handler.HTTPHandler;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.armor.IAttackHandler;
import com.hbm.items.armor.IDamageHandler;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.items.armor.ItemModRevive;
import com.hbm.items.armor.ItemModShackles;
import com.hbm.items.food.ItemConserve.EnumFoodType;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PermaSyncPacket;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RequestNetwork;
import com.hbm.util.AchievementHandler;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.EnchantmentUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.InventoryUtil;
import com.hbm.util.ShadyUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.world.generator.TimedGenerator;

import api.hbm.energymk2.Nodespace;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ModEventHandler {
	
	private static Random rand = new Random();
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

		if(!event.player.worldObj.isRemote) {
			
			if(GeneralConfig.enableMOTD) {
				event.player.addChatMessage(new ChatComponentText("Loaded world with Hbm's Nuclear Tech Mod " + RefStrings.VERSION + " for Minecraft 1.7.10!"));
	
				if(HTTPHandler.newVersion) {
					event.player.addChatMessage(
							new ChatComponentText("New version " + HTTPHandler.versionNumber + " is available! Click ")
							.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW))
							.appendSibling(new ChatComponentText("[here]")
									.setChatStyle(new ChatStyle()
										.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT/releases"))
										.setUnderlined(true)
										.setColor(EnumChatFormatting.RED)
									)
								)
							.appendSibling(new ChatComponentText(" to download!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
							);
				}
			}
			
			if(MobConfig.enableDucks && event.player instanceof EntityPlayerMP && !event.player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getBoolean("hasDucked"))
				PacketDispatcher.wrapper.sendTo(new PlayerInformPacket("Press O to Duck!", MainRegistry.proxy.ID_DUCK, 30_000), (EntityPlayerMP) event.player);
			

			if(GeneralConfig.enableGuideBook) {
				HbmPlayerProps props = HbmPlayerProps.getData(event.player);
				
				if(!props.hasReceivedBook) {
					event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.book_guide, 1, BookType.STARTER.ordinal()));
					event.player.inventoryContainer.detectAndSendChanges();
					props.hasReceivedBook = true;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		
		EntityPlayer player = event.player;
		
		if((player.getUniqueID().toString().equals(ShadyUtil.Dr_Nostalgia) || player.getDisplayName().equals("Dr_Nostalgia")) && !player.worldObj.isRemote) {
			
			if(!player.inventory.hasItem(ModItems.hat))
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.hat));
			
			if(!player.inventory.hasItem(ModItems.beta))
				player.inventory.addItemStackToInventory(new ItemStack(ModItems.beta));
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event) {
		
		if(event.entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entity;
			HbmPlayerProps.getData(player); //this already calls the register method if it's null so no further action required
			
			if(event.entity == MainRegistry.proxy.me())
				BlockAshes.ashes = 0;
		}
		
		if(event.entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) event.entity;
			HbmLivingProps.getData(living); //ditto
		}
	}

	@SubscribeEvent
	public void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
		EntityPlayer player = event.player;
		HbmPlayerProps data = HbmPlayerProps.getData(player);
		data.setKeyPressed(EnumKeybind.JETPACK, false);
		data.setKeyPressed(EnumKeybind.DASH, false);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityDeathFirst(LivingDeathEvent event) {
		
		for(int i = 1; i < 5; i++) {
			
			ItemStack stack = event.entityLiving.getEquipmentInSlot(i);
			
			if(stack != null && stack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(stack)) {
				
				ItemStack revive = ArmorModHandler.pryMods(stack)[ArmorModHandler.extra];
				
				if(revive != null) {
					
					//Classic revive
					if(revive.getItem() instanceof ItemModRevive) {
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
					
					//Shackles
					if(revive.getItem() instanceof ItemModShackles && HbmLivingProps.getRadiation(event.entityLiving) < 1000F) {
						
						revive.setItemDamage(revive.getItemDamage() + 1);
						
						int dmg = revive.getItemDamage();
						ArmorModHandler.applyMod(stack, revive);
						
						event.entityLiving.setHealth(event.entityLiving.getMaxHealth());
						HbmLivingProps.incrementRadiation(event.entityLiving, dmg * dmg);
						event.setCanceled(true);
						return;
					}
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		
		HbmLivingProps.setRadiation(event.entityLiving, 0);
		
		if(event.entity.worldObj.isRemote)
			return;
		
		if(GeneralConfig.enableCataclysm) {
			EntityBurningFOEQ foeq = new EntityBurningFOEQ(event.entity.worldObj);
			foeq.setPositionAndRotation(event.entity.posX, 500, event.entity.posZ, 0.0F, 0.0F);
			event.entity.worldObj.spawnEntityInWorld(foeq);
		}
		
		if(event.entity.getUniqueID().toString().equals(ShadyUtil.HbMinecraft) || event.entity.getCommandSenderName().equals("HbMinecraft")) {
			event.entity.dropItem(ModItems.book_of_, 1);
		}
		
		if(event.entity instanceof EntityCreeperTainted && event.source == ModDamageSource.boxcar) {
			
			for(Object o : event.entity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, event.entity.boundingBox.expand(50, 50, 50))) {
				EntityPlayer player = (EntityPlayer)o;
				player.triggerAchievement(MainRegistry.bobHidden);
			}
		}
		
		if(!event.entityLiving.worldObj.isRemote) {
			
			if(event.source instanceof EntityDamageSource && ((EntityDamageSource)event.source).getEntity() instanceof EntityPlayer
					 && !(((EntityDamageSource)event.source).getEntity() instanceof FakePlayer)) {
				
				if(event.entityLiving instanceof EntitySpider && event.entityLiving.getRNG().nextInt(500) == 0) {
					event.entityLiving.dropItem(ModItems.spider_milk, 1);
				}
				
				if(event.entityLiving instanceof EntityCaveSpider && event.entityLiving.getRNG().nextInt(100) == 0) {
					event.entityLiving.dropItem(ModItems.serum, 1);
				}
				
				if(event.entityLiving instanceof EntityAnimal && event.entityLiving.getRNG().nextInt(500) == 0) {
					event.entityLiving.dropItem(ModItems.bandaid, 1);
				}
				
				if(event.entityLiving instanceof IMob) {
					if(event.entityLiving.getRNG().nextInt(1000) == 0) event.entityLiving.dropItem(ModItems.heart_piece, 1);
					if(event.entityLiving.getRNG().nextInt(250) == 0) event.entityLiving.dropItem(ModItems.key_red_cracked, 1);
					if(event.entityLiving.getRNG().nextInt(250) == 0) event.entityLiving.dropItem(ModItems.launch_code_piece, 1);
				}
				
				if(event.entityLiving instanceof EntityCyberCrab && event.entityLiving.getRNG().nextInt(500) == 0) {
					event.entityLiving.dropItem(ModItems.wd40, 1);
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityDeathLast(LivingDeathEvent event) {
		
		EntityLivingBase entity = event.entityLiving;
		
		if(EntityDamageUtil.wasAttackedByV1(event.source)) {

			NBTTagCompound vdat = new NBTTagCompound();
			vdat.setString("type", "giblets");
			vdat.setInteger("ent", entity.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, entity.posX, entity.posY + entity.height * 0.5, entity.posZ), new TargetPoint(entity.dimension, entity.posX, entity.posY + entity.height * 0.5, entity.posZ, 150));
			
			entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + entity.worldObj.rand.nextFloat() * 0.2F);
			
			EntityPlayer attacker = (EntityPlayer) ((EntityDamageSource)event.source).getEntity();
			
			if(attacker.getDistanceSqToEntity(entity) < 100) {
				attacker.heal(entity.getMaxHealth() * 0.25F);
			}
		}
		
		if(event.entityLiving instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			
			for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
				
				ItemStack stack = player.inventory.getStackInSlot(i);
				
				if(stack != null && stack.getItem() == ModItems.detonator_deadman) {
					
					if(stack.stackTagCompound != null) {
						
						int x = stack.stackTagCompound.getInteger("x");
						int y = stack.stackTagCompound.getInteger("y");
						int z = stack.stackTagCompound.getInteger("z");

						if(!player.worldObj.isRemote && player.worldObj.getBlock(x, y, z) instanceof IBomb) {
							
							((IBomb) player.worldObj.getBlock(x, y, z)).explode(player.worldObj, x, y, z);
							
							if(GeneralConfig.enableExtendedLogging)
								MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by dead man's switch from " + player.getDisplayName() + "!");
						}
						
						player.inventory.setInventorySlotContents(i, null);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void decorateMob(LivingSpawnEvent event) {
		EntityLivingBase entity = event.entityLiving;
		World world = event.world;
		
		if(!MobConfig.enableMobGear || entity.isChild() || world.isRemote)
			return;

		if(entity instanceof EntityZombie) {
			if(rand.nextInt(64) == 0) {
				ItemStack mask = new ItemStack(ModItems.gas_mask_m65);
				ArmorUtil.installGasMaskFilter(mask, new ItemStack(ModItems.gas_mask_filter));
				entity.setCurrentItemOrArmor(4, mask);
			}
			if(rand.nextInt(128) == 0) {
				ItemStack mask = new ItemStack(ModItems.gas_mask_olde);
				ArmorUtil.installGasMaskFilter(mask, new ItemStack(ModItems.gas_mask_filter));
				entity.setCurrentItemOrArmor(4, mask);
			}
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
				ItemStack mask = new ItemStack(ModItems.gas_mask_m65);
				ArmorUtil.installGasMaskFilter(mask, new ItemStack(ModItems.gas_mask_filter));
				entity.setCurrentItemOrArmor(4, mask);
			}
			if(rand.nextInt(64) == 0)
				entity.setCurrentItemOrArmor(3, new ItemStack(ModItems.steel_plate, 1, world.rand.nextInt(ModItems.steel_plate.getMaxDamage())));
		}
	}
	
	@SubscribeEvent
	public void onItemToss(ItemTossEvent event) {
		
		ItemStack yeet = event.entityItem.getEntityItem();
		
		if(yeet.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(yeet)) {
			
			ItemStack[] mods = ArmorModHandler.pryMods(yeet);
			ItemStack cladding = mods[ArmorModHandler.cladding];
			
			if(cladding != null && cladding.getItem() == ModItems.cladding_obsidian) {
				ReflectionHelper.setPrivateValue(Entity.class, event.entityItem, true, "field_149119_a", "field_83001_bt", "field_149500_a", "invulnerable");
			}
		}
		
		if(yeet.getItem() == ModItems.bismuth_tool) {
			ReflectionHelper.setPrivateValue(Entity.class, event.entityItem, true, "field_149119_a", "field_83001_bt", "field_149500_a", "invulnerable");
		}
	}
	
	@SubscribeEvent
	public void onLivingDrop(LivingDropsEvent event) {
		
		if(!event.entityLiving.worldObj.isRemote) {
			boolean contaminated = HbmLivingProps.getContagion(event.entityLiving) > 0;
			
			if(contaminated) {
				
				for(EntityItem item : event.drops) {
					ItemStack stack = item.getEntityItem();
					
					if(!stack.hasTagCompound()) {
						stack.stackTagCompound = new NBTTagCompound();
					}
					
					stack.stackTagCompound.setBoolean("ntmContagion", true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		
		ItemStack[] prevArmor = event.entityLiving.previousEquipment;

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
						HazardSystem.applyHazards(mod, event.entityLiving);
						
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
		
		if(!event.entity.worldObj.isRemote && !(event.entityLiving instanceof EntityPlayer)) {
			HazardSystem.updateLivingInventory(event.entityLiving);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLoad(WorldEvent.Load event) {
		BobmazonOfferFactory.init();
	}
	
	public static boolean didSit = false;
	public static Field reference = null;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		
		/// RADIATION STUFF START ///
		if(event.world != null && !event.world.isRemote) {
			
			if(reference != null) {
				for(Object player : event.world.playerEntities) {
					if(((EntityPlayer) player).ridingEntity != null) { didSit = true; }
				}
				if(didSit && event.world.getTotalWorldTime() % (1 * 20 * 20) == 0) {
					try { reference.setFloat(null, (float) (rand.nextGaussian() * 0.1 + Math.PI)); } catch(Throwable e) { }
				}
			}
			
			int thunder = AuxSavedData.getThunder(event.world);
			
			if(thunder > 0)
				AuxSavedData.setThunder(event.world, thunder - 1);
			
			if(!event.world.loadedEntityList.isEmpty()) {
				
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
						
						if(entity.getClass().equals(EntityCreeper.class) && eRad >= 200 && entity.getHealth() > 0) {
							
							if(event.world.rand.nextInt(3) == 0 ) {
								EntityCreeperNuclear creep = new EntityCreeperNuclear(event.world);
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
						
						if(eRad < 200 || ContaminationUtil.isRadImmune(entity))
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
					
					if(e instanceof EntityItem) {
						EntityItem item = (EntityItem) e;
						HazardSystem.updateDroppedItem(item);
					}
				}
				/**
				 * REMOVE THIS ^ ^ ^
				 */
			}
			/// RADIATION STUFF END ///
			
			
			if(event.phase == Phase.END) {
				EntityRailCarBase.updateMotion(event.world);
			}
		}
		
		if(event.phase == Phase.START) {
			BossSpawnHandler.rollTheDice(event.world);
			TimedGenerator.automaton(event.world, 100);
		}
	}
	
	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		
		EntityLivingBase e = event.entityLiving;

		if(e instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) e;
			
			if(ArmorUtil.checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
				HbmPlayerProps.plink(player, "random.break", 0.5F, 1.0F + e.getRNG().nextFloat() * 0.5F);
				event.setCanceled(true);
			}
			
			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof ArmorFSB)
				((ArmorFSB)player.inventory.armorInventory[2].getItem()).handleAttack(event);
			
			for(ItemStack stack : player.inventory.armorInventory) {
				if(stack != null && stack.getItem() instanceof IAttackHandler) {
					((IAttackHandler)stack.getItem()).handleAttack(event, stack);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event) {
		
		EntityLivingBase e = event.entityLiving;
		
		if(e instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) e;
			
			HbmPlayerProps props = HbmPlayerProps.getData(player);
			if(props.shield > 0) {
				float reduce = Math.min(props.shield, event.ammount);
				props.shield -= reduce;
				event.ammount -= reduce;
			}
			props.lastDamage = player.ticksExisted;
		}
		
		if(HbmLivingProps.getContagion(e) > 0 && event.ammount < 100)
			event.ammount *= 2F;
		
		/// V1 ///
		if(EntityDamageUtil.wasAttackedByV1(event.source)) {
			EntityPlayer attacker = (EntityPlayer) ((EntityDamageSource)event.source).getEntity();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setInteger("count", (int)Math.min(e.getMaxHealth() / 2F, 250));
			data.setDouble("motion", 0.1D);
			data.setString("mode", "blockdust");
			data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 50));
			
			if(attacker.getDistanceSqToEntity(e) < 25) {
				attacker.heal(event.ammount * 0.5F);
			}
		}
		
		/// ARMOR MODS ///
		for(int i = 1; i < 5; i++) {
			
			ItemStack armor = e.getEquipmentInSlot(i);
			
			if(armor != null && ArmorModHandler.hasMods(armor)) {
				
				for(ItemStack mod : ArmorModHandler.pryMods(armor)) {
					
					if(mod != null && mod.getItem() instanceof ItemArmorMod) {
						((ItemArmorMod)mod.getItem()).modDamage(event, armor);
					}
				}
			}
		}
		
		if(e instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) e;
			
			/// FSB ARMOR ///
			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof ArmorFSB)
				((ArmorFSB)player.inventory.armorInventory[2].getItem()).handleHurt(event);
	
			
			for(ItemStack stack : player.inventory.armorInventory) {
				if(stack != null && stack.getItem() instanceof IDamageHandler) {
					((IDamageHandler)stack.getItem()).handleDamage(event, stack);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerFall(PlayerFlyableFallEvent event) {
		
		EntityPlayer e = event.entityPlayer;
		
		if(e.inventory.armorInventory[2] != null && e.inventory.armorInventory[2].getItem() instanceof ArmorFSB)
			((ArmorFSB)e.inventory.armorInventory[2].getItem()).handleFall(e);
	}
	
	@SubscribeEvent
	public void onPlayerPunch(AttackEntityEvent event) {
		
		EntityPlayer player = event.entityPlayer;
		ItemStack chestplate = player.inventory.armorInventory[2];
		
		if(!player.worldObj.isRemote && player.getHeldItem() == null && chestplate != null && ArmorModHandler.hasMods(chestplate)) {
			ItemStack[] mods = ArmorModHandler.pryMods(chestplate);
			ItemStack servo = mods[ArmorModHandler.servos];
			
			if(servo != null && servo.getItem() == ModItems.ballistic_gauntlet) {
				
				BulletConfiguration firedConfig = null;

				for(Integer config : HbmCollection.g12) {
					BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
					
					if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, true, true)) {
						firedConfig = cfg;
						break;
					}
				}
				
				if(firedConfig != null) {
					int bullets = firedConfig.bulletsMin;
					
					if(firedConfig.bulletsMax > firedConfig.bulletsMin) {
						bullets += player.getRNG().nextInt(firedConfig.bulletsMax - firedConfig.bulletsMin);
					}
					
					for(int i = 0; i < bullets; i++) {
						EntityBulletBaseNT bullet = new EntityBulletBaseNT(player.worldObj, BulletConfigSyncingUtil.getKey(firedConfig), player);
						player.worldObj.spawnEntityInWorld(bullet);
					}
					
					player.worldObj.playSoundAtEntity(player, "hbm:weapon.shotgunShoot", 1.0F, 1.0F);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event) {
		
		EntityLivingBase e = event.entityLiving;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).inventory.armorInventory[2] != null && ((EntityPlayer)e).inventory.armorInventory[2].getItem() instanceof ArmorFSB)
			((ArmorFSB)((EntityPlayer)e).inventory.armorInventory[2].getItem()).handleJump((EntityPlayer)e);
	}
	
	@SubscribeEvent
	public void onEntityFall(LivingFallEvent event) {
		
		EntityLivingBase e = event.entityLiving;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).inventory.armorInventory[2] != null && ((EntityPlayer)e).inventory.armorInventory[2].getItem() instanceof ArmorFSB)
			((ArmorFSB)((EntityPlayer)e).inventory.armorInventory[2].getItem()).handleFall((EntityPlayer)e);
	}
	
	@SubscribeEvent
	public void onWingFlop(TickEvent.PlayerTickEvent event) {

		EntityPlayer player = event.player;
		
		if(event.phase == TickEvent.Phase.START) {
			
			if(player.getCurrentArmor(2) == null && !player.onGround) {
				
				boolean isBob = player.getUniqueID().toString().equals(ShadyUtil.HbMinecraft) || player.getDisplayName().equals("HbMinecraft");
				boolean isOther = player.getUniqueID().toString().equals(ShadyUtil.the_NCR) || player.getDisplayName().equals("the_NCR");
				
				if(isBob || isOther) {
					
					ArmorUtil.resetFlightTime(player);
					
					if(player.fallDistance > 0)
						player.fallDistance = 0;
					
					if(player.motionY < -0.4D)
						player.motionY = -0.4D;
					
					HbmPlayerProps props = HbmPlayerProps.getData(player);
					
					if(isBob || player.getFoodStats().getFoodLevel() > 6) {
						
						if(props.isJetpackActive()) {
							
							double cap = (isBob ? 0.8D : 0.4D);
							
							if(player.motionY < cap)
								player.motionY += 0.15D;
							else
								player.motionY = cap + 0.15D;
							
							if(isOther) {
								if(player.getFoodStats().getSaturationLevel() > 0F)
									player.addExhaustion(4F); //burn up saturation so that super-saturating foods have no effect
								else
									player.addExhaustion(0.2F); //4:1 -> 0.05 hunger per tick or 1 per second
							}
							
						} else if(props.enableBackpack && !player.isSneaking()) {
							
							if(player.motionY < -1)
								player.motionY += 0.4D;
							else if(player.motionY < -0.1)
								player.motionY += 0.2D;
							else if(player.motionY < 0)
								player.motionY = 0;

							if(isOther && !player.onGround) {
								if(player.getFoodStats().getSaturationLevel() > 0F)
									player.addExhaustion(4F);
								else
									player.addExhaustion(0.04F);
							}
							
						} else if(!props.enableBackpack && player.isSneaking()) {
							
							if(player.motionY < -0.08) {
	
								double mo = player.motionY * (isBob ? -0.6 : -0.4);
								player.motionY += mo;
	
								Vec3 vec = player.getLookVec();
								vec.xCoord *= mo;
								vec.yCoord *= mo;
								vec.zCoord *= mo;
	
								player.motionX += vec.xCoord;
								player.motionY += vec.yCoord;
								player.motionZ += vec.zCoord;
							}
						}
					}
					
					Vec3 orig = player.getLookVec();
					Vec3 look = Vec3.createVectorHelper(orig.xCoord, 0, orig.zCoord).normalize();
					double mod = props.enableBackpack ? (isBob ? 0.5D : 0.25D) : 0.125D;
					
					if(player.moveForward != 0) {
						player.motionX += look.xCoord * 0.35 * player.moveForward * mod;
						player.motionZ += look.zCoord * 0.35 * player.moveForward * mod;
					}
					
					if(player.moveStrafing != 0) {
						look.rotateAroundY((float) Math.PI * 0.5F);
						player.motionX += look.xCoord * 0.15 * player.moveStrafing * mod;
						player.motionZ += look.zCoord * 0.15 * player.moveStrafing * mod;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() instanceof ArmorFSB)
			((ArmorFSB)player.inventory.armorInventory[2].getItem()).handleTick(event);
		
		if(player.ticksExisted == 100 || player.ticksExisted == 200)
			CraftingManager.crumple();
		
		if(event.phase == TickEvent.Phase.START) {
			int x = MathHelper.floor_double(player.posX);
			int y = MathHelper.floor_double(player.posY - player.yOffset - 0.01);
			int z = MathHelper.floor_double(player.posZ);
			Block b = player.worldObj.getBlock(x, y, z);
			
			if(b instanceof IStepTickReceiver && !player.capabilities.isFlying) {
				IStepTickReceiver step = (IStepTickReceiver) b;
				step.onPlayerStep(player.worldObj, x, y, z, player);
			}
		}
		
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
			
			if(player.getUniqueID().toString().equals(ShadyUtil.Pu_238)) {
				
				List<EntityLivingBase> entities = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, player.boundingBox.expand(3, 3, 3));
				
				for(EntityLivingBase e : entities) {
					
					if(e != player) {
						e.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 300, 2));
					}
				}
			}
			
			/// PU RADIATION END ///
			
			/*if(player instanceof EntityPlayerMP) {

				int x = (int) Math.floor(player.posX);
				int y = (int) Math.floor(player.posY - 0.01);
				int z = (int) Math.floor(player.posZ);
				
				if(player.worldObj.getTileEntity(x, y, z) instanceof IEnergyConductor) {
					PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(((IEnergyConductor) player.worldObj.getTileEntity(x, y, z)).getPowerNet() + ""), (EntityPlayerMP) player);
				}
			}*/

			/// NEW ITEM SYS START ///
			HazardSystem.updatePlayerInventory(player);
			/// NEW ITEM SYS END ///
			
			/// SYNC START ///
			if(player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new PermaSyncPacket((EntityPlayerMP) player), (EntityPlayerMP) player);
			/// SYNC END ///
		}

		//TODO: rewrite this so it doesn't look like shit
		if(player.worldObj.isRemote && event.phase == event.phase.START && !player.isInvisible() && !player.isSneaking()) {
			
			if(player.getUniqueID().toString().equals(ShadyUtil.HbMinecraft)) {
				
				int i = player.ticksExisted * 3;
				
				Vec3 vec = Vec3.createVectorHelper(3, 0, 0);
				
				vec.rotateAroundY((float) (i * Math.PI / 180D));
				
				for(int k = 0; k < 5; k++) {
					
					vec.rotateAroundY((float) (1F * Math.PI / 180D));
					//player.worldObj.spawnParticle("townaura", player.posX + vec.xCoord, player.posY + 1 + player.worldObj.rand.nextDouble() * 0.05, player.posZ + vec.zCoord, 0.0, 0.0, 0.0);
				}
			}
			
			if(player.getUniqueID().toString().equals(ShadyUtil.Pu_238)) {
				
				Vec3 vec = Vec3.createVectorHelper(3 * rand.nextDouble(), 0, 0);
				
				vec.rotateAroundZ((float) (rand.nextDouble() * Math.PI));
				vec.rotateAroundY((float) (rand.nextDouble() * Math.PI * 2));
				
				player.worldObj.spawnParticle("townaura", player.posX + vec.xCoord, player.posY + 1 + vec.yCoord, player.posZ + vec.zCoord, 0.0, 0.0, 0.0);
			}
		}
	}
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		
		if(event.phase == event.phase.START) {
			RTTYSystem.updateBroadcastQueue();
			RequestNetwork.updateEntries();
			TileEntityMachineRadarNT.updateSystem();
			Nodespace.updateNodespace();
		}
	}
	
	@SubscribeEvent
	public void enteringChunk(EnteringChunk evt) {
		
		/*if(evt.entity instanceof EntityMissileBaseNT) {
			((EntityMissileBaseNT) evt.entity).loadNeighboringChunks(evt.newChunkX, evt.newChunkZ);
		}

		if(evt.entity instanceof EntityMissileCustom) {
			((EntityMissileCustom) evt.entity).loadNeighboringChunks(evt.newChunkX, evt.newChunkZ);
		}*/
	}
	
	@SubscribeEvent
	public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		
		NBTTagCompound data = new NBTTagCompound();
		HbmPlayerProps.getData(event.original).saveNBTData(data);
		HbmPlayerProps.getData(event.entityPlayer).loadNBTData(data);
	}
	
	@SubscribeEvent
	public void itemCrafted(PlayerEvent.ItemCraftedEvent e) {
		AchievementHandler.fire(e.player, e.crafting);
	}
	
	@SubscribeEvent
	public void itemSmelted(PlayerEvent.ItemSmeltedEvent e) {
		AchievementHandler.fire(e.player, e.smelting);
		
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
	public void onItemPickup(PlayerEvent.ItemPickupEvent event) {
		if(event.pickedUp.getEntityItem().getItem() == ModItems.canned_conserve && EnumUtil.grabEnumSafely(EnumFoodType.class, event.pickedUp.getEntityItem().getItemDamage()) == EnumFoodType.JIZZ)
			event.player.triggerAchievement(MainRegistry.achC20_5);
		if(event.pickedUp.getEntityItem().getItem() == Items.slime_ball)
			event.player.triggerAchievement(MainRegistry.achSlimeball);
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		
		EntityPlayer player = event.getPlayer();
		
		if(!(player instanceof EntityPlayerMP))
			return;
		
		if(event.block == ModBlocks.stone_gneiss && !((EntityPlayerMP) player).func_147099_x().hasAchievementUnlocked(MainRegistry.achStratum)) {
			event.getPlayer().triggerAchievement(MainRegistry.achStratum);
			event.setExpToDrop(500);
		}
		
		if(event.block == Blocks.coal_ore || event.block == Blocks.coal_block || event.block == ModBlocks.ore_lignite) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

				int x = event.x + dir.offsetX;
				int y = event.y + dir.offsetY;
				int z = event.z + dir.offsetZ;
				
				if(event.world.rand.nextInt(2) == 0 && event.world.getBlock(x, y, z) == Blocks.air)
					event.world.setBlock(x, y, z, ModBlocks.gas_coal);
			}
		}
		
		if(RadiationConfig.enablePollution && RadiationConfig.enableLeadFromBlocks) {
			if(!ArmorRegistry.hasProtection(player, 3, HazardClass.PARTICLE_FINE)) {
				
				float metal = PollutionHandler.getPollution(player.worldObj, event.x, event.y, event.z, PollutionType.HEAVYMETAL);
				
				if(metal < 5) return;
				
				if(metal < 10) {
					player.addPotionEffect(new PotionEffect(HbmPotion.lead.id, 100, 0));
				} else if(metal < 25) {
					player.addPotionEffect(new PotionEffect(HbmPotion.lead.id, 100, 1));
				} else {
					player.addPotionEffect(new PotionEffect(HbmPotion.lead.id, 100, 2));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onClickSign(PlayerInteractEvent event) {

		int x = event.x;
		int y = event.y;
		int z = event.z;
		World world = event.world;
		
		if(!world.isRemote && event.action == Action.RIGHT_CLICK_BLOCK && world.getTileEntity(x, y, z) instanceof TileEntitySign) {
			
			TileEntitySign sign = (TileEntitySign)world.getTileEntity(x, y, z);
			
			String result = ShadyUtil.smoosh(sign.signText[0], sign.signText[1], sign.signText[2], sign.signText[3]);
			
			if(ShadyUtil.hashes.contains(result)) {
				world.func_147480_a(x, y, z, false);
				EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(ModItems.bobmazon_hidden));
				entityitem.delayBeforeCanPickup = 10;
				world.spawnEntityInWorld(entityitem);
			}
		}
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
		
		//this part means that the message's first character has to equal a '!': ----------------------------+
		//                                                                                                   |
		//this is a logical AND operator: ----------------------------------------------------------------+  |
		//                                                                                                |  |
		//this is a reference to a field in                                                               |  |
		//Library.java containing a reference UUID: -----------------------------------------+            |  |
		//                                                                                   |            |  |
		//this will compare said UUID with                                                   |            |  |
		//the string representation of the                                                   |            |  |
		//current player's UUID: -----------+                                                |            |  |
		//                                  |                                                |            |  |
		//another AND operator: ---------+  |                                                |            |  |
		//                               |  |                                                |            |  |
		//this is a reference to a       |  |                                                |            |  |
		//boolean called                 |  |                                                |            |  |
		//'enableDebugMode' which is     |  |                                                |            |  |
		//only set once by the mod's     |  |                                                |            |  |
		//config and is disabled by      |  |                                                |            |  |
		//default. "debug" is not a      |  |                                                |            |  |
		//substring of the message, nor  |  |                                                |            |  |
		//something that can be toggled  |  |                                                |            |  |
		//in any other way except for    |  |                                                |            |  |
		//the config file: |             |  |                                                |            |  |
		//                 V             V  V                                                V            V  V
		if(GeneralConfig.enableDebugMode && player.getUniqueID().toString().equals(ShadyUtil.HbMinecraft) && message.startsWith("!")) {
			
			String[] msg = message.split(" ");
			
			String m = msg[0].substring(1, msg[0].length()).toLowerCase(Locale.US);
			
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
			
			while(itr.hasNext()) {
				
				int i = ((Integer) itr.next()).intValue();
				int j = ((Integer) mapright.get(Integer.valueOf(i))).intValue();
				Enchantment e = Enchantment.enchantmentsList[i];
				
				EnchantmentUtil.removeEnchantment(event.output, e);
				EnchantmentUtil.addEnchantment(event.output, e, j);
			}
			
			event.cost = 10;
		}
	}
	
	@SubscribeEvent
	public void onFoodEaten(PlayerUseItemEvent.Finish event) {
		
		ItemStack stack = event.item;
		
		if(stack != null && stack.getItem() instanceof ItemFood) {
			
			if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("ntmCyanide")) {
				for(int i = 0; i < 10; i++) {
					event.entityPlayer.attackEntityFrom(rand.nextBoolean() ? ModDamageSource.euthanizedSelf : ModDamageSource.euthanizedSelf2, 1000);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void filterBrokenEntity(EntityJoinWorldEvent event) {
		
		Entity entity = event.entity;
		Entity[] parts = entity.getParts();
		
		//MainRegistry.logger.error("Trying to spawn entity " + entity.getClass().getCanonicalName());
		
		if(parts != null) {
			
			for(int i = 0; i < parts.length; i++) {
				if(parts[i] == null) {
					MainRegistry.logger.error("Prevented spawning of multipart entity " + entity.getClass().getCanonicalName() + " due to parts being null!");
					event.setCanceled(true);
					return;
				}
			}
		}
	}
}
