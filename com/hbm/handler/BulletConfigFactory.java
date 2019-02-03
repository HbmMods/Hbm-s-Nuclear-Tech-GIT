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
	protected static BulletConfiguration standardBulletConfig() {
		
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
		
		return bullet;
	}
	
	protected static BulletConfiguration standardBuckshotConfig() {
		
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
		
		return bullet;
	}
	
	protected static BulletConfiguration standardRocketConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.velocity = 2.0F;
		bullet.spread = 0.005F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.gravity = 0.005D;
		bullet.maxAge = 300;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 3;
		bullet.HBRC = 0;
		bullet.LBRC = 95;
		bullet.bounceMod = 0.8;
		bullet.doesPenetrate = false;
		bullet.doesBreakGlass = false;
		bullet.explosive = 5.0F;
		bullet.style = BulletConfiguration.STYLE_ROCKET;
		bullet.plink = BulletConfiguration.PLINK_GRENADE;
		
		return bullet;
	}
	
	/// ADJUSTED CONFIGS ///
	
	/// .357 ///
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
	
	/// 12 Gauge ///
	protected static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	protected static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_incendiary;
		bullet.wear = 15;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	/// 20 Gauge ///
	protected static BulletConfiguration get20GaugeConfig() {
		
		BulletConfiguration bullet = standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 3;
		
		return bullet;
	}

	protected static BulletConfiguration get20GaugeSlugConfig() {
		
		BulletConfiguration bullet = standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_slug;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	protected static BulletConfiguration get20GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_flechette;
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	protected static BulletConfiguration get20GaugeFireConfig() {
		
		BulletConfiguration bullet = standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_incendiary;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	protected static BulletConfiguration get20GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_explosive;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.wear = 25;
		bullet.explosive = 0.5F;
		
		return bullet;
	}
	
	/// 84mm Rockets ///
	protected static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.trail = 0;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRocketHEConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_he;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 6.5F;
		bullet.trail = 1;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRocketIncendiaryConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_incendiary;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 2;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRocketEMPConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_emp;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 2.5F;
		bullet.emp = 10;
		bullet.trail = 4;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRocketSleekConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_sleek;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 10F;
		bullet.trail = 6;
		bullet.gravity = 0;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRocketShrapnelConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_shrapnel;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.shrapnel = 25;
		bullet.trail = 3;
		
		return bullet;
	}
	
	protected static BulletConfiguration getRocketGlareConfig() {
		
		BulletConfiguration bullet = standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_glare;
		bullet.velocity = 5.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 20;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 5;
		
		return bullet;
	}

}
