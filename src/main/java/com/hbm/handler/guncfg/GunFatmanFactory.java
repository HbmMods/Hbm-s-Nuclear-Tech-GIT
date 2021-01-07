package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.saveddata.RadiationSavedData;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;

public class GunFatmanFactory {
	
	public static GunConfiguration getFatmanConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 120;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.fatmanShoot";
		config.reloadSound = GunConfiguration.RSOUND_FATMAN;
		config.reloadSoundEnd = false;
		
		config.name = "M-42 Tactical Nuclear Catapult";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_NORMAL);
		config.durability = 1000;
		
		return config;
	}
	
	public static GunConfiguration getMIRVConfig() {
		
		GunConfiguration config = getFatmanConfig();
		
		config.name = "M-42 Experimental MIRV";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV);
		config.durability = 1000;
		
		return config;
	}
	
	public static GunConfiguration getBELConfig() {
		
		GunConfiguration config = getFatmanConfig();
		
		config.name = "Balefire Egg Launcher";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_AMAT);
		
		return config;
	}
	
	public static GunConfiguration getProtoConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 20;
		config.roundsPerCycle = 8;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 120;
		config.firingDuration = 0;
		config.ammoCap = 8;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCUMFLEX;
		config.firingSound = "hbm:weapon.fatmanShoot";
		config.reloadSound = GunConfiguration.RSOUND_FATMAN;
		config.reloadSoundEnd = false;
		
		config.name = "M-42 Tactical Nuclear Catapult";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO);
		config.durability = 1000;
		
		return config;
	}
	
	public static BulletConfiguration getNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();

		bullet.ammo = ModItems.gun_fatman_ammo;
		
		bullet.nuke = 0;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {

					double posX = bullet.posX;
					double posY = bullet.posY + 0.5;
					double posZ = bullet.posZ;
					
					if(y >= 0) {
						posX = x + 0.5;
						posY = y + 1.5;
						posZ = z + 0.5;
					}
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "muke");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 250));
					bullet.worldObj.playSoundEffect(x, y, z, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
					
					ExplosionLarge.spawnShrapnels(bullet.worldObj, posX, posY, posZ, 25);
					
					ExplosionNT exp = new ExplosionNT(bullet.worldObj, null, posX, posY, posZ, 15F)
							.addAttrib(ExAttrib.FIRE)
							.addAttrib(ExAttrib.NOPARTICLE)
							.addAttrib(ExAttrib.NOSOUND)
							.addAttrib(ExAttrib.NODROP)
							.addAttrib(ExAttrib.NOHURT);
					exp.doExplosionA();
					exp.doExplosionB(false);
					
					ExplosionNukeGeneric.dealDamage(bullet.worldObj, posX, posY, posZ, 45);
					
					for(int i = -2; i <= 2; i++)
						for(int j = -2; j <= 2; j++)
							if(i + j < 4)
								RadiationSavedData.incrementRad(bullet.worldObj, (int)posX + i * 16, (int)posZ + j * 16, 50 / (Math.abs(i) + Math.abs(j) + 1), 1000);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeProtoConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();

		bullet.spread = 0.1F;
		bullet.ammo = ModItems.gun_fatman_ammo;
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		
		bullet.ammo = ModItems.gun_mirv_ammo;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_NORMAL);
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						double mod = 0.1D;
						nuke.motionX = bullet.worldObj.rand.nextGaussian() * mod;
						nuke.motionY = -0.1D;
						nuke.motionZ = bullet.worldObj.rand.nextGaussian() * mod;
						bullet.worldObj.spawnEntityInWorld(nuke);
					}
				}
			}
			
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getBalefireConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		
		bullet.ammo = ModItems.gun_bf_ammo;
		bullet.nuke = 0;
		bullet.style = BulletConfiguration.STYLE_BF;
		
		bullet.bImpact = new IBulletImpactBehavior() {
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {

					double posX = bullet.posX;
					double posY = bullet.posY + 0.5;
					double posZ = bullet.posZ;
					
					if(y >= 0) {
						posX = x + 0.5;
						posY = y + 1.5;
						posZ = z + 0.5;
					}
					
					/*EntityBalefire bf = new EntityBalefire(bullet.worldObj);
					bf.posX = x;
					bf.posY = y;
					bf.posZ = z;
					bf.destructionRange = (int) (BombConfig.fatmanRadius * 1.25);
					bullet.worldObj.spawnEntityInWorld(bf);*/
					
					bullet.worldObj.playSoundEffect(x, y, z, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
					
					ExplosionLarge.spawnShrapnels(bullet.worldObj, posX, posY, posZ, 25);
					
					ExplosionNT exp = new ExplosionNT(bullet.worldObj, null, posX, posY, posZ, 15F)
							.addAttrib(ExAttrib.BALEFIRE)
							.addAttrib(ExAttrib.NOPARTICLE)
							.addAttrib(ExAttrib.NOSOUND)
							.addAttrib(ExAttrib.NODROP)
							.addAttrib(ExAttrib.NOHURT);
					exp.doExplosionA();
					exp.doExplosionB(false);
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "muke");
					data.setBoolean("balefire", true);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y + 0.5, z), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 250));
				}
			}
		};
		
		return bullet;
	}

}
