package com.hbm.handler;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

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
		
		if(fuel.getItem() == ModItems.biomass)								return single * 2;
		if(fuel.getItem() == ModItems.biomass_compressed)					return single * 4;
		if(fuel.getItem() == ModItems.powder_coal)							return single * 8;
		if(fuel.getItem() == ModItems.scrap)								return single / 4;
		if(fuel.getItem() == ModItems.dust)									return single / 8;
		if(fuel.getItem() == Item.getItemFromBlock(ModBlocks.block_scrap))	return single * 2;
		if(fuel.getItem() == ModItems.powder_fire)							return 6400;
		if(fuel.getItem() == ModItems.lignite)								return 1200;
		if(fuel.getItem() == ModItems.powder_lignite)						return 1200;
		if(fuel.getItem() == ModItems.coke)									return single * 16;
		if(fuel.getItem() == ModItems.powder_coke)							return single * 16;
		if(fuel.getItem() == Item.getItemFromBlock(ModBlocks.block_coke))	return single * 160;
		if(fuel.getItem() == ModItems.book_guide)							return single;
		if(fuel.getItem() == ModItems.coal_infernal)						return 4800;
		if(fuel.getItem() == ModItems.crystal_coal)							return 6400;
		if(fuel.getItem() == ModItems.powder_sawdust)						return single / 2;
		
		if(fuel.getItem() == ModItems.briquette) {
			int meta = fuel.getItemDamage();
			switch(meta) {
			case 0: return single * 10;
			case 1: return single * 8;
			case 2: return single * 2;
			}
		}
		
		if(fuel.getItem() == ModItems.powder_ash) {
			int meta = fuel.getItemDamage();
			switch(meta) {
			case 0: return single / 2;
			case 1: return single;
			case 2: return single / 2;
			case 3: return single;
			case 4: return single / 2;
			}
		}
		
		return 0;
	}
	
	private static HashMap<ComparableStack, Integer> burnCache = new HashMap();
	
	public static int getBurnTimeFromCache(ItemStack stack) {
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		
		if(burnCache.containsKey(comp)) {
			return burnCache.get(comp);
		}
		
		int burnTime = TileEntityFurnace.getItemBurnTime(stack);
		burnCache.put(comp, burnTime);
		
		return burnTime;
	}
}
