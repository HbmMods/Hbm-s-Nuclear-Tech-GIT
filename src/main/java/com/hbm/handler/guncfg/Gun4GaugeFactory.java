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

public class Gun4GaugeFactory {
	
	private static GunConfiguration getShotgunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 15;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 4;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		
		return config;
	}
	
	public static GunConfiguration getKS23Config() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.65F;
		
		config.name = "KS-23";
		config.manufacturer = "Tulsky Oruzheiny Zavod";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G4_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G4_SLUG);
		config.config.add(BulletConfigSyncingUtil.G4_EXPLOSIVE);
		
		return config;
	}
	
	public static GunConfiguration getSauerConfig() {
		
		GunConfiguration config = getShotgunConfig();

		config.rateOfFire = 20;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.sauergun";
		config.firingPitch = 1.0F;
		
		config.name = "Sauer Shotgun";
		config.manufacturer = "Cube 2: Sauerbraten";
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SAUER_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						)
				.addBus("SAUER_TILT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.0, 0, 0, 200))	// do nothing for 200ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 30, 150))	//tilt forward
						.addKeyframe(new BusAnimationKeyframe(45, 0, 30, 150))	//tilt sideways
						.addKeyframe(new BusAnimationKeyframe(45, 0, 30, 200))	//do nothing for 200ms (eject)
						.addKeyframe(new BusAnimationKeyframe(0, 0, 30, 150))	//restore sideways
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))	//restore forward
						)
				.addBus("SAUER_COCK", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 100))	//pull back lever for 100ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100))	//release lever for 100ms
						)
				.addBus("SAUER_SHELL_EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 500))	//FLING!
						)
				);
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G4_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G4_SLUG);
		config.config.add(BulletConfigSyncingUtil.G4_EXPLOSIVE);
		
		return config;
	}
	
	public static BulletConfiguration get4GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_4gauge;
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.bulletsMin *= 2;
		bullet.bulletsMax *= 2;
		
		return bullet;
	}
	
	public static BulletConfiguration get4GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_4gauge_slug;
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	public static BulletConfiguration get4GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardGrenadeConfig();
		
		bullet.ammo = ModItems.ammo_4gauge_explosive;
		bullet.velocity *= 2;
		bullet.gravity *= 2;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 25;
		bullet.trail = 1;
		
		return bullet;
	}
}
