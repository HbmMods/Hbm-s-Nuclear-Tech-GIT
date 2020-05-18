package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class GunEnergyFactory {
	
	public static GunConfiguration getEMPConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasReloadAnim = false;
		config.hasFiringAnim = false;
		config.hasSpinup = false;
		config.hasSpindown = false;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.firingSound = "hbm:weapon.teslaShoot";
		
		config.name = "EMP Orb Projector";
		config.manufacturer = "MWT Prototype Labs";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_EMP);
		
		return config;
	}
	
	public static BulletConfiguration getOrbusConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_emp_ammo;
		
		bullet.velocity = 1F;
		bullet.spread = 0.0F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 10;
		bullet.dmgMax = 12;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_ORB;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		bullet.emp = 10;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(Potion.weakness.id, 10 * 20, 4));
		
		return bullet;
	}
}
