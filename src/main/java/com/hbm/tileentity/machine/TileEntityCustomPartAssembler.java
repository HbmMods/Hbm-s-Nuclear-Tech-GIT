package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;

public class TileEntityCustomPartAssembler extends TileEntityMachineBase {
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	
	public TileEntityCustomPartAssembler() {
		super(13); //12 input, 1 output
	}

	@Override
	public String getName() {
		return "container.customPartAssembler";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i <= 11;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 12;
	}

	@Override
	public void updateEntity() {
		
	}
	
	
	
}
