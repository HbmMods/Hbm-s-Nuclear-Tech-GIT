package com.hbm.handler;

import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {

		if(fuel.getItem().equals(ModItems.canister_fuel))
			return 3200;
		if(fuel.getItem().equals(ModItems.powder_coal))
			return 1600;
		if(fuel.getItem().equals(ModItems.scrap))
			return 800;
		if(fuel.getItem().equals(ModItems.dust))
			return 400;
		if(fuel.getItem().equals(ModItems.powder_fire))
			return 6400;
		
		return 0;
	}

}
