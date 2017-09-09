package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.logic.EntityNukeExplosionPlus;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.interfaces.IBomb;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class NukeCustom extends BlockContainer implements IBomb {

	public TileEntityNukeCustom tetn = new TileEntityNukeCustom();

	private static boolean keepInventory = false;
	private final static Random field_149933_a = new Random();
	private boolean isExploding = false;

	public NukeCustom(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityNukeCustom();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.nuke_custom);
	}

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_,
			int p_149749_6_) {
		if (!keepInventory) {
			TileEntityNukeCustom tileentityfurnace = (TileEntityNukeCustom) p_149749_1_.getTileEntity(p_149749_2_,
					p_149749_3_, p_149749_4_);

			if (tileentityfurnace != null) {
				for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if (itemstack != null) {
						float f = NukeCustom.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f1 = NukeCustom.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f2 = NukeCustom.field_149933_a.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0) {
							int j1 = NukeCustom.field_149933_a.nextInt(21) + 10;

							if (j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1,
									p_149749_4_ + f2,
									new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound()) {
								entityitem.getEntityItem()
										.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) NukeCustom.field_149933_a.nextGaussian() * f3;
							entityitem.motionY = (float) NukeCustom.field_149933_a.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) NukeCustom.field_149933_a.nextGaussian() * f3;
							p_149749_1_.spawnEntityInWorld(entityitem);
						}
					}
				}

				p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
			}
		}

		super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (!player.isSneaking()) {
			TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(x, y, z);
			if (entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_nuke_custom, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_) {
		TileEntityNukeCustom entity = (TileEntityNukeCustom) p_149695_1_.getTileEntity(x, y, z);
		if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z)) {
			if (entity.isReady()) {
				float[] f = entity.returnAllValues();
				this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
				entity.clearSlots();
				p_149695_1_.setBlockToAir(x, y, z);
				igniteTestBomb(p_149695_1_, x, y, z, f);
			}
		}
	}

	public boolean igniteTestBomb(World world, int x, int y, int z, float[] f) {
		if (!world.isRemote) {
			world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F); // x,y,z,sound,volume,pitch
			
			float tnt = f[0];
			float nuke = f[1];
			float hydro = f[2];;
			float amat = f[3];
			float dirty = f[4];
			float schrab = f[5];
			float euph = f[6];

			System.out.println(tnt);
			System.out.println(nuke);
			
			if(euph > 0) {
				EntityGrenadeZOMG zomg = new EntityGrenadeZOMG(world);
				zomg.posX = x + 0.5;
				zomg.posY = y + 0.5;
				zomg.posZ = z + 0.5;
				ExplosionChaos.zomgMeSinPi(world, x, y, z, 1000, null, zomg);
			} else if(schrab > 0) {
				nuke += (tnt/2);
				hydro += (nuke/2);
				amat += (hydro/2);
				schrab += (amat/2);
				
				if(schrab > 300)
					schrab = 300;
				
				EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
				entity.posX = x + 0.5;
				entity.posY = y + 0.5;
				entity.posZ = z + 0.5;
				entity.destructionRange = (int)schrab;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;

				world.spawnEntityInWorld(entity);
	    		
	    		EntityCloudFleija cloud = new EntityCloudFleija(world, (int)schrab);
	    		cloud.posX = x + 0.5;
	    		cloud.posY = y + 0.5;
	    		cloud.posZ = z + 0.5;
	    		world.spawnEntityInWorld(cloud);
				
			} else if (amat > 0) {
				nuke += (tnt/2);
				hydro += (nuke/2);
				amat += (hydro/2);

				if(amat > 350)
					amat = 350;

				EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
				entity.posX = x + 0.5;
				entity.posY = y + 0.5;
				entity.posZ = z + 0.5;
				entity.destructionRange = (int)amat;
				entity.speed = 25;
	    	    entity.coefficient = 10.0F;
	    	    entity.wasteRange = (int) (amat * 1.4) + (int) dirty;
	    	    	
	    	    world.spawnEntityInWorld(entity);
	    	    
	    	    if(amat < 75) {
	    	    	ExplosionParticleB.spawnMush(world, x, y - ((int)amat / 10), z);
	    	    } else if(amat < 200) {
	    	    	if(MainRegistry.enableNukeClouds) {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (amat/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	} else {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (amat/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	}
	    	    } else {
	    	    	if(MainRegistry.enableNukeClouds) {
						EntityNukeCloudBig entity2 = new EntityNukeCloudBig(world, 1000);
						entity2.posX = x;
	    				entity2.posY = y - (amat/10);
						entity2.posZ = z;
						world.spawnEntityInWorld(entity2);
	    	    	} else {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (amat/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	}
	    	    }
				
			} else if(hydro > 0) {
				nuke += (tnt/2);
				hydro += (nuke/2);

				if(hydro > 350)
					hydro = 350;

				EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
				entity.posX = x + 0.5;
				entity.posY = y + 0.5;
				entity.posZ = z + 0.5;
				entity.destructionRange = (int)hydro;
				entity.speed = 25;
	    	    entity.coefficient = 10.0F;
	    	    entity.wasteRange = (int) (hydro * 1.4) + (int) dirty;
	    	    	
	    	    world.spawnEntityInWorld(entity);
	    	    
	    	    if(hydro < 75) {
	    	    	ExplosionParticle.spawnMush(world, x, y - ((int)hydro / 10), z);
	    	    } else if(hydro < 200) {
	    	    	if(MainRegistry.enableNukeClouds) {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (hydro/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	} else {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (hydro/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	}
	    	    } else {
	    	    	if(MainRegistry.enableNukeClouds) {
						EntityNukeCloudBig entity2 = new EntityNukeCloudBig(world, 1000);
						entity2.posX = x;
	    				entity2.posY = y - (hydro/10);
						entity2.posZ = z;
						world.spawnEntityInWorld(entity2);
	    	    	} else {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (hydro/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	}
	    	    }
				
			} else if(nuke > 0) {
				nuke += (tnt/2);

				if(nuke > 350)
					nuke = 350;

				EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
				entity.posX = x + 0.5;
				entity.posY = y + 0.5;
				entity.posZ = z + 0.5;
				entity.destructionRange = (int)nuke;
				entity.speed = 25;
	    	    entity.coefficient = 10.0F;
	    	    entity.wasteRange = (int) (nuke * 1.4) + (int) dirty;
	    	    	
	    	    world.spawnEntityInWorld(entity);
	    	    
	    	    if(nuke < 75) {
	    	    	ExplosionParticle.spawnMush(world, x, y - ((int)nuke / 10), z);
	    	    } else if(nuke < 200) {
	    	    	if(MainRegistry.enableNukeClouds) {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (nuke/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	} else {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (nuke/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	}
	    	    } else {
	    	    	if(MainRegistry.enableNukeClouds) {
						EntityNukeCloudBig entity2 = new EntityNukeCloudBig(world, 1000);
						entity2.posX = x;
	    				entity2.posY = y - (nuke/10);
						entity2.posZ = z;
						world.spawnEntityInWorld(entity2);
	    	    	} else {
	    				EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
	    				entity2.posX = x;
	    				entity2.posY = y - (nuke/10);
	    				entity2.posZ = z;
	    				world.spawnEntityInWorld(entity2);
	    	    	}
	    	    }
				
			} else if(tnt > 0) {

				if(tnt > 100)
					tnt = 100;
				//world.newExplosion((Entity)null, x + 0.5F, y + 0.5F, z + 0.5F, tnt, field_149933_a.nextInt(5) == 0 ? true : false, true);
				ExplosionLarge.explode(world, x, y, z, tnt, true, true, true);
			}
		}
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(x, y, z);
		{
			if (entity.isReady()) {
				float[] f = entity.returnAllValues();
				this.onBlockDestroyedByPlayer(world, x, y, z, 1);
				entity.clearSlots();
				world.setBlockToAir(x, y, z);
				igniteTestBomb(world, x, y, z, f);
			}
		}
	}
}
