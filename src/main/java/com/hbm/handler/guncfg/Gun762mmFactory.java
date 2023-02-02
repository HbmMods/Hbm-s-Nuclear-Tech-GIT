package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo762NATO;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.PotionEffect;

public class Gun762mmFactory {
	
	private static final CasingEjector EJECTOR_RIFLE;
	private static final SpentCasing CASING762NATO;

	static {
		EJECTOR_RIFLE = new CasingEjector().setMotion(-0.35, 0.6, 0).setOffset(-0.35, 0, 0.35).setAngleRange(0.01F, 0.03F);
		CASING762NATO = new SpentCasing(CasingType.BOTTLENECK).setScale(1.7F).setBounceMotion(0.01F, 0.05F).setColor(SpentCasing.COLOR_CASE_BRASS);
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

		config.config.addAll(HbmCollection.threeZeroEight);
		
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
		config.config.addAll(HbmCollection.threeZeroEight);
		
		config.ejector = EJECTOR_RIFLE;

		return config;
	}

	public static BulletConfiguration get762NATOConfig() {
		final BulletConfiguration bullet = Gun556mmFactory.get556Config().clone();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.STOCK));
		bullet.dmgMax *= 2;
		bullet.dmgMin *= 2;
		bullet.velocity *= 2.5;
		bullet.maxAge *= 2;
		bullet.spread /= 2;
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOStock");

		return bullet;
	}

	public static BulletConfiguration get762APConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.AP));
		bullet.dmgMax *= 1.5;
		bullet.dmgMin *= 1.5;
		
		bullet.spentCasing = CASING762NATO.clone().register("762NATOAP");

		return bullet;
	}

	public static BulletConfiguration get762DUConfig() {
		final BulletConfiguration bullet = get762NATOConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_762.stackFromEnum(Ammo762NATO.DU));
		bullet.dmgMax *= 2;
		bullet.dmgMin *= 2;
		
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
		bullet.effects = new ArrayList<PotionEffect>();
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