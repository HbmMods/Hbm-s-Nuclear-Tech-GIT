package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun9mmFactory {
	
	public static GunConfiguration getMP40Config() {
		
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
		config.ammoCap = 32;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.rifleShoot";
		config.reloadSoundEnd = false;
		
		config.name = "Maschinenpistole 40";
		config.manufacturer = "Erfurter Maschinenfabrik Geipel";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);
		
		return config;
	}

	static float inaccuracy = 5;
	public static BulletConfiguration get9mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_9mm;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_9mm_ap;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_9mm_du;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.leadChance = 50;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_9mm_rocket;
		bullet.velocity = 5;
		bullet.explosive = 7.5F;
		bullet.trail = 5;
		
		return bullet;
	}

}
