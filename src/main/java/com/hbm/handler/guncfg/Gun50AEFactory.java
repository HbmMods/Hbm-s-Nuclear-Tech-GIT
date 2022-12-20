package com.hbm.handler.guncfg;

import java.util.Optional;

import com.hbm.calc.EasyLocation;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasingConfig;
import com.hbm.particle.SpentCasingConfig.CasingType;
import com.hbm.particle.SpentCasingConfigBuilder;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.util.Vec3;

public class Gun50AEFactory {
	
	static final SpentCasingConfig CASING_50AE = new SpentCasingConfigBuilder("50ae", CasingType.BRASS_STRAIGHT_WALL, false)
			.setSmokeChance(4).setInitialMotion(Vec3.createVectorHelper(-0.3, 0.7, 0)).setPitchFactor(0.03f).setYawFactor(0.01f)
			.setPosOffset(new EasyLocation(-0.5, 0, 0.5)).setScaleZ(1.5f).build();
	
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
		
		config.casingConfig = Optional.of(CASING_50AE);
		
		return config;
	}
	
	public static GunConfiguration getDeagleConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "deagle";
		config.manufacturer = EnumGunManufacturer.MAGNUM_R_IMI;
		
		config.hasSights = true;
		config.config = HbmCollection.fiftyAE;
		
		return config;
	}
	
	public static GunConfiguration getUACDeagleConfig()
	{
		GunConfiguration config = getDeagleConfig().clone();
		
		config.durability = 5000;
		config.ammoCap = 12;
		config.name = "uacDeagle";
		config.manufacturer = EnumGunManufacturer.UAC;
		config.comment.add("Aka: UAC Desert Eagle Mark VII");
		
		return config;
	}

	static final float inaccuracy = 0.0005F;
	static byte i = 0;
	public static BulletConfiguration get50AEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 28;
		bullet.dmgMax = 32;
		bullet.penetration = 35;
		
		return bullet;
	}

	public static BulletConfiguration get50APConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.penetration = 45;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		return bullet;
	}

	public static BulletConfiguration get50DUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 38;
		bullet.dmgMax = 46;
		bullet.penetration = 60;
		bullet.leadChance = 50;
		bullet.wear = 25;
		
		return bullet;
	}

	public static BulletConfiguration get50StarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_50ae, 1, i++);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 52;
		bullet.dmgMax = 60;
		bullet.penetration = 80;
		bullet.leadChance = 100;
		bullet.wear = 25;
		
		return bullet;
	}

}