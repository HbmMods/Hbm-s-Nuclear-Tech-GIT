package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

public class BulletConfigFactory {
	
	/// configs should never be loaded manually due to syncing issues: use the syncing util and pass the UID in the DW of the bullet to make the client load the config correctly ////
	
	public static BulletConfiguration getTestConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_revolver_ammo;
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.dmgMin = 15;
		bullet.dmgMax = 17;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 90;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.style = 0;
		bullet.plink = 1;
		
		return bullet;
		
	}
	
	/// STANDARD CONFIGS ///
	//do not include damage or ammo
	public static BulletConfiguration standardBulletConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 5;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = true;
		bullet.destroysBlocks = false;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.leadChance = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration standardBuckshotConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
		bullet.wear = 10;
		bullet.bulletsMin = 5;
		bullet.bulletsMax = 8;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 5;
		bullet.HBRC = 10;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = true;
		bullet.style = BulletConfiguration.STYLE_PELLET;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.leadChance = 10;
		
		return bullet;
	}
	
	public static BulletConfiguration standardRocketConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.005D;
		bullet.maxAge = 300;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 10;
		bullet.HBRC = 2;
		bullet.LBRC = 100;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 5.0F;
		bullet.style = BulletConfiguration.STYLE_ROCKET;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		
		return bullet;
	}
	
	public static BulletConfiguration standardGrenadeConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.035D;
		bullet.maxAge = 300;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 2.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		
		return bullet;
	}
	
	public static BulletConfiguration standardNukeConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 3.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 1000;
		bullet.dmgMax = 1000;
		bullet.gravity = 0.025D;
		bullet.maxAge = 300;
		bullet.doesRicochet = false;
		bullet.ricochetAngle = 0;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		bullet.bounceMod = 1.0;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.nuke = 35;
		bullet.style = BulletConfiguration.STYLE_NUKE;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		
		return bullet;
	}

}
