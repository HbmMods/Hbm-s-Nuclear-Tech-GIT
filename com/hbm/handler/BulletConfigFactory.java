package com.hbm.handler;

import com.hbm.items.ModItems;

public class BulletConfigFactory {
	
	public static BulletConfiguration getTestConfig() {
		
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
		
		return bullet;
		
	}

}
