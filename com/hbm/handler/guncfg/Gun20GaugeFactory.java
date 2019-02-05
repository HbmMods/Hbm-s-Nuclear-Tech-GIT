package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

public class Gun20GaugeFactory {
	
	public static GunConfiguration getMareConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
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
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.durability = 350;
		
		config.name = "Winchester Model 1887";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		
		return config;
	}
	
	public static BulletConfiguration get20GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 3;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_slug;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_flechette;
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_incendiary;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_explosive;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.wear = 25;
		bullet.explosive = 0.5F;
		
		return bullet;
	}

}
