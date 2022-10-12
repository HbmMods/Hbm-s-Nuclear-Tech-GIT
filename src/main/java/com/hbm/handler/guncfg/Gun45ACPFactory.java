package com.hbm.handler.guncfg;

import java.util.ArrayList;

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

public class Gun45ACPFactory
{
	
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
		config.config.addAll(HbmCollection.fourtyFiveACP);
		
		config.advLore.add("The Thompson submachine gun (also known as the \"Tommy Gun\", \"Chicago Typewriter\",");
		config.advLore.add("\"Chicago Piano\", or \"Trench Broom\") is a blowback-operated, air-cooled, magazine-fed");
		config.advLore.add("selective-fire submachine gun, invented by the United States Army Brigadier general John T.");
		config.advLore.add("Thompson in 1918. It was originally designed to break the stalemate of trench warfare of World");
		config.advLore.add("War I, but was not finished until after the war ended.");
		
		return config;
	}
	
	public static GunConfiguration getUACPistolConfig()
	{
		final GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 4;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 8;
		config.ammoCap = 16;
		config.durability = 10000;
		config.reloadType = 1;
		config.allowsInfinity = true;
		config.hasSights = true;
		config.crosshair = Crosshair.CROSS;
		config.reloadSound = "hbm:weapon.pistolReloadPB3";
		config.firingSound = "hbm:weapon.pistolFirePB3";
		config.reloadSoundEnd = true;
		
		config.name = "uacPistol";
		config.manufacturer = EnumGunManufacturer.UAC;
		
		config.config.addAll(HbmCollection.fourtyFiveACP);
		
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
	
	public static GunConfiguration getUACSMGConfig()
	{
		final GunConfiguration config = new GunConfiguration();
		
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
		
		config.config.addAll(HbmCollection.fourtyFiveACP);
		
		return config;
	}
	public static BulletConfiguration get45AutoConfig()
	{
		final BulletConfiguration bullet = Gun9mmFactory.get9mmConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_45, 1, 0);
		bullet.dmgMax = 30;
		bullet.dmgMin = 27;
		bullet.penetration *= 1.25;
		
		return bullet;
	}
	
	public static BulletConfiguration get45AutoAPConfig()
	{
		final BulletConfiguration bullet = get45AutoConfig().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_45, 1, 1);
		bullet.dmgMax *= 1.5;
		bullet.dmgMin *= 1.5;
		bullet.penetration *= 1.5;
		
		return bullet;
	}
	
	public static BulletConfiguration get45AutoDUConfig()
	{
		final BulletConfiguration bullet = get45AutoAPConfig().clone();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_45, 1, 2);
		bullet.dmgMax *= 1.5;
		bullet.dmgMin *= 1.5;
		bullet.penetration *= 1.5;
		
		return bullet;
	}
}