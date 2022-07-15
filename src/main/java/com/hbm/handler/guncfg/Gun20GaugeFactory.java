package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Gun20GaugeFactory {
	
	public static GunConfiguration getShotgunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 25;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("LEVER_ROTATE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 45, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				.addBus("LEVER_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))
						.addKeyframe(new BusAnimationKeyframe(0, -0.5, 0, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				);
		
		config.config = HbmCollection.twentyGauge;
		
		return config;
	}
	
	public static GunConfiguration getMareConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.75F;
		
		config.name = "win1887";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;
		
		config.config = HbmCollection.twentyGauge;
		
		return config;
	}
	
	public static GunConfiguration getMareDarkConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.75F;
		
		config.name = "win1887Inox";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;
		
		config.config = HbmCollection.twentyGauge;
		
		return config;
	}
	
	public static GunConfiguration getBoltConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		
		config.name = "win20Inox";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				.addBus("LEVER_PULL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375)) //wait out recoil and lever flick
						.addKeyframe(new BusAnimationKeyframe(-1, 0, 0, 375)) //pull back bolt
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375)) //release bolt
						)
				.addBus("LEVER_ROTATE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250)) //wait out recoil
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 125)) //flick up lever in  125ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 750)) //pull action
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 125)) //flick down lever again
						)
				);
		
		config.config = HbmCollection.twentyGauge;
		
		return config;
	}
	
	public static GunConfiguration getBoltGreenConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		
		config.name = "win20Poly";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				.addBus("LEVER_PULL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375)) //wait out recoil and lever flick
						.addKeyframe(new BusAnimationKeyframe(-1, 0, 0, 375)) //pull back bolt
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375)) //release bolt
						)
				.addBus("LEVER_ROTATE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250)) //wait out recoil
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 125)) //flick up lever in  125ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 750)) //pull action
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 125)) //flick down lever again
						)
				);
		
		
		config.config = HbmCollection.twentyGauge;
		
		return config;
	}
	
	public static GunConfiguration getBoltSaturniteConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.ammoCap = 1;
		config.durability = 4000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		
		config.name = "win20Satur";
		config.manufacturer = EnumGunManufacturer.WINCHESTER_BIGMT;
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				.addBus("LEVER_PULL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375)) //wait out recoil and lever flick
						.addKeyframe(new BusAnimationKeyframe(-1, 0, 0, 375)) //pull back bolt
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 375)) //release bolt
						)
				.addBus("LEVER_ROTATE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250)) //wait out recoil
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 125)) //flick up lever in  125ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 750)) //pull action
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 125)) //flick down lever again
						)
				);
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G20_SLUG_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_NORMAL_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_FLECHETTE_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G20_EXPLOSIVE_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_CAUSTIC_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SHOCK_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_WITHER_FIRE);
		config.config.add(BulletConfigSyncingUtil.G20_SLEEK);
		
		return config;
	}
	static byte i = 0;
	public static BulletConfiguration get20GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		bullet.penetration = 17;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		bullet.penetration = 25;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 8;
		bullet.dmgMax = 15;
		bullet.penetration = 20;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.wear = 15;
		bullet.dmgMin = 7;
		bullet.dmgMax = 12;
		bullet.penetration = 22;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 7;
		bullet.dmgMax = 12;
		bullet.penetration = 20;
		bullet.wear = 25;
		bullet.explosive = 0.5F;
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeCausticConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 3;
		bullet.dmgMax = 7;
		bullet.penetration = 18;
		bullet.wear = 25;
		bullet.caustic = 5;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(Potion.poison.id, 10 * 20, 1));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShockConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 8;
		bullet.dmgMax = 10;
		bullet.wear = 25;
		bullet.emp = 2;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(Potion.weakness.id, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeWitherConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		bullet.dmgMin = 6;
		bullet.dmgMax = 10;
		bullet.penetration = 20;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(Potion.wither.id, 10 * 20, 2));
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge, 1, i++);
		
		return bullet;
	}

}
