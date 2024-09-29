package com.hbm.tileentity;

import net.minecraft.item.ItemStack;

/**
 * Masks operation such as isItemValidForSlot and getAccessibleSlotsFromSide found in ISidedInveotry
 * Intended to be used to return a different result depending on the port, assuming the port detects IConditionalInvAccess
 * 
 * @author hbm
 */
public interface IConditionalInvAccess {

	public boolean isItemValidForSlot(int x, int y, int z, int slot, ItemStack stack);
	public default boolean canInsertItem(int x, int y, int z, int slot, ItemStack stack, int side) { return isItemValidForSlot(x, y, z, slot, stack); }
	public boolean canExtractItem(int x, int y, int z, int slot, ItemStack stack, int side);
	public int[] getAccessibleSlotsFromSide(int x, int y, int z, int side);
}
