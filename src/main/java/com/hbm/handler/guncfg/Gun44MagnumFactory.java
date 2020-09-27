package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
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
		
		return config;
	}
	
	public static GunConfiguration getNovacConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "IF-18 Horseshoe";
		config.manufacturer = "Ironshod Firearms";
		config.comment.add("Fallout New Vegas wasn't THAT good.");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.M44_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_M44);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getMacintoshConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		
		config.name = "IF-18 Horseshoe Scoped";
		config.manufacturer = "Ironshod Firearms";
		config.comment.add("Poppin' mentats like tic tacs");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_PIP);
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.M44_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_M44);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getBlackjackConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 5;
		
		config.name = "IF-18 Horseshoe Vanity";
		config.manufacturer = "Ironshod Firearms";
		config.comment.add("Alcoholism is cool!");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_BJ);
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.M44_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_M44);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getSilverConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 6;
		
		config.name = "IF-18 Horseshoe Silver Storm";
		config.manufacturer = "Ironshod Firearms";
		config.comment.add("Our friendship is based on abusive behaviour");
		config.comment.add("and mutual hate. It's not that complicated.");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_SILVER);
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.M44_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_M44);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getRedConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 64;
		
		config.name = "IF-18 Horseshoe Bottomless Pit";
		config.manufacturer = "Ironshod Firearms R&D";
		config.comment.add("Explore the other side");
		config.comment.add("...from afar!");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.M44_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_M44);
		config.config.add(BulletConfigSyncingUtil.M44_PIP);
		config.config.add(BulletConfigSyncingUtil.M44_BJ);
		config.config.add(BulletConfigSyncingUtil.M44_SILVER);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static BulletConfiguration getNoPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44;
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_ap;
		bullet.dmgMin = 7;
		bullet.dmgMax = 10;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_du;
		bullet.dmgMin = 7;
		bullet.dmgMax = 10;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration getPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_phosphorus;
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		bullet.wear = 15;
		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "flame");
				data.setInteger("count", 15);
				data.setDouble("motion", 0.05D);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_star;
		bullet.dmgMin = 14;
		bullet.dmgMax = 20;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		return bullet;
	}
	
	public static BulletConfiguration getPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_pip;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bHit = new IBulletHitBehavior() {

			@Override
			public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
				
				if(!bullet.worldObj.isRemote) {
					EntityBoxcar pippo = new EntityBoxcar(bullet.worldObj);
					pippo.posX = hit.posX;
					pippo.posY = hit.posY + 50;
					pippo.posZ = hit.posZ;
					
					for(int j = 0; j < 50; j++) {
						EntityBSmokeFX fx = new EntityBSmokeFX(bullet.worldObj, pippo.posX + (bullet.worldObj.rand.nextDouble() - 0.5) * 4, pippo.posY + (bullet.worldObj.rand.nextDouble() - 0.5) * 12, pippo.posZ + (bullet.worldObj.rand.nextDouble() - 0.5) * 4, 0, 0, 0);
						bullet.worldObj.spawnEntityInWorld(fx);
					}
					bullet.worldObj.spawnEntityInWorld(pippo);
					
					bullet.worldObj.playSoundEffect(pippo.posX, 
							pippo.posY + 50, 
							pippo.posZ, "hbm:alarm.trainHorn", 100F, 1F);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getBJConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_bj;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bHit = new IBulletHitBehavior() {

			@Override
			public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
				
				if(!bullet.worldObj.isRemote) {
					EntityDuchessGambit pippo = new EntityDuchessGambit(bullet.worldObj);
					pippo.posX = hit.posX;
					pippo.posY = hit.posY + 50;
					pippo.posZ = hit.posZ;
					
					for(int j = 0; j < 150; j++) {
						EntityBSmokeFX fx = new EntityBSmokeFX(bullet.worldObj, pippo.posX + (bullet.worldObj.rand.nextDouble() - 0.5) * 7, pippo.posY + (bullet.worldObj.rand.nextDouble() - 0.5) * 8, pippo.posZ + (bullet.worldObj.rand.nextDouble() - 0.5) * 18, 0, 0, 0);
						bullet.worldObj.spawnEntityInWorld(fx);
					}
					bullet.worldObj.spawnEntityInWorld(pippo);
					
					bullet.worldObj.playSoundEffect(pippo.posX, 
							pippo.posY + 50, 
							pippo.posZ, "hbm:weapon.boat", 100F, 1F);
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getSilverStormConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_silver;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		bullet.bHit = new IBulletHitBehavior() {

			@Override
			public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
				
				if(!bullet.worldObj.isRemote) {
					EntityBuilding pippo = new EntityBuilding(bullet.worldObj);
					pippo.posX = hit.posX;
					pippo.posY = hit.posY + 50;
					pippo.posZ = hit.posZ;
					
					for(int j = 0; j < 150; j++) {
						EntityBSmokeFX fx = new EntityBSmokeFX(bullet.worldObj, pippo.posX + (bullet.worldObj.rand.nextDouble() - 0.5) * 15, pippo.posY + (bullet.worldObj.rand.nextDouble() - 0.5) * 15, pippo.posZ + (bullet.worldObj.rand.nextDouble() - 0.5) * 15, 0, 0, 0);
						bullet.worldObj.spawnEntityInWorld(fx);
					}
					bullet.worldObj.spawnEntityInWorld(pippo);
					
					bullet.worldObj.playSoundEffect(pippo.posX, 
							pippo.posY + 50, 
							pippo.posZ, "hbm:block.debris", 100F, 1F);
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_44_rocket;
		bullet.velocity = 5;
		bullet.explosive = 15F;
		bullet.trail = 1;
		
		return bullet;
	}

}
