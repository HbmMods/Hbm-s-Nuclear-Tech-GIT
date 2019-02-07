package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

public class Gun12GaugeFactory {
	
	public static GunConfiguration getUboinikConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 8;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.shotgunShoot";
		
		config.name = "Uboinik Revolving Shotgun";
		config.manufacturer = "Metro Gunsmiths";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		
		return config;
	}
	
	public static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_incendiary;
		bullet.wear = 15;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.incendiary = 5;
		
		return bullet;
	}

}
