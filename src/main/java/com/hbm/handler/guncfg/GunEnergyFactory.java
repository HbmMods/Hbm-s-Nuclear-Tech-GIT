package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.IBulletImpactBehaviorNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.IBulletUpdateBehaviorNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.AmmoFireExt;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.IRepairable.EnumExtinguishType;
import com.hbm.util.CompatExternal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class GunEnergyFactory {

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
		config.manufacturer = EnumGunManufacturer.GLORIA;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.FEXT_NORMAL);
		config.config.add(BulletConfigSyncingUtil.FEXT_FOAM);
		config.config.add(BulletConfigSyncingUtil.FEXT_SAND);
		
		return config;
	}

	public static GunConfiguration getCryoCannonConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.firingDuration = 0;
		config.ammoCap = 1_000;
		config.durability = 10_000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		
		config.name = "Cryo Cannon";
		config.manufacturer = EnumGunManufacturer.DRG;
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.CRYO_NORMAL);
		
		return config;
	}
	
	public static BulletConfiguration getFextConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.WATER));
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
		
		bullet.bntHurt = (bulletEntity, target) -> { target.extinguish(); };
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
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
					
					TileEntity core = CompatExternal.getCoreFromPos(bullet.worldObj, ix, iy, iz);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(bullet.worldObj, ix, iy, iz, EnumExtinguishType.WATER);
					}
					
					if(fizz)
						bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
				}
			}
		};
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.FOAM));
		bullet.spread = 0.05F;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
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
					
					TileEntity core = CompatExternal.getCoreFromPos(bullet.worldObj, ix, iy, iz);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(bullet.worldObj, ix, iy, iz, EnumExtinguishType.FOAM);
						return;
					}
					
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
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
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
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.SAND));
		bullet.spread = 0.1F;
		
		bullet.bntHurt = null; // does not extinguish entities
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					Block b = bullet.worldObj.getBlock(ix, iy, iz);
					
					TileEntity core = CompatExternal.getCoreFromPos(bullet.worldObj, ix, iy, iz);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(bullet.worldObj, ix, iy, iz, EnumExtinguishType.SAND);
						return;
					}
					
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
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
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
	
	public static BulletConfiguration getCryoConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		bullet.ammo = new ComparableStack(ModItems.gun_cryolator_ammo);
		bullet.ammoCount = 100;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		return bullet;
	}

	public static BulletConfiguration getTurbineConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.nothing);
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
}
