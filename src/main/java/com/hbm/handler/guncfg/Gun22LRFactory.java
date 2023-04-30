package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo22LR;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun22LRFactory {
	
	private static final CasingEjector EJECTOR_22LR;
	private static final SpentCasing CASING22LR;

	static {
		EJECTOR_22LR = new CasingEjector().setMotion(-0.4, 0.1, 0).setOffset(-0.35, -0.2, 0.35).setAngleRange(0.01F, 0.03F);
		CASING22LR = new SpentCasing(CasingType.STRAIGHT).setScale(0.8F).setBounceMotion(0.05F, 0.02F).setColor(SpentCasing.COLOR_CASE_BRASS);
	}
	
	public static GunConfiguration getUziConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 32;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CROSS;
		config.durability = 4000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.uziShoot";
		config.reloadSoundEnd = false;
		
		config.name = "uzi";
		config.manufacturer = EnumGunManufacturer.IMI;
		config.comment.add("Mom, where are my mittens?");
		
		config.config = HbmCollection.lr22;
		
		config.ejector = EJECTOR_22LR;
		
		return config;
	}
	
	public static GunConfiguration getSaturniteConfig() {
		
		GunConfiguration config = getUziConfig();
		
		config.durability = 7500;
		config.ammoCap = 64;
		config.name = "uziSatur";
		config.reloadDuration = 10;
		config.manufacturer = EnumGunManufacturer.IMI_BIGMT;

		config.config = HbmCollection.lr22Inc;
		
		return config;
	}

	static float inaccuracy = 4;
	public static BulletConfiguration get22LRConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_22lr.stackFromEnum(Ammo22LR.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 8;
		bullet.dmgMax = 12;
		bullet.wear = 5;
		bullet.spentCasing = CASING22LR.clone().register("22LRStock");
		
		return bullet;
	}
	public static BulletConfiguration get22LRSatConfig() {

		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_22lr.stackFromEnum(Ammo22LR.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 18;
		bullet.dmgMax = 26;
		bullet.dmgFire = true;
		bullet.wear = 5;
		bullet.spentCasing = CASING22LR.clone().register("22LRStock");

		return bullet;
	}
	public static BulletConfiguration get22LRAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_22lr.stackFromEnum(Ammo22LR.AP));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 12;
		bullet.dmgMax = 16;
		bullet.leadChance = 10;
		bullet.wear = 10;
		
		bullet.spentCasing = CASING22LR.clone().register("22LRAP");
		
		return bullet;
	}

}
