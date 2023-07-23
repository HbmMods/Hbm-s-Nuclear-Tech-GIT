package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoGrenade;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunGrenadeFactory {
	
	private static final CasingEjector EJECTOR_LAUNCHER;
	private static final SpentCasing CASING40MM;

	static {
		EJECTOR_LAUNCHER = new CasingEjector().setAngleRange(0.02F, 0.03F).setAfterReload();
		CASING40MM = new SpentCasing(CasingType.STRAIGHT).setScale(4F, 4F, 3F).setBounceMotion(0.02F, 0.03F).setColor(0x777777).setupSmoke(1F, 0.5D, 60, 40);
	}
	
	public static GunConfiguration getHK69Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasSights = true;
		config.zoomFOV = 0.66F;
		config.reloadDuration = 40;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.hkShoot";
		config.reloadSound = GunConfiguration.RSOUND_GRENADE;
		config.reloadSoundEnd = false;
		
		config.name = "gPistol";
		config.manufacturer = EnumGunManufacturer.H_AND_K;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.GRENADE_NORMAL);
		config.config.add(BulletConfigSyncingUtil.GRENADE_HE);
		config.config.add(BulletConfigSyncingUtil.GRENADE_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.GRENADE_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.GRENADE_CHEMICAL);
		config.config.add(BulletConfigSyncingUtil.GRENADE_CONCUSSION);
		config.config.add(BulletConfigSyncingUtil.GRENADE_FINNED);
		config.config.add(BulletConfigSyncingUtil.GRENADE_SLEEK);
		config.config.add(BulletConfigSyncingUtil.GRENADE_NUCLEAR);
		config.config.add(BulletConfigSyncingUtil.GRENADE_TRACER);
		config.config.add(BulletConfigSyncingUtil.GRENADE_KAMPF);
		config.durability = 300;
		
		config.ejector = EJECTOR_LAUNCHER;
		
		return config;
	}

	public static BulletConfiguration getGrenadeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.STOCK));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.trail = 0;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMStock");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeHEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.HE));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 20;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 5.0F;
		bullet.trail = 1;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMHE");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeIncendirayConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.INCENDIARY));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.trail = 0;
		bullet.incendiary = 2;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMInc");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadePhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.PHOSPHORUS));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.trail = 0;
		bullet.incendiary = 2;
		
		bullet.bntImpact = BulletConfigFactory.getPhosphorousEffect(10, 60 * 20, 100, 0.5D, 1F);
		
		bullet.spentCasing = CASING40MM.clone().register("40MMPhos");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeChlorineConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.CHLORINE));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.trail = 3;
		bullet.explosive = 0;
		bullet.chlorine = 50;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMTox");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.SLEEK));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.trail = 4;
		bullet.explosive = 7.5F;
		bullet.jolt = 6.5D;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMIF");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeConcussionConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.CONCUSSION));
		bullet.velocity = 2.0F;
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.blockDamage = false;
		bullet.explosive = 10.0F;
		bullet.trail = 3;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMCon");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeFinnedConfig() {
		
		BulletConfiguration bullet = getGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.FINNED));
		bullet.gravity = 0.02;
		bullet.explosive = 1.5F;
		bullet.trail = 5;
		
		bullet.spentCasing = CASING40MM.clone().register("40MMFin");
		
		return bullet;
	}
	
	public static BulletConfiguration getGrenadeNuclearConfig() {
		
		BulletConfiguration bullet = getGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.NUCLEAR));
		bullet.velocity = 4;
		bullet.explosive = 0.0F;
		
		bullet.bntImpact = (bulletnt, x, y, z) -> {
			BulletConfigFactory.nuclearExplosion(bulletnt, x, y, z, ExplosionNukeSmall.PARAMS_TOTS);
		};
		
		bullet.spentCasing = CASING40MM.clone().register("40MMNuke");
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeTracerConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.TRACER));
		bullet.velocity = 2.0F;
		bullet.wear = 10;
		bullet.explosive = 0F;
		bullet.trail = 5;
		bullet.vPFX = "bluedust";
		
		bullet.spentCasing = CASING40MM.clone().register("40MMTrac").setColor(0xEEEEEE);
		
		return bullet;
	}

	public static BulletConfiguration getGrenadeKampfConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_grenade.stackFromEnum(AmmoGrenade.KAMPF));
		bullet.spread = 0.0F;
		bullet.gravity = 0.0D;
		bullet.wear = 15;
		bullet.explosive = 3.5F;
		bullet.style = BulletConfiguration.STYLE_GRENADE;
		bullet.trail = 4;
		bullet.vPFX = "smoke";
		
		//bullet.spentCasing = CASING40MM.clone().register("40MMKampf").setColor(0xEBC35E); //does not eject, whole cartridge leaves the gun
		
		return bullet;
	}
}
