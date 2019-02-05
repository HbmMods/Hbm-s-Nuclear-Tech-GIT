package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

public class Gun5mmFactory {
	
	static float inaccuracy = 10;
	public static BulletConfiguration get5mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_5mm;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_5mm_explosive;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 4;
		bullet.dmgMax = 7;
		bullet.explosive = 0.5F;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_5mm_du;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 10;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}

}
