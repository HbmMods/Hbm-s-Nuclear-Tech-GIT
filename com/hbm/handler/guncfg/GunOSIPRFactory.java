package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunOSIPRFactory {
	
	public static GunConfiguration getOSIPRConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		config.durability = 10000;
		config.reloadSound = "hbm:weapon.osiprReload";
		config.firingSound = "hbm:weapon.osiprShoot";
		config.reloadSoundEnd = false;
		
		config.name = "Overwatch Standard Issue Pulse Rifle";
		config.manufacturer = "The Universal Union";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR);
		
		return config;
	}
	
	public static GunConfiguration getAltConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 15;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.firingSound = "hbm:weapon.singFlyby";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR_CHARGED);
		
		return config;
	}

	static float inaccuracy = 5;
	public static BulletConfiguration getPulseConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_osipr_ammo;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		bullet.trail = 2;
		
		return bullet;
	}
	public static BulletConfiguration getPulseChargedConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_osipr_ammo2;
		
		return bullet;
	}
}
