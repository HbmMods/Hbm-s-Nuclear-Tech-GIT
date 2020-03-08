package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun44MagnumFactory {
	
	public static GunConfiguration getBaseConfig() {
		
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
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getMacintoshConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		
		config.name = "IF-18 Horseshoe Scoped";
		config.manufacturer = "Ironshod Firearms";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_PIP);
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getBlackjackConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 5;
		
		config.name = "IF-18 Horseshoe Vanity";
		config.manufacturer = "Ironshod Firearms";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_BJ);
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getRedConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.ammoCap = 64;
		
		config.name = "IF-18 Horseshoe Bottomless Pit";
		config.manufacturer = "Ironshod Firearms R&D";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.M44_NORMAL);
		config.config.add(BulletConfigSyncingUtil.M44_AP);
		config.config.add(BulletConfigSyncingUtil.M44_DU);
		config.config.add(BulletConfigSyncingUtil.M44_PIP);
		config.config.add(BulletConfigSyncingUtil.M44_BJ);
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
	
	public static BulletConfiguration getPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_pip;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.boxcar = true;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
		return bullet;
	}
	
	public static BulletConfiguration getBJConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_bj;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.boat = true;
		bullet.wear = 25;
		bullet.doesPenetrate = false;
		
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
