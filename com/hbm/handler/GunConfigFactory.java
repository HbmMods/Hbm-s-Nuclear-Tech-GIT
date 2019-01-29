package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.items.ModItems;

public class GunConfigFactory {
	
	public static GunConfiguration getRevolverConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.gunMode = 0;
		config.firingMode = 1;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = 1;
		config.allowsInfinity = true;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
		
		return config;
	}

}
