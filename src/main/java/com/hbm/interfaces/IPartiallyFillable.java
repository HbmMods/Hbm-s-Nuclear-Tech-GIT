package com.hbm.interfaces;

import com.hbm.handler.FluidTypeHandler.FluidType;

import net.minecraft.item.ItemStack;

public interface IPartiallyFillable
{
	public static final String tankTypeKey = "NTM_FLUID_TYPE";
	public static final String tankFillKey = "NTM_FLUID_FILL";
	public static final String tankMaxFillKey = "NTM_FLUID_MAX_FILL";
	
	public FluidType getType(ItemStack stack);
	
	public int getFill(ItemStack stack);
	
	public void setFill(ItemStack stack, int fill);
	
	public int getMaxFill(ItemStack stack);
	
	public int getLoadSpeed(ItemStack stack);
	
	public int getUnloadSpeed(ItemStack stack);

	public static double getDurability(ItemStack stack)
	{
		final IPartiallyFillable tank = (IPartiallyFillable) stack.getItem();
		return 1D - (double) tank.getFill(stack) / (double) tank.getMaxFill(stack);
	}
	
}
