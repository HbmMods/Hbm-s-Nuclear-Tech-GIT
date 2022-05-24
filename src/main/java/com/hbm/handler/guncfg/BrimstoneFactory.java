package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityLaser;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BrimstoneFactory {
	
	public static GunConfiguration getBrimConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_RAD;
		config.durability = 10000;
		config.firingSound = "hbm:weapon.tauShoot";
		
		config.name = "Brimstone";
		config.manufacturer = "----- Industries";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.BRIMSTONE_AMMO);
		
		return config;
	}
	public static BulletConfiguration getBrimstoneConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.brimstoneConfig();

		bullet.ammo = ModItems.turret_tau_ammo;
		bullet.dmgMin = 6;
		bullet.dmgMax = 9;
		bullet.LBRC = 80;
		bullet.HBRC = 5;
		
		return bullet;
	}
}