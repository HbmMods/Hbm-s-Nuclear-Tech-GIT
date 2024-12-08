package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSupplyCrate extends BlockContainer {

	public BlockSupplyCrate(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySupplyCrate();
	}

	@Override public int getRenderType() { return BlockCanCrate.renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override public Item getItemDropped(int i, Random rand, int j) { return null; }
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			
			ItemStack drop = new ItemStack(this);
			TileEntitySupplyCrate inv = (TileEntitySupplyCrate) world.getTileEntity(x, y, z);
			NBTTagCompound nbt = new NBTTagCompound();
			
			if(inv != null) {
				for(int i = 0; i < inv.items.size(); i++) {
					ItemStack stack = inv.items.get(i);
					if(stack == null) continue;
					NBTTagCompound slot = new NBTTagCompound();
					stack.writeToNBT(slot);
					nbt.setTag("slot" + i, slot);
				}
				nbt.setInteger("amount", inv.items.size());
			}
			
			if(!nbt.hasNoTags()) drop.stackTagCompound = nbt;
			world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, drop));
		}
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		
		TileEntitySupplyCrate inv = (TileEntitySupplyCrate) world.getTileEntity(x, y, z);
		
		if(inv != null && stack.hasTagCompound()) {
			int amount = stack.stackTagCompound.getInteger("amount");
			for(int i = 0; i < amount; i++) {
				inv.items.add(ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i)));
			}
		}

		super.onBlockPlacedBy(world, x, y, z, player, stack);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem().equals(ModItems.crowbar)) {
			if(!world.isRemote) {
				dropContents(world, x, y, z);
				world.func_147480_a(x, y, z, false);
				world.playSoundEffect(x, y, z, "hbm:block.crateBreak", 0.5F, 1.0F);
			}
			return true;
		}
		return false;
	}

	public void dropContents(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileEntitySupplyCrate) {
			TileEntitySupplyCrate crate = (TileEntitySupplyCrate) tile;
			
			for(ItemStack item : crate.items) {
				this.dropBlockAsItem(world, x, y, z, item);
			}
		}
	}
	
	public static class TileEntitySupplyCrate extends TileEntity {
		
		public List<ItemStack> items = new ArrayList();
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			items.clear();
			NBTTagList list = nbt.getTagList("items", 10);
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound nbt1 = list.getCompoundTagAt(i);
				items.add(ItemStack.loadItemStackFromNBT(nbt1));
			}
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			NBTTagList list = new NBTTagList();
			for(int i = 0; i < items.size(); i++) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				items.get(i).writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
			nbt.setTag("items", list);
		}
	}
}
