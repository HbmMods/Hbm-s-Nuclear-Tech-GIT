package com.hbm.inventory.transfer;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface ITransferSource {

	/** Returns a list of ItemStacks accessible from this source */
	public List<ItemStack> offer();
	/** Removes the selected ItemStacks */
	public void remove(List<ItemStack> toRem);
}
