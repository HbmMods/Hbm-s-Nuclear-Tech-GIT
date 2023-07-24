package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo762NATO;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.RefStrings;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import static com.hbm.handler.GunConfiguration.RELOAD_FULL;

public class Gun762mmFactory {
	
	public static final ResourceLocation scope_bolt = new ResourceLocation(RefStrings.MODID, "textures/misc/scope_bolt.png");
	
	private static final CasingEjector EJECTOR_RIFLE;
	private static final CasingEjector EJECTOR_BOLT;
	private static final SpentCasing CASING762NATO;

	static {
		EJECTOR_RIFLE = new CasingEjector().setMotion(-0.35, 0.6, 0).setOffset(-0.35, 0, 0.35).setAngleRange(0.01F, 0.03F);
		EJECTOR_BOLT = new CasingEjector().setMotion(-0.35, 0.6, 0).setOffset(-0.35, 0, 0.35).setAngleRange(0.01F, 0.03F).setDelay(15);
		CASING762NATO = new SpentCasing(CasingType.BOTTLENECK).setScale(1.7F).setBounceMotion(0.01F, 0.05F).setColor(SpentCasing.COLOR_CASE_BRASS);
	}
	
	public static GunConfiguration getCalamityConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 50;
		config.reloadType = RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.NONE;
		config.durability = 15 * 50 * 10; //15 * capacity * default wear
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.calShoot";
		config.reloadSoundEnd = false;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				);
		
		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("MAG", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, -1, 0, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				);
		
		config.name = "mg3";
		config.manufacturer = EnumGunManufacturer.WGW;
		
		config.config = HbmCollection.r762;
		
		config.ejector = EJECTOR_RIFLE;
		
		return config;
	}
	
	public static GunConfiguration getUACDMRConfig() {
		final GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 30;
		config.firingDuration = 8;
		config.ammoCap = 30;
		config.durability = 30000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.DMRMagInPB3";
		config.firingSound = "hbm:weapon.DMRShootPB3Alt";
		config.reloadSoundEnd = true;

		config.name = "uacDMR";
		config.manufacturer = EnumGunManufacturer.UAC;

		config.config.addAll(HbmCollection.r762);
		
		config.ejector = EJECTOR_RIFLE;

		return config;
	}

	public static GunConfiguration getUACCarbineConfig() {
		final GunConfiguration config = getUACDMRConfig();

		config.rateOfFire = 2;
		config.reloadDuration = 20;
		config.ammoCap = 40;
		config.durability = 40000;
		config.crosshair = Crosshair.SPLIT;
		config.reloadSound = "hbm:weapon.carbineMagInPB3";
		config.firingSound = "hbm:weapon.carbineShootPB3";

		config.name = "uacCarbine";

		return config;
	}

	public static GunConfiguration getUACLMGConfig() {
		final GunConfiguration config = getUACCarbineConfig();

		config.ammoCap = 60;
		config.durability = 50000;
		config.crosshair = Crosshair.BOX;
		config.reloadSound = "hbm:weapon.LMGMagInPB3";
		config.firingSound = "hbm:weapon.LMGShootPB3Alt";

		config.name = "uacLMG";

		return config;
	}

	public static GunConfiguration getM60Config() {
		final GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 2;
		config.durability = 10000;
		config.roundsPerCycle = 1;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.ammoCap = 0;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.L_BOX;
		config.firingSound = "hbm:weapon.LMGShootPB3";

		config.name = "m60";
		config.manufacturer = EnumGunManufacturer.SACO;
		config.comment.add("\"Get some!\"");
		config.comment.add(" ~ Stuart Brown (aka Ahoy)");
		config.config.addAll(HbmCollection.r762);
		
		config.ejector = EJECTOR_RIFLE;

		return config;
	}
	
	public static GunConfiguration getBoltConfig() {

		GunConfiguration config = Gun20GaugeFactory.getShotgunConfig();

		config.ammoCap = 5;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		config.crosshair = Crosshair.CIRCLE;
		
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
		
		config.name = "win20Inox";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;
		
		config.ejector = EJECTOR_BOLT;
		
		config.config = HbmCollection.r762;
		
		return config;
	}
	
	public static GunConfiguration getBoltGreenConfig() {

		GunConfiguration config = Gun20GaugeFactory.getShotgunConfig();

		config.ammoCap = 5;
		config.durability = 2500;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		config.crosshair = Crosshair.CIRCLE;
		
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
		
		config.name = "win20Poly";
		config.manufacturer = EnumGunManufacturer.WINCHESTER;
		
		config.ejector = EJECTOR_BOLT;
		
		config.config = HbmCollection.r762;
		
		return config;
	}
	
	public static GunConfiguration getBoltSaturniteConfig() {
		
		GunConfiguration config = Gun20GaugeFactory.getShotgunConfig();
		
		config.ammoCap = 5;
		config.durability = 4000;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.firingPitch = 0.75F;
		config.hasSights = true;
		config.absoluteFOV = true;
		config.zoomFOV = 0.25F;
		config.scopeTexture = scope_bolt;
		config.crosshair = Crosshair.CIRCLE;
		
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
		
		config.name = "win20Satur";
		config.manufacturer = EnumGunManufacturer.WINCHESTER_BIGMT;
		
		config.ejector = EJECTOR_BOLT;
		
		config.config = HbmCollection.r762;
		
		return config;
	}
	


	public static BulletConfiguration get762NATOConfig() {
		final BulletConfiguration bullet = Gun556mmFactory.get556Config().clone();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.STOCK));

		bullet.dmgMax = 20;
		bullet.dmgMin = 24;
		bullet.velocity *= 2.5;
		bullet.maxAge *= 2;
		bullet.spread /= 2;
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOStock");

		return bullet;
	}

	public static BulletConfiguration get762APConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.AP));
		bullet.doesPenetrate = true;

		bullet.dmgMax = 24;
		bullet.dmgMin = 28;
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOAP");

		return bullet;
	}

	public static BulletConfiguration get762DUConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.DU));
		bullet.doesPenetrate = true;
		bullet.dmgMax = 36;
		bullet.dmgMin = 40;
		bullet.spentCasing = CASING762NATO.clone().register("762NATODU");

		return bullet;
	}

	public static BulletConfiguration get762TracerConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.TRACER));
		bullet.vPFX = "reddust";
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOTrac");

		return bullet;
	}

	public static BulletConfiguration get762WPConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.PHOSPHORUS));
		bullet.setToFire(20 * 5);
		bullet.vPFX = "reddust";
		final PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList<>();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOPhos");

		return bullet;
	}

	public static BulletConfiguration get762BlankConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.BLANK));
		bullet.dmgMax = 0;
		bullet.dmgMin = 0;
		bullet.maxAge = 0;
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOK");

		return bullet;
	}
}