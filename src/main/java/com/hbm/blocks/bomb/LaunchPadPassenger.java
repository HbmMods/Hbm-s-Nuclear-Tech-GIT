package com.hbm.blocks.bomb;

import java.util.Random;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.missile.*;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchPadPassenger;

import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LaunchPadPassenger extends BlockContainer implements IBomb {

	public TileEntityLaunchPadPassenger tetn = new TileEntityLaunchPadPassenger();
	public static boolean keepInventory = false;
	private final static Random field_149933_a = new Random();

	public LaunchPadPassenger(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLaunchPadPassenger();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.launch_pad_passenger);
	}

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		if(!keepInventory) {
			TileEntityLaunchPadPassenger tileentityfurnace = (TileEntityLaunchPadPassenger) p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

			if(tileentityfurnace != null) {
				for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if(itemstack != null) {
						float f = LaunchPadPassenger.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f1 = LaunchPadPassenger.field_149933_a.nextFloat() * 0.8F + 0.1F;
						float f2 = LaunchPadPassenger.field_149933_a.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int j1 = LaunchPadPassenger.field_149933_a.nextInt(21) + 10;

							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) LaunchPadPassenger.field_149933_a.nextGaussian() * f3;
							entityitem.motionY = (float) LaunchPadPassenger.field_149933_a.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) LaunchPadPassenger.field_149933_a.nextGaussian() * f3;
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
			TileEntityLaunchPadPassenger entity = (TileEntityLaunchPadPassenger) world.getTileEntity(x, y, z);
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
		if(p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z) && !p_149695_1_.isRemote) {
			this.explode(p_149695_1_, x, y, z);
		}
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
		if(!world.isRemote) {
			if(GeneralConfig.enableExtendedLogging) {
				MainRegistry.logger.log(Level.INFO, "[BOMBPL]" + this.getLocalizedName() + " placed at " + x + " / " + y + " / " + z + "! " + "by "+ player.getCommandSenderName());
		}	
	}
	}

	/*
	 * @Override public void setBlockBoundsBasedOnState(IBlockAccess
	 * p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) { float f
	 * = 0.0625F; this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F); }
	 * 
	 * @Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World
	 * world, int x, int y, int z) { float f = 0.0625F;
	 * this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8*f, 1.0F); return
	 * AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ,
	 * x + this.maxX, y + this.maxY, z + this.maxZ); }
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return Item.getItemFromBlock(ModBlocks.launch_pad);
	}

	@Spaghetti("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA *takes breath* AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		TileEntityLaunchPadPassenger entity = (TileEntityLaunchPadPassenger) world.getTileEntity(x, y, z);
		
		if(entity.slots[0] == null || world.isRemote)
			return BombReturnCode.ERROR_MISSING_COMPONENT;
		
		if(entity.slots[1] != null && entity.slots[1].getItem() instanceof IDesignatorItem && entity.power >= 75000) {
			
			if(!((IDesignatorItem)entity.slots[1].getItem()).isReady(world, entity.slots[1], x, y, z))
				return BombReturnCode.ERROR_MISSING_COMPONENT;
			
			int xCoord = entity.slots[1].stackTagCompound.getInteger("xCoord");
			int zCoord = entity.slots[1].stackTagCompound.getInteger("zCoord");

			if(xCoord == entity.xCoord && zCoord == entity.zCoord) {
				xCoord += 1;
			}
			
			Entity missile = null;
		}
		
		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.passenger_carrier && entity.power >= 75000) {
			EntityRidableCarrier missile = new EntityRidableCarrier(world);
			missile.posX = x + 0.5F;
			missile.posY = y + 1F;
			missile.posZ = z + 0.5F;

			if(entity.slots[1] != null)
				missile.setPayload(entity.slots[1]);

			world.spawnEntityInWorld(missile);
			entity.power -= 75000;

			entity.slots[0] = null;
			entity.slots[1] = null;
			world.playSoundEffect(x, y, z, "hbm:entity.rocketTakeoff", 100.0F, 1.0F);
			return BombReturnCode.LAUNCHED;
		}

		
		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}

}
