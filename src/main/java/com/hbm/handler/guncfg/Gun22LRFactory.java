package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun22LRFactory {
	
	public static GunConfiguration getUziConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 32;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CROSS;
		config.durability = 3000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.uziShoot";
		config.reloadSoundEnd = false;
		
		config.name = "uzi";
		config.manufacturer = EnumGunManufacturer.IMI;
		config.comment.add("Mom, where are my mittens?");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.LR22_NORMAL);
		config.config.add(BulletConfigSyncingUtil.LR22_AP);
		config.config.add(BulletConfigSyncingUtil.CHL_LR22);
		
		config.advLore.add("The Uzi ( /ˈuːzi/; Hebrew: עוזי, romanized: Ūżi; officially cased as UZI) is a family of");
		config.advLore.add("Israeli open-bolt, blowback-operated submachine guns and machine pistols first designed by Major");
		config.advLore.add("Uziel \"Uzi\" Gal in the late 1940s, shortly after the establishment of the State of Israel. It");
		config.advLore.add("is one of the first weapons to incorporate a telescoping bolt design, which allows the magazine to");
		config.advLore.add("be housed in the pistol grip for a shorter weapon.");
		
		config.advFuncLore.add("The Uzi uses an open-bolt, blowback-operated design, quite similar to the Jaroslav Holeček-designed");
		config.advFuncLore.add("Czech ZK 476 (prototype only) and the production Sa 23 - 26 series of submachine guns. The open bolt design exposes");
		config.advFuncLore.add("the breech end of the barrel, and improves cooling during periods of continuous fire. However, it means that since");
		config.advFuncLore.add("the bolt is held to the rear when cocked, the receiver is more susceptible to contamination from sand and dirt. It");
		config.advFuncLore.add("uses a telescoping bolt design, in which the bolt wraps around the breech end of the barrel. This allows the barrel");
		config.advFuncLore.add("to be moved far back into the receiver and the magazine to be housed in the pistol grip, allowing for a heavier,");
		config.advFuncLore.add("slower-firing bolt in a shorter, better-balanced weapon.");
		
		return config;
	}
	
	public static GunConfiguration getSaturniteConfig() {
		
		GunConfiguration config = getUziConfig();
		
		config.durability = 4500;
		
		config.name = "uziSatur";
		config.manufacturer = EnumGunManufacturer.IMI_BIGMT;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.LR22_NORMAL_FIRE);
		config.config.add(BulletConfigSyncingUtil.LR22_AP_FIRE);
		config.config.add(BulletConfigSyncingUtil.CHL_LR22_FIRE);
		
		return config;
	}

	static final float inaccuracy = 5;
	public static BulletConfiguration get22LRConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_22lr, 1, 0);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.penetration = 6;
		
		return bullet;
	}
	
	public static BulletConfiguration get22LRAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_22lr, 1, 1);
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 12;
		bullet.dmgMax = 16;
		bullet.penetration = 12;
		bullet.leadChance = 10;
		bullet.wear = 15;
		return bullet;
	}

}