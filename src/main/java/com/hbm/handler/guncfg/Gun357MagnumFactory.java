package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
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
	
	    ////    //  //  //      //      //////  //////  //////
	   //  //  //  //  //      //      //        //    //
	  ////    //  //  //      //      ////      //    //////
	 //  //  //  //  //      //      //        //        //
	////    //////  //////  //////  //////    //    //////
	
	public static BulletConfiguration getRevIronConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_iron_ammo;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevSteelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_ammo;
		bullet.dmgMin = 3;
		bullet.dmgMax = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevLeadConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_lead_ammo;
		bullet.dmgMin = 2;
		bullet.dmgMax = 3;
		
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(HbmPotion.radiation.id, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration getRevGoldConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_gold_ammo;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevDeshConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_357_desh;
		bullet.dmgMin = 15;
		bullet.dmgMax = 17;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevSchrabidiumConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_schrabidium_ammo;
		bullet.dmgMin = 10000;
		bullet.dmgMax = 100000;
		bullet.instakill = true;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevCursedConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_cursed_ammo;
		bullet.dmgMin = 12;
		bullet.dmgMax = 15;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNightmareConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_nightmare_ammo;
		bullet.dmgMin = 1;
		bullet.dmgMax = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNightmare2Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.gun_revolver_nightmare2_ammo;
		bullet.spread *= 10;
		bullet.bulletsMin = 4;
		bullet.bulletsMax = 6;
		bullet.dmgMin = 50;
		bullet.dmgMax = 150;
		bullet.destroysBlocks = true;
		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_NIGHTMARE;
		
		return bullet;
	}

}
