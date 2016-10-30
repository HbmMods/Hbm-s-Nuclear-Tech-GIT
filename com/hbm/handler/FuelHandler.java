package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {

		int i = 200;
		
		if(fuel.getItem().equals(ModItems.canister_fuel))
<<<<<<< HEAD
			return i * 16;
		if(fuel.getItem().equals(ModItems.powder_coal))
			return i * 8;
		if(fuel.getItem().equals(ModItems.scrap))
			return i * 4;
		if(fuel.getItem().equals(ModItems.dust))
			return i * 2;
		if(fuel.getItem().equals(ModItems.powder_fire))
			return i * 32;
		if(fuel.getItem().equals(Item.getItemFromBlock(ModBlocks.block_scrap)))
			return i * 20;
=======
			return 3200;
		if(fuel.getItem().equals(ModItems.powder_coal))
			return 1600;
		if(fuel.getItem().equals(ModItems.scrap))
			return 800;
		if(fuel.getItem().equals(ModItems.dust))
			return 400;
		if(fuel.getItem().equals(ModItems.powder_fire))
			return 6400;
>>>>>>> 540fb3d256a0f4ae6a8b1db586f8e9cfd6ed7372
		
		return 0;
	}

}
