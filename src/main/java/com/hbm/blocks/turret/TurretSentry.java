package com.hbm.blocks.turret;

import java.util.Random;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.turret.TileEntityTurretSentry;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretSentry extends BlockContainer {

	public TurretSentry() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTurretSentry();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return false;
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
	
	Random rand = new Random();
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta) {

		TileEntityTurretSentry sentry = (TileEntityTurretSentry) world.getTileEntity(x, y, z);

		if(sentry != null) {
			for(int i = 0; i < sentry.getSizeInventory(); ++i) {
				ItemStack itemstack = sentry.getStackInSlot(i);

				if(itemstack != null) {
					float oX = this.rand.nextFloat() * 0.8F + 0.1F;
					float oY = this.rand.nextFloat() * 0.8F + 0.1F;
					float oZ = this.rand.nextFloat() * 0.8F + 0.1F;

					while(itemstack.stackSize > 0) {
						int toDrop = this.rand.nextInt(21) + 10;

						if(toDrop > itemstack.stackSize) {
							toDrop = itemstack.stackSize;
						}

						itemstack.stackSize -= toDrop;
						EntityItem entityitem = new EntityItem(world, x + oX, y + oY, z + oZ, new ItemStack(itemstack.getItem(), toDrop, itemstack.getItemDamage()));

						if(itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						float jump = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * jump;
						entityitem.motionY = (float) this.rand.nextGaussian() * jump + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * jump;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}

			world.func_147453_f(x, y, z, b);
		}

		super.breakBlock(world, x, y, z, b, meta);
	}
}
