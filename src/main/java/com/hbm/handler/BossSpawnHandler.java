package com.hbm.handler;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPedestal;
import com.hbm.blocks.generic.BlockPedestal.PedestalEntry;
import com.hbm.blocks.generic.BlockPedestal.PedestalEntryType;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.WorldConfig;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.entity.mob.EntityFBIDrone;
import com.hbm.entity.mob.EntityGhost;
import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BossSpawnHandler {

	//because some dimwit keeps resetting the world rand
	private static final Random meteorRand = new Random();

	public static void rollTheDice(World world) {

		/*
		 * Spawns every 20 minutes if
		 * - the player is 3 blocks below the surface
		 * - the player has at least 50 RAD
		 * - the player has either crafted or placed an ore acidizer before
		 */
		if(MobConfig.enableMaskman && world.getTotalWorldTime() % 20 == 0 && world.provider.isSurfaceWorld() && world.difficultySetting != EnumDifficulty.PEACEFUL) {
			
			for(Object o : world.playerEntities) {
				if(!(o instanceof EntityPlayerMP)) return;
				EntityPlayerMP player = (EntityPlayerMP) o;
				
				int id = Item.getIdFromItem(Item.getItemFromBlock(ModBlocks.machine_crystallizer));
				StatBase statCraft = StatList.objectCraftStats[id];
				StatBase statPlace = StatList.objectUseStats[id];
				
				boolean acidizerStat = !GeneralConfig.enableStatReRegistering || (statCraft != null && player.func_147099_x().writeStat(statCraft) > 0)|| (statPlace != null && player.func_147099_x().writeStat(statPlace) > 0);
				boolean hasRads = ContaminationUtil.getRads(player) >= MobConfig.maskmanMinRad;
				boolean underground = world.getHeightValue((int) Math.floor(player.posX), (int) Math.floor(player.posZ)) > player.posY + 3 || !MobConfig.maskmanUnderground;
				
				if(acidizerStat && hasRads && underground) {
					HbmPlayerProps data = HbmPlayerProps.getData(player);
					
					data.maskManTimer++;
					
					if(data.maskManTimer == MobConfig.maskmanDelay - 60) {
						player.addChatComponentMessage(new ChatComponentText("The mask man draws near.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
					}
					
					if(data.maskManTimer >= MobConfig.maskmanDelay) {
						data.maskManTimer = 0;
						
						double spawnX = player.posX + world.rand.nextGaussian() * 20;
						double spawnZ = player.posZ + world.rand.nextGaussian() * 20;
						double spawnY = world.getHeightValue((int) Math.floor(spawnX), (int) Math.floor(spawnZ));
						if(trySpawn(world, (float) spawnX, (float) spawnY, (float) spawnZ, new EntityMaskMan(world))) {
							player.addChatComponentMessage(new ChatComponentText("The mask man is about to claim another victim.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
						} else {
							player.addChatComponentMessage(new ChatComponentText("Seems like mask man couldn't come today.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BLUE)));
						}
					}
					
				} else {
					HbmPlayerProps.getData(player).maskManTimer = 0;
				}
			}
		}

		if(MobConfig.enableRaids) {

			if(world.getTotalWorldTime() % MobConfig.raidDelay == 0) {

				if(world.rand.nextInt(MobConfig.raidChance) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {

					EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));

					if(player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).getLong("fbiMark") < world.getTotalWorldTime()) {
						player.addChatComponentMessage(new ChatComponentText("FBI, OPEN UP!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));

						Vec3 vec = Vec3.createVectorHelper(MobConfig.raidAttackDistance, 0, 0);
						vec.rotateAroundY((float)(Math.PI * 2) * world.rand.nextFloat());

						for(int i = 0; i < MobConfig.raidAmount; i++) {

							double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian() * 5;
							double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian() * 5;
							double spawnY = world.getHeightValue((int)spawnX, (int)spawnZ);

							trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, new EntityFBI(world));
						}

						for(int i = 0; i < MobConfig.raidDrones; i++) {

							double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian() * 5;
							double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian() * 5;
							double spawnY = world.getHeightValue((int)spawnX, (int)spawnZ);

							trySpawn(world, (float)spawnX, (float)spawnY + 10, (float)spawnZ, new EntityFBIDrone(world));
						}
					}
				}
			}
		}

		if(MobConfig.enableElementals) {

			if(world.getTotalWorldTime() % MobConfig.elementalDelay == 0) {

				if(world.rand.nextInt(MobConfig.elementalChance) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {

					EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));

					if(player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).getBoolean("radMark")) {

						player.addChatComponentMessage(new ChatComponentText("You hear a faint clicking...").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
						player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).setBoolean("radMark", false);

						Vec3 vec = Vec3.createVectorHelper(MobConfig.raidAttackDistance, 0, 0);

						for(int i = 0; i < MobConfig.elementalAmount; i++) {

							vec.rotateAroundY((float)(Math.PI * 2) * world.rand.nextFloat());

							double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian();
							double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian();
							double spawnY = world.getHeightValue((int)spawnX, (int)spawnZ);

							EntityRADBeast rad = new EntityRADBeast(world);

							if(i == 0)
								rad.makeLeader();

							trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, rad);
						}
					}
				}
			}
		}

		if(WorldConfig.enableMeteorStrikes && !world.isRemote) {
			meteorUpdate(world);
		}

		if(world.getTotalWorldTime() % 20 == 0) {

			if(world.rand.nextInt(5) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {

				EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));

				if(HbmLivingProps.getDigamma(player) > 0) {
					Vec3 vec = Vec3.createVectorHelper(75, 0, 0);
					vec.rotateAroundY((float)(Math.PI * 2) * world.rand.nextFloat());
					double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian();
					double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian();
					double spawnY = world.getHeightValue((int)spawnX, (int)spawnZ);
					trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, new EntityGhost(world));
				}
			}
		}
	}

	private static boolean trySpawn(World world, float x, float y, float z, EntityLiving e) {
		e.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
		Result canSpawn = ForgeEventFactory.canEntitySpawn(e, world, x, y, z);

		if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT) {

			world.spawnEntityInWorld(e);
			ForgeEventFactory.doSpecialSpawn(e, world, x, y, z);
			e.onSpawnWithEgg(null);
			return true;
		}
		
		return false;
	}

	public static void markFBI(EntityPlayer player) {

		if(!player.worldObj.isRemote)
			player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).setLong("fbiMark", player.worldObj.getTotalWorldTime() + 20 * 60 * 20);
	}

	public static int meteorShower = 0;
	private static void meteorUpdate(World world) {

		if(meteorRand.nextInt(meteorShower > 0 ? WorldConfig.meteorShowerChance : WorldConfig.meteorStrikeChance) == 0) {

			if(!world.playerEntities.isEmpty()) {

				EntityPlayer p = (EntityPlayer)world.playerEntities.get(meteorRand.nextInt(world.playerEntities.size()));

				if(p != null && p.dimension == 0) {

					boolean repell = false;
					boolean strike = true;

					for(int i = 0; i < 4; i++) {
						ItemStack armor = p.getCurrentArmor(i);
						if(armor != null && ArmorModHandler.hasMods(armor)) {

							for(int j = 0; j < 8; j++) {
								ItemStack mod = ArmorModHandler.pryMods(armor)[j];

								if(mod != null) {
									if(mod.getItem() == ModItems.protection_charm) repell = true;
									if(mod.getItem() == ModItems.meteor_charm) strike = false;
								}
							}
						}
					}
					
					// only check if either charm is not present
					if(!repell || strike) {
						int x = (int) Math.floor(p.posX);
						int z = (int) Math.floor(p.posZ);
						
						List<PedestalEntry> entries = BlockPedestal.getEntriesForDimension(world.provider.dimensionId);
						if(entries != null) for(PedestalEntry entry : entries) {
							if(Math.abs(entry.pos.getX() - x) <= 100 && Math.abs(entry.pos.getZ() - z) <= 100) {
								if(entry.type == PedestalEntryType.CHARM_OF_PROTECTION) repell = true;
								if(entry.type == PedestalEntryType.METEORITE_CHARM) strike = false;
							}
						}
					}

					if(strike) spawnMeteorAtPlayer(p, repell);
				}
			}
		}

		if(meteorShower > 0) {
			meteorShower--;
			if(meteorShower == 0 && GeneralConfig.enableDebugMode)
				MainRegistry.logger.info("Ended meteor shower.");
		}

		if(meteorRand.nextInt(WorldConfig.meteorStrikeChance * 100) == 0 && WorldConfig.enableMeteorShowers) {
			meteorShower = (int)(WorldConfig.meteorShowerDuration * 0.75 + WorldConfig.meteorShowerDuration * 0.25 * meteorRand.nextFloat());

			if(GeneralConfig.enableDebugMode)
				MainRegistry.logger.info("Started meteor shower! Duration: " + meteorShower);
		}
	}

	public static void spawnMeteorAtPlayer(EntityPlayer player, boolean repell) {

		EntityMeteor meteor = new EntityMeteor(player.worldObj);
		meteor.setPositionAndRotation(player.posX + meteorRand.nextInt(201) - 100, 384, player.posZ + meteorRand.nextInt(201) - 100, 0, 0);

		Vec3 vec;
		if(repell) {
			vec = Vec3.createVectorHelper(meteor.posX - player.posX, 0, meteor.posZ - player.posZ).normalize();
			double vel = meteorRand.nextDouble();
			vec.xCoord = vec.xCoord * vel;
			vec.zCoord = vec.zCoord * vel;
			meteor.safe = true;
		} else {
			vec = Vec3.createVectorHelper(meteorRand.nextDouble() - 0.5D, 0, 0);
			vec.rotateAroundY((float) (Math.PI * meteorRand.nextDouble()));
		}

		meteor.motionX = vec.xCoord;
		meteor.motionY = -2.5;
		meteor.motionZ = vec.zCoord;
		player.worldObj.spawnEntityInWorld(meteor);
	}
}
