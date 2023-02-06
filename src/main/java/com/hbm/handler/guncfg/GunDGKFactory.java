package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

public class GunDGKFactory {

	public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = ModItems.ammo_dgk;
		return bullet;
	}

}
