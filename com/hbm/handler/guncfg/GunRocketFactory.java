package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletRicochetBehavior;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class GunRocketFactory {
	
	public static GunConfiguration getGustavConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 30;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.rpgShoot";
		config.reloadSound = GunConfiguration.RSOUND_LAUNCHER;
		config.reloadSoundEnd = false;
		
		config.name = "Carl Gustav Recoilless Rifle M1";
		config.manufacturer = "Saab Bofors Dynamics";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_NORMAL);
		config.config.add(BulletConfigSyncingUtil.ROCKET_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		config.config.add(BulletConfigSyncingUtil.ROCKET_EMP);
		config.config.add(BulletConfigSyncingUtil.ROCKET_GLARE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_TOXIC);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		config.config.add(BulletConfigSyncingUtil.ROCKET_NUKE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		config.durability = 140;
		
		return config;
	}
	
	public static GunConfiguration getKarlConfig() {
		
		GunConfiguration config = getGustavConfig();
		
		config.reloadDuration = 20;
		
		config.name = "M1 Karl-Gerät";
		config.manufacturer = "???";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_EMP);
		config.config.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		config.config.add(BulletConfigSyncingUtil.ROCKET_TOXIC);
		config.config.add(BulletConfigSyncingUtil.ROCKET_NUKE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		config.durability = 500;
		
		return config;
	}
	
	public static GunConfiguration getPanzConfig() {
		
		GunConfiguration config = getGustavConfig();
		
		config.reloadDuration = 25;
		config.hasSights = true;
		
		config.name = "Raketenpanzerbüchse 54";
		config.manufacturer = "Enzinger Union";
		
		config.durability = 260;
		
		return config;
	}
	
	public static BulletConfiguration getRocketConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.trail = 0;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketHEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_he;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 6.5F;
		bullet.trail = 1;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketIncendiaryConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_incendiary;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 15;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 2;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketEMPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_emp;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 2.5F;
		bullet.emp = 10;
		bullet.trail = 4;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_sleek;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 10F;
		bullet.trail = 6;
		bullet.gravity = 0;
		bullet.jolt = 6.5D;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketShrapnelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_shrapnel;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.explosive = 4F;
		bullet.shrapnel = 25;
		bullet.trail = 3;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketGlareConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_glare;
		bullet.velocity = 5.0F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 20;
		bullet.explosive = 4F;
		bullet.incendiary = 5;
		bullet.trail = 5;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_nuclear;
		bullet.velocity = 1.5F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 35;
		bullet.explosive = 0;
		bullet.incendiary = 0;
		bullet.trail = 7;
		bullet.nuke = 25;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketChlorineConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_toxic;
		bullet.velocity = 1.5F;
		bullet.dmgMin = 10;
		bullet.dmgMax = 15;
		bullet.wear = 10;
		bullet.explosive = 0;
		bullet.chlorine = 50;
		bullet.trail = 7;
		
		return bullet;
	}
	
	public static BulletConfiguration getRocketRPCConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();
		
		bullet.ammo = ModItems.ammo_rocket_rpc;
		bullet.velocity = 3.0F;
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.wear = 15;
		bullet.explosive = 0;
		bullet.incendiary = 0;
		bullet.trail = 8;
		bullet.gravity = 0.000D;
		bullet.ricochetAngle = 90;
		bullet.LBRC = 100;
		bullet.doesPenetrate = true;
		
		bullet.bRicochet = new IBulletRicochetBehavior() {
			
			public void behaveBlockRicochet(EntityBulletBase bullet, int bX, int bY, int bZ) {
				World worldObj = bullet.worldObj;
				if(!worldObj.isRemote && 
						(worldObj.getBlock(bX, bY, bZ).getMaterial() == Material.wood ||
						worldObj.getBlock(bX, bY, bZ).getMaterial() == Material.plants ||
						worldObj.getBlock(bX, bY, bZ).getMaterial() == Material.glass ||
						worldObj.getBlock(bX, bY, bZ).getMaterial() == Material.leaves))
					worldObj.func_147480_a(bX, bY, bZ, false);}
			
		};
		
		return bullet;
	}

}
