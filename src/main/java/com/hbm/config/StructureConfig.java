package com.hbm.config;

import com.hbm.main.MainRegistry;

import net.minecraftforge.common.config.Configuration;

public class StructureConfig {
	
	public static boolean enableStructures = true;
	
	public static int structureMinChunks = 8;
	public static int structureMaxChunks = 24;
	
	public static double lootAmountFactor = 1D;
	
	public static void loadFromConfig(Configuration config) {
		
		final String CATEGORY_STRUCTURES = CommonConfig.CATEGORY_STRUCTURES;
		enableStructures = CommonConfig.createConfigBool(config, CATEGORY_STRUCTURES, "5.00_enableStructures", "Switch for whether structures using the MapGenStructure system spawn.", true);
		
		structureMinChunks = CommonConfig.createConfigInt(config, CATEGORY_STRUCTURES, "5.01_structureMinChunks", "Minimum non-zero distance between structures in chunks (Settings lower than 8 may be problematic).", 8);
		structureMaxChunks = CommonConfig.createConfigInt(config, CATEGORY_STRUCTURES, "5.02_structureMaxChunks", "Maximum non-zero distance between structures in chunks.", 24);
		
		lootAmountFactor = CommonConfig.createConfigDouble(config, CATEGORY_STRUCTURES, "5.03_lootAmountFactor", "General factor for loot spawns. Applies to spawned IInventories, not loot blocks.", 1D);
		
		structureMinChunks = CommonConfig.setDef(structureMinChunks, 8);
		structureMaxChunks = CommonConfig.setDef(structureMaxChunks, 24);
		
		if(structureMinChunks > structureMaxChunks) {
			MainRegistry.logger.error("Fatal error config: Minimum value has been set higher than the maximum value!");
			MainRegistry.logger.error(String.format("Errored values will default back to %1$d and %2$d respectively, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", 8, 24));
			structureMinChunks = 8;
			structureMaxChunks = 24;
		}
			
	}
}
