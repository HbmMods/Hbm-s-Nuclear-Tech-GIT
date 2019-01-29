package com.hbm.handler;

import java.util.ArrayList;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;

import net.minecraft.potion.PotionEffect;

public class BulletConfigFactory {
	
	/// configs should never be loaded manually due to syncing issues: use the syncing util and pass the UID in the DW of the bullet to make the client load the config correctly ////
	
	protected static BulletConfiguration getTestConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_revolver_ammo;
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
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
		bullet.incendiary = 0;
		bullet.emp = 0;
		bullet.rainbow = 0;
		bullet.nuke = 0;
		bullet.boxcar = false;
		bullet.boat = false;
		bullet.destroysBlocks = false;
		bullet.style = 0;
		bullet.plink = 1;
		
		return bullet;
		
	}
	
	/// STANDARD CONFIGS ///
	//do not include damage or ammo
	protected static BulletConfiguration standardBulletConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.005F;
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
		bullet.incendiary = 0;
		bullet.emp = 0;
		bullet.rainbow = 0;
		bullet.nuke = 0;
		bullet.boxcar = false;
		bullet.boat = false;
		bullet.destroysBlocks = false;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.instakill = false;
		
		return bullet;
	}
	
	protected static BulletConfiguration standardBuckshotConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 5.0F;
		bullet.spread = 0.05F;
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
		bullet.incendiary = 0;
		bullet.emp = 0;
		bullet.rainbow = 0;
		bullet.nuke = 0;
		bullet.boxcar = false;
		bullet.boat = false;
		bullet.destroysBlocks = false;
		bullet.style = BulletConfiguration.STYLE_PELLET;
		bullet.plink = BulletConfiguration.PLINK_BULLET;
		bullet.instakill = false;
		
		return bullet;
	}
	
	/// ADJUSTED CONFIGS ///
	protected static BulletConfiguration getRevIronConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_iron_ammo;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRevSteelConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_ammo;
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRevLeadConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_lead_ammo;
		bullet.dmgMin = 2;
		bullet.dmgMax = 3;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(HbmPotion.radiation.id, 10 * 20, 4));
		
		return bullet;
	}
	
	protected static BulletConfiguration getRevGoldConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_gold_ammo;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRevSchrabidiumConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_schrabidium_ammo;
		bullet.dmgMin = 10000;
		bullet.dmgMax = 100000;
		bullet.instakill = true;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRevCursedConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_cursed_ammo;
		bullet.dmgMin = 12;
		bullet.dmgMax = 15;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRevNightmareConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_nightmare_ammo;
		bullet.dmgMin = 1;
		bullet.dmgMax = 50;
		
		return bullet;
	}

}
