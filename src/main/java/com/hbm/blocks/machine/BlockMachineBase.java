package com.hbm.blocks.machine;

import com.hbm.main.MainRegistry;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BlockMachineBase extends BlockContainer implements INBTBlockTransformable {

	int guiID = -1;
	protected boolean rotatable = false;

	public BlockMachineBase(Material mat, int guiID) {
		super(mat);
		this.guiID = guiID;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(guiID == -1)
			return false;

		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, this.guiID, world, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	private static boolean keepInventory;

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		if(!keepInventory) {

			TileEntity te = world.getTileEntity(x, y, z);

			if(!(te instanceof ISidedInventory))
				return;

			ISidedInventory tileentityfurnace = (ISidedInventory) te;

			if(tileentityfurnace != null) {

				for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {

					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if(itemstack != null) {

						float f = world.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {

							int j1 = world.rand.nextInt(21) + 10;

							if(j1 > itemstack.stackSize)
								j1 = itemstack.stackSize;

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound())
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());

							float f3 = 0.05F;
							entityitem.motionX = (float) world.rand.nextGaussian() * f3;
							entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(!rotatable)
			return;

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		if(!rotatable) return meta;
		return INBTBlockTransformable.transformMetaDeco(meta, coordBaseMode);
	}
}