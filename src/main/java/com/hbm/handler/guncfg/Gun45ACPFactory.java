package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun45ACPFactory
{
	
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
		
		config.name = "M1A1 Submachine Gun";
		config.manufacturer = "Auto-Ordnance Corporation";
		
		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.fourtyFiveACP);
		
		return config;
	}
	
	public static GunConfiguration getUACPistolConfig()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 8;
		config.ammoCap = 15;
		config.durability = 10000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.pistolReloadPB3";
		config.firingSound = "hbm:weapon.pistolFirePB3";
		config.reloadSoundEnd = true;
		
		config.name = "UAC .45 Standard Issue Handgun";
		config.manufacturer = "Union Aerospace Command";
		config.damage = "6 - 8 (stock)";
		
		config.config.addAll(HbmCollection.fourtyFiveACP);
		
		return config;
	}
	
	public static GunConfiguration getUACSMGConfig()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 10;
		config.firingDuration = 4;
		config.ammoCap = 40;
		config.durability = 40000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.SMGMagInPB3";
		config.firingSound = "hbm:weapon.SMGFirePB3";
		config.firingPitch = 1.15F;
		config.reloadSoundEnd = true;
		
		config.name = "UAC Compact Sub-Machine Gun";
		config.manufacturer = "Union Aerospace Command";
		config.damage = "6 - 8 (stock)";
		
		config.config.addAll(HbmCollection.fourtyFiveACP);
		
		return config;
	}

	public static BulletConfiguration get45ACPConfig()
	{
		BulletConfiguration bullet = Gun9mmFactory.get9mmConfig();
		
		bullet.ammo = ModItems.ammo_45;
		bullet.dmgMax = 8;
		bullet.dmgMin = 6;
		
		return bullet;
	}
}
