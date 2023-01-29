package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo9mm;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun9mmFactory {
	
	public static GunConfiguration getMP40Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
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
		
		config.name = "mp40";
		config.manufacturer = EnumGunManufacturer.NAZI;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.CHL_P9);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);
		
		return config;
	}
	
	public static GunConfiguration getThompsonConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.rifleShoot";
		config.reloadSoundEnd = false;
		
		config.name = "tommy9";
		config.manufacturer = EnumGunManufacturer.AUTO_ORDINANCE;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.CHL_P9);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);
		
		return config;
	}

	static float inaccuracy = 5;
	public static BulletConfiguration get9mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm.stackFromEnum(Ammo9mm.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 10;
		bullet.dmgMax = 14;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm.stackFromEnum(Ammo9mm.AP));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 18;
		bullet.dmgMax = 20;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm.stackFromEnum(Ammo9mm.DU));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 22;
		bullet.dmgMax = 26;
		bullet.leadChance = 50;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm.stackFromEnum(Ammo9mm.ROCKET));
		bullet.velocity = 5;
		bullet.explosive = 7.5F;
		bullet.trail = 5;
		
		return bullet;
	}

}
