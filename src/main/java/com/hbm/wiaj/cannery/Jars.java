package com.hbm.wiaj.cannery;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.hbm.items.ItemEnums.EnumPlantType;

public class Jars {

	public static HashMap<ComparableStack, CanneryBase> canneries = new HashMap();
	
	public static void initJars() {
		
		addCannery(ModBlocks.heater_firebox, new CanneryFirebox());
		addCannery(ModBlocks.machine_stirling, new CanneryStirling());
		addCannery(ModBlocks.machine_stirling_steel, new CanneryStirling());
		addCannery(ModBlocks.machine_gascent, new CanneryCentrifuge());
		addCannery(ModBlocks.machine_fensu, new CanneryFEnSU());
		addCannery(ModBlocks.machine_fel, new CannerySILEX());
		addCannery(ModBlocks.machine_silex, new CannerySILEX());
		addCannery(ModBlocks.foundry_channel, new CanneryFoundryChannel());
		addCannery(ModBlocks.machine_crucible, new CanneryCrucible());

		addCannery(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.MUSTARDWILLOW), new CanneryWillow());
		addCannery(DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.CD0), new CanneryWillow());
	}
	
	public static void addCannery(Block block, CanneryBase base) {
		try {
			canneries.put(ComparableStack.getComparableStack(block), base);
		} catch(Exception e) {}
	}
	
	public static void addCannery(ItemStack stack, CanneryBase base) {
		if(stack != null)
			try {
				canneries.put(ComparableStack.getComparableStack(stack), base);
			} catch(Exception e) {}
	}
}
