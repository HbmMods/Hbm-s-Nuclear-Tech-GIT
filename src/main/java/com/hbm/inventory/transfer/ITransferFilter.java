package com.hbm.inventory.transfer;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface ITransferFilter {

	/** Returns a list of valid ItemStacks that pass the filter and can be added. The returned list is what is added to the target and removed from the source. */
	public List<ItemStack> select(List<ItemStack> offer);
}
