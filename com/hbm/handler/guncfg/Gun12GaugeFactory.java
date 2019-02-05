package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

public class Gun12GaugeFactory {
	
	public static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_incendiary;
		bullet.wear = 15;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.incendiary = 5;
		
		return bullet;
	}

}
