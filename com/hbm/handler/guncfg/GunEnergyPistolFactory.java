package com.hbm.handler.guncfg;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudCustom;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;


public class GunEnergyPistolFactory {
	public static GunConfiguration getEnergyPistolConfig() {
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		// TODO: ADD ANIMATIONS
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 2;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.KRUCK;
		config.firingSound = "hbm:weapon.sparkShoot";
		
		config.name = "Generic Energy Pistol";
		config.manufacturer = "NXT Technologies";
		
		config.ammoless = true;
		config.unbreakable = true;
		
		config.ammoDisplayTag = "energypistol_charge";
		config.ammoMaxValue = "10";
		config.ammoName = "Charge";

		return config;
	}
	
	public static GunConfiguration getB92Config() {
		GunConfiguration config = getEnergyPistolConfig();
		
		config.name = "B92 Energy Pistol";
		config.manufacturer = "NXT Technologies";
		
		config.ammoType = BulletConfigSyncingUtil.ENERGYPISTOL_B92;
		
		return config;
	}
	
	public static GunConfiguration getB93Config() {
		GunConfiguration config = getEnergyPistolConfig();
		
		config.firingSound = "hbm:weapon.sparkShootDeep";
		config.name = "B93 Energy Mod";
		config.manufacturer = "NXT Technologies?";
		
		config.ammoType = BulletConfigSyncingUtil.ENERGYPISTOL_B93;
		config.ammoMaxValue = "13";
		
		return config;
	}
	
	public static BulletConfiguration getLaserConfig() {
		BulletConfiguration config = BulletConfigFactory.standardEnergyPistolConfig();
		
		//config.style = BulletConfiguration.STYLE_B92;
		config.style = BulletConfiguration.STYLE_BOLT;
		config.trail = BulletConfiguration.BOLT_B92;
		
		return config;
	}
	
