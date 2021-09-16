package com.hbm.hazard;

import com.hbm.hazard.type.HazardTypeBase;
import com.hbm.hazard.type.HazardTypeRadiation;
import com.hbm.items.special.ItemHazard;

public class HazardRegistry {

	public static final HazardTypeBase RADIATION = new HazardTypeRadiation();
	
	public static void registerItems() {
		HazardSystem.register("ingotPlutonium", makeData(RADIATION, ItemHazard.pu * ItemHazard.ingot));
		//TODO: move all the itemhazard stuff here
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	public static void reloadSystem() {
		HazardSystem.oreMap.clear();
		HazardSystem.itemMap.clear();
		HazardSystem.stackMap.clear();
		registerItems();
	}
}
