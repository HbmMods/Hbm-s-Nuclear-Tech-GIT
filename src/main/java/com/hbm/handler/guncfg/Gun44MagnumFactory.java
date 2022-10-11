package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

public class Gun44MagnumFactory {
	
	public static GunConfiguration getBaseConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.reloadSoundEnd = false;
		
		config.config.addAll(HbmCollection.fourtyFourMagBasic);
		
		return config;
	}
	
	public static GunConfiguration getNovacConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "ifHorseshoe";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Fallout New Vegas wasn't THAT good.");
//		
//		config.config = new ArrayList<Integer>();
//		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
//		config.config.add(BulletConfigSyncingUtil.M44_AP);
//		config.config.add(BulletConfigSyncingUtil.M44_DU);
//		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
//		config.config.add(BulletConfigSyncingUtil.M44_STAR);
//		config.config.add(BulletConfigSyncingUtil.CHL_M44);
//		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.fourtyFourMagBasic);
		
		return config;
	}
	
	public static GunConfiguration getMacintoshConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		
		config.name = "ifScope";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Poppin' mentats like tic tacs");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_PIP);
		config.config.addAll(HbmCollection.fourtyFourMagBasic);
		
		return config;
	}
	
	public static GunConfiguration getBlackjackConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 5;
		
		config.name = "ifVanity";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Alcoholism is cool!");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_BJ);
		config.config.addAll(HbmCollection.fourtyFourMagBasic);
		
		return config;
	}
	
	public static GunConfiguration getSilverConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 6;
		
		config.name = "ifStorm";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Our friendship is based on abusive behaviour");
		config.comment.add("and mutual hate. It's not that complicated.");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_SILVER);
		config.config.addAll(HbmCollection.fourtyFourMagBasic);
		
		return config;
	}
	
	public static GunConfiguration getRedConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 64;
		
		config.name = "ifPit";
		config.manufacturer = EnumGunManufacturer.IF;
		config.comment.add("Explore the other side");
		config.comment.add("...from afar!");
		
		config.config.clear();
		config.config.addAll(HbmCollection.fourtyFourMagAll);
//		config.config = new ArrayList<Integer>();
//		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
//		config.config.add(BulletConfigSyncingUtil.M44_AP);
//		config.config.add(BulletConfigSyncingUtil.M44_DU);
//		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
//		config.config.add(BulletConfigSyncingUtil.M44_STAR);
//		config.config.add(BulletConfigSyncingUtil.CHL_M44);
//		config.config.add(BulletConfigSyncingUtil.M44_PIP);
//		config.config.add(BulletConfigSyncingUtil.M44_BJ);
//		config.config.add(BulletConfigSyncingUtil.M44_SILVER);
//		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	static byte i = 0;
	public static BulletConfiguration getNoPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 18;
		bullet.dmgMax = 26;
		bullet.penetration = 20;
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 25;
		bullet.dmgMax = 32;
		bullet.penetration = 30;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 28;
		bullet.dmgMax = 40;
		bullet.penetration = 45;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration getPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 18;
		bullet.dmgMax = 26;
		bullet.wear = 15;
		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bImpact = (projectile, x, y, z) ->
		{
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "flame");
				data.setInteger("count", 15);
				data.setDouble("motion", 0.05D);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, projectile.posX, projectile.posY, projectile.posZ), new TargetPoint(projectile.dimension, projectile.posX, projectile.posY, projectile.posZ, 50));
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 42;
		bullet.dmgMax = 50;
		bullet.penetration = 50;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		return bullet;
	}
	
	public static BulletConfiguration getPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bHit = (projectile, hit) ->
			{
				
				if(!projectile.worldObj.isRemote) {
					EntityBoxcar pippo = new EntityBoxcar(projectile.worldObj);
					pippo.posX = hit.posX;
					pippo.posY = hit.posY + 50;
					pippo.posZ = hit.posZ;
					
					for(int j = 0; j < 50; j++) {
						EntityBSmokeFX fx = new EntityBSmokeFX(projectile.worldObj, pippo.posX + (projectile.worldObj.rand.nextDouble() - 0.5) * 4, pippo.posY + (projectile.worldObj.rand.nextDouble() - 0.5) * 12, pippo.posZ + (projectile.worldObj.rand.nextDouble() - 0.5) * 4, 0, 0, 0);
						projectile.worldObj.spawnEntityInWorld(fx);
					}
					projectile.worldObj.spawnEntityInWorld(pippo);
					
					projectile.worldObj.playSoundEffect(pippo.posX, 
							pippo.posY + 50, 
							pippo.posZ, "hbm:alarm.trainHorn", 100F, 1F);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getBJConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bHit = (projectile, hit) -> {

				if(!projectile.worldObj.isRemote) {
					EntityDuchessGambit pippo = new EntityDuchessGambit(projectile.worldObj);
					pippo.posX = hit.posX;
					pippo.posY = hit.posY + 50;
					pippo.posZ = hit.posZ;
					
					for(int j = 0; j < 150; j++) {
						EntityBSmokeFX fx = new EntityBSmokeFX(projectile.worldObj, pippo.posX + (projectile.worldObj.rand.nextDouble() - 0.5) * 7, pippo.posY + (projectile.worldObj.rand.nextDouble() - 0.5) * 8, pippo.posZ + (projectile.worldObj.rand.nextDouble() - 0.5) * 18, 0, 0, 0);
						projectile.worldObj.spawnEntityInWorld(fx);
					}
					projectile.worldObj.spawnEntityInWorld(pippo);
					
					projectile.worldObj.playSoundEffect(pippo.posX, 
							pippo.posY + 50, 
							pippo.posZ, "hbm:weapon.boat", 100F, 1F);
				}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getSilverStormConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bHit = (projectile, hit) -> {

				if(!projectile.worldObj.isRemote) {
					EntityBuilding pippo = new EntityBuilding(projectile.worldObj);
					pippo.posX = hit.posX;
					pippo.posY = hit.posY + 50;
					pippo.posZ = hit.posZ;
					
					for(int j = 0; j < 150; j++) {
						EntityBSmokeFX fx = new EntityBSmokeFX(projectile.worldObj, pippo.posX + (projectile.worldObj.rand.nextDouble() - 0.5) * 15, pippo.posY + (projectile.worldObj.rand.nextDouble() - 0.5) * 15, pippo.posZ + (projectile.worldObj.rand.nextDouble() - 0.5) * 15, 0, 0, 0);
						projectile.worldObj.spawnEntityInWorld(fx);
					}
					projectile.worldObj.spawnEntityInWorld(pippo);
					
					projectile.worldObj.playSoundEffect(pippo.posX, 
							pippo.posY + 50, 
							pippo.posZ, "hbm:block.debris", 100F, 1F);
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_44, 1, i++);
		bullet.velocity = 5;
		bullet.explosive = 15F;
		bullet.trail = 1;
		
		return bullet;
	}

}