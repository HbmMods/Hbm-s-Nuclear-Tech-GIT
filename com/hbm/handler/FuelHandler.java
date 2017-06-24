package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {

		if(fuel.getItem().equals(ModItems.canister_oil))
			return 3200;
		if(fuel.getItem().equals(ModItems.canister_smear))
			return 6400;
		if(fuel.getItem().equals(ModItems.canister_reoil))
			return 9600;
		if(fuel.getItem().equals(ModItems.canister_petroil))
			return 9200;
		if(fuel.getItem().equals(ModItems.canister_canola))
			return 4800;
		if(fuel.getItem().equals(ModItems.canister_fuel))
			return 3200;
		if(fuel.getItem().equals(ModItems.canister_kerosene))
			return 2400;
		if(fuel.getItem().equals(ModItems.powder_coal))
			return 1600;
		if(fuel.getItem().equals(ModItems.scrap))
			return 800;
		if(fuel.getItem().equals(ModItems.dust))
			return 400;
		if(fuel.getItem().equals(ModItems.powder_fire))
			return 6400;
		if(fuel.getItem().equals(Item.getItemFromBlock(ModBlocks.red_barrel)))
			return 32000;
		if(fuel.getItem().equals(Item.getItemFromBlock(ModBlocks.block_scrap)))
			return 8000;
		
		return 0;
	}

}
