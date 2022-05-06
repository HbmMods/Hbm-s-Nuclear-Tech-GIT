package com.hbm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotPattern extends Slot {

	public SlotPattern(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
