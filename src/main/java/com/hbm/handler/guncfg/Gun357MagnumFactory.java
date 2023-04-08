package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;

public class Gun357MagnumFactory {
	
	private static final CasingEjector EJECTOR_REVOLVER;
	private static final SpentCasing CASING357;
	private static final SpentCasing CASINGNM;
	
	static {
		EJECTOR_REVOLVER = new CasingEjector().setMotion(Vec3.createVectorHelper(0, 0, -0.03)).setOffset(Vec3.createVectorHelper(0, -0.15, 0)).setAngleRange(0.01F, 0.05F).setAfterReload().setAmount(6);
		CASING357 = new SpentCasing(CasingType.STRAIGHT).setBounceMotion(0.01F, 0.05F);
		CASINGNM = new SpentCasing(CasingType.SHOTGUN).setScale(1.25F).setBounceMotion(0.01F, 0.05F).setColor(0xC7AB1C, 0x6D63A6).register("357N2");
	}
	
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
		
		config.ejector = EJECTOR_REVOLVER;
		
		return config;
	}
	
	public static GunConfiguration getRevolverConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 3500;
		
		config.name = "ffiVInox";
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.LEAD_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverSaturniteConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4500;
		config.ammoCap = 7;
		config.name = "ffivSatur";
		config.reloadDuration = 4;
		config.rateOfFire = 5;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.manufacturer = EnumGunManufacturer.FLIMFLAM;
		config.comment.add("Don't ask why it now has an extra cartridge, just enjoy");
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SATURNITE_REVOLVER);
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
		config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.LEAD_REVOLVER);
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
		config.config.add(BulletConfigSyncingUtil.GOLD_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.STEEL_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.IRON_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.LEAD_REVOLVER);
		config.config.add(BulletConfigSyncingUtil.DESH_REVOLVER);
		
		return config;
	}
	
	public static GunConfiguration getRevolverNightmareConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 4000;
		config.firingSound = "hbm:weapon.schrabidiumShoot";
		
		config.name = "ffiVN1";
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
		
		config.name = "bio";
		config.manufacturer = EnumGunManufacturer.RYAN;

		config.config.add(BulletConfigSyncingUtil.STEEL_HS);
		config.config.add(BulletConfigSyncingUtil.GOLD_HS);
		config.config.add(BulletConfigSyncingUtil.IRON_HS);
		config.config.add(BulletConfigSyncingUtil.LEAD_HS);
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.IRON));
		bullet.dmgMin = 8;
		bullet.dmgMax = 10;
		
		bullet.spentCasing = CASING357.clone().register("357Iron").setColor(0xA8A8A8);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevLeadConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.LEAD));
		bullet.dmgMin = 18;
		bullet.dmgMax = 22;
		
		bullet.spentCasing = CASING357.clone().register("357Lead").setColor(0x646470);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNuclearConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.NUCLEAR));
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(HbmPotion.radiation.id, 10 * 20, 4));
		
		bullet.spentCasing = CASING357.clone().register("357Nuc").setColor(0xFEFEFE);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevGoldConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.GOLD));
		bullet.dmgMin = 25;
		bullet.dmgMax = 28;
		
		bullet.spentCasing = CASING357.clone().register("357Gold").setColor(0xF9FF3E);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevDeshConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.DESH));
		bullet.dmgMin = 30;
		bullet.dmgMax = 33;
		
		bullet.spentCasing = CASING357.clone().register("357Desh").setColor(0xF22929);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevSchrabidiumConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.SCHRABIDIUM));
		bullet.dmgMin = 10000;
		bullet.dmgMax = 100000;
		bullet.instakill = true;
		
		bullet.spentCasing = CASING357.clone().register("357Schrab").setColor(0x32FFFF);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevCursedConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.STEEL));
		bullet.dmgMin = 18;
		bullet.dmgMax = 25;
		
		bullet.spentCasing = CASING357.clone().register("357Cursed").setColor(0x565656);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNightmare1Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.NIGHTMARE1));
		bullet.dmgMin = 1;
		bullet.dmgMax = 100;
		
		bullet.spentCasing = CASING357.clone().register("357N1").setColor(0x3A3A3A);
		
		return bullet;
	}
	
	public static BulletConfiguration getRevNightmare2Config() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_357.stackFromEnum(Ammo357Magnum.NIGHTMARE2));
		bullet.spread *= 10;
		bullet.bulletsMin = 4;
		bullet.bulletsMax = 6;
		bullet.dmgMin = 25;
		bullet.dmgMax = 100;
		bullet.doesRicochet = false;
		bullet.destroysBlocks = true;
		bullet.style = bullet.STYLE_BOLT;
		bullet.trail = bullet.BOLT_NIGHTMARE;
		
		bullet.damageType = ModDamageSource.s_laser;
		
		bullet.spentCasing = CASINGNM;
		
		return bullet;
	}

}
