package com.hbm.blocks.bomb;

import java.util.Random;

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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityMist;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IBomb;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityBombMulti;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BombMulti extends BlockContainer implements IBomb {

	public final float explosionBaseValue = 8.0F;

	private final Random field_149933_a = new Random();
	private static boolean keepInventory = false;

	public BombMulti(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBombMulti();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.bomb_multi);
	}

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		if(!keepInventory) {
			TileEntityBombMulti tileentityfurnace = (TileEntityBombMulti) p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

			if(tileentityfurnace != null) {
				for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if(itemstack != null) {
						float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int j1 = this.field_149933_a.nextInt(21) + 10;

							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) this.field_149933_a.nextGaussian() * f3;
							entityitem.motionY = (float) this.field_149933_a.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) this.field_149933_a.nextGaussian() * f3;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_) {
		TileEntityBombMulti entity = (TileEntityBombMulti) p_149695_1_.getTileEntity(x, y, z);
		if(p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z)) {
			if(/* entity.getExplosionType() != 0 */entity.isLoaded()) {
				this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
				igniteTestBomb(p_149695_1_, x, y, z);
			}
		}
	}

	public BombReturnCode igniteTestBomb(World world, int x, int y, int z) {
		TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(x, y, z);
		if(!world.isRemote) {
			
			if(entity.isLoaded()) {
				
				float explosionValue = 0.0F;
				int clusterCount = 0;
				int fireRadius = 0;
				int poisonRadius = 0;
				int gasCloud = 0;
				
				explosionValue = this.explosionBaseValue;
				
				switch(entity.return2type()) {
				case 1: explosionValue += 1.0F; break;
				case 2: explosionValue += 4.0F; break;
				case 3: clusterCount += 50; break;
				case 4: fireRadius += 10; break;
				case 5: poisonRadius += 15; break;
				case 6: gasCloud += 50; break;
				}
				switch(entity.return5type()) {
				case 1: explosionValue += 1.0F; break;
				case 2: explosionValue += 4.0F; break;
				case 3: clusterCount += 50; break;
				case 4: fireRadius += 10; break;
				case 5: poisonRadius += 15; break;
				case 6: gasCloud += 50; break;
				}

				entity.clearSlots();
				world.setBlockToAir(x, y, z);
				// world.createExplosion(null, x , y , z , this.explosionValue,
				// true);
				ExplosionLarge.explode(world, x, y, z, explosionValue, true, true, true);
				explosionValue = 0;

				if(clusterCount > 0) {
					ExplosionChaos.cluster(world, x, y, z, clusterCount, 1);
				}

				if(fireRadius > 0) {
					ExplosionChaos.burn(world, x, y, z, fireRadius);
				}

				if(poisonRadius > 0) {
					ExplosionNukeGeneric.wasteNoSchrab(world, x, y, z, poisonRadius);
				}

				if(gasCloud > 0) {
					EntityMist mist = new EntityMist(world);
					mist.setType(Fluids.CHLORINE);
					mist.setPosition(x + 0.5, y + 0.5, z + 0.5);
					mist.setArea(gasCloud * 15F / 50F, gasCloud * 7.5F / 50F);
					world.spawnEntityInWorld(mist);
				}
				
				return BombReturnCode.DETONATED;
			}
		}
		return BombReturnCode.ERROR_MISSING_COMPONENT;
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

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		float f = 0.0625F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8 * f, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8 * f, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(x, y, z);
			
			if(entity.isLoaded()) {
				return igniteTestBomb(world, x, y, z);
			}
			
			return BombReturnCode.ERROR_MISSING_COMPONENT;
		}
		
		return BombReturnCode.UNDEFINED;
	}

}