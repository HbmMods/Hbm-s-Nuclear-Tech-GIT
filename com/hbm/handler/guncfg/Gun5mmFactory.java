package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun5mmFactory {
	
	public static GunConfiguration getMinigunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 5;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.durability = 5000;
		config.firingSound = "hbm:weapon.lacunaeShoot";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.R5_NORMAL);
		config.config.add(BulletConfigSyncingUtil.R5_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.R5_DU);
		
		return config;
	}
	
	public static GunConfiguration get53Config() {
		
		GunConfiguration config = getMinigunConfig();
		
		config.name = "CZ53 Personal Minigun";
		config.manufacturer = "Rockwell International Corporation";
		
		return config;
	}
	
	public static GunConfiguration get57Config() {
		
		GunConfiguration config = getMinigunConfig();

		config.durability = 6500;
		config.name = "CZ57 Avenger Minigun";
		config.manufacturer = "Rockwell International Corporation";
		
		return config;
	}
	
	public static GunConfiguration getLacunaeConfig() {
		
		GunConfiguration config = getMinigunConfig();

		config.durability = 10000;
		config.name = "Auntie Lacunae";
		config.manufacturer = "Rockwell International Corporation?";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.R5_NORMAL_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_DU_BOLT);
		
		return config;
	}
	
	static float inaccuracy = 10;
	public static BulletConfiguration get5mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_5mm;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_5mm_explosive;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 4;
		bullet.dmgMax = 7;
		bullet.explosive = 1F;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_5mm_du;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 10;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}

}
