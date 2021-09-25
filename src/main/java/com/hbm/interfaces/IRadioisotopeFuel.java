package com.hbm.interfaces;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * For radioactive items that can provide power, like RTG pellets
 * @author UFFR
 *
 */
public interface IRadioisotopeFuel
{
	public static final String lifeKey = "PELLET_DEPLETION";
	public long getMaxLifespan();
	@CheckForNull
	public ItemStack getDecayItem();
	public IRadioisotopeFuel setDecays(@Nonnull ItemStack stack, long lifespan);
	public boolean getDoesDecay();
	public short getPower();
	
	public default void decay(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof IRadioisotopeFuel)
		{
			if (!((IRadioisotopeFuel) stack.getItem()).getDoesDecay())
				return;
			if (stack.hasTagCompound())
				stack.stackTagCompound.setLong(lifeKey, getLifespan(stack) - 1);
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong(lifeKey, getMaxLifespan());
			}
		}
	}
	
	public default long getLifespan(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof IRadioisotopeFuel)
		{
			IRadioisotopeFuel fuel = (IRadioisotopeFuel) stack.getItem();
			if (stack.hasTagCompound())
				return stack.stackTagCompound.getLong(lifeKey);
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong(lifeKey, getMaxLifespan());
				return getMaxLifespan();
			}
		}
		return 0;
	}
	
	public static void handleDecay(ItemStack stack, IRadioisotopeFuel instance)
	{
		if (instance.getDoesDecay())
		{
			if (instance.getLifespan(stack) <= 0)
				stack = instance.getDecayItem();
			else
				instance.decay(stack);
		}
	}
}
