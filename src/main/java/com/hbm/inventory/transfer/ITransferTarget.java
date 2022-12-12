package com.hbm.inventory.transfer;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface ITransferTarget {

	/** Adds the selected ItemStacks to the target */
	public void fill(List<ItemStack> offer);
}
