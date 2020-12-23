package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

public class Gun75BoltFactory {

	public static GunConfiguration getBolterConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 2;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.hasSights = false;
		config.reloadDuration = 40;
		config.firingDuration = 0;
		config.ammoCap = 30;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.NONE;
		config.durability = 10000;
		config.reloadSound = GunConfiguration.RSOUND_MAG;
		config.firingSound = "hbm:weapon.hksShoot";
		config.reloadSoundEnd = false;
		config.showAmmo = false;
		
		config.animations.put(AnimType.CYCLE, new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				.addBus("EJECT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 25))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 75))
						)
				);
		
		config.animations.put(AnimType.RELOAD, new BusAnimation()
				.addBus("TILT", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 250))
						.addKeyframe(new BusAnimationKeyframe(1, 0, 0, 1500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						)
				.addBus("MAG", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 500))
						.addKeyframe(new BusAnimationKeyframe(1, 0, 1, 500))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 500))
						)
				);
		
		config.name = "Manticora Pattern Boltgun";
		config.manufacturer = "Cerix Magnus";
		
		config.config = new ArrayList();
		config.config.add(BulletConfigSyncingUtil.B75_NORMAL);
		config.config.add(BulletConfigSyncingUtil.B75_INCENDIARY);
		config.config.add(BulletConfigSyncingUtil.B75_HE);
		
		return config;
	}

	static float inaccuracy = 0.5F;
	public static BulletConfiguration get75BoltConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_75bolt;
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 24;
		bullet.dmgMax = 32;
		bullet.doesRicochet = false;
		bullet.explosive = 0.25F;
		
		return bullet;
	}

	public static BulletConfiguration get75BoltIncConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_75bolt_incendiary;
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 26;
		bullet.dmgMax = 36;
		bullet.doesRicochet = false;
		bullet.explosive = 0.25F;

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
	
	public static BulletConfiguration get75BoltHEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = ModItems.ammo_75bolt_he;
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 32;
		bullet.dmgMax = 48;
		bullet.doesRicochet = false;
		bullet.explosive = 2.5F;
		bullet.blockDamage = false;
		
		return bullet;
	}
}
