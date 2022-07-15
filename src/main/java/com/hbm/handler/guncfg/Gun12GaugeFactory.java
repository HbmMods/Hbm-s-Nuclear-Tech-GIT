package com.hbm.handler.guncfg;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class Gun12GaugeFactory {
	
	public static GunConfiguration getSpas12Config() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 25;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 5;
		config.ammoCap = 8;
		config.durability = 2500;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.shotgunPump";
		
		config.name = "spas12";
		config.manufacturer = EnumGunManufacturer.BLACK_MESA;
		config.comment.add("\"Here, I have a more suitable gun for you. You'll need it - Catch!\"");
		config.comment.add("Alt-fire with Mouse 2 (Right-click) to fire 2 shells at once");
		
		config.config = HbmCollection.twelveGauge;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SPAS_RECOIL_TRANSLATE", new BusAnimationSequence()
					.addKeyframe(new BusAnimationKeyframe(0, 0, -2, 100))
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
					)
				.addBus("SPAS_RECOIL_ROT", new BusAnimationSequence()
					.addKeyframe(new BusAnimationKeyframe(-1, 0, 1, 100))
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
					)
				.addBus("SPAS_PUMP", new BusAnimationSequence()
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 450))
					.addKeyframe(new BusAnimationKeyframe(0, 0, -1.8, 200))
					.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
					)
				);
		
		return config;
	}
	
	public static GunConfiguration getSpas12AltConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 35;
		config.roundsPerCycle = 2;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.firingDuration = 10;
		config.ammoCap = 8;
		config.reloadSound = GunConfiguration.RSOUND_SHOTGUN;
		config.firingSound = "hbm:weapon.shotgunPump";
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		
		
		config.config = HbmCollection.twelveGauge;

		return config;
	}
	
	public static GunConfiguration getUboinikConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 8;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.shotgunShoot";
		
		config.name = "uboinik";
		config.manufacturer = EnumGunManufacturer.METRO;
		
		config.config = HbmCollection.twelveGauge;
		
		return config;
	}
	
	public static GunConfiguration getShottyConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 2;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.durability = 3000;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.shottyShoot";
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("SHOTTY_RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0.5, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						)
				.addBus("SHOTTY_BREAK", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100))	//do nothing for 100ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 200))	//open
						.addKeyframe(new BusAnimationKeyframe(0, 0, 60, 500))	//do nothing for 500ms
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))	//close
						)
				.addBus("SHOTTY_EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))	//do nothing for 300ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 700))	//fling!
						)
				.addBus("SHOTTY_INSERT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))	//do nothing for 300ms
						.addKeyframe(new BusAnimationKeyframe(1, 0, 1, 0))		//reposition
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 350))	//come in from the side
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))	//push
						)
				);
		
		config.name = "supershotty";
		config.manufacturer = EnumGunManufacturer.UAC;
		config.comment.add("God-damned ARCH-VILES!");
		
		config.config = HbmCollection.twelveGauge;
		
		return config;
	}
	
	public static GunConfiguration getBenelliConfig()
	{
		GunConfiguration config = getUboinikConfig().clone();
		config.gunMode = 0;
		config.firingMode = 1;
		config.rateOfFire = 5;
		config.ammoCap = 8;
		config.reloadDuration = 8;
		config.crosshair = Crosshair.CIRCLE;
		config.hasSights = true;
		config.durability = 250000;
		config.allowsInfinity = true;
		config.firingSound = "hbm:weapon.autoshotgunFirePB3";
		config.reloadSound = "hbm:weapon.shotgunReloadPB3";
		config.reloadType = 2;
		config.reloadSoundEnd = true;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(6.25, 0.25, 2.5, 55))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 55))
						)
				.addBus("EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(25, 0, 0, 100))
						)
				);
		
		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("RELOAD", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(60, 0, -10, 400))
						.addKeyframe(new BusAnimationKeyframe(60, 125, -10, 200))
						.addKeyframe(new BusAnimationKeyframe(60, 125, -10, 300))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))
						)
				.addBus("PUMP", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 900))
						.addKeyframe(new BusAnimationKeyframe(10, 0, 0, 200))
						.addKeyframe(new BusAnimationKeyframe())
						)
				);
		
		config.name = "benelli";
		config.manufacturer = EnumGunManufacturer.BENELLI;
		config.comment.add("Eat your heart out SPAS-12");
		config.config = HbmCollection.twelveGauge;
		
		config.advLore.add("The Benelli M4 is a semi-automatic shotgun produced by Italian firearm manufacturer Benelli");
		config.advLore.add("Armi SpA, and the last of the \"Benelli Super 90\" series of semi-automatic shotguns. The M4");
		config.advLore.add("uses a proprietary action design called the \"auto-regulating gas-operated\" (ARGO) system,");
		config.advLore.add("which was created specifically for the weapon. The weapon was designed in 1998, and was adopted");
		config.advLore.add("by the armed forces of Italy, the United States, United Kingdom, among others, and has been used");
		config.advLore.add("in a variety of conflicts, leading up to the present day.");
		
		config.advFuncLore.add("The M4 was the first gas-operated shotgun produced by Benelli. Its function is designed around an");
		config.advFuncLore.add("entirely new design called the \"auto-regulating gas-operated\" (ARGO) system. The short-stroke design");
		config.advFuncLore.add("uses two self-cleaning stainless steel pistons located just ahead of the chamber to function opposite");
		config.advFuncLore.add("a rotating bolt, thereby eliminating the need for the complex mechanisms found on other gas-actuated");
		config.advFuncLore.add("automatics. The ARGO incorporates only four parts: two symmetrical fore-end shrouds containing two small");
		config.advFuncLore.add("steel pistons that push directly against the bolt.");
		config.advFuncLore.add("Additionally, the weapon is self-regulating for use with shotshells of varying lengths and power levels.");
		config.advFuncLore.add("It can fire 2.75 in (70 mm) and 3 in (76 mm) shells of differing propellant loads without any operator");
		config.advFuncLore.add("adjustments and in any combination. Low-power rounds, such as less-lethal baton rounds, must be cycled");
		config.advFuncLore.add("manually. The sights are military-style ghost ring and are adjustable in the field using only the rim");
		config.advFuncLore.add("of a shell. The MIL-STD-1913 Picatinny rail on top of the receiver allows use of both conventional optical");
		config.advFuncLore.add("sights and night-vision devices, while retaining use of the original iron sights.");
		config.advFuncLore.add("The modular basis of the shotgun means many of its features can be reconfigured as needed. It allows a");
		config.advFuncLore.add("user to quickly exchange the various assembly groups (barrel, buttstock, forend, etc.) without the use");
		config.advFuncLore.add("of additional tools.");
		
		return config;
	}
	
	public static GunConfiguration getBenelliModConfig()
	{
		GunConfiguration config = getBenelliConfig();
		
		config.reloadType = 1;
		config.ammoCap = 24;
		config.reloadDuration = 20;
		config.reloadSound = "hbm:weapon.shotgunDrumPB3";
		config.reloadSoundEnd = true;
		config.name += "Drum";
		
		return config;
	}
	
	public static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge, 1, 0);
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		bullet.penetration = 17;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = get12GaugeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge, 1, 1);
		bullet.wear = 15;
		bullet.dmgMin = 5;
		bullet.dmgMax = 7;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = get12GaugeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge, 1, 2);
		bullet.wear = 15;
		bullet.dmgMin = 10;
		bullet.dmgMax = 17;
		bullet.penetration = 20;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge, 1, 3);
		bullet.wear = 20;
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		bullet.penetration = 30;
		bullet.doesPenetrate = true;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeAMConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge, 1, 4);
		bullet.wear = 20;
		bullet.dmgMin = 100;
		bullet.dmgMax = 500;
		bullet.penetration = Integer.MAX_VALUE;
		bullet.leadChance = 50;
		
		bullet.bHurt = (projectile, hit) -> {

				if(hit instanceof EntityLivingBase)
					((EntityLivingBase)hit).addPotionEffect(new PotionEffect(HbmPotion.bang.id, 20, 0));
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardAirstrikeConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_12gauge, 1, 5);
		
		return bullet;
	}

}
