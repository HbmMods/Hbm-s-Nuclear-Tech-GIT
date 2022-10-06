package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		
		int single = 200;

		if(fuel.getItem().equals(ModItems.solid_fuel))						return single * 16;
		if(fuel.getItem().equals(ModItems.solid_fuel_presto))				return single * 40;
		if(fuel.getItem().equals(ModItems.solid_fuel_presto_triplet))		return single * 200;
		if(fuel.getItem().equals(ModItems.solid_fuel_bf))					return single * 160;
		if(fuel.getItem().equals(ModItems.solid_fuel_presto_bf))			return single * 400;
		if(fuel.getItem().equals(ModItems.solid_fuel_presto_triplet_bf))	return single * 2000;
		if(fuel.getItem().equals(ModItems.rocket_fuel))						return single * 32;
		
		if(fuel.getItem().equals(ModItems.biomass)) return 800;
		if(fuel.getItem().equals(ModItems.biomass_compressed)) return 2400;
		if(fuel.getItem().equals(ModItems.powder_coal)) return 1600;
		if(fuel.getItem().equals(ModItems.scrap)) return 800;
		if(fuel.getItem().equals(ModItems.dust)) return 400;
		if(fuel.getItem().equals(ModItems.powder_fire)) return 6400;
		if(fuel.getItem().equals(Item.getItemFromBlock(ModBlocks.block_scrap))) return 4000;
		if(fuel.getItem() == ModItems.lignite) return 1200;
		if(fuel.getItem() == ModItems.powder_lignite) return 1200;
		if(fuel.getItem() == ModItems.briquette_lignite) return 1600;
		if(fuel.getItem() == ModItems.coke) return 3200;
		if(fuel.getItem() == ModItems.book_guide) return 800;
		if(fuel.getItem() == ModItems.coal_infernal) return 4800;
		if(fuel.getItem() == ModItems.crystal_coal) return 6400;
		if(fuel.getItem() == ModItems.powder_sawdust) return 100;
		
		return 0;
	}

}
