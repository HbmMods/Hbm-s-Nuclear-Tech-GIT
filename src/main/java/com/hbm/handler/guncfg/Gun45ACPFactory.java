package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo45ACP;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun45ACPFactory {
	
	private static final SpentCasing CASING45;
	
	static {
		CASING45 = new SpentCasing(CasingType.STRAIGHT).setBounceMotion(0.01F, 0.05F).setScale(1.25F, 1.25F, 1F).setColor(SpentCasing.COLOR_CASE_BRASS).register("45ACP");
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
		config.durability = 5000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.rifleShoot";
		config.reloadSoundEnd = false;

		config.name = "tommy";
		config.manufacturer = EnumGunManufacturer.AUTO_ORDINANCE;

		config.config = new ArrayList<Integer>();
		config.config.addAll(HbmCollection.acp45);

		return config;
	}

	public static GunConfiguration getUACPistolConfig() {
		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 8;
		config.ammoCap = 21;
		config.durability = 10000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.absoluteFOV = true;
		config.zoomFOV = 0.5F;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.deagleShoot";
		config.reloadSoundEnd = true;

		config.name = "uacPistol";
		config.manufacturer = EnumGunManufacturer.UAC;

		config.config.addAll(HbmCollection.acp45);

		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SLIDE", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 10))// Wait for hammer
						.addKeyframe(new BusAnimationKeyframe(0, 0, -3.5, 40))// Slide back
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 40)))// Return
				.addBus("HAMMER", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(15, 0, 0, 10))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 40))));

		return config;
	}

	public static GunConfiguration getUACPistolBurstConfig() {
		GunConfiguration config = getUACPistolConfig();
		config.rateOfFire = 5;
		config.roundsPerBurst = 3;
		config.firingDuration = 2;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_BURST;

		return config;
	}

	public static GunConfiguration getUACSMGConfig() {
		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 10;
		config.firingDuration = 4;
		config.ammoCap = 40;
		config.durability = 40000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.SMGMagInPB3";
		config.firingSound = "hbm:weapon.SMGFirePB3";
		config.firingPitch = 1.15F;
		config.reloadSoundEnd = true;

		config.name = "uacSMG";
		config.manufacturer = EnumGunManufacturer.UAC;

		config.config.addAll(HbmCollection.acp45);

		return config;
	}
	
	static float inaccuracy = 5;
	public static BulletConfiguration get45AutoConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_45.stackFromEnum(Ammo45ACP.STOCK));
		bullet.spread *= inaccuracy;
		bullet.dmgMax = 12;
		bullet.dmgMin = 16;
		
		bullet.spentCasing = CASING45;

		return bullet;
	}

	public static BulletConfiguration get45AutoAPConfig() {
		BulletConfiguration bullet = get45AutoConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_45.stackFromEnum(Ammo45ACP.AP));
		bullet.dmgMax = 18;
		bullet.dmgMin = 26;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		bullet.spentCasing = CASING45;

		return bullet;
	}

	public static BulletConfiguration get45AutoDUConfig() {
		BulletConfiguration bullet = get45AutoConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_45.stackFromEnum(Ammo45ACP.DU));
		bullet.dmgMax = 30;
		bullet.dmgMin = 44;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		bullet.spentCasing = CASING45;

		return bullet;
	}
}
