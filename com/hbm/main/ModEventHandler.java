package com.hbm.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.RadSurveyPacket;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.saveddata.RadEntitySavedData;
import com.hbm.saveddata.RadiationSavedData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

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
	public void onPlayerDeath(LivingDeathEvent event) {

		RadEntitySavedData eData = RadEntitySavedData.getData(event.entityLiving.worldObj);
		eData.setRadForEntity(event.entityLiving, 0);
		
		if(MainRegistry.enableCataclysm) {
			EntityBurningFOEQ foeq = new EntityBurningFOEQ(event.entity.worldObj);
			foeq.setPositionAndRotation(event.entity.posX, 500, event.entity.posZ, 0.0F, 0.0F);
			event.entity.worldObj.spawnEntityInWorld(foeq);
		}
		
		if(event.entity.getUniqueID().toString().equals(Library.HbMinecraft)) {
			
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
		
		
		/// METEOR SHOWER START ///
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
				if(meteorShower == 0 && MainRegistry.enableDebugMode)
					MainRegistry.logger.info("Ended meteor shower.");
			}
			
			if(event.world.rand.nextInt(MainRegistry.meteorStrikeChance * 100) == 0 && MainRegistry.enableMeteorShowers) {
				meteorShower = 
						(int)(MainRegistry.meteorShowerDuration * 0.75 + 
								MainRegistry.meteorShowerDuration * 0.25 * event.world.rand.nextFloat());

				if(MainRegistry.enableDebugMode)
					MainRegistry.logger.info("Started meteor shower! Duration: " + meteorShower);
			}
		}
		/// METEOR SHOWER END ///

		/// RADIATION STUFF START ///
		if(event.world != null && !event.world.isRemote && MainRegistry.enableRads) {
			
			int thunder = AuxSavedData.getThunder(event.world);
			
			if(thunder > 0)
				AuxSavedData.setThunder(event.world, thunder - 1);
			
			if(!event.world.loadedEntityList.isEmpty()) {

				RadiationSavedData data = RadiationSavedData.getData(event.world);
				RadEntitySavedData eData = RadEntitySavedData.getData(event.world);
				
				if(eData.worldObj == null) {
					eData.worldObj = event.world;
				}
				
				if(data.worldObj == null) {
					data.worldObj = event.world;
				}
				
				for(Object o : event.world.playerEntities) {
					
					EntityPlayer player = (EntityPlayer)o;
					PacketDispatcher.wrapper.sendTo(new RadSurveyPacket(eData.getRadFromEntity(player)), (EntityPlayerMP) player);
				}
				
				if(event.world.getTotalWorldTime() % 20 == 0) {
					data.updateSystem();
				}
				
				List<Object> oList = new ArrayList<Object>();
				oList.addAll(event.world.loadedEntityList);
				
				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {
						
						//effect for radiation
						EntityLivingBase entity = (EntityLivingBase) e;

						if(event.world.getTotalWorldTime() % 20 == 0) {

							Chunk chunk = entity.worldObj.getChunkFromBlockCoords((int)entity.posX, (int)entity.posZ);
							float rad = data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition);
							
							if(rad > 0) {
								//eData.increaseRad(entity, rad / 2);
								
								if(!entity.isPotionActive(HbmPotion.mutation))
									Library.applyRadData(entity, rad / 2);
							}
							
							if(entity.worldObj.isRaining() && MainRegistry.cont > 0 && AuxSavedData.getThunder(entity.worldObj) > 0 &&
									entity.worldObj.canBlockSeeTheSky(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ))) {

								if(!entity.isPotionActive(HbmPotion.mutation)) {
									Library.applyRadData(entity, MainRegistry.cont * 0.005F);
								}
							}
						}
						
						float eRad = eData.getRadFromEntity(entity);
						
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
			        	}
						
						if(eRad < 200 || entity instanceof EntityNuclearCreeper || entity instanceof EntityMooshroom || entity instanceof EntityZombie || entity instanceof EntitySkeleton)
							continue;
						
						if(eRad >= 1000) {
							if(entity.attackEntityFrom(ModDamageSource.radiation, 1000))
								eData.setRadForEntity(entity, 0);
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
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.hunger.id, 5 * 20, 3));
							
						} else if(eRad >= 600) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 30, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 2));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 10 * 20, 2));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.poison.id, 3 * 20, 1));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.hunger.id, 3 * 20, 3));
							
						} else if(eRad >= 400) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 30, 0));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 20, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 1));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.hunger.id, 3 * 20, 2));
				        	
						} else if(eRad >= 200) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 5 * 20, 0));
				        	if(event.world.rand.nextInt(700) == 0)
				            	entity.addPotionEffect(new PotionEffect(Potion.hunger.id, 3 * 20, 2));
						}
					}
				}
			}
		}
		/// RADIATION STUFF END ///
		
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
	
	String[] hashes = new String[] {
			"7e6fabead604ce497b32c2ff29148254879980c045b97fa627eb074eb2de81e7",
			"9031b60664473360593e89c7c9ece297ee3fbee25b0891450d904f15b3255bf6",
			"d841ef1ebaf7bdb284fc20be3508a8db1dae78f8630bf9d192bb7cd8384458da",
			"7c78f529c5f4748577bd8922753c04135cd733f525172b5a782894cfb9d544a8"

	};
	
	static final String salt = "df5fe65986dc0f145a867ad29aee9bfebc3a1cd5afb05f32";
	
	@SubscribeEvent
	public void onClickSign(PlayerInteractEvent event) {

		int x = event.x;
		int y = event.y;
		int z = event.z;
		World world = event.world;
		
		if(!world.isRemote && event.action == Action.RIGHT_CLICK_BLOCK && world.getBlock(x, y, z) == Blocks.standing_sign) {
			
			TileEntitySign sign = (TileEntitySign)world.getTileEntity(x, y, z);
			
			int correct = 0;
			
			for(int i = 0; i < 4; i++) {
				if(hashes[i].equals(getHash(sign.signText[i] + salt))) {
					sign.signText[i] = "Correct!";
					sign.markDirty();
					sign.getWorldObj().markBlockForUpdate(x, y, z);
					correct++;
				}
			}
			
			if(correct == 4) {
				world.func_147480_a(x, y, z, false);
	            EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(ModItems.bobmazon_hidden));
	            entityitem.delayBeforeCanPickup = 10;
	            world.spawnEntityInWorld(entityitem);
			}
		}
		
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
	
	/*@SubscribeEvent
	public void itemCollected(PlayerEvent.ItemPickupEvent e) {
		if(e.pickedUp.getEntityItem().equals(ModItems.nothing)) {
			//e.player.addStat(MainRegistry.achievementGetAmblygonite, 1);
		}
	}*/
}
