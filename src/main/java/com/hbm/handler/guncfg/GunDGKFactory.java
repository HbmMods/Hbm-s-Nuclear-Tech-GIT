package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

public class GunDGKFactory {

	public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_dgk);
		return bullet;
	}

}