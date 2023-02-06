package com.hbm.handler.guncfg;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;

public class GunCannonFactory {

	public static BulletConfiguration getShellConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = ModItems.ammo_shell;
		bullet.dmgMin = 25;
		bullet.dmgMax = 35;
		bullet.explosive = 4F;
		bullet.blockDamage = false;
		
		return bullet;
	}

	public static BulletConfiguration getShellExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = ModItems.ammo_shell_explosive;
		bullet.dmgMin = 35;
		bullet.dmgMax = 45;
		bullet.explosive = 4F;
		bullet.blockDamage = true;
		
		return bullet;
	}

	public static BulletConfiguration getShellAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = ModItems.ammo_shell_apfsds_t;
		bullet.dmgMin = 50;
		bullet.dmgMax = 55;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_APDS;
		
		return bullet;
	}

	public static BulletConfiguration getShellDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = ModItems.ammo_shell_apfsds_du;
		bullet.dmgMin = 70;
		bullet.dmgMax = 80;
		bullet.doesPenetrate = true;
		bullet.style = BulletConfiguration.STYLE_APDS;
		
		return bullet;
	}

	public static BulletConfiguration getShellW9Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardShellConfig();
		
		bullet.ammo = ModItems.ammo_shell_w9;
		bullet.dmgMin = 100;
		bullet.dmgMax = 150;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 1);
			}
		};
		
		return bullet;
	}

}
