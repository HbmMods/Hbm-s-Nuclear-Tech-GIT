package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.util.Tuple.Quartet;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLoot extends BlockContainer {

	public BlockLoot() {
		super(Material.iron);
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
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		
		TileEntityLoot loot = (TileEntityLoot) world.getTileEntity(x, y, z);
		
		if(loot != null && loot.items.isEmpty()) {
			
			for(int i = 0; i < 64; i++) {
				loot.addItem(new ItemStack(ModItems.cigarette), -0.375 + world.rand.nextDouble() * 0.75, world.rand.nextDouble() * 0.0625, -0.375 + world.rand.nextDouble() * 0.75);
			}
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			
			TileEntityLoot entity = (TileEntityLoot) world.getTileEntity(x, y, z);
			if(entity != null) {
				
				for(Quartet<ItemStack, Double, Double, Double> quartet : entity.items) {
					player.inventory.addItemStackToInventory(quartet.getW());
				}
				
				world.setBlockToAir(x, y, z);
				player.inventoryContainer.detectAndSendChanges();
			}
			return true;
			
		} else {
			return false;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLoot();
	}

	public static class TileEntityLoot extends TileEntity {
		
		public List<Quartet<ItemStack, Double, Double, Double>> items = new ArrayList();

		@Override
		public boolean canUpdate() {
			return false;
		}
		
		public TileEntityLoot addItem(ItemStack stack, double x, double y, double z) {
			items.add(new Quartet(stack, x, y, z));
			return this;
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
			
			int count = nbt.getInteger("count");
			
			System.out.println("count");
			
			for(int i = 0; i < count; i++) {
				ItemStack stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item" + i));
				double x = nbt.getDouble("x" + i);
				double y = nbt.getDouble("y" + i);
				double z = nbt.getDouble("z" + i);
				items.add(new Quartet(stack, x, y, z));
			}
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			
			nbt.setInteger("count", items.size());
			
			for(int i = 0; i < items.size(); i++) {
				Quartet<ItemStack, Double, Double, Double> item = items.get(i);
				NBTTagCompound stack = new NBTTagCompound();
				item.getW().writeToNBT(stack);
				nbt.setTag("item" + i, stack);
				nbt.setDouble("x" + i, item.getX());
				nbt.setDouble("y" + i, item.getY());
				nbt.setDouble("z" + i, item.getZ());
			}
		}
	}
}
