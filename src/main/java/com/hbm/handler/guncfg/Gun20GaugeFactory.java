package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo20Gauge;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;

public class Gun20GaugeFactory {
	
	private static final CasingEjector EJECTOR_SHOTGUN;
	private static final SpentCasing CASING20GAUGE;

	static {
		EJECTOR_SHOTGUN = new CasingEjector().setMotion(Vec3.createVectorHelper(-0.4, 0.95, 0)).setOffset(Vec3.createVectorHelper(-0.55, 0, 0.5)).setAngleRange(0.01F, 0.05F);
		CASING20GAUGE = new SpentCasing(CasingType.SHOTGUN).setScale(1.25F).setBounceMotion(0.01F, 0.05F).setupSmoke(0.25F, 0.5D, 60, 20);
	}
	
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
		
		config.config = HbmCollection.g20;
		
		config.ejector = EJECTOR_SHOTGUN;
		
		return config;
	}
	
	public static GunConfiguration getMareConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.75F;
		config.hasSights = true;
		config.zoomFOV = 0.75F;
		
		config.name = "win1887";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;

		config.config = HbmCollection.g20;
		
		return config;
	}
	
	public static GunConfiguration getMareDarkConfig() {
		
		GunConfiguration config = getShotgunConfig();
		
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShootAlt";
		config.firingPitch = 0.75F;
		config.hasSights = true;
		config.zoomFOV = 0.75F;
		
		config.name = "win1887Inox";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;

		config.config = HbmCollection.g20;
		
		return config;
	}
	
	public static BulletConfiguration get20GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.STOCK));
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaStock").setColor(0xB52B2B, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeSlugConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.SLUG));
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		bullet.wear = 7;
		bullet.style = BulletConfiguration.STYLE_NORMAL;
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaSlug").setColor(0x2A2A2A, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}

	public static BulletConfiguration get20GaugeFlechetteConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.FLECHETTE));
		bullet.dmgMin = 8;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.style = BulletConfiguration.STYLE_FLECHETTE;
		bullet.HBRC = 2;
		bullet.LBRC = 95;
		BulletConfigFactory.makeFlechette(bullet);
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaFlech").setColor(0x2847FF, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.INCENDIARY));
		bullet.dmgMin = 3;
		bullet.dmgMax = 6;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaInc").setColor(0xFF6329, SpentCasing.COLOR_CASE_BRASS).setupSmoke(1F, 0.5D, 60, 40);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.SHRAPNEL));
		bullet.wear = 15;
		bullet.dmgMin = 7;
		bullet.dmgMax = 12;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaShrap").setColor(0xF0E800, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.EXPLOSIVE));
		bullet.dmgMin = 7;
		bullet.dmgMax = 12;
		bullet.wear = 25;
		bullet.explosive = 0.5F;
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaExp").setColor(0xF0E800, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeCausticConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.CAUSTIC));
		bullet.dmgMin = 3;
		bullet.dmgMax = 7;
		bullet.wear = 25;
		bullet.caustic = 5;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.poison.id, 10 * 20, 1));
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaCaus").setColor(0x64E800, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeShockConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.SHOCK));
		bullet.dmgMin = 4;
		bullet.dmgMax = 8;
		bullet.wear = 25;
		bullet.emp = 2;
		bullet.doesRicochet = false;
		bullet.HBRC = 0;
		bullet.LBRC = 0;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(Potion.weakness.id, 10 * 20, 4));
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaShock").setColor(0x00EFEF, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeWitherConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.WITHER));
		bullet.dmgMin = 4;
		bullet.dmgMax = 8;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.wither.id, 10 * 20, 2));
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaWith").setColor(0x391717, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}
	
	public static BulletConfiguration get20GaugeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_20gauge.stackFromEnum(Ammo20Gauge.SLEEK));
		
		bullet.spentCasing = CASING20GAUGE.clone().register("20GaIF").setColor(0x2A2A2A, SpentCasing.COLOR_CASE_BRASS);
		
		return bullet;
	}

}
