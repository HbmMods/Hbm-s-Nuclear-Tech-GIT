package com.hbm.items.weapon.sedna.impl;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.XFactoryDrill;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mags.MagazineLiquidEngine;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.fluidmk2.IFillableItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemGunDrill extends ItemGunBaseNT implements IFillableItem, IBatteryItem {

	public ItemGunDrill(WeaponQuality quality, GunConfig... cfg) {
		super(quality, cfg);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return XFactoryDrill.getModdableHarvestLevel(stack, ToolMaterial.IRON.getHarvestLevel());
	}
	
	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		return true; // this lets us break things that have no set harvest level (i.e. most NTM shit)
	}

	@Override
	public boolean acceptsFluid(FluidType type, ItemStack stack) {
		IMagazine mag = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		return mag instanceof MagazineLiquidEngine;
	}

	@Override
	public int tryFill(FluidType type, int amount, ItemStack stack) {
		IMagazine mag = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		
		if(mag instanceof MagazineLiquidEngine) {
			MagazineLiquidEngine engine = (MagazineLiquidEngine) mag;
			int toFill = Math.min(amount, 50);
			toFill = Math.min(toFill, engine.getCapacity(stack) - this.getFill(stack));
			engine.setAmount(stack, this.getFill(stack) + toFill);
			return amount - toFill;
		}
		
		return 0;
	}

	@Override public boolean providesFluid(FluidType type, ItemStack stack) { return false; }
	@Override public int tryEmpty(FluidType type, int amount, ItemStack stack) { return amount; }

	@Override
	public FluidType getFirstFluidType(ItemStack stack) {
		IMagazine mag = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		if(mag instanceof MagazineLiquidEngine) return ((MagazineLiquidEngine) mag).getType(stack, null);
		return Fluids.NONE;
	}
	
	@Override
	public int getFill(ItemStack stack) {
		IMagazine mag = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack);
		
		if(mag instanceof MagazineLiquidEngine) {
			MagazineLiquidEngine engine = (MagazineLiquidEngine) mag;
			return engine.getAmount(stack, null);
		}
		
		return 0;
	}

	// TBI
	@Override public void chargeBattery(ItemStack stack, long i) { }
	@Override public void setCharge(ItemStack stack, long i) { }
	@Override public void dischargeBattery(ItemStack stack, long i) { }
	@Override public long getCharge(ItemStack stack) { return 0; }
	@Override public long getMaxCharge(ItemStack stack) { return 0; }
	@Override public long getChargeRate() { return 0; }
	@Override public long getDischargeRate() { return 0; }
}
