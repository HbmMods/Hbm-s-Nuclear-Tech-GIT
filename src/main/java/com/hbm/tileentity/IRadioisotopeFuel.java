package com.hbm.tileentity;

import java.util.List;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.hbm.config.VersatileConfig;
import com.hbm.lib.Library;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.TimeDurationType;

import api.hbm.Date;
import net.minecraft.item.Item;
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
	public short getHeat();
	/**
	 * Get the scaled power between max and 0, depending on decay
	 * @param fuel The fuel instance
	 * @param stack The stack the instance is from
	 * @return Power between its maximum and 0, proportional to decay
	 */
	public static short getScaledPower(IRadioisotopeFuel fuel, ItemStack stack)
	{
		return (short) Math.ceil(fuel.getHeat() * (fuel.getLifespan(stack) * fuel.getMaxLifespan()));
	}
	
	@CheckForNull
	public static IRadioisotopeFuel getInstance(Item item)
	{
		return item instanceof IRadioisotopeFuel ? (IRadioisotopeFuel) item : null;
	}
	@CheckForNull
	public static IRadioisotopeFuel getInstance(ItemStack stack)
	{
		return stack == null ? null : getInstance(stack.getItem());
	}
	
	public default void setLifespan(ItemStack stack, long life)
	{
		if (stack != null && stack.getItem() instanceof IRadioisotopeFuel)
		{
			if (stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();

			stack.stackTagCompound.setLong(lifeKey, life);
		}
	}
	
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
	/**
	 * Universal decay handler for a fuel item. Used internally only
	 * @param stack The radioisotope fuel
	 * @param instance The fuel's {@link#IRadioisotopeFuel} handler
	 * @return Either the same stack or what it decays
	 */
	public static ItemStack handleDecay(ItemStack stack, IRadioisotopeFuel instance)
	{
		if (instance.getDoesDecay() && VersatileConfig.rtgDecay())
		{
			if (instance.getLifespan(stack) <= 0)
				return instance.getDecayItem();
			else
				instance.decay(stack);
		}
		return stack;
	}
	/**
	 * Gets the lifespan of an RTG based on half-life
	 * @param halfLife The half-life
	 * @param type Half-life units: {@link#TimeDurationType}
	 * @return The half-life calculated into Minecraft ticks
	 */
	public static long getLifespan(float halfLife, TimeDurationType type, boolean realTime)
	{
		return (long) (Library.getLifespan(halfLife, type, realTime) * 1.5);
	}
	/**
	 * Add extended tooltip information
	 * @param tooltip The list for the tooltip
	 * @param instance {@link#IRadioisotopeFuel} instance
	 * @param stack Appropriate ItemStack
	 */
	public static void addTooltip(List<String> tooltip, ItemStack stack, boolean showAdv)
	{
		final IRadioisotopeFuel instance = (IRadioisotopeFuel) stack.getItem();
		tooltip.add(I18nUtil.resolveKey("desc.item.rtgHeat", instance.getDoesDecay() && VersatileConfig.scaleRTGPower() ? getScaledPower(instance, stack) : instance.getHeat()));
		if (instance.getDoesDecay())
		{
			tooltip.add(I18nUtil.resolveKey("desc.item.rtgDecay", I18nUtil.resolveKey(instance.getDecayItem().getUnlocalizedName() + ".name")));
			tooltip.add(BobMathUtil.toPercentage(instance.getLifespan(stack), instance.getMaxLifespan()));
			if (showAdv)
			{
				tooltip.add("EXTENDED INFO:");
				tooltip.add(String.format("%s / %s ticks", instance.getLifespan(stack), instance.getMaxLifespan()));
				final Number[] timeLeft = new Date(instance.getLifespan(stack)).export();/*BobMathUtil.ticksToDate(instance.getLifespan(stack));*/
				final Number[] maxLife = new Date(instance.getMaxLifespan()).export();/*BobMathUtil.ticksToDate(instance.getMaxLifespan());*/
				tooltip.add(String.format("Time remaining: %s y, %s d, %s h", (Object[]) timeLeft));
				tooltip.add(String.format("Maximum life: %s y, %s d, %s h", (Object[]) maxLife));
			}
		}
	}
	
	public static double getDuraBar(ItemStack stack)
	{
		final IRadioisotopeFuel instance = (IRadioisotopeFuel) stack.getItem();
		return 1D - (double) instance.getLifespan(stack) / (double) instance.getMaxLifespan();
	}
}
