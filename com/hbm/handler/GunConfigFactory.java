package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.items.ModItems;

public class GunConfigFactory {
	
	public static GunConfiguration getRevolverConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_revolver_lead_ammo;
		bullet.spread = 0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 17;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 15;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.incendiary = 0;
		bullet.emp = 0;
		bullet.rainbow = 0;
		bullet.nuke = 0;
		bullet.boxcar = false;
		bullet.destroysBlocks = false;
		bullet.style = 0;
		bullet.plink = 1;
		
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
		config.config.add(bullet);
		
		return config;
	}

}
