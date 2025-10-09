package com.hbm.items.weapon.sedna.impl;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.fluidmk2.IFillableItem;
import net.minecraft.item.ItemStack;

public class ItemGunDrill extends ItemGunBaseNT implements IFillableItem, IBatteryItem {

	public ItemGunDrill(WeaponQuality quality, GunConfig... cfg) {
		super(quality, cfg);
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		return false;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		return 0;
	}

	@Override public boolean providesFluid(FluidType type, ItemStack stack) { return false; }

	@Override
	public int tryEmpty(FluidType type, int amount, ItemStack stack) {
		return 0;
	}

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		return null;
	}

	@Override
	public int getFill(ItemStack stack) {
		return 0;
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		
	}

	@Override
	public long getCharge(ItemStack stack) {
		return 0;
	}

	@Override
	public long getMaxCharge(ItemStack stack) {
		return 0;
	}

	@Override
	public long getChargeRate() {
		return 0;
	}

	@Override
	public long getDischargeRate() {
		return 0;
	}
}
