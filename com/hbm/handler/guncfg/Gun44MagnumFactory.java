package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

public class Gun44MagnumFactory {
	
	public static BulletConfiguration getNoPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44;
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_ap;
		bullet.dmgMin = 7;
		bullet.dmgMax = 10;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		return bullet;
	}
	
	public static BulletConfiguration getNoPipDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_du;
		bullet.dmgMin = 7;
		bullet.dmgMax = 10;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration getPipConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_pip;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.boxcar = true;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration getBJConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_44_bj;
		bullet.dmgMin = 4;
		bullet.dmgMax = 5;
		bullet.boat = true;
		bullet.wear = 25;
		
		return bullet;
	}

}
