package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.AmmoStinger;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.player.EntityPlayer;

public class GunRocketHomingFactory {

	public static GunConfiguration getStingerConfig() {

		GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 20;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_KRUCK;
		config.firingSound = "hbm:weapon.rpgShoot";
		config.reloadSound = GunConfiguration.RSOUND_LAUNCHER;
		config.reloadSoundEnd = false;

		config.name = "stinger";
		config.manufacturer = EnumGunManufacturer.RAYTHEON;
		config.comment.add("Woosh, beep-beep-beep!");

		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_NUCLEAR);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_BONES);
		config.durability = 250;

		return config;
	}

	public static GunConfiguration getSkyStingerConfig() {
GunConfiguration config = new GunConfiguration();

		config.rateOfFire = 20;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_SINGLE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_KRUCK;
		config.firingSound = "hbm:weapon.rpgShoot";
		config.reloadSound = GunConfiguration.RSOUND_LAUNCHER;
		config.reloadSoundEnd = false;

		config.name = "stingerOneSky";
		config.manufacturer = EnumGunManufacturer.EQUESTRIA;
		config.comment.add("Oh, I get it, because of the...nyeees!");
		config.comment.add("It all makes sense now!");
		config.comment.add("");
		config.comment.add("Rockets travel faster, are Three times stronger");
		config.comment.add("and fires a second rocket for free");
		config.comment.add("");
		config.comment.add("[LEGENDARY WEAPON]");

		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_HE);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_NUCLEAR);
		config.config.add(BulletConfigSyncingUtil.ROCKET_STINGER_BONES);
		config.durability = 1000;

		return config;
	}

	public static BulletConfiguration getRocketStingerConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_stinger_rocket.stackFromEnum(AmmoStinger.STOCK));
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.explosive = 4F;
		bullet.trail = 0;

		bullet.bntUpdate = (bulletnt) -> {

			if(!bulletnt.worldObj.isRemote) {

				EntityPlayer player = bulletnt.worldObj.getClosestPlayerToEntity(bulletnt, -1.0D);
				EntityRocketHoming rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.0F, 5.0F, 0);
				if(player.getHeldItem().getItem() == ModItems.gun_skystinger && !player.isSneaking()) {
					EntityRocketHoming rocket2 = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 0);
					rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 0);
					rocket.setIsCritical(true);
					rocket2.setIsCritical(true);
					bulletnt.worldObj.spawnEntityInWorld(rocket2);
				}
				rocket.homingMod = 5;
				rocket.homingRadius = 25;
				bulletnt.worldObj.spawnEntityInWorld(rocket);
				bulletnt.setDead();

			}
		};
		return bullet;
	}

	public static BulletConfiguration getRocketStingerHEConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_stinger_rocket.stackFromEnum(AmmoStinger.HE));
		bullet.dmgMin = 30;
		bullet.dmgMax = 35;
		bullet.explosive = 8F;
		bullet.trail = 0;
		bullet.wear = 15;

		bullet.bntUpdate = (bulletnt) -> {

			if(!bulletnt.worldObj.isRemote) {

				EntityPlayer player = bulletnt.worldObj.getClosestPlayerToEntity(bulletnt, -1.0D);
				EntityRocketHoming rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.0F, 5.0F, 1);
				if(player.getHeldItem().getItem() == ModItems.gun_skystinger && !player.isSneaking()) {
					EntityRocketHoming rocket2 = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 1);
					rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 1);
					rocket.setIsCritical(true);
					rocket2.setIsCritical(true);
					bulletnt.worldObj.spawnEntityInWorld(rocket2);
				}
				rocket.homingMod = 5;
				rocket.homingRadius = 25;
				bulletnt.worldObj.spawnEntityInWorld(rocket);
				bulletnt.setDead();

			}
		};
		return bullet;
	}

	public static BulletConfiguration getRocketStingerIncendiaryConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_stinger_rocket.stackFromEnum(AmmoStinger.INCENDIARY));
		bullet.dmgMin = 15;
		bullet.dmgMax = 20;
		bullet.explosive = 4F;
		bullet.trail = 0;
		bullet.wear = 12;

		bullet.bntUpdate = (bulletnt) -> {

			if(!bulletnt.worldObj.isRemote) {

				EntityPlayer player = bulletnt.worldObj.getClosestPlayerToEntity(bulletnt, -1.0D);
				EntityRocketHoming rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.0F, 5.0F, 2);
				if(player.getHeldItem().getItem() == ModItems.gun_skystinger && !player.isSneaking()) {
					EntityRocketHoming rocket2 = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 2);
					rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 2);
					rocket.setIsCritical(true);
					rocket2.setIsCritical(true);
					bulletnt.worldObj.spawnEntityInWorld(rocket2);
				}
				rocket.homingMod = 5;
				rocket.homingRadius = 25;
				bulletnt.worldObj.spawnEntityInWorld(rocket);
				bulletnt.setDead();

			}
		};
		return bullet;
	}

	public static BulletConfiguration getRocketStingerNuclearConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_stinger_rocket.stackFromEnum(AmmoStinger.NUCLEAR));
		bullet.dmgMin = 50;
		bullet.dmgMax = 55;
		bullet.explosive = 15F;
		bullet.trail = 0;
		bullet.wear = 30;

		bullet.bntUpdate = (bulletnt) -> {

			if(!bulletnt.worldObj.isRemote) {

				EntityPlayer player = bulletnt.worldObj.getClosestPlayerToEntity(bulletnt, -1.0D);

				if(player.getDistanceToEntity(bulletnt) < 16) {
					EntityRocketHoming rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.0F, 5.0F, 4);
					if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.gun_skystinger && !player.isSneaking()) {
						EntityRocketHoming rocket2 = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 4);
						rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 4);
						rocket.setIsCritical(true);
						rocket2.setIsCritical(true);
						bulletnt.worldObj.spawnEntityInWorld(rocket2);
					}
					rocket.homingMod = 5;
					rocket.homingRadius = 25;
					bulletnt.worldObj.spawnEntityInWorld(rocket);
				}
				bulletnt.setDead();

			}
		};
		return bullet;
	}

	public static BulletConfiguration getRocketStingerBonesConfig() {
		BulletConfiguration bullet = BulletConfigFactory.standardRocketConfig();

		bullet.ammo = new ComparableStack(ModItems.ammo_stinger_rocket.stackFromEnum(AmmoStinger.BONES));
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.explosive = 8F;
		bullet.trail = 0;

		bullet.bntUpdate = (bulletnt) -> {

			if(!bulletnt.worldObj.isRemote) {

				EntityPlayer player = bulletnt.worldObj.getClosestPlayerToEntity(bulletnt, -1.0D);

				if(player.getDistanceToEntity(bulletnt) < 16) {
					EntityRocketHoming rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.0F, 5.0F, 42);
					if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.gun_skystinger && !player.isSneaking()) {
						EntityRocketHoming rocket2 = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 42);
						rocket = new EntityRocketHoming(bulletnt.worldObj, player, 1.5F, 15.0F, 42);
						rocket.setIsCritical(true);
						rocket2.setIsCritical(true);
						bulletnt.worldObj.spawnEntityInWorld(rocket2);
					}
					rocket.homingMod = 5;
					rocket.homingRadius = 25;
					bulletnt.worldObj.spawnEntityInWorld(rocket);
				}
				bulletnt.setDead();

			}
		};
		return bullet;
	}
} 