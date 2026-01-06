package com.hbm.items.machine;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBatteryCreative extends Item implements IBatteryItem {

	@Override public void chargeBattery(ItemStack stack, long i) { }
	@Override public void setCharge(ItemStack stack, long i) { }
	@Override public void dischargeBattery(ItemStack stack, long i) { }

	@Override public long getCharge(ItemStack stack) { return Long.MAX_VALUE / 2L; }
	@Override public long getMaxCharge(ItemStack stack) { return Long.MAX_VALUE; }

	@Override public long getChargeRate(ItemStack stack) { return Long.MAX_VALUE / 100L; }
	@Override public long getDischargeRate(ItemStack stack) { return Long.MAX_VALUE / 100L; }
}
