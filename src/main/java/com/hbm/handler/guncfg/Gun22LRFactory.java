package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun22LRFactory {
	
	public static GunConfiguration getUziConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 32;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CROSS;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.uziShoot";
		config.reloadSoundEnd = false;
		
		config.name = "IMI Uzi";
		config.manufacturer = "Israel Military Industries";
		config.comment.add("Mom, where are my mittens?");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.LR22_NORMAL);
		config.config.add(BulletConfigSyncingUtil.LR22_AP);
		config.config.add(BulletConfigSyncingUtil.CHL_LR22);
		
		return config;
	}
	
	public static GunConfiguration getSaturniteConfig() {
		
		GunConfiguration config = getUziConfig();
		
		config.durability = 4500;
		
		config.name = "IMI Uzi D-25A";
		config.manufacturer = "IMI / Big MT";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.LR22_NORMAL_FIRE);
		config.config.add(BulletConfigSyncingUtil.LR22_AP_FIRE);
		config.config.add(BulletConfigSyncingUtil.CHL_LR22_FIRE);
		
		return config;
	}

	static float inaccuracy = 5;
	public static BulletConfiguration get22LRConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = ModItems.ammo_22lr;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		
		return bullet;
	}
	
	public static BulletConfiguration get22LRAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = ModItems.ammo_22lr_ap;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 12;
		bullet.dmgMax = 16;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		return bullet;
	}

}
