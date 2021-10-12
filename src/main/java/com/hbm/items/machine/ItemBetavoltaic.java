package com.hbm.items.machine;

import java.util.List;

import javax.annotation.CheckForNull;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.IRadioisotopeFuel;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
@Beta
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
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemstack, player, list, bool);
		IRadioisotopeFuel.addTooltip(list, itemstack, bool);
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
		return decays ? null : decayItem.copy();
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

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getDoesDecay() && getLifespan(stack) != getMaxLifespan();
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return IRadioisotopeFuel.getDuraBar(stack);
	}
	
}
