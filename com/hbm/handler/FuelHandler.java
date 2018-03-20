package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {

		if(fuel.getItem().equals(ModItems.solid_fuel))
			return 3200;
		if(fuel.getItem().equals(ModItems.biomass_compressed))
			return 8000;
		if(fuel.getItem().equals(ModItems.powder_coal))
			return 1600;
		if(fuel.getItem().equals(ModItems.scrap))
			return 800;
		if(fuel.getItem().equals(ModItems.dust))
			return 400;
		if(fuel.getItem().equals(ModItems.powder_fire))
			return 6400;
		if(fuel.getItem().equals(Item.getItemFromBlock(ModBlocks.block_scrap)))
			return 4000;
		
		return 0;
	}

}
