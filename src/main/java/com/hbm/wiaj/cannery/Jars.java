package com.hbm.wiaj.cannery;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumPlantType;

public class Jars {

	public static HashMap<ComparableStack, CanneryBase> canneries = new HashMap<ComparableStack, CanneryBase>();
	
	public static void initJars() {
		canneries.put(new ComparableStack(ModBlocks.heater_firebox), new CanneryFirebox());
		canneries.put(new ComparableStack(ModBlocks.machine_stirling), new CanneryStirling());
		canneries.put(new ComparableStack(ModBlocks.machine_stirling_steel), new CanneryStirling());
		canneries.put(new ComparableStack(ModBlocks.machine_gascent), new CanneryCentrifuge());
		canneries.put(new ComparableStack(ModBlocks.machine_fensu), new CanneryFEnSU());
		canneries.put(new ComparableStack(ModBlocks.machine_fel), new CannerySILEX());
		canneries.put(new ComparableStack(ModBlocks.machine_silex), new CannerySILEX());
		canneries.put(new ComparableStack(ModBlocks.foundry_channel), new CanneryFoundryChannel());
		canneries.put(new ComparableStack(ModBlocks.machine_crucible), new CanneryCrucible());
		canneries.put(new ComparableStack(ModBlocks.hadron_core), new CanneryHadron());
		canneries.put(new ComparableStack(ModBlocks.hadron_diode), new CannerySchottky());

		canneries.put(new ComparableStack(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.MUSTARDWILLOW)), new CanneryWillow());
		canneries.put(new ComparableStack(DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.CD0)), new CanneryWillow());
	}
}