	static Random rand = new Random();
	public static BulletConfiguration getModConfig() {
		BulletConfiguration config = BulletConfigFactory.standardEnergyPistolConfig();
		config.rainbow = 0;
		config.dmgMin = 1;
		config.dmgMax = 1;
		
		config.style = BulletConfiguration.STYLE_BOLT;
		config.trail = BulletConfiguration.BOLT_B93;
		
		config.bImpact = new IBulletImpactBehavior() {
			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				if (bullet.powerMultiplier == 1) {
					ExplosionLarge.explode(bullet.worldObj, x, y, z, 5, true, false, false);
				} else if (bullet.powerMultiplier == 2) {
					ExplosionLarge.explodeFire(bullet.worldObj, x, y, z, 10, true, false, false);
				} else if (bullet.powerMultiplier == 3) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
				
					EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(bullet.worldObj);
					entity.posX = x;
					entity.posY = y;
					entity.posZ = z;
					entity.destructionRange = 15;
					entity.speed = 25;
					entity.coefficient = 1.0F;
					entity.waste = false;
					
					bullet.worldObj.spawnEntityInWorld(entity);
					
					EntityCloudCustom cloud = new EntityCloudCustom(bullet.worldObj, 226, 77, 77, 15);
					cloud.posX = x;
					cloud.posY = y;
					cloud.posZ = z;
					bullet.worldObj.spawnEntityInWorld(cloud);
				} else if (bullet.powerMultiplier == 4) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
					
					EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(bullet.worldObj);
					entity.posX = x;
					entity.posY = y;
					entity.posZ = z;
					entity.destructionRange = 35;
					entity.speed = 25;
					entity.coefficient = 1.0F;
					entity.waste = false;
					
					bullet.worldObj.spawnEntityInWorld(entity);
					
					EntityCloudCustom cloud = new EntityCloudCustom(bullet.worldObj, 175, 59, 59, 35);
					cloud.posX = x;
					cloud.posY = y;
					cloud.posZ = z;
					bullet.worldObj.spawnEntityInWorld(cloud);
				} else if (bullet.powerMultiplier == 5) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		    		
		    		EntityVortex vortex = new EntityVortex(bullet.worldObj, 1F);
		    		vortex.posX = x;
		    		vortex.posY = y;
		    		vortex.posZ = z;
		    		bullet.worldObj.spawnEntityInWorld(vortex);
				} else if (bullet.powerMultiplier == 6) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		    		
		    		EntityVortex vortex = new EntityVortex(bullet.worldObj, 2.5F);
		    		vortex.posX = x;
		    		vortex.posY = y;
		    		vortex.posZ = z;
		    		bullet.worldObj.spawnEntityInWorld(vortex);
				} else if (bullet.powerMultiplier == 7) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		    		
		    		EntityRagingVortex vortex = new EntityRagingVortex(bullet.worldObj, 2.5F);
		    		vortex.posX = x;
		    		vortex.posY = y;
		    		vortex.posZ = z;
		    		bullet.worldObj.spawnEntityInWorld(vortex);
				} else if (bullet.powerMultiplier == 8) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		    		
		    		EntityRagingVortex vortex = new EntityRagingVortex(bullet.worldObj, 5F);
		    		vortex.posX = x;
		    		vortex.posY = y;
		    		vortex.posZ = z;
		    		bullet.worldObj.spawnEntityInWorld(vortex);
				} else if (bullet.powerMultiplier == 9) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		    		
		    		EntityBlackHole vortex = new EntityBlackHole(bullet.worldObj, 7.5F);
		    		vortex.posX = x;
		    		vortex.posY = y;
		    		vortex.posZ = z;
		    		bullet.worldObj.spawnEntityInWorld(vortex);
				} else if (bullet.powerMultiplier == 10) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		    		
		    		bullet.worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(bullet.worldObj, MainRegistry.gadgetRadius, x, y, z));
		    		bullet.worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(bullet.worldObj, x, y, z, MainRegistry.gadgetRadius));
				} else if (bullet.powerMultiplier == 11) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
					EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(bullet.worldObj);
					exp.posX = x;
					exp.posY = y;
					exp.posZ = z;
					if (bullet.worldObj.getBlock(x + 1, y, z) == Blocks.air) {
						exp.posX++;
					} else if (bullet.worldObj.getBlock(x - 1, y, z) == Blocks.air) {
						exp.posX--;
					} else if (bullet.worldObj.getBlock(x, y + 1, z) == Blocks.air) {
						exp.posY++;
					} else if (bullet.worldObj.getBlock(x, y - 1, z) == Blocks.air) {
						exp.posY--;
					} else if (bullet.worldObj.getBlock(x, y, z + 1) == Blocks.air) {
						exp.posZ++;
					} else if (bullet.worldObj.getBlock(x, y, z - 1) == Blocks.air) {
						exp.posZ--;
					}
					exp.destructionRange = 100;
					exp.speed = 50;
					exp.coefficient = 1.0F;
					exp.waste = false;
					exp.destructive = false;
					exp.affectedByWalls = false;
					exp.effects = new ArrayList<PotionEffect>();
					exp.effects.add(new PotionEffect(HbmPotion.bang.id, 200, 0));
										
					bullet.worldObj.spawnEntityInWorld(exp);
					EntityCloudCustom cloud = new EntityCloudCustom(bullet.worldObj, 128, 0, 0, 100);
					cloud.posX = x;
					cloud.posY = y;
					cloud.posZ = z;
					bullet.worldObj.spawnEntityInWorld(cloud);
				} else if (bullet.powerMultiplier == 12) {
					double d1 = 0;
					double d2 = 0;
					double d3 = 0;
					
					for (int i = 0; i < 20; i++) {
						d1 = rand.nextDouble();
						d2 = rand.nextDouble();
						d3 = rand.nextDouble();
						
						if (rand.nextInt(2) == 0) {
							d1 *= -1;
						}
						
						if (rand.nextInt(2) == 0) {
							d2 *= -1;
						}
						
						if (rand.nextInt(2) == 0) {
							d3 *= -1;
						}
						
						EntityRainbow beam = new EntityRainbow(bullet.worldObj, x, y, z, d1, d2, d3, 0);
						bullet.worldObj.spawnEntityInWorld(beam);
						bullet.worldObj.playSoundAtEntity(bullet, "hbm:weapon.zomgShoot", 1.0F, 1.0F);
					}
				} else if (bullet.powerMultiplier == 13) {
					bullet.worldObj.playSoundEffect(x, y, z, "random.explode", 100.0f, bullet.worldObj.rand.nextFloat() * 0.1F + 0.9F);
					
					EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(bullet.worldObj);
					exp.posX = x;
					exp.posY = y;
					exp.posZ = z;
					exp.destructionRange = 200;
					exp.speed = 25;
					exp.coefficient = 1.0F;
					exp.waste = true;
					exp.effects = new ArrayList<PotionEffect>();
					exp.effects.add(new PotionEffect(HbmPotion.bang.id, 1, 0));
					
					bullet.worldObj.spawnEntityInWorld(exp);
					EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(bullet.worldObj, 200);
					cloud.posX = x;
					cloud.posY = y;
					cloud.posZ = z;
					bullet.worldObj.spawnEntityInWorld(cloud);
				}
			}
		};
		
		config.alwaysApplyEffects = true;
		config.effects = new ArrayList<PotionEffect>();
		config.effects.add(new PotionEffect(HbmPotion.bang.id, 60, 0));
		
		return config;
	}
}
