package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletHitBehavior;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

public class Gun50BMGFactory {
	
	public static GunConfiguration getCalamityConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 6;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 50;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_BOX;
		config.durability = 2000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.calShoot";
		config.reloadSoundEnd = false;
		
		config.name = "Maxim gun";
		config.manufacturer = "Hiram Maxim";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.BMG50_NORMAL);
		config.config.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.BMG50_AP);
		config.config.add(BulletConfigSyncingUtil.BMG50_DU);
		config.config.add(BulletConfigSyncingUtil.BMG50_STAR);
		config.config.add(BulletConfigSyncingUtil.BMG50_SLEEK);
		
		return config;
	}
	
	public static GunConfiguration getSaddleConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 3;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 30;
		config.firingDuration = 0;
		config.ammoCap = 100;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_BOX;
		config.durability = 3500;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.calShoot";
		
		config.name = "Double Maxim gun";
		config.manufacturer = "???";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.BMG50_NORMAL);
		config.config.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
		config.config.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
		config.config.add(BulletConfigSyncingUtil.BMG50_AP);
		config.config.add(BulletConfigSyncingUtil.BMG50_DU);
		config.config.add(BulletConfigSyncingUtil.BMG50_STAR);
		config.config.add(BulletConfigSyncingUtil.BMG50_SLEEK);
		
		return config;
	}

	static float inaccuracy = 2.5F;
	public static BulletConfiguration get50BMGConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 15;
		bullet.dmgMax = 18;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGFireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_incendiary;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 15;
		bullet.dmgMax = 18;
		bullet.wear = 15;
		bullet.incendiary = 5;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGPhosphorusConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_phosphorus;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 15;
		bullet.dmgMax = 18;
		bullet.wear = 15;
		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setString("mode", "flame");
				data.setInteger("count", 15);
				data.setDouble("motion", 0.05D);
				
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration get50BMGExplosiveConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_explosive;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 20;
		bullet.dmgMax = 25;
		bullet.wear = 25;
		bullet.explosive = 1;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGAPConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_ap;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 25;
		bullet.wear = 15;
		bullet.leadChance = 10;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGDUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_du;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 40;
		bullet.dmgMax = 45;
		bullet.wear = 25;
		bullet.leadChance = 50;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGStarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_star;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 70;
		bullet.wear = 25;
		bullet.leadChance = 100;
		
		return bullet;
	}

	public static BulletConfiguration get50BMGSleekConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_50bmg_sleek;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 50;
		bullet.dmgMax = 70;
		bullet.wear = 10;
		bullet.leadChance = 100;
		bullet.doesPenetrate = false;
		
		bullet.bHit = new IBulletHitBehavior() {

			@Override
			public void behaveEntityHit(EntityBulletBase bullet, Entity hit) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				EntityBulletBase meteor = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
				meteor.setPosition(hit.posX, hit.posY + 30 + meteor.worldObj.rand.nextInt(10), hit.posZ);
				meteor.motionY = -1D;
				meteor.shooter = bullet.shooter;
				bullet.worldObj.spawnEntityInWorld(meteor);
			}
		};
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(y == -1)
					return;
				
				EntityBulletBase meteor = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.MASKMAN_METEOR);
				meteor.setPosition(bullet.posX, bullet.posY + 30 + meteor.worldObj.rand.nextInt(10), bullet.posZ);
				meteor.motionY = -1D;
				meteor.shooter = bullet.shooter;
				bullet.worldObj.spawnEntityInWorld(meteor);
			}
		};
		
		return bullet;
	}

}
