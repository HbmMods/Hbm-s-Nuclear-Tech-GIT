package com.hbm.items.machine;

import org.apache.logging.log4j.Level;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IPartiallyFillable;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.main.MainRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHydrogenTank extends ItemCustomLore implements IPartiallyFillable
{
	private int maxFill;
	private int fillSpeed;
	private int emptySpeed;
	public ItemHydrogenTank(int maxFill, int fillSpeed, int emptySpeed)
	{
		setMaxStackSize(1);
		this.maxFill = maxFill;
		this.fillSpeed = fillSpeed;
		this.emptySpeed = emptySpeed;
	}

	@Override
	public FluidType getType(ItemStack stack)
	{
		try
		{
			return stack.hasTagCompound() ? FluidType.valueOf(stack.stackTagCompound.getString(tankTypeKey)) : FluidType.NONE;
		}
		catch (EnumConstantNotPresentException e)
		{
			MainRegistry.logger.catching(Level.ERROR, e);
			MainRegistry.logger.error(String.format("Item: %s did not have an NBT tag under the key: %s that matches any fluid. Attempted tag: ", stack.getDisplayName(), tankTypeKey, stack.stackTagCompound.getString(tankTypeKey)));
			return FluidType.NONE;
		}
	}

	@Override
	public int getFill(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.stackTagCompound.getInteger(tankFillKey) : 0;
	}

	@Override
	public void setFill(ItemStack stack, int fill)
	{
		if (stack.hasTagCompound())
			stack.stackTagCompound.setInteger(tankFillKey, fill);
		else
		{
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger(tankFillKey, fill);
		}
	}

	@Override
	public int getMaxFill(ItemStack stack)
	{
		return maxFill;
	}

	@Override
	public int getLoadSpeed(ItemStack stack)
	{
		return fillSpeed;
	}

	@Override
	public int getUnloadSpeed(ItemStack stack)
	{
		return emptySpeed;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getMaxFill(stack) != getFill(stack);
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return IPartiallyFillable.getDurability(stack);
	}
}
