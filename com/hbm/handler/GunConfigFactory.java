package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.items.ModItems;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

public class GunConfigFactory {
	
	public static GunConfiguration getRevolverConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.durability = 350;
		
		config.name = "FFI Viper";
		config.manufacturer = "FlimFlam Industries";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getMareConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
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
	
	public static GunConfiguration getGustavConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 60;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		
		config.name = "Carl Gustav Recoilless Rifle M1";
		config.manufacturer = "Saab Bofors Dynamics";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_NORMAL);
		config.config.add(BulletConfigSyncingUtil.ROCKET_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.ROCKET_EMP);
		config.config.add(BulletConfigSyncingUtil.ROCKET_GLARE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		config.durability = 140;
		
		return config;
	}
	
	public static GunConfiguration getGrenadeConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 40;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		
		config.name = "Granatpistole HK69";
		config.manufacturer = "Heckler & Koch";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.GRENADE_NORMAL);
		config.config.add(BulletConfigSyncingUtil.GRENADE_HE);
		config.config.add(BulletConfigSyncingUtil.GRENADE_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.GRENADE_CHEMICAL);
		config.config.add(BulletConfigSyncingUtil.GRENADE_SLEEK);
		config.durability = 140;
		
		return config;
	}

}
