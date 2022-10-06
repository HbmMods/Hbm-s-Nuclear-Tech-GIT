package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
		config.config.add(BulletConfigSyncingUtil.NUKE_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_TOTS);
		config.config.add(BulletConfigSyncingUtil.NUKE_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_PUMPKIN);
		config.config.add(BulletConfigSyncingUtil.NUKE_BARREL);
		config.durability = 1000;
		
		return config;
	}
	
	public static GunConfiguration getMIRVConfig() {
		
		GunConfiguration config = getFatmanConfig();
		
		config.name = "M-42 Experimental MIRV";
		config.manufacturer = "Fort Strong";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL);
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
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_NORMAL);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_LOW);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_HIGH);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_TOTS);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_SAFE);
		config.config.add(BulletConfigSyncingUtil.NUKE_PROTO_PUMPKIN);
		config.config.add(BulletConfigSyncingUtil.NUKE_BARREL);
		config.durability = 1000;
		
		return config;
	}
	
	public static BulletConfiguration getNukeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 3);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeLowConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_low;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 2);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeHighConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_high;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 4);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeTotsConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_tots;
		bullet.bulletsMin = 8;
		bullet.bulletsMax = 8;
		bullet.spread = 0.1F;
		bullet.style = bullet.STYLE_GRENADE;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 1);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeSafeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_safe;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, 0);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukePumpkinConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_pumpkin;
		bullet.explosive = 10F;
		
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
					
					ExplosionLarge.spawnParticles(bullet.worldObj, posX, posY, posZ, 45);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeBarrelConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = ModItems.ammo_nuke_barrel;
		bullet.explosive = 3F;
		bullet.style = bullet.STYLE_BARREL;
		
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

					x = (int)Math.floor(posX);
					y = (int)Math.floor(posY);
					z = (int)Math.floor(posZ);
					
					World worldObj = bullet.worldObj;
					
					for(int ix = x - 3; ix <= x + 3; ix++) {
						for(int iy = y - 3; iy <= y + 3; iy++) {
							for(int iz = z - 3; iz <= z + 3; iz++) {
								
								if(worldObj.rand.nextInt(3) == 0 && worldObj.getBlock(ix, iy, iz).isReplaceable(worldObj, ix, iy, iz) && ModBlocks.fallout.canPlaceBlockAt(worldObj, ix, iy, iz)) {
									worldObj.setBlock(ix, iy, iz, ModBlocks.fallout);
								} else if(worldObj.getBlock(ix, iy, iz) == Blocks.air) {
									
									if(worldObj.rand.nextBoolean())
										worldObj.setBlock(ix, iy, iz, ModBlocks.gas_radon);
									else
										worldObj.setBlock(ix, iy, iz, ModBlocks.gas_radon_dense);
								}
							}
						}
					}
					
					ChunkRadiationManager.proxy.incrementRad(worldObj, x, y, z, 100F);
					
					ExplosionLarge.spawnParticles(bullet.worldObj, posX, posY, posZ, 45);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getMirvConfig() {
		
		BulletConfiguration bullet = getNukeConfig();
		
		bullet.ammo = ModItems.ammo_mirv;
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
	
	public static BulletConfiguration getMirvLowConfig() {
		
		BulletConfiguration bullet = getNukeLowConfig();
		
		bullet.ammo = ModItems.ammo_mirv_low;
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
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_LOW);
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
	
	public static BulletConfiguration getMirvHighConfig() {
		
		BulletConfiguration bullet = getNukeHighConfig();
		
		bullet.ammo = ModItems.ammo_mirv_high;
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
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_HIGH);
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
	
	public static BulletConfiguration getMirvSafeConfig() {
		
		BulletConfiguration bullet = getNukeSafeConfig();
		
		bullet.ammo = ModItems.ammo_mirv_safe;
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
						
						EntityBulletBase nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_SAFE);
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
	
	public static BulletConfiguration getMirvSpecialConfig() {
		
		BulletConfiguration bullet = getNukeConfig();
		
		bullet.ammo = ModItems.ammo_mirv_special;
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 24; i++) {
						
						EntityBulletBase nuke = null;
						
						if(i < 6)
							nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_LOW);
						else if(i < 12)
							nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_TOTS);
						else if(i < 18)
							nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_NORMAL);
						else
							nuke = new EntityBulletBase(bullet.worldObj, BulletConfigSyncingUtil.NUKE_AMAT);
						
						nuke.setPosition(bullet.posX, bullet.posY, bullet.posZ);
						
						double mod = 0.25D;
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
					
					bullet.worldObj.playSoundEffect(x, y, z, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
					
					ExplosionLarge.spawnShrapnels(bullet.worldObj, posX, posY, posZ, 25);
					
					ExplosionNT exp = new ExplosionNT(bullet.worldObj, null, posX, posY, posZ, 15F)
							.addAttrib(ExAttrib.BALEFIRE)
							.addAttrib(ExAttrib.NOPARTICLE)
							.addAttrib(ExAttrib.NOSOUND)
							.addAttrib(ExAttrib.NODROP)
							.overrideResolution(64);
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
