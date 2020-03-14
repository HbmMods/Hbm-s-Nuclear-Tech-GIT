package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunFatmanFactory {
	
	public static GunConfiguration getFatmanConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 120;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.fatmanShoot";
		config.reloadSound = GunConfiguration.RSOUND_FATMAN;
		config.reloadSoundEnd = false;
		
		config.name = "M-42 Tactical Nuclear Catapult";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_NORMAL);
		config.durability = 1000;
		
		return config;
	}
	
	public static GunConfiguration getBELConfig() {
		
		GunConfiguration config = getFatmanConfig();
		
		config.name = "Balefire Egg Launcher";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_AMAT);
		
		return config;
	}
	
	public static GunConfiguration getProtoConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 8;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 120;
		config.firingDuration = 0;
		config.ammoCap = 8;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.fatmanShoot";
		config.reloadSound = GunConfiguration.RSOUND_FATMAN;
		config.reloadSoundEnd = false;
		
		config.name = "M-42 Tactical Nuclear Catapult";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO);
		config.durability = 1000;
		
		return config;
	}
	
	public static BulletConfiguration getNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();

		bullet.ammo = ModItems.gun_fatman_ammo;
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeProtoConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();

		bullet.spread = 0.1F;
		bullet.ammo = ModItems.gun_fatman_ammo;
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		
		bullet.ammo = ModItems.gun_mirv_ammo;
		
		return bullet;
	}
	
	public static BulletConfiguration getBalefireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		
		bullet.ammo = ModItems.gun_bf_ammo;
		bullet.nuke = 0;
		bullet.style = BulletConfiguration.STYLE_BF;
		
		bullet.bImpact = new IBulletImpactBehavior() {
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {
					EntityBalefire bf = new EntityBalefire(bullet.worldObj);
					bf.posX = x;
					bf.posY = y;
					bf.posZ = z;
					bf.destructionRange = (int) (MainRegistry.fatmanRadius * 1.25);
					bullet.worldObj.spawnEntityInWorld(bf);
		    		ExplosionParticleB.spawnMush(bullet.worldObj, x, y, z);
				}
			}
		};
		
		return bullet;
	}

}
