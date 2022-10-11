package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.EntityLivingBase;
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
		
		config.name = "bolter";
		config.manufacturer = EnumGunManufacturer.CERIX;
		
		config.config = HbmCollection.seventyFive;
		
		return config;
	}

	static final float inaccuracy = 0.5F;
	static byte i = 0;
	public static BulletConfiguration get75BoltConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_75bolt, 1, i++);
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 74;
		bullet.dmgMax = 82;
		bullet.penetration = 22;
		bullet.doesRicochet = false;
		bullet.explosive = 0.25F;
		
		bullet.bHurt = (projectile, hit) -> {
				
			if(projectile.worldObj.isRemote)
				return;
			
			if(hit instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) hit;
				float f = living.getHealth();
				
				if(f > 0) {
					f = Math.max(0, f - 2);
					living.setHealth(f);
					
					if(f == 0)
						living.onDeath(ModDamageSource.lead);
				}
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration get75BoltIncConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_75bolt, 1, i++);
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 72;
		bullet.dmgMax = 76;
		bullet.penetration = 22;
		bullet.doesRicochet = false;
		bullet.explosive = 0.25F;

		bullet.incendiary = 5;
		bullet.doesPenetrate = false;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList<PotionEffect>();
		bullet.effects.add(new PotionEffect(eff));
		
		bullet.bImpact = (projectile, x, y, z) -> {
				
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 15);
			data.setDouble("motion", 0.05D);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, projectile.posX, projectile.posY, projectile.posZ), new TargetPoint(projectile.dimension, projectile.posX, projectile.posY, projectile.posZ, 50));
		};
		
		return bullet;
	}
	
	public static BulletConfiguration get75BoltHEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardBulletConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_75bolt, 1, i++);
		bullet.ammoCount = 30;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 94;
		bullet.dmgMax = 100;
		bullet.doesRicochet = false;
		bullet.explosive = 2.5F;
		bullet.blockDamage = false;
		
		return bullet;
	}
}