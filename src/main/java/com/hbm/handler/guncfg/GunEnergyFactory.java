package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.interfaces.IBulletUpdateBehavior;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class GunEnergyFactory {
	
	public static GunConfiguration getEMPConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.firingSound = "hbm:weapon.teslaShoot";
		
		config.name = "EMP Orb Projector";
		config.manufacturer = "MWT Prototype Labs";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_EMP);
		
		return config;
	}
	
	public static GunConfiguration getFlamerConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.reloadSoundEnd = false;
		config.firingDuration = 0;
		config.ammoCap = 100;
		config.durability = 1000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.firingSound = "hbm:weapon.flamethrowerShoot";
		config.reloadSound = "hbm:weapon.flamerReload";
		
		config.name = "Heavy Duty Flamer";
		config.manufacturer = "MWT Prototype Labs";

		config.comment.add("Dragon-slaying: Advanced techniques, part 1:");
		config.comment.add("Try not to get eaten by the dragon.");
		config.comment.add("");
		config.comment.add("Hope that helps.");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.FLAMER_NORMAL);
		config.config.add(BulletConfigSyncingUtil.FLAMER_NAPALM);
		config.config.add(BulletConfigSyncingUtil.FLAMER_WP);
		config.config.add(BulletConfigSyncingUtil.FLAMER_VAPORIZER);
		config.config.add(BulletConfigSyncingUtil.FLAMER_GAS);
		
		return config;
	}
	
	public static GunConfiguration getZOMGConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 10;
		config.reloadSoundEnd = false;
		config.firingDuration = 0;
		config.durability = 100000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.ammoCap = 1000;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		config.firingSound = "hbm:weapon.zomgShoot";
		config.reloadSound = "hbm:weapon.b92Reload";
		
		config.name = "EMC101 Prismatic Negative Energy Cannon";
		config.manufacturer = "MWT Prototype Labs";

		config.comment.add("Taste the rainbow!");
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.ZOMG_BOLT);
		
		return config;
	}

	public static GunConfiguration getExtConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.reloadSoundEnd = false;
		config.firingDuration = 0;
		config.ammoCap = 300; //good for 15 seconds of continued spray
		config.durability = 10000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.firingSound = "hbm:weapon.extinguisher";
		config.reloadSound = "hbm:weapon.flamerReload";
		
		config.name = "PROTEX Fire Exinguisher 6kg";
		config.manufacturer = "Gloria GmbH";
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.FEXT_NORMAL);
		config.config.add(BulletConfigSyncingUtil.FEXT_FOAM);
		config.config.add(BulletConfigSyncingUtil.FEXT_SAND);
		
		return config;
	}
	
	public static GunConfiguration getVortexConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		return config;
		
	}
	
	public static BulletConfiguration getOrbusConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.gun_emp_ammo;
		
		bullet.velocity = 1F;
		bullet.spread = 0.0F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 10;
		bullet.dmgMax = 12;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_ORB;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		bullet.emp = 10;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(Potion.weakness.id, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration getFlameConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.ammo_fuel;
		bullet.ammoCount = 100;
		
		bullet.velocity = 0.75F;
		bullet.spread = 0.025F;
		bullet.wear = 1;
		bullet.bulletsMin = 3;
		bullet.bulletsMax = 5;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;
		bullet.gravity = 0.01D;
		bullet.maxAge = 60;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_NONE;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		bullet.vPFX = "flame";
		bullet.incendiary = 10;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaburst");
					data.setString("mode", "flame");
					data.setInteger("count", 15);
					data.setDouble("motion", 0.1D);
					
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNapalmConfig() {
		
		BulletConfiguration bullet = getFlameConfig();
		
		bullet.ammo = ModItems.ammo_fuel_napalm;
		bullet.wear = 2;
		bullet.dmgMin = 4;
		bullet.dmgMax = 6;
		bullet.maxAge = 200;
		
		return bullet;
	}
	
	public static BulletConfiguration getPhosphorusConfig() {
		
		BulletConfiguration bullet = getFlameConfig();
		
		bullet.ammo = ModItems.ammo_fuel_phosphorus;
		bullet.wear = 2;
		bullet.spread = 0.0F;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 4;
		bullet.dmgMax = 6;
		bullet.maxAge = 200;
		bullet.vPFX = "smoke";
		
		bullet.bImpact = BulletConfigFactory.getPhosphorousEffect(5, 60 * 20, 25, 0.25, 0.1F);
		
		return bullet;
	}
	
	public static BulletConfiguration getVaporizerConfig() {
		
		BulletConfiguration bullet = getFlameConfig();
		
		bullet.ammo = ModItems.ammo_fuel_vaporizer;
		bullet.wear = 4;
		bullet.spread = 0.25F;
		bullet.bulletsMin = 8;
		bullet.bulletsMax = 10;
		bullet.dmgMin = 6;
		bullet.dmgMax = 10;
		bullet.maxAge = 15;
		bullet.vPFX = "flame";
		bullet.incendiary = 0;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(eff));
		
		return bullet;
	}
	
	public static BulletConfiguration getGasConfig() {
		
		BulletConfiguration bullet = getFlameConfig();
		
		bullet.ammo = ModItems.ammo_fuel_gas;
		bullet.wear = 1;
		bullet.spread = 0.05F;
		bullet.gravity = 0D;
		bullet.bulletsMin = 5;
		bullet.bulletsMax = 7;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.vPFX = "cloud";
		bullet.incendiary = 0;
		
		bullet.bImpact = BulletConfigFactory.getGasEffect(5, 60 * 20);
		
		return bullet;
	}
	
	public static BulletConfiguration getFextConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.ammo_fireext;
		bullet.ammoCount = 300;
		
		bullet.velocity = 0.75F;
		bullet.spread = 0.025F;
		bullet.wear = 1;
		bullet.bulletsMin = 2;
		bullet.bulletsMax = 3;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.gravity = 0.04D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_NONE;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					boolean fizz = false;
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								if(bullet.worldObj.getBlock(ix + i, iy + j, iz + k) == Blocks.fire) {
									bullet.worldObj.setBlock(ix + i, iy + j, iz + k, Blocks.air);
									fizz = true;
								}
							}
						}
					}
					
					if(fizz)
						bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
				}
			}
		};
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.worldObj.isRemote) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(Blocks.water));
					data.setDouble("posX", bullet.posX);
					data.setDouble("posY", bullet.posY);
					data.setDouble("posZ", bullet.posZ);
					data.setDouble("mX", bullet.motionX + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mY", bullet.motionY - 0.2 + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mZ", bullet.motionZ + bullet.worldObj.rand.nextGaussian() * 0.05);
					MainRegistry.proxy.effectNT(data);
				} else {

					int x = (int)Math.floor(bullet.posX);
					int y = (int)Math.floor(bullet.posY);
					int z = (int)Math.floor(bullet.posZ);
					
					if(bullet.worldObj.getBlock(x, y, z) == ModBlocks.volcanic_lava_block && bullet.worldObj.getBlockMetadata(x, y, z) == 0) {
						bullet.worldObj.setBlock(x, y, z, Blocks.obsidian);
						bullet.setDead();
					}
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getFextFoamConfig() {
		
		BulletConfiguration bullet = getFextConfig();
		
		bullet.ammo = ModItems.ammo_fireext_foam;
		bullet.spread = 0.05F;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					boolean fizz = false;
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								Block b = bullet.worldObj.getBlock(ix + i, iy + j, iz + k);
								
								if(b.getMaterial() == Material.fire) {
									bullet.worldObj.setBlock(ix + i, iy + j, iz + k, Blocks.air);
									fizz = true;
								}
							}
						}
					}
					
					Block b = bullet.worldObj.getBlock(ix, iy, iz);
					
					if(b.isReplaceable(bullet.worldObj, ix, iy, iz) && ModBlocks.foam_layer.canPlaceBlockAt(bullet.worldObj, ix, iy, iz)) {
						
						if(b != ModBlocks.foam_layer) {
							bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.foam_layer);
						} else {
							int meta = bullet.worldObj.getBlockMetadata(ix, iy, iz);
							
							if(meta < 6)
								bullet.worldObj.setBlockMetadataWithNotify(ix, iy, iz, meta + 1, 3);
							else
								bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.block_foam);
						}
					}
					
					if(fizz)
						bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
				}
			}
		};
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.worldObj.isRemote) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(ModBlocks.block_foam));
					data.setDouble("posX", bullet.posX);
					data.setDouble("posY", bullet.posY);
					data.setDouble("posZ", bullet.posZ);
					data.setDouble("mX", bullet.motionX + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mY", bullet.motionY - 0.2 + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mZ", bullet.motionZ + bullet.worldObj.rand.nextGaussian() * 0.05);
					MainRegistry.proxy.effectNT(data);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getFextSandConfig() {
		
		BulletConfiguration bullet = getFextConfig();
		
		bullet.ammo = ModItems.ammo_fireext_sand;
		bullet.spread = 0.1F;
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					Block b = bullet.worldObj.getBlock(ix, iy, iz);
					
					if((b.isReplaceable(bullet.worldObj, ix, iy, iz) || b == ModBlocks.sand_boron_layer) && ModBlocks.sand_boron_layer.canPlaceBlockAt(bullet.worldObj, ix, iy, iz)) {
						
						if(b != ModBlocks.sand_boron_layer) {
							bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.sand_boron_layer);
						} else {
							int meta = bullet.worldObj.getBlockMetadata(ix, iy, iz);
							
							if(meta < 6)
								bullet.worldObj.setBlockMetadataWithNotify(ix, iy, iz, meta + 1, 3);
							else
								bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.sand_boron);
						}
						
						if(b.getMaterial() == Material.fire)
							bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
					}
				}
			}
		};
		
		bullet.bUpdate = new IBulletUpdateBehavior() {

			@Override
			public void behaveUpdate(EntityBulletBase bullet) {
				
				if(bullet.worldObj.isRemote) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(ModBlocks.sand_boron));
					data.setDouble("posX", bullet.posX);
					data.setDouble("posY", bullet.posY);
					data.setDouble("posZ", bullet.posZ);
					data.setDouble("mX", bullet.motionX + bullet.worldObj.rand.nextGaussian() * 0.1);
					data.setDouble("mY", bullet.motionY - 0.2 + bullet.worldObj.rand.nextGaussian() * 0.1);
					data.setDouble("mZ", bullet.motionZ + bullet.worldObj.rand.nextGaussian() * 0.1);
					MainRegistry.proxy.effectNT(data);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getZOMGBoltConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.nugget_euphemium;
		bullet.ammoCount = 1000;
		bullet.wear = 1;
		bullet.velocity = 1F;
		bullet.spread = 0.125F;
		bullet.maxAge = 100;
		bullet.gravity = 0D;
		bullet.bulletsMin = 5;
		bullet.bulletsMax = 5;
		bullet.dmgMin = 10000;
		bullet.dmgMax = 25000;
		bullet.liveAfterImpact = true;

		bullet.style = bullet.STYLE_BOLT;
		bullet.trail = bullet.BOLT_ZOMG;
		
		bullet.effects = new ArrayList();
		bullet.effects.add(new PotionEffect(HbmPotion.bang.id, 10 * 20, 0));
		
		bullet.bImpact = new IBulletImpactBehavior() {

			@Override
			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
				
				if(!bullet.worldObj.isRemote) {
					ExplosionChaos.explodeZOMG(bullet.worldObj, (int)Math.floor(bullet.posX), (int)Math.floor(bullet.posY), (int)Math.floor(bullet.posZ), 5);
					bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "hbm:entity.bombDet", 5.0F, 1.0F);
    				ExplosionLarge.spawnParticles(bullet.worldObj, bullet.posX, bullet.posY, bullet.posZ, 5);
				}
			}
		};
		
		return bullet;
	}

	public static BulletConfiguration getTurbineConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.nothing;
		bullet.dmgMin = 100;
		bullet.dmgMax = 150;
		bullet.velocity = 1F;
		bullet.gravity = 0.0;
		bullet.maxAge = 200;
		bullet.style = bullet.STYLE_BLADE;
		bullet.destroysBlocks = true;
		bullet.doesRicochet = false;
		
		return bullet;
	}

	public static BulletConfiguration getTurretConfig() {
		BulletConfiguration bullet = getFlameConfig();
		bullet.spread *= 2F;
		bullet.gravity = 0.0025D;
		return bullet;
	}
}
