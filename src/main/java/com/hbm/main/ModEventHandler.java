package com.hbm.main;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.hbm.blocks.IStepTickReceiver;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockAshes;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.ServerConfig;
import com.hbm.entity.mob.*;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.BobmazonOfferFactory;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.EntityEffectHandler;
import com.hbm.hazard.HazardSystem;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.handler.HTTPHandler;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.handler.neutron.NeutronNodeWorld;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.armor.*;
import com.hbm.items.food.ItemConserve.EnumFoodType;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.XFactory12ga;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.PermaSyncPacket;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.packet.toclient.SerializableRecipePacket;
import com.hbm.particle.helper.BlackPowderCreator;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.network.RTTYSystem;
import com.hbm.tileentity.network.RequestNetwork;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.*;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.world.generator.TimedGenerator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import net.minecraft.block.Block;
import net.minecraft.command.CommandGameRule;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
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
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Spaghetti("fuck")
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

			if(GeneralConfig.enableServerRecipeSync && FMLCommonHandler.instance().getSide() == Side.SERVER && event.player instanceof EntityPlayerMP) {
				File recDir = new File(MainRegistry.configDir.getAbsolutePath() + File.separatorChar + "hbmRecipes");

				MainRegistry.logger.info("Sending recipes to client!");

				boolean hasSent = false;

				for(SerializableRecipe recipe : SerializableRecipe.recipeHandlers) {
					File recFile = new File(recDir.getAbsolutePath() + File.separatorChar + recipe.getFileName());
					if(recFile.exists() && recFile.isFile()) {
						MainRegistry.logger.info("Sending recipe file: " + recFile.getName());
						PacketDispatcher.wrapper.sendTo(new SerializableRecipePacket(recFile), (EntityPlayerMP) event.player);
						hasSent = true;
					}
				}

				if(hasSent) {
					PacketDispatcher.wrapper.sendTo(new SerializableRecipePacket(true), (EntityPlayerMP) event.player);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLeftClient(ClientDisconnectionFromServerEvent event) {
		SerializableRecipe.clearReceivedRecipes();
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

		if(!MobConfig.enableMobGear || entity.isChild() || world.isRemote) return;

		Map<Integer, List<WeightedRandomObject>> slotPools = new HashMap<>();

		float soot = PollutionHandler.getPollution(entity.worldObj, MathHelper.floor_double(event.x), MathHelper.floor_double(event.y), MathHelper.floor_double(event.z), PollutionType.SOOT); //uhfgfg

		if(entity instanceof EntityZombie) {
			if(world.rand.nextFloat() < 0.005F && soot > 2) { // full hazmat zombine
				MobUtil.equipFullSet(entity, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots);
				return;
			}
			slotPools = MobUtil.slotPoolCommon;

		} else if(entity instanceof EntitySkeleton) {
			slotPools = MobUtil.slotPoolRanged;
			ItemStack bowReplacement = getSkelegun(soot, world.rand);
			slotPools.put(0, createSlotPool(50, bowReplacement != null ? new Object[][]{{bowReplacement, 1}} : new Object[][]{}));
		}

		MobUtil.assignItemsToEntity(entity, slotPools, rand);
	}

	private List<WeightedRandomObject> createSlotPool(int nullWeight, Object[][] items) {
		List<WeightedRandomObject> pool = new ArrayList<>();
		pool.add(new WeightedRandomObject(null, nullWeight));
		for (Object[] item : items) {
			Object obj = item[0];
			int weight = (int) item[1];

			if (obj instanceof Item) {
				pool.add(new WeightedRandomObject(new ItemStack((Item) obj), weight));
			} else if (obj instanceof ItemStack) {		//lol just make it pass ItemStack aswell
				pool.add(new WeightedRandomObject(obj, weight));
			}
		}
		return pool;
	}

	private static ItemStack getSkelegun(float soot, Random rand) {
		if (!MobConfig.enableMobWeapons) return null;
		if (rand.nextDouble() > Math.log(soot) * 0.25) return null;

		ArrayList<WeightedRandomObject> pool = new ArrayList<>();

		if(soot < 0.3){
			pool.add(new WeightedRandomObject(new ItemStack(ModItems.gun_pepperbox), 5));
			pool.add(new WeightedRandomObject(null, 20));
		} else if(soot > 0.3 && soot < 1) {
			pool.addAll(MobUtil.slotPoolGuns.get(0.3));
		} else if (soot < 3) {
			pool.addAll(MobUtil.slotPoolGuns.get(1D));
		} else if (soot < 5) {
			pool.addAll(MobUtil.slotPoolGuns.get(3D));
		} else {
			pool.addAll(MobUtil.slotPoolGuns.get(5D));
		}

		WeightedRandomObject selected = (WeightedRandomObject) WeightedRandom.getRandomItem(rand, pool);

		return selected.asStack();
	}

	@SubscribeEvent
	public void addAITasks(EntityJoinWorldEvent event) {
		if(event.world.isRemote || !(event.entity instanceof EntityLiving)) return;

		EntityLiving living = (EntityLiving) event.entity;
		ItemStack held = living.getHeldItem();

		if(held != null && held.getItem() instanceof ItemGunBaseNT) {
			MobUtil.addFireTask(living);
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

			((IEquipReceiver)event.entityLiving.getHeldItem().getItem()).onEquip((EntityPlayer) event.entityLiving, event.entityLiving.getHeldItem());
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

	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event) {
		NeutronNodeWorld.streamWorlds.remove(event.world);
	}

	public static boolean didSit = false;
	public static Field reference = null;

	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {

		if(event.world != null && !event.world.isRemote) {

			if(reference != null) {
				for(Object player : event.world.playerEntities) {
					if(((EntityPlayer) player).ridingEntity != null && event.world.getTotalWorldTime() % (1 * 60 * 20) == 0) {
						((EntityPlayer) player).mountEntity(null);
						didSit = true;
					}
				}
				/*if(didSit && event.world.getTotalWorldTime() % (1 * 20 * 20) == 0) {
					try { reference.setFloat(null, (float) (rand.nextGaussian() * 0.1 + Math.PI)); } catch(Throwable e) { }
				}*/
			}

			if(event.phase == Phase.END) {

				int tickrate = Math.max(1, ServerConfig.ITEM_HAZARD_DROP_TICKRATE.get());

				if(event.world.getTotalWorldTime() % tickrate == 0) {
					List loadedEntityList = new ArrayList();
					loadedEntityList.addAll(event.world.loadedEntityList); // ConcurrentModificationException my balls

					for(Object e : loadedEntityList) {

						if(e instanceof EntityItem) {
							EntityItem item = (EntityItem) e;
							HazardSystem.updateDroppedItem(item);
						}
					}
				}

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

	// only for the ballistic gauntlet! contains dangerous conditional returns!
	@SubscribeEvent
	public void onPlayerPunch(AttackEntityEvent event) {

		EntityPlayer player = event.entityPlayer;
		ItemStack chestplate = player.inventory.armorInventory[2];

		if(!player.worldObj.isRemote && chestplate != null && ArmorModHandler.hasMods(chestplate)) {

			if(player.getHeldItem() != null && player.getHeldItem().getAttributeModifiers().containsKey(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) return;

			ItemStack[] mods = ArmorModHandler.pryMods(chestplate);
			ItemStack servo = mods[ArmorModHandler.servos];

			if(servo != null && servo.getItem() == ModItems.ballistic_gauntlet) {

				BulletConfig firedConfig = null;
				BulletConfig[] gauntletConfigs = new BulletConfig[] {XFactory12ga.g12_bp, XFactory12ga.g12_bp_magnum, XFactory12ga.g12_bp_slug, XFactory12ga.g12, XFactory12ga.g12_slug, XFactory12ga.g12_flechette, XFactory12ga.g12_magnum, XFactory12ga.g12_explosive, XFactory12ga.g12_phosphorus};

				for(BulletConfig config : gauntletConfigs) {

					if(InventoryUtil.doesPlayerHaveAStack(player, config.ammo, true, true)) {
						firedConfig = config;
						break;
					}
				}

				if(firedConfig != null) {
					int bullets = firedConfig.projectilesMin;

					if(firedConfig.projectilesMax > firedConfig.projectilesMin) {
						bullets += player.getRNG().nextInt(firedConfig.projectilesMax - firedConfig.projectilesMin);
					}

					for(int i = 0; i < bullets; i++) {
						EntityBulletBaseMK4 mk4 = new EntityBulletBaseMK4(player, firedConfig, 15F, 0F, -0.1875, -0.0625, 0.5);
						player.worldObj.spawnEntityInWorld(mk4);
						if(i == 0 && firedConfig.blackPowder) BlackPowderCreator.composeEffect(player.worldObj, mk4.posX, mk4.posY, mk4.posZ, mk4.motionX, mk4.motionY, mk4.motionZ, 10, 0.25F, 0.5F, 10, 0.25F);
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

	private static final UUID fopSpeed = UUID.fromString("e5a8c95d-c7a0-4ecf-8126-76fb8c949389");

	@SubscribeEvent
	public void onWingFlop(TickEvent.PlayerTickEvent event) {

		EntityPlayer player = event.player;

		if(event.phase == TickEvent.Phase.START) {

			if(player.getCurrentArmor(2) == null && !player.onGround) {

				if(player.getUniqueID().toString().equals(ShadyUtil.Barnaby99_x) || player.getDisplayName().equals("pheo7")) {

					ArmorUtil.resetFlightTime(player);
					HbmPlayerProps props = HbmPlayerProps.getData(player);

					if(props.isJetpackActive()) {

						if(player.motionY < 0.4D)
							player.motionY += 0.1D;

						Vec3 look = player.getLookVec();

						if(Vec3.createVectorHelper(player.motionX, player.motionY, player.motionZ).lengthVector() < 2) {
							player.motionX += look.xCoord * 0.2;
							player.motionY += look.yCoord * 0.2;
							player.motionZ += look.zCoord * 0.2;

							if(look.yCoord > 0)
								player.fallDistance = 0;
						}
					} else if(props.enableBackpack && !player.isSneaking()) {
						if(player.motionY < -0.2) player.motionY += 0.075D;
						if(player.fallDistance > 0) player.fallDistance = 0;
					}
				}

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

			if(player.getUniqueID().toString().equals(ShadyUtil.LePeeperSauvage) ||	player.getDisplayName().equals("LePeeperSauvage")) {

				Multimap multimap = HashMultimap.create();
				multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(fopSpeed, "FOP SPEED", 0.5, 1));
				player.getAttributeMap().removeAttributeModifiers(multimap);

				if(player.isSprinting()) {
					player.getAttributeMap().applyAttributeModifiers(multimap);
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

			/// NEW ITEM SYS START ///
			HazardSystem.updatePlayerInventory(player);
			/// NEW ITEM SYS END ///

			/// SYNC START ///
			if(!player.worldObj.isRemote && player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new PermaSyncPacket((EntityPlayerMP) player), (EntityPlayerMP) player);
			/// SYNC END ///
		}

		if(player.worldObj.isRemote && event.phase == event.phase.START && !player.isInvisible() && !player.isSneaking()) {

			if(player.getUniqueID().toString().equals(ShadyUtil.Pu_238)) {

				Vec3 vec = Vec3.createVectorHelper(3 * rand.nextDouble(), 0, 0);
				vec.rotateAroundZ((float) (rand.nextDouble() * Math.PI));
				vec.rotateAroundY((float) (rand.nextDouble() * Math.PI * 2));
				player.worldObj.spawnParticle("townaura", player.posX + vec.xCoord, player.posY + 1 + vec.yCoord, player.posZ + vec.zCoord, 0.0, 0.0, 0.0);
			}
		}

		/*if(!player.worldObj.isRemote && event.phase == TickEvent.Phase.END && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGunBaseNT && player instanceof EntityPlayerMP) {
			HeldItemNBTPacket packet = new HeldItemNBTPacket(player.getHeldItem());
			PacketDispatcher.wrapper.sendTo(packet, (EntityPlayerMP) player);
		}*/
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

		if(event.phase == Phase.START) {

			// Redstone over Radio
			RTTYSystem.updateBroadcastQueue();
			// Logistics drone network
			RequestNetwork.updateEntries();
			// Radar entry handling
			TileEntityMachineRadarNT.updateSystem();
			// Networks! All of them!
			UniNodespace.updateNodespace();
		}

		// There is an issue here somewhere...
		// I cannot, for the life of me, figure out why a single certain bug happens.
		// Every 20-30 or so ticks, players will receive wrong/outdated/weird information in packets
		// I have tried everything to see if I can get this to stop, but it just doesn't seem to work.

		// ^ Update ^ - I figured it out, when the packets were being made for some machines they were being created inside the thread,
		// meaning sometimes the machine would change data *after* the packet was supposed to be sent, meaning incorrect data was being sent.
		// This has since been fixed.

		if(event.phase == Phase.END) {
			// As ByteBufs are added to the queue in `com.hbm.packet.toclient.PacketThreading`, they are processed by the packet thread.
			// This waits until the thread is finished, which most of the time will be instantly since it has plenty of time to process in parallel to everything else.
			PacketThreading.waitUntilThreadFinished();

			NetworkHandler.flush(); // Flush ALL network packets.
		}
	}

	@SubscribeEvent
	public void commandEvent(CommandEvent event) {
		ICommand command = event.command;
		ICommandSender sender = event.sender;
		if(command instanceof CommandGameRule) {
			if(command.canCommandSenderUseCommand(sender)) {
				command.processCommand(sender,event.parameters);
				RBMKDials.refresh(sender.getEntityWorld()); // Refresh RBMK gamerules.
				event.setCanceled(true);
			}
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
	public void onChunkLoad(ChunkEvent.Load event) {

		//test for automatic in-world block replacement

		/*for(int x = 0; x < 16; x++) for(int y = 0; y < 255; y++) for(int z = 0; z < 16; z++) {
			if(event.getChunk().getBlock(x, y, z) instanceof MachineArcFurnace) {
				event.getChunk().func_150807_a(x, y, z, Blocks.air, 0);
			}
		}*/
	}

	@SubscribeEvent
	public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {

		ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer();
		HbmPlayerProps.getData(event.original).serialize(buf);
		HbmPlayerProps.getData(event.entityPlayer).deserialize(buf);
		buf.release();
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
	public void onAnvilRepair(AnvilRepairEvent event) {

		// Anvil renaming no longer increments the repair cost
		// Note: Forge has a bug, the names are wrong. Right is output, output is left, left is right
		if(event.left == null && event.right != null && event.output != null) {
			int oldRepairCost = event.output.getRepairCost();

			if (oldRepairCost > 0) {
				event.right.setRepairCost(oldRepairCost);
			} else if (event.right.hasTagCompound()) {
				NBTTagCompound nbt = event.right.getTagCompound();
				nbt.removeTag("RepairCost");
				if (nbt.hasNoTags()) {
					event.right.setTagCompound(null);
				}
			}
		}
	}

	@SubscribeEvent
	public void onClickSign(PlayerInteractEvent event) {

		int x = event.x;
		int y = event.z;
		int z = event.y;
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
