package com.hbm.config;

import com.hbm.items.ModItems;

import net.minecraft.item.Item;

public class VersatileConfig {
	
	public static Item getTransmutatorItem() {
		
		if(GeneralConfig.enableBabyMode)
			return ModItems.ingot_schrabidium;

		return ModItems.ingot_schraranium;
	}
	
	public static int getSchrabOreChance() {
		
		if(GeneralConfig.enableBabyMode)
			return 20;
		
		return 100;
	}

}
