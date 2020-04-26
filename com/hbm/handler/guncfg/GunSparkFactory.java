package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
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
	
	public static GunConfiguration getDefabricatorConfig() {
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 3;
		config.roundsPerCycle = 1;
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
		config.crosshair = Crosshair.DUAL;
		config.durability = 75000;
		config.firingSound = "hbm:weapon.defabShoot";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.DEFABRICATOR);
		
		return config;
	}
	
	public static GunConfiguration getHPConfig() {
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
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
		config.durability = 125000;
		config.firingSound = "hbm:weapon.immolatorShoot";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.HP);
		
		return config;
	}
	
	public static BulletConfiguration getHPCartridgeConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_hp_ammo;
		bullet.velocity = 3.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.dmgMin = 25;
		bullet.dmgMax = 75;
		bullet.bulletsMin = 3;
		bullet.bulletsMax = 5;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.bounceMod = 0;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_PLASMA;
		bullet.ammoMultiplier = 4;
		
		bullet.bImpact = new IBulletImpactBehavior() {
			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				ExplosionChaos.burn(bullet.worldObj, x, y, z, 2);
				ExplosionChaos.flameDeath(bullet.worldObj, x, y, z, 5);
				ExplosionThermo.scorchLight(bullet.worldObj, x, y, z, 3);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getEnergyCellConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_defabricator_ammo;
		bullet.velocity = 3.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.dmgMin = 50;
		bullet.dmgMax = 150;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 50;
		bullet.LBRC = 50;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.style = BulletConfiguration.STYLE_PELLET;
		bullet.vPFX = "reddust";
		bullet.ammoMultiplier = 3;
		
		return bullet;
	}
	
	public static BulletConfiguration getElectroMagnetConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_spark_ammo;
		bullet.blockDamage = true;
		bullet.bounceMod = 1.0D;
		bullet.dmgMin = 100;
		bullet.dmgMax = 1000;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.maxAge = 100;
		bullet.velocity = 2.0F;
		bullet.explosive = 50;
		bullet.shrapnel = 10;
		bullet.gravity = 0.0075D;
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
