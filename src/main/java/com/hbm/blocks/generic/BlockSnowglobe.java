package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.wiaj.WorldInAJar;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockSnowglobe extends BlockContainer {

	public BlockSnowglobe() {
		super(Material.glass);
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
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		
		TileEntitySnowglobe entity = (TileEntitySnowglobe) world.getTileEntity(x, y, z);
		
		if(entity != null) {
			return new ItemStack(this, 1, entity.type.ordinal());
		}
		
		return super.getPickBlock(target, world, x, y, z, player);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		if(!world.isRemote) {
			TileEntitySnowglobe entity = (TileEntitySnowglobe) world.getTileEntity(x, y, z);
			if(entity != null) {
				EntityItem item = new EntityItem(world, x + 0.5, y, z + 0.5, new ItemStack(this, 1, entity.type.ordinal()));
				item.motionX = 0;
				item.motionY = 0;
				item.motionZ = 0;
				world.spawnEntityInWorld(item);
			}
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModItems.guiID_item_snowglobe, world, x, y, z);
			return true;
			
		} else {
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		
		for(int i = 1; i < SnowglobeType.values().length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int meta = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		
		TileEntitySnowglobe bobble = (TileEntitySnowglobe) world.getTileEntity(x, y, z);
		bobble.type = SnowglobeType.values()[Math.abs(stack.getItemDamage()) % SnowglobeType.values().length];
		bobble.markDirty();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySnowglobe();
	}

	public static class TileEntitySnowglobe extends TileEntity {
		
		public SnowglobeType type = SnowglobeType.NONE;

		@Override
		public boolean canUpdate() {
			return false;
		}

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
			this.type = SnowglobeType.values()[Math.abs(nbt.getByte("type")) % SnowglobeType.values().length];
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setByte("type", (byte) type.ordinal());
		}
	}
	
	public static enum SnowglobeType {
		NONE("NONE", new WorldInAJar(1, 1, 1)),
		TEST("Test", getTestJar());
		
		public String label;
		public WorldInAJar scene;
		
		private SnowglobeType(String label, WorldInAJar scene) {
			this.label = label;
			this.scene = scene;
		}
	}
	
	private static WorldInAJar getTestJar() {
		WorldInAJar world = new WorldInAJar(3, 3, 3);
		for(int x = 0; x < 3; x++) for(int z = 0; z < 3; z++) world.setBlock(x, 0, z, Blocks.brick_block, 0);
		world.setBlock(1, 1, 1, Blocks.gold_block, 0);
		world.setBlock(1, 2, 1, Blocks.gold_block, 0);
		return world;
	}
}
