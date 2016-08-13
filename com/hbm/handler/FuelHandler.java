package com.hbm.handler;

import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {

		if(fuel.getItem().equals(ModItems.canister_fuel))
			return 32000;
		if(fuel.getItem().equals(ModItems.scrap))
			return 4000;
		if(fuel.getItem().equals(ModItems.powder_fire))
			return 20000;
		
		return 0;
	}

}
