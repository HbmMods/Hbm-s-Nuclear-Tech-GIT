package com.hbm.handler.guncfg;

import java.util.ArrayList;
import java.util.Optional;

import com.hbm.calc.EasyLocation;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasingConfig;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.util.Vec3;

public class Gun9mmFactory {
	
	static final SpentCasingConfig CASING_9 = Gun45ACPFactory.CASING_45_UAC.toBuilder("9")
			.setInitialMotion(Vec3.createVectorHelper(-0.3, 0.6, 0)).setPosOffset(new EasyLocation(-0.35, -0.2, 0.55))
			.setScaleX(1).setScaleY(1).setScaleZ(0.6f).build();
	
	public static GunConfiguration getMP40Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 32;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.rifleShoot";
		config.reloadSoundEnd = false;
		
		config.name = "mp40";
		config.manufacturer = EnumGunManufacturer.NAZI;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.CHL_P9);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);
		
		config.casingConfig = Optional.of(CASING_9);
		
		return config;
	}
	
	public static GunConfiguration getThompsonConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.rifleShoot";
		config.reloadSoundEnd = false;
		
		config.name = "tommy9";
		config.manufacturer = EnumGunManufacturer.AUTO_ORDINANCE;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.P9_NORMAL);
		config.config.add(BulletConfigSyncingUtil.P9_AP);
		config.config.add(BulletConfigSyncingUtil.P9_DU);
		config.config.add(BulletConfigSyncingUtil.CHL_P9);
		config.config.add(BulletConfigSyncingUtil.P9_ROCKET);

		config.casingConfig = Optional.of(CASING_9);
		
		return config;
	}
	
	public static GunConfiguration getLLRConfig()
	{
		GunConfiguration config = new GunConfiguration();
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = 0;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = true;
		config.reloadDuration = 10;
		config.ammoCap = 60;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.durability = 60000;
		config.firingSound = "hbm:weapon.rifleShoot";
		config.firingPitch = 1.25F;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.reloadSoundEnd = false;
		
		config.name = "lunaSMG";
		config.manufacturer = EnumGunManufacturer.LUNA;
		config.comment.add("Calling this a rifle is a bit of a misnomer");
		
		config.config = HbmCollection.nineMM;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, -0.1, 30))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 30))));

		config.casingConfig = Optional.of(CASING_9);
		
		return config;
	}
	
	static final float inaccuracy = 1.15f;
	public static BulletConfiguration get9mmConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm, 1, 0);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 10;
		bullet.dmgMax = 14;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm, 1, 1);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 18;
		bullet.dmgMax = 20;
		bullet.penetration *= 1.5;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm, 1, 2);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 22;
		bullet.dmgMax = 26;
		bullet.penetration *= 2;
		bullet.leadChance = 50;
		bullet.wear = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration get9mmRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_9mm, 1, 3);
		bullet.velocity = 5;
		bullet.explosive = 7.5F;
		bullet.trail = 5;
		
		return bullet;
	}

}