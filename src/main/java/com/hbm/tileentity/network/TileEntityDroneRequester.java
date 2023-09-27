package com.hbm.tileentity.network;

import com.hbm.module.ModulePatternMatcher;

import net.minecraft.item.ItemStack;

public class TileEntityDroneRequester extends TileEntityRequestNetworkContainer {
	
	public ModulePatternMatcher matcher;

	public TileEntityDroneRequester() {
		super(18);
		this.matcher = new ModulePatternMatcher(9);
	}

	@Override
	public String getName() {
		return "container.droneRequester";
	}
	
	public boolean matchesFilter(ItemStack stack) {
		
		for(int i = 0; i < 9; i++) {
			ItemStack filter = slots[i];
			
			if(filter != null && this.matcher.isValidForFilter(filter, i, stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void nextMode(int i) {
		this.matcher.nextMode(worldObj, slots[i], i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return true;
	}
}
