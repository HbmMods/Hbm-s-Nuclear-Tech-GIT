package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.PotionEffect;

public class Gun357MagnumFactory {
	
	public static GunConfiguration getBaseConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 6;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.revolverShoot";
		config.reloadSoundEnd = false;
		
		return config;
	}
	
	public static GunConfiguration getRevolverIronConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2000;
		
		config.name = "ffiV";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 3500;
		
		config.name = "ffiVInox";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverSaturniteConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 3500;
		
		config.name = "ffivSatur";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SATURNITE_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverLeadConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2000;
		
		config.name = "ffiVLead";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.LEAD_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverGoldConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "ffivBling";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.GOLD_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverCursedConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.rateOfFire = 7;
		config.ammoCap = 17;
		config.durability = 5000;
		config.firingSound = "hbm:weapon.heavyShoot";
		
		config.name = "revolverCursed";
		config.manufacturer = EnumGunManufacturer.BAE;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.CURSED_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverSchrabidiumConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 7500;
		config.firingSound = "hbm:weapon.schrabidiumShoot";
		
		config.name = "ffiVUltra";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SCHRABIDIUM_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverNightmareConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.firingSound = "hbm:weapon.schrabidiumShoot";
		
		config.name = "ffiVN2";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NIGHT_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverNightmare2Config() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.firingSound = "hbm:weapon.schrabidiumShoot";
		config.crosshair = Crosshair.NONE;
		
		config.name = "ffiVN2";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NIGHT2_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverBioConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 100000;
		config.firingSound = "hbm:weapon.deagleShoot";
		config.reloadDuration = 53;
		config.crosshair = Crosshair.CIRCLE;
		
		config.name = "RI No. 2 Mark 1";
		config.manufacturer = EnumGunManufacturer.RYAN;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.IRON_HS);
		config.config.add(BulletConfigSyncingUtil.STEEL_HS);
		config.config.add(BulletConfigSyncingUtil.GOLD_HS);
		config.config.add(BulletConfigSyncingUtil.DESH_HS);
		
		return config;
	}
	
	public static GunConfiguration getColtPythonConfig()
	{
		GunConfiguration config = getBaseConfig().clone();
		
		config.durability = 8000;
		config.name = "cPython";
		config.manufacturer = EnumGunManufacturer.COLT;
		
		config.config.add(BulletConfigSyncingUtil.LEAD_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_HS);
		
		return config;
	}
	
	    ////    //  //  //      //      //////  //////  //////
	   //  //  //  //  //      //      //        //    //
	  ////    //  //  //      //      ////      //    //////
	 //  //  //  //  //      //      //        //        //
	////    //////  //////  //////  //////    //    //////
	
	public static BulletConfiguration getRevIronConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 0);
		bullet.dmgMin = 8;
		bullet.dmgMax = 10;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevLeadConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 1);
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		bullet.penetration = 17;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNuclearConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 2);
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(HbmPotion.radiation.id, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration getRevGoldConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 3);
		bullet.dmgMin = 25;
		bullet.dmgMax = 28;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevDeshConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 4);
		bullet.dmgMin = 30;
		bullet.dmgMax = 33;
		bullet.penetration = 19;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevSchrabidiumConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 5);
		bullet.dmgMin = 10000;
		bullet.dmgMax = 100000;
		bullet.penetration = Integer.MAX_VALUE;
		bullet.dmgBypass = true;
		bullet.instakill = true;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevCursedConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 6);
		bullet.dmgMin = 18;
		bullet.dmgMax = 25;
		bullet.penetration = 17;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNightmare1Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 7);
		bullet.dmgMin = 1;
		bullet.dmgMax = 100;
		bullet.penetration = 18;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNightmare2Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357, 1, 8);
		bullet.spread *= 10;
		bullet.bulletsMin = 4;
		bullet.bulletsMax = 6;
		bullet.dmgMin = 25;
		bullet.dmgMax = 100;
		bullet.penetration = 50;
		bullet.doesRicochet = false;
		bullet.destroysBlocks = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_NIGHTMARE;
		
		bullet.damageType = ModDamageSource.s_laser;
		
		return bullet;
	}

}