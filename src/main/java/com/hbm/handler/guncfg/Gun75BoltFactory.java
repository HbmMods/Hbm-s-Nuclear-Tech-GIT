package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun75BoltFactory {

	public static GunConfiguration getBolterConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = false;
		config.reloadDuration = 40;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.NONE;
		config.durability = 10000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.hksShoot";
		config.reloadSoundEnd = false;
		config.showAmmo = false;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				.addBus("EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 75))
						)
				);
		
		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("TILT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 250))
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 1500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						)
				.addBus("MAG", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 500))
						.addKeyframe(new BusAnimationKeyframe(1, 0, 1, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				);
		
		config.name = "Manticora Pattern Boltgun";
		config.manufacturer = "Cerix Magnus";
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.B75_NORMAL);
		
		return config;
	}

	static float inaccuracy = 0.5F;
	public static BulletConfiguration get75BoltConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_75bolt;
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 16;
		bullet.dmgMax = 24;
		bullet.doesRicochet = false;
		bullet.explosive = 0.25F;
		
		return bullet;
	}

}
