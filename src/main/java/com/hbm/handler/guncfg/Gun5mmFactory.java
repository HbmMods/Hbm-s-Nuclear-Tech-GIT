package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.Ammo5mm;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun5mmFactory {
	
	private static final CasingEjector EJECTOR_MINIGUN;
	private static final SpentCasing CASING5MM;

	static {
		EJECTOR_MINIGUN = new CasingEjector().setMotion(-0.4, 0.1, 0).setOffset(-0.35, -0.2, 0.35).setAngleRange(0.01F, 0.03F).setAmount(5);
		CASING5MM = new SpentCasing(CasingType.STRAIGHT).setScale(1.25F).setBounceMotion(0.05F, 0.02F).setColor(SpentCasing.COLOR_CASE_BRASS).setMaxAge(100);
	}
	
	public static GunConfiguration getMinigunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 5;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.durability = 10000;
		config.firingSound = "hbm:weapon.lacunaeShoot";
		
		config.config = HbmCollection.r5;
		
		config.ejector = EJECTOR_MINIGUN;
		
		return config;
	}
	
	public static GunConfiguration get53Config() {
		
		GunConfiguration config = getMinigunConfig();
		
		config.name = "cz53";
		config.manufacturer = EnumGunManufacturer.ROCKWELL;
		
		return config;
	}
	
	public static GunConfiguration get57Config() {
		
		GunConfiguration config = getMinigunConfig();

		config.durability = 15000;
		config.name = "cz57";
		config.manufacturer = EnumGunManufacturer.ROCKWELL;
		
		return config;
	}
	
	public static GunConfiguration getLacunaeConfig() {
		
		GunConfiguration config = getMinigunConfig();

		config.durability = 25000;
		config.name = "lacunae";
		config.manufacturer = EnumGunManufacturer.ROCKWELL_U;
		
		config.config = HbmCollection.r5Bolt;
		
		return config;
	}
	
	private static float inaccuracy = 10;
	public static BulletConfiguration get5mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm.stackFromEnum(Ammo5mm.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 12;
		bullet.dmgMax = 14;
		
		bullet.spentCasing = CASING5MM.clone().register("5mmStock");
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm.stackFromEnum(Ammo5mm.EXPLOSIVE));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 32;
		bullet.explosive = 1F;
		bullet.wear = 25;
		
		bullet.spentCasing = CASING5MM.clone().register("5mmExp");
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm.stackFromEnum(Ammo5mm.DU));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 36;
		bullet.dmgMax = 40;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		bullet.spentCasing = CASING5MM.clone().register("5mmDU");
		
		return bullet;
	}
	
	public static BulletConfiguration get5mmStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_5mm.stackFromEnum(Ammo5mm.STAR));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 46;
		bullet.dmgMax = 50;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		bullet.spentCasing = CASING5MM.clone().register("5mmStar");
		
		return bullet;
	}

}
