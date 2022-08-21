package com.hbm.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IPersistentNBT {

	public void writeNBT(NBTTagCompound nbt);
	public void readNBT(NBTTagCompound nbt);
	
	public default ArrayList<ItemStack> getDrops(Block b) {
		ArrayList<ItemStack> list = new ArrayList();
		ItemStack stack = new ItemStack(b);
		stack.stackTagCompound = new NBTTagCompound();
		writeNBT(stack.stackTagCompound);
		list.add(stack);
		return list;
	}
	
	public static ArrayList<ItemStack> getDrops(World world, int x, int y, int z, Block b) {
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof IPersistentNBT) {
			return ((IPersistentNBT) tile).getDrops(b);
		}
		
		return new ArrayList();
	}
	
	public static void restoreData(World world, int x, int y, int z, ItemStack stack) {
		if(!stack.hasTagCompound()) return;
		IPersistentNBT tile = (IPersistentNBT) world.getTileEntity(x, y, z);
		tile.readNBT(stack.stackTagCompound);
	}
}
