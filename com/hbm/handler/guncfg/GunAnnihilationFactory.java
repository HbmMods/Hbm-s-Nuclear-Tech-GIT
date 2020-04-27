package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunAnnihilationFactory {
	public static GunConfiguration getZOMGConfig() {
		GunConfiguration config = new GunConfiguration();
		config.rateOfFire = 1;
		config.durability = 100000;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.firingSound = "hbm:weapon.osiprShoot";
		config.roundsPerCycle = 3;
		config.ammoless = true;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.crosshair = Crosshair.CIRCLE;
		config.firingDuration = 1;
		config.unbreakable = true;
		
		config.maxCharge = 1000000000L;
		config.chargeRate = 100000000L;
		config.shotCharge = 1000000L;
		
		config.name = "ZOMG Cannon";
		config.manufacturer = "NXT Technologies";
		
		config.ammoType = BulletConfigSyncingUtil.ZOMG_CANNON;
		
		return config;
	}
	
	public static GunConfiguration getBrimstoneConfig() {
		GunConfiguration config = new GunConfiguration();
		config.rateOfFire = 1;
		config.durability = 100000;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.roundsPerCycle = 1;
		config.ammoless = true;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.crosshair = Crosshair.BOX;
		config.firingDuration = 1;
		config.unbreakable = true;
		
		config.name = "BRIMSTONE";
		config.manufacturer = "NXT Technologies?";
		
		config.ammoType = BulletConfigSyncingUtil.BRIMSTONE;
		
		return config;
	}
	
	public static BulletConfiguration getZOMGShotConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.destroysBlocks = false;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		bullet.plink = BulletConfiguration.PLINK_ENERGY;
		bullet.leadChance = 0;
		bullet.dmgMin = 10;
		bullet.dmgMax = 100;
		
		return bullet;
	}
	
	public static BulletConfiguration getAnnihilationLaserConfig() {
		BulletConfiguration bullet = getZOMGShotConfig();
		
		bullet.destroysBlocks = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_RAINBOW;
		bullet.dmgMin = 100;
		bullet.dmgMax = 1000;
		
		bullet.clearOutStrength = 3;
		bullet.maximumBlockHardness = -1;
		bullet.maximumPenetratedBlocks = 6;
		
		return bullet;
	}
	
	public static BulletConfiguration getBrimstoneLaserConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 0.0F;
		bullet.spread = 0.0F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 0;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 0;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.destroysBlocks = true;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		bullet.plink = BulletConfiguration.PLINK_ENERGY;
		bullet.leadChance = 0;
		bullet.dmgMin = 10;
		bullet.dmgMax = 100;
		
		return bullet;
	}
}
