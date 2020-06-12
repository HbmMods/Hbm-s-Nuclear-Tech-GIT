package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public class Gun12GaugeFactory {
	
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
		
		config.name = "Uboinik Revolving Shotgun";
		config.manufacturer = "Metro Gunsmiths";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.G12_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		config.config.add(BulletConfigSyncingUtil.G12_AM);
		
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
		
		config.name = "???";
		config.manufacturer = "???";
		config.comment.add("but bOB WhY iS TExtURE no woRk");
		config.comment.add("hoW do I cRAFT PleasE HElp");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.G12_NORMAL);
		config.config.add(BulletConfigSyncingUtil.G12_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.G12_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.G12_DU);
		config.config.add(BulletConfigSyncingUtil.G12_AM);
		
		return config;
	}
	
	public static BulletConfiguration get12GaugeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_incendiary;
		bullet.wear = 15;
		bullet.dmgMin = 1;
		bullet.dmgMax = 4;
		bullet.incendiary = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_shrapnel;
		bullet.wear = 15;
		bullet.dmgMin = 2;
		bullet.dmgMax = 6;
		bullet.ricochetAngle = 15;
		bullet.HBRC = 80;
		bullet.LBRC = 95;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_du;
		bullet.wear = 20;
		bullet.dmgMin = 3;
		bullet.dmgMax = 8;
		bullet.doesPenetrate = true;
		bullet.leadChance = 50;
		
		return bullet;
	}
	
	public static BulletConfiguration get12GaugeAMConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBuckshotConfig();
		
		bullet.ammo = ModItems.ammo_12gauge_marauder;
		bullet.wear = 20;
		bullet.dmgMin = 100;
		bullet.dmgMax = 500;
		bullet.leadChance = 50;
		
		bullet.bHurt = new IBulletHurtBehavior() {

			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit) {
				
				if(hit instanceof EntityLivingBase)
					((EntityLivingBase)hit).addPotionEffect(new PotionEffect(HbmPotion.bang.id, 20, 0));
			}
			
		};
		
		return bullet;
	}

}
