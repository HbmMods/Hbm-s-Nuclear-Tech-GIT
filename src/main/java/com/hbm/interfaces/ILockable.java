package com.hbm.interfaces;

import com.google.common.annotations.Beta;
import com.hbm.items.tool.ItemKeyPin;

import net.minecraft.item.ItemStack;

@Beta
@Untested
public interface ILockable
{
	public static boolean checkKey(ItemStack stack, int pins)
	{
		if (stack != null && stack.getItem() instanceof ItemKeyPin)
			return ItemKeyPin.getPins(stack) == pins;
		else
			return false;
	}
}
