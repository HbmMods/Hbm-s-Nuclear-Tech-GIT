package com.hbm.wiaj.cannery;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;

public class Jars {

	public static HashMap<ComparableStack, CanneryBase> canneries = new HashMap();
	
	static {
		canneries.put(new ComparableStack(ModBlocks.heater_firebox), new CanneryFirebox());
		canneries.put(new ComparableStack(ModBlocks.machine_stirling), new CanneryStirling());
		canneries.put(new ComparableStack(ModBlocks.machine_stirling_steel), new CanneryStirling());
		canneries.put(new ComparableStack(ModBlocks.machine_gascent), new CanneryCentrifuge());
		canneries.put(new ComparableStack(ModBlocks.machine_fensu), new CanneryFEnSU());
		canneries.put(new ComparableStack(ModBlocks.machine_fel), new CannerySILEX());
		canneries.put(new ComparableStack(ModBlocks.machine_silex), new CannerySILEX());
	}
}
