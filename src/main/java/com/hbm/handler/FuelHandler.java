package com.hbm.handler;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class FuelHandler implements IFuelHandler
{
	public static final HashMap<Item, Integer> fuelMap = new HashMap<>();
	static final short single = 200;
	static
	{
		fuelMap.put(ModItems.solid_fuel, single * 16);
		fuelMap.put(ModItems.solid_fuel_presto, single * 40);
		fuelMap.put(ModItems.solid_fuel_presto_triplet, single * 200);
		fuelMap.put(ModItems.biomass, 800);
		fuelMap.put(ModItems.biomass_compressed, 2400);
		fuelMap.put(ModItems.powder_coal, 1600);
		fuelMap.put(ModItems.scrap, 800);
		fuelMap.put(ModItems.dust, 400);
		fuelMap.put(ModItems.powder_fire, 6400);
		fuelMap.put(Item.getItemFromBlock(ModBlocks.block_scrap), 4000);
		fuelMap.put(ModItems.lignite, 1200);
		fuelMap.put(ModItems.powder_lignite, 1200);
		fuelMap.put(ModItems.briquette_lignite, 1600);
		fuelMap.put(ModItems.coke, 3200);
		fuelMap.put(ModItems.book_guide, 800);
		fuelMap.put(ModItems.coal_infernal, 4800);
		fuelMap.put(ModItems.crystal_coal, 6400);
	}
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		return fuelMap.getOrDefault(fuel.getItem(), 0);
	}

}
