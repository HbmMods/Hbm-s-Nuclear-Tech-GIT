package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCustomPartAssembler extends TileEntityMachineBase {
	
	//Save the item and the mode for automation purposes
	public byte item;
	public byte mode;
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
	
	public TileEntityCustomPartAssembler() {
		super(17); //16 input, 1 output
	}

	@Override
	public String getName() {
		return "container.customPartAssembler";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i <= 15;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 16;
	}
	
	@Override
	public void updateEntity() {
		/*
		 * String troll = "trolling";
		 * 
		 * System.Console.WriteLine($"We do a little bit of {troll}!");
		 */
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		item = nbt.getByte("item");
		mode = nbt.getByte("mode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("item", item);
		nbt.setByte("mode", mode);
	}
	
}
