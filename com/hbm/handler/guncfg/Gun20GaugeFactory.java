package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Gun20GaugeFactory {
	
	public static GunConfiguration getShotgunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		
		return config;
	}
	
	public static GunConfiguration getMareConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 1887";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		
		return config;
	}
	
	public static GunConfiguration getMareDarkConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 1887 Inox";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		
		return config;
	}
	
	public static GunConfiguration getBoltConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 20 Inox";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		
		return config;
	}
	
	public static GunConfiguration getBoltGreenConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 20 Polymer";
		config.manufacturer = "Winchester Repeating Arms Company";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER);
		
		return config;
	}
	
	public static GunConfiguration getBoltSaturniteConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 4000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		
		config.name = "Winchester Model 20 D-25A";
		config.manufacturer = "Winchester Repeating Arms Company / Big MT";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER_FIRE);
		
		return config;
	}
	
	public static BulletConfiguration get20GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 3;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_slug;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_flechette;
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_incendiary;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_explosive;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.wear = 25;
		bullet.explosive = 0.5F;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeCausticConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_caustic;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.wear = 25;
		bullet.caustic = 5;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.poison.id, 10 * 20, 1));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShockConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_shock;
		bullet.dmgMin = 4;
		bullet.dmgMax = 8;
		bullet.wear = 25;
		bullet.emp = 2;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(Potion.weakness.id, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeWitherConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_20gauge_wither;
		bullet.dmgMin = 4;
		bullet.dmgMax = 8;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.wither.id, 10 * 20, 2));
		
		return bullet;
	}

}
