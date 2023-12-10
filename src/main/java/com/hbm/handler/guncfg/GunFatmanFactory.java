package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.*;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.AmmoFatman;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
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
		
		config.name = "m42";
		config.manufacturer = EnumGunManufacturer.F_STRONG;
		
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
		
		config.name = "m42MIRV";
		config.manufacturer = EnumGunManufacturer.F_STRONG;
		
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
		
		config.name = "bel";
		config.manufacturer = EnumGunManufacturer.F_STRONG;
		
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
		
		config.name = "m42";
		config.manufacturer = EnumGunManufacturer.F_STRONG;
		
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
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.STOCK));
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, ExplosionNukeSmall.PARAMS_MEDIUM);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeLowConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.LOW));
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, ExplosionNukeSmall.PARAMS_LOW);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeHighConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.HIGH));
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, ExplosionNukeSmall.PARAMS_HIGH);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeTotsConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.TOTS));
		bullet.bulletsMin = 8;
		bullet.bulletsMax = 8;
		bullet.spread = 0.1F;
		bullet.style = bullet.STYLE_GRENADE;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, ExplosionNukeSmall.PARAMS_TOTS);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukeSafeConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.SAFE));
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				BulletConfigFactory.nuclearExplosion(bullet, x, y, z, ExplosionNukeSmall.PARAMS_SAFE);
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNukePumpkinConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardNukeConfig();
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.PUMPKIN));
		bullet.explosive = 10F;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
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
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.BARREL));
		bullet.explosive = 3F;
		bullet.style = bullet.STYLE_BARREL;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.MIRV));
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBaseNT nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_NORMAL);
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.MIRV_LOW));
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBaseNT nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_LOW);
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.MIRV_HIGH));
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBaseNT nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_HIGH);
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.MIRV_SAFE));
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 6; i++) {
						
						EntityBulletBaseNT nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_SAFE);
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.MIRV_SPECIAL));
		bullet.style = BulletConfiguration.STYLE_MIRV;
		bullet.velocity *= 3;
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote)
					return;
				
				if(bullet.ticksExisted == 15) {
					bullet.setDead();
					
					for(int i = 0; i < 24; i++) {
						
						EntityBulletBaseNT nuke = null;
						
						if(i < 6)
							nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_LOW);
						else if(i < 12)
							nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_TOTS);
						else if(i < 18)
							nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_NORMAL);
						else
							nuke = new EntityBulletBaseNT(bullet.worldObj, BulletConfigSyncingUtil.NUKE_AMAT);
						
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.BALEFIRE));
		bullet.style = BulletConfiguration.STYLE_BF;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {
			@Override public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
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
