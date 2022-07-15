package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.GunConfigurationEnergy;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunOSIPRFactory {
	
	public static GunConfiguration getOSIPRConfig() {
		
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
		config.crosshair = Crosshair.L_ARROWS;
		config.durability = 10000;
		config.reloadSound = "hbm:weapon.osiprReload";
		config.firingSound = "hbm:weapon.osiprShoot";
		config.reloadSoundEnd = false;
		
		config.name = "osipr";
		config.manufacturer = EnumGunManufacturer.COMBINE;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_OSIPR);
		
		return config;
	}
	
	public static GunConfiguration getOSIPREnergyConfig()
	{
		GunConfigurationEnergy config = (GunConfigurationEnergy) getOSIPRConfig().clone();
		
		config.ammoChargeCap = 300000;
		config.ammoRate = 10000;
		
		return config;
	}
	
	public static GunConfiguration getAltConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 15;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
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
	
	public static GunConfiguration getSMGConfig()
	{
		GunConfiguration config = getOSIPRConfig().clone();
		
		return config;
	}

	static final float inaccuracy = 2;
	public static BulletConfiguration getPulseConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.gun_osipr_ammo);
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 15;
		bullet.dmgMax = 21;
		bullet.penetration = 24;
		bullet.velocity = 40;
		bullet.trail = 2;
		
		return bullet;
	}
	public static BulletConfiguration getPulseChargedConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.gun_osipr_ammo2);
		
		return bullet;
	}
}
