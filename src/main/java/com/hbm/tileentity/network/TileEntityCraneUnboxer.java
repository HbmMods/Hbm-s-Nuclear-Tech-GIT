package com.hbm.tileentity.network;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.ItemStack;

public class TileEntityCraneUnboxer extends TileEntityMachineBase {

	public TileEntityCraneUnboxer() {
		super(7 * 3);
	}

	@Override
	public String getName() {
		return "container.craneUnboxer";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
}
