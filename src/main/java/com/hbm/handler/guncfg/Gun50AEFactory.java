package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo50AE;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun50AEFactory {
	
	private static final CasingEjector EJECTOR_PISTOL;
	private static final SpentCasing CASING50AE;

	static {
		EJECTOR_PISTOL = new CasingEjector().setMotion(-0.3, 0.7, 0).setOffset(-0.5, 0, 0.5).setAngleRange(0.01F, 0.03F);
		CASING50AE = new SpentCasing(CasingType.STRAIGHT).setScale(1.5F).setBounceMotion(0.01F, 0.03F).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(0.25F, 0.5D, 60, 20);
	}
	
	public static GunConfiguration getBaseConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 7;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.deagleShoot";
		config.reloadSoundEnd = false;
		
		return config;
	}
	
	public static GunConfiguration getDeagleConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "deagle";
		config.manufacturer = EnumGunManufacturer.MAGNUM_R_IMI;
		
		config.absoluteFOV = true;
		config.zoomFOV = 0.5F;
		
		config.hasSights = true;
		config.config = HbmCollection.ae50;
		
		config.ejector = EJECTOR_PISTOL;
		
		return config;
	}

	private static float inaccuracy = 0.0005F;
	public static BulletConfiguration get50AEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae.stackFromEnum(Ammo50AE.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 28;
		bullet.dmgMax = 32;
		
		bullet.spentCasing = CASING50AE.clone().register("50AEStock");
		
		return bullet;
	}

	public static BulletConfiguration get50APConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae.stackFromEnum(Ammo50AE.AP));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		bullet.spentCasing = CASING50AE.clone().register("50AEAP");
		
		return bullet;
	}

	public static BulletConfiguration get50DUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae.stackFromEnum(Ammo50AE.DU));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 38;
		bullet.dmgMax = 46;
		bullet.leadChance = 50;
		bullet.wear = 25;
		
		bullet.spentCasing = CASING50AE.clone().register("50AEDU");
		
		return bullet;
	}

	public static BulletConfiguration get50StarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae.stackFromEnum(Ammo50AE.STAR));
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 52;
		bullet.dmgMax = 60;
		bullet.leadChance = 100;
		bullet.wear = 25;
		
		bullet.spentCasing = CASING50AE.clone().register("50AEStar");
		
		return bullet;
	}

}
