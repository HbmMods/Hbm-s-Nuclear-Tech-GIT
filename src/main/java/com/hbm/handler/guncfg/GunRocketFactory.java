package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

public class GunRocketFactory {
	
	public static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.nothing);
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.trail = 0;
		
		return bullet;
	}
}
