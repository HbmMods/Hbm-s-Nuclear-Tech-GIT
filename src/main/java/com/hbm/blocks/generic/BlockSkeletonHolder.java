package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSkeletonHolder extends BlockContainer {

	public BlockSkeletonHolder() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySkeletonHolder();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(player.isSneaking()) return false;
		
		TileEntitySkeletonHolder pedestal = (TileEntitySkeletonHolder) world.getTileEntity(x, y, z);
		
		if(pedestal.item == null && player.getHeldItem() != null) {
			pedestal.item = player.getHeldItem().copy();
			player.inventory.mainInventory[player.inventory.currentItem] = null;
			pedestal.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		} else if(pedestal.item != null && player.getHeldItem() == null) {
			player.inventory.mainInventory[player.inventory.currentItem] = pedestal.item.copy();
			pedestal.item = null;
			pedestal.markDirty();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		if(!world.isRemote) {
			TileEntitySkeletonHolder entity = (TileEntitySkeletonHolder) world.getTileEntity(x, y, z);
			if(entity != null && entity.item != null) {
				EntityItem item = new EntityItem(world, x + 0.5, y, z + 0.5, entity.item.copy());
				world.spawnEntityInWorld(item);
			}
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}

	public static class TileEntitySkeletonHolder extends TileEntity {

		public ItemStack item;
		
		@Override public boolean canUpdate() { return false; }

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item"));
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(this.item != null) {
				NBTTagCompound stack = new NBTTagCompound();
				this.item.writeToNBT(stack);
				nbt.setTag("item", stack);
			}
		}
	}
}
