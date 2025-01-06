package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDecoContainer extends BlockDecoModel implements ITileEntityProvider {
	
	Class<? extends TileEntity> tile;
	
	public BlockDecoContainer(Material mat, Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture, Class<? extends TileEntity> tile) {
		super(mat, theEnum, multiName, multiTexture);
		this.tile = tile;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		try {
			return tile.newInstance();
		} catch (Exception e) {
			MainRegistry.logger.error("BlockDecoContainer attempted to create a TE, but couldn't. How does that even happen?");
			return null;
		}
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNo, int eventArg) {
		super.onBlockEventReceived(world, x, y, z, eventNo, eventArg);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(eventNo, eventArg) : false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else {
			TileEntity entity = world.getTileEntity(x, y, z);
			if(entity instanceof TileEntityLockableBase) { //annoying accommodations for the filing cabinet, but whatever, could potentially be useful
				if(player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemLock || player.getHeldItem().getItem() == ModItems.key_kit)) {
					return false;
				} else if(!player.isSneaking() && ((TileEntityLockableBase) entity).canAccess(player)) {
					FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
					return true;
				}
			} else if(!player.isSneaking()) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		IInventory inventory = (IInventory) world.getTileEntity(x, y, z);
		Random rand = world.rand;
		
		if(inventory != null) {
			for(int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
				ItemStack itemstack = inventory.getStackInSlot(i1);

				if (itemstack != null) {
					float f = rand.nextFloat() * 0.8F + 0.1F;
					float f1 = rand.nextFloat() * 0.8F + 0.1F;
					float f2 = rand.nextFloat() * 0.8F + 0.1F;

					while(itemstack.stackSize > 0) {
						int j1 = rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}
						
						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						
						if(itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
						
						float f3 = 0.05F;
						entityitem.motionX = (float) rand.nextGaussian() * f3;
						entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) rand.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}

				world.func_147453_f(x, y, z, block);
			}

			super.breakBlock(world, x, y, z, block, meta);
		}
	}
}
