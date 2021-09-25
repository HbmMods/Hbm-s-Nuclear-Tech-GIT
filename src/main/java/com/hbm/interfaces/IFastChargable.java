package com.hbm.interfaces;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.google.common.annotations.Beta;
import com.hbm.items.machine.ItemBatteryFast;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
@Untested
public interface IFastChargable extends IBatteryItem
{
	@Beta
	default boolean fastDischarge(EntityPlayer playerIn, @Nonnull ItemStack toChargeIn, @Nonnull ItemStack toDischargeIn)
	{
		if (fastCharge(toChargeIn, toDischargeIn))
		{
			ItemBatteryFast discharged = (ItemBatteryFast) toDischargeIn.getItem();
			if (discharged.getIsSingleUse())
				discharged.consumeItem(toDischargeIn, playerIn);
			return true;
		}
		else
			return false;
	}
	
	@Beta
	static boolean fastCharge(@Nonnull ItemStack toChargeIn, @Nonnull ItemStack toDischargeIn)
	{
		if (!(toChargeIn.getItem() instanceof IFastChargable) || !(toDischargeIn.getItem() instanceof ItemBatteryFast))
			return false;
		IFastChargable toCharge = (IFastChargable) toChargeIn.getItem();
		ItemBatteryFast toDischarge = (ItemBatteryFast) toDischargeIn.getItem();
		long inputEnergy = toDischarge.getCharge(toDischargeIn);
		long currEnergy = toCharge.getCharge(toChargeIn);
		final long energyCap = toCharge.getMaxCharge();
		
		if (inputEnergy == 0 || currEnergy == toCharge.getMaxCharge())
			return false;
		
		final boolean flag = toDischarge.getIsSingleUse();
		@Nonnegative
		long diff = Math.subtractExact(energyCap, currEnergy);
		
		if (Math.addExact(inputEnergy, currEnergy) <= energyCap)
			toCharge.setCharge(toChargeIn, Math.addExact(inputEnergy, currEnergy));
		else if (Math.addExact(inputEnergy, currEnergy) > energyCap)
			toCharge.setCharge(toChargeIn, toCharge.getMaxCharge());
		
		if (diff > toDischarge.getMaxCharge())
			diff = toDischarge.getMaxCharge();
		
		if (!flag)
			toDischarge.dischargeBattery(toDischargeIn, diff);
		
		return true;
	}
}
