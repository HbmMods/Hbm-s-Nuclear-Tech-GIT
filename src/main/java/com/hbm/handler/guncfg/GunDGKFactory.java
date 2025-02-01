package com.hbm.handler.guncfg;

import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;

public class GunDGKFactory {
	
	public static final SpentCasing CASINGDGK;

	static {
		CASINGDGK = new SpentCasing(CasingType.STRAIGHT).setScale(1.5F).setBounceMotion(0.05F, 0.02F).setColor(SpentCasing.COLOR_CASE_BRASS).register("DGK").setupSmoke(0.02F, 0.5D, 60, 20).setMaxAge(60); //3 instead of 12 seconds
	}

	/*public static BulletConfiguration getDGKConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_dgk);
		bullet.spentCasing = CASINGDGK.register("DGK");
		return bullet;
	}*/
}
