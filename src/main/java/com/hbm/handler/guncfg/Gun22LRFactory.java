package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

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
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_22lr;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;
		
		bullet.bHurt = new IBulletHurtBehavior()
		{
			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit)
			{
				final double dmgMod = MathHelper.clamp_double(bullet.getDistanceSqToEntity(bullet.shooter), 0, 2.5);
				bullet.overrideDamage = (float) dmgMod;
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration get22LRAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_22lr_ap;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 6;
		bullet.dmgMax = 8;
		bullet.leadChance = 10;
		bullet.wear = 15;
		bullet.bHurt = new IBulletHurtBehavior()
		{
			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit)
			{
				final double dmgMod = MathHelper.clamp_double(bullet.getDistanceSqToEntity(bullet.shooter), 0, 2);
				bullet.overrideDamage = (float) dmgMod;
			}
		};
		return bullet;
	}

}
