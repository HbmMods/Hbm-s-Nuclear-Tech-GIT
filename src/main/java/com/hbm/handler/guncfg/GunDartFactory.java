package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.AmmoDart;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunDart;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.EntityLivingBase;
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
		
		config.name = "dart";
		config.manufacturer = EnumGunManufacturer.NONE;
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.NEEDLE_GPS);
		config.config.add(BulletConfigSyncingUtil.NEEDLE_NUKE);
		
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
		
		config.name = "nerf";
		config.manufacturer = EnumGunManufacturer.HASBRO;
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.DART_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NEEDLE_NUKE);
		
		return config;
	}

	public static BulletConfiguration getGPSConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_dart.stackFromEnum(AmmoDart.GPS));
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
		
		bullet.bntHurt = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(hit instanceof EntityPlayer) {

				if(((EntityPlayer) hit).inventory.hasItem(ModItems.ingot_meteorite_forged))
					return;

				if(bulletnt.getThrower() instanceof EntityPlayer) {

					EntityPlayer shooter = (EntityPlayer) bulletnt.getThrower();

					if(shooter.getHeldItem() != null && shooter.getHeldItem().getItem() == ModItems.gun_darter) {
						ItemGunDart.writePlayer(shooter.getHeldItem(), (EntityPlayer) hit);
						shooter.playSound("random.orb", 1.0F, 1.0F);
					}
				}
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration getNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_dart.stackFromEnum(AmmoDart.NUCLEAR));
		bullet.velocity = 5.0F;
		bullet.spread = 0;
		bullet.dmgMin = 1;
		bullet.dmgMax = 2;
		bullet.doesRicochet = true;
		bullet.doesPenetrate = false;
		bullet.style = bullet.STYLE_FLECHETTE;
		bullet.leadChance = 0;
		
		bullet.bntHurt = (bulletnt, hit) -> {

			if(bulletnt.worldObj.isRemote)
				return;

			if(hit instanceof EntityLivingBase) {

				EntityLivingBase e = (EntityLivingBase) hit;

				if(HbmLivingProps.getRadiation(e) < 250)
					HbmLivingProps.setRadiation(e, 250);
				if(HbmLivingProps.getTimer(e) <= 0)
					HbmLivingProps.setTimer(e, MainRegistry.polaroidID * 60 * 20);
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration getNERFConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_dart.stackFromEnum(AmmoDart.NERF));
		bullet.velocity = 1.0F;
		bullet.gravity = 0.04D;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.leadChance = 0;
		
		return bullet;
	}
}
