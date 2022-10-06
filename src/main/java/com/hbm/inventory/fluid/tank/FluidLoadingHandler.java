package com.hbm.inventory.fluid.tank;

import net.minecraft.item.ItemStack;

public abstract class FluidLoadingHandler {

	public abstract boolean fillItem(ItemStack[] slots, int in, int out, FluidTank tank);
	public abstract boolean emptyItem(ItemStack[] slots, int in, int out, FluidTank tank);
}
