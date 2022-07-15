package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun5mmFactory {
	
	public static GunConfiguration getMinigunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 5;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.durability = 10000;
		config.firingSound = "hbm:weapon.lacunaeShoot";
		
		config.config = HbmCollection.fiveMM;
		
		return config;
	}
	
	public static GunConfiguration get53Config() {
		
		GunConfiguration config = getMinigunConfig();
		
		config.name = "cz53";
		config.manufacturer = EnumGunManufacturer.ROCKWELL;
		
		return config;
	}
	
	public static GunConfiguration get57Config() {
		
		GunConfiguration config = getMinigunConfig();

		config.durability = 15000;
		config.name = "cz57";
		config.manufacturer = EnumGunManufacturer.ROCKWELL;
		
		return config;
	}
	
	public static GunConfiguration getLacunaeConfig() {
		
		GunConfiguration config = getMinigunConfig();

		config.durability = 25000;
		config.name = "lacunae";
		config.manufacturer = EnumGunManufacturer.ROCKWELL_U;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.R5_NORMAL_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_DU_BOLT);
		config.config.add(BulletConfigSyncingUtil.R5_STAR_BOLT);
		config.config.add(BulletConfigSyncingUtil.CHL_R5_BOLT);
		
		return config;
	}
	
	static final float inaccuracy = 10;
	static byte i = 0;
	public static BulletConfiguration get5mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 12;
		bullet.dmgMax = 14;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 32;
		bullet.explosive = 1F;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 36;
		bullet.dmgMax = 40;
		bullet.penetration = 20;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 46;
		bullet.dmgMax = 50;
		bullet.penetration = 30;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		return bullet;
	}

}
