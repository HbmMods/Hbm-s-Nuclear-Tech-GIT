package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunGaussFactory {
	
	public static GunConfiguration getXVLConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.CIRCLE;
		config.durability = 15000;
		config.firingSound = "hbm:weapon.tauShoot";
		config.firingVolume = 0.5F;
		
		config.name = "tau";
		config.manufacturer = EnumGunManufacturer.BLACK_MESA;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_GAUSS);
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				);
		
		return config;
	}
	
	public static GunConfiguration getChargedConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.reloadDuration = 1;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_GAUSS_CHARGED);
		
		return config;
	}

	public static BulletConfiguration getGaussConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.gun_xvl1456_ammo);
		bullet.dmgMin = 18;
		bullet.dmgMax = 27;
		bullet.style = bullet.STYLE_TAU;
		bullet.LBRC = 80;
		bullet.HBRC = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration getAltConfig() {
		
		BulletConfiguration bullet = getGaussConfig();
		
		bullet.trail = 1;
		
		return bullet;
	}
}
