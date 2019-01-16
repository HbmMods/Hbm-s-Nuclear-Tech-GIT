package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.items.ModItems;

public class GunConfigFactory {
	
	public static GunConfiguration getRevolverConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.bulletsMin = 1;
		config.bulletsMax = 1;
		config.gunMode = 0;
		config.firingMode = 0;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 0;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = 1;
		config.allowsInfinity = true;
		
		config.config = new ArrayList<BulletConfiguration>();
		config.config.add(BulletConfigFactory.getTestConfig());
		
		return config;
	}

}
