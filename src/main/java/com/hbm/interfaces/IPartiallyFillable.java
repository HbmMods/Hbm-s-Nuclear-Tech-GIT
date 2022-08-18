package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.item.ItemStack;

@Deprecated
public interface IPartiallyFillable {
	
	public FluidType getType(ItemStack stack);
	
	public int getFill(ItemStack stack);
	
	public void setFill(ItemStack stack, int fill);
	
	public int getMaxFill(ItemStack stack);
	
	public int getLoadSpeed(ItemStack stack);
	
	public int getUnloadSpeed(ItemStack stack);

}
