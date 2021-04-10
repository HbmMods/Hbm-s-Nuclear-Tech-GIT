package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHurtBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunDart;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class GunDartFactory {

	public static GunConfiguration getDarterConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasSights = false;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CROSS;
		config.durability = 1000;
		config.reloadSound = GunConfiguration.RSOUND_GRENADE;
		config.firingSound = "hbm:weapon.dartShoot";
		config.reloadSoundEnd = false;
		config.showAmmo = true;
		
		config.name = "Needle Gun";
		config.manufacturer = "-";
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.NEEDLE_GPS);
		
		return config;
	}

	public static GunConfiguration getMymyConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.hasSights = false;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.NONE;
		config.durability = 1000;
		config.reloadSound = GunConfiguration.RSOUND_GRENADE;
		config.firingSound = "hbm:weapon.dartShoot";
		config.reloadSoundEnd = false;
		config.showAmmo = true;
		
		config.name = "NERF blaster of unknown design";
		config.manufacturer = "Hasbro";
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.DART_NORMAL);
		
		return config;
	}

	public static BulletConfiguration getGPSConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_dart;
		bullet.velocity = 5.0F;
		bullet.spread = 0;
		bullet.dmgMin = 1;
		bullet.dmgMax = 2;
		bullet.doesRicochet = true;
		bullet.doesPenetrate = false;
		bullet.style = bullet.STYLE_FLECHETTE;
		bullet.leadChance = 0;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.wither.id, 60 * 20, 2));
		
		bullet.bHurt = new IBulletHurtBehavior() {

			@Override
			public void behaveEntityHurt(EntityBulletBase bullet, Entity hit) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(hit instanceof EntityPlayer) {
					
					if(((EntityPlayer) hit).inventory.hasItem(ModItems.ingot_meteorite_forged))
						return;
					
					if(bullet.shooter instanceof EntityPlayer) {
						
						EntityPlayer shooter = (EntityPlayer) bullet.shooter;
						
						if(shooter.getHeldItem() != null && shooter.getHeldItem().getItem() == ModItems.gun_darter) {
							ItemGunDart.writePlayer(shooter.getHeldItem(), (EntityPlayer)hit);
							shooter.playSound("random.orb", 1.0F, 1.0F);
						}
					}
				}
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration getNERFConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_dart_nerf;
		bullet.velocity = 1.0F;
		bullet.gravity = 0.04D;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.leadChance = 0;
		
		return bullet;
	}
}
