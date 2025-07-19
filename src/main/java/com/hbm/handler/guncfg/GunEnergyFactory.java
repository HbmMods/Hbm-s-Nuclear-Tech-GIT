package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

public class GunEnergyFactory {

	public static BulletConfiguration getTurbineConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.nothing);
		bullet.dmgMin = 100;
		bullet.dmgMax = 150;
		bullet.velocity = 1F;
		bullet.gravity = 0.0;
		bullet.maxAge = 200;
		bullet.style = bullet.STYLE_BLADE;
		bullet.destroysBlocks = true;
		bullet.doesRicochet = false;
		
		return bullet;
	}
}
