package com.hbm.items.machine;

import javax.annotation.CheckForNull;

import com.hbm.interfaces.IRadioisotopeFuel;
import com.hbm.items.special.ItemCustomLore;

import net.minecraft.item.ItemStack;

public class ItemBetavoltaic extends ItemCustomLore implements IRadioisotopeFuel
{
	private ItemStack decayItem = null;
	private long maxLife = 0;
	private short power = 0;
	private boolean decays = false;
	public ItemBetavoltaic(int pow)
	{
		power = (short) pow;
		setMaxStackSize(1);
	}

	@Override
	public long getMaxLifespan()
	{
		return maxLife;
	}

	@CheckForNull
	@Override
	public ItemStack getDecayItem()
	{
		return decayItem == null ? null : decayItem.copy();
	}

	@Override
	public ItemBetavoltaic setDecays(ItemStack stack, long lifespan)
	{
		decayItem = stack;
		maxLife = lifespan;
		return this;
	}

	@Override
	public boolean getDoesDecay()
	{
		return decays;
	}

	@Override
	public short getPower()
	{
		return power;
	}

}
