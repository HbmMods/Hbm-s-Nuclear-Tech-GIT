package com.hbm.blocks.network.pneumatic;

import java.io.IOException;
import java.util.Random;

import com.hbm.config.ServerConfig;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.pneumatic.TileEntityPneumoStorageClutter;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class PneumoStorageClutter extends BlockContainer {

	public PneumoStorageClutter() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoStorageClutter();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

		if(dropInv) {
			TileEntityPneumoStorageClutter storage = (TileEntityPneumoStorageClutter) world.getTileEntity(x, y, z);
			Random rand = world.rand;
			if(storage != null) {
				for(int i = 0; i < storage.getSizeInventory(); ++i) {
					ItemStack itemstack = storage.getStackInSlot(i);
					if(itemstack != null) {
						float offsetX = rand.nextFloat() * 0.8F + 0.1F;
						float offsetY = rand.nextFloat() * 0.8F + 0.1F;
						float offsetZ = rand.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int split = rand.nextInt(21) + 10;
							if(split > itemstack.stackSize) split = itemstack.stackSize;
							itemstack.stackSize -= split;
							EntityItem entityitem = new EntityItem(world, x + offsetX, y + offsetY, z + offsetZ, new ItemStack(itemstack.getItem(), split, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
							float intensity = 0.05F;
							entityitem.motionX = rand.nextGaussian() * intensity;
							entityitem.motionY = rand.nextGaussian() * intensity + 0.2F;
							entityitem.motionZ = rand.nextGaussian() * intensity;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	private static boolean dropInv = true;

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		if(!world.isRemote && !ServerConfig.CRATE_KEEP_CONTENTS.get()) {
			if(!player.capabilities.isCreativeMode) {
				world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(this)));
			}
			dropInv = true;
			boolean flag = world.setBlockToAir(x, y, z);
			return flag;
		}

		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {

			ItemStack drop = new ItemStack(this);
			ISidedInventory inv = (ISidedInventory)world.getTileEntity(x, y, z);

			NBTTagCompound nbt = new NBTTagCompound();

			if(inv != null) {

				for(int i = 0; i < inv.getSizeInventory(); i++) {
					ItemStack stack = inv.getStackInSlot(i);
					if(stack == null) continue;
					NBTTagCompound slot = new NBTTagCompound();
					stack.writeToNBT(slot);
					nbt.setTag("slot" + i, slot);
				}
			}

			if(!nbt.hasNoTags()) {
				drop.stackTagCompound = nbt;
			}

			if(inv instanceof TileEntityPneumoStorageClutter) {
				TileEntityPneumoStorageClutter crate = (TileEntityPneumoStorageClutter) inv;
				if (crate.hasCustomInventoryName()) {
					drop.setStackDisplayName(crate.getInventoryName());
				}
			}

			if(drop.hasTagCompound()) {
				try {
					byte[] abyte = CompressedStreamTools.compress(drop.stackTagCompound);

					if(abyte.length > 6000) {
						player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Warning: Container NBT exceeds 6kB, contents will be ejected!"));
						world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(this)));
						return world.setBlockToAir(x, y, z);
					}

				} catch(IOException e) { }
			}

			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop));
		}

		dropInv = false;
		boolean flag = world.setBlockToAir(x, y, z);
		dropInv = true;

		return flag;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {

		TileEntityPneumoStorageClutter inv = (TileEntityPneumoStorageClutter) world.getTileEntity(x, y, z);

		if(inv != null && stack.hasTagCompound()) {
			for(int i = 0; i < inv.getSizeInventory(); i++) {
				inv.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
			if(stack.hasDisplayName()) inv.setCustomName(stack.getDisplayName());
		}
		
		super.onBlockPlacedBy(world, x, y, z, player, stack);
	}
}
