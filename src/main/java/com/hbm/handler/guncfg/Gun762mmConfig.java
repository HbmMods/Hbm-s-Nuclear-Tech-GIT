package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun762mmConfig
{

	public static GunConfiguration getUACDMRConfig()
	{
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 30;
		config.firingDuration = 8;
		config.ammoCap = 30;
		config.durability = 30000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.DMRMagInPB3";
		config.firingSound = "hbm:weapon.DMRShootPB3";
		config.firingPitch = 0.85F;
		config.reloadSoundEnd = true;
		
		config.name = "uacDMR";
		config.manufacturer = EnumGunManufacturer.UAC;
		
		config.config.addAll(HbmCollection.threeZeroEight);
		
		return config;
	}
	
	public static GunConfiguration getUACCarbineConfig()
	{
		GunConfiguration config = getUACDMRConfig();
		
		config.rateOfFire = 2;
		config.reloadDuration = 20;
		config.ammoCap = 40;
		config.durability = 40000;
		config.crosshair = Crosshair.SPLIT;
		config.reloadSound = "hbm:weapon.carbineMagInPB3";
		config.firingSound = "hbm:weapon.carbineShootPB3";
		
		config.name = "uacCarbine";
		
		return config;
	}

	public static GunConfiguration getUACLMGConfig()
	{
		GunConfiguration config = getUACCarbineConfig();
		
		config.ammoCap = 60;
		config.durability = 50000;
		config.crosshair = Crosshair.BOX;
		config.reloadSound = "hbm:weapon.LMGMagInPB3";
		config.firingSound = "hbm:weapon.LMGShootPB3";
		config.firingPitch = 0.9F;
		
		config.name = "uacLMG";
		
		return config;
	}

	
	public static BulletConfiguration get762NATOConfig()
	{
		BulletConfiguration bullet = Gun556mmFactory.get556Config();
		
		bullet.ammo = ModItems.ammo_308;
		bullet.dmgMax = 12;
		bullet.dmgMin = 10;
		bullet.velocity *= 2.5;
		bullet.maxAge *= 2;
		bullet.spread /= 2;
		
		return bullet;
	}
}
