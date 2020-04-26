package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunSparkFactory {
	public static GunConfiguration getSparkConfig() {
		GunConfiguration config = new GunConfiguration();
		config.ammoCap = 3;
		config.rateOfFire = 20;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.durability = 50000;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.firingSound = "hbm:weapon.sparkShoot";
		config.reloadSound = GunConfiguration.RSOUND_LAUNCHER;
		config.reloadDuration = 60;
		config.crosshair = Crosshair.BOX;
		config.roundsPerCycle = 1;
		config.firingDuration = 12;
		
		config.name = "Spark Plug";
		config.manufacturer = "MagicTech Inc.";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPARK_PLUG);
		
		return config;
	}
	
	public static BulletConfiguration getElectroMagnetConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.gun_spark_ammo;
		bullet.blockDamage = true;
		bullet.bounceMod = 1.0D;
		bullet.dmgMin = 100;
		bullet.dmgMax = 1000;
		bullet.explosive = 50;
		bullet.shrapnel = 10;
		bullet.doesBreakGlass = true;
		bullet.doesPenetrate = false;
		bullet.doesRicochet = true;
		bullet.jolt = 10;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_SPARK;
		bullet.plink = BulletConfiguration.PLINK_ENERGY;
		
		return bullet;
	}
}
