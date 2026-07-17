package com.hbm.blocks.network.pneumatic;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageExporter;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PneumoStorageExporter extends BlockMachineBase {

	public PneumoStorageExporter() {
		super(Material.iron, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoStorageExporter();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof ISidedInventory)) return;

		ISidedInventory tileentityfurnace = (ISidedInventory) te;

		if(tileentityfurnace != null) {

			for(int i = 9; i < tileentityfurnace.getSizeInventory(); ++i) {

				ItemStack itemstack = tileentityfurnace.getStackInSlot(i);

				if(itemstack != null) {

					float mX = world.rand.nextFloat() * 0.8F + 0.1F;
					float mY = world.rand.nextFloat() * 0.8F + 0.1F;
					float mZ = world.rand.nextFloat() * 0.8F + 0.1F;

					while(itemstack.stackSize > 0) {

						int amount = world.rand.nextInt(21) + 10;
						if(amount > itemstack.stackSize) amount = itemstack.stackSize;

						itemstack.stackSize -= amount;
						EntityItem entityitem = new EntityItem(world, x + mX, y + mY, z + mZ, new ItemStack(itemstack.getItem(), amount, itemstack.getItemDamage()));

						if(itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());

						float motion = 0.05F;
						entityitem.motionX = (float) world.rand.nextGaussian() * motion;
						entityitem.motionY = (float) world.rand.nextGaussian() * motion + 0.2F;
						entityitem.motionZ = (float) world.rand.nextGaussian() * motion;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, meta);
	}
}
