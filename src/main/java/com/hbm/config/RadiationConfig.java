package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class RadiationConfig {

	public static int fogRad = 100;
	public static int fogCh = 20;
	public static double hellRad = 0.1;
	public static int worldRad = 10;
	public static int worldRadThreshold = 20;
	public static boolean worldRadEffects = true;
	public static boolean cleanupDeadDirt = false;

	public static boolean enableContamination = true;
	public static boolean enableChunkRads = true;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_NUKE = CommonConfig.CATEGORY_RADIATION;

		fogRad = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "FOG_00_threshold", "Radiation in RADs required for fog to spawn", 100);
		fogCh = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "FOG_01_threshold", "1:n chance of fog spawning every second", 20);
		hellRad = CommonConfig.createConfigDouble(config, CATEGORY_NUKE, "AMBIENT_00_nether", "RAD/s in the nether", 0.1D);
		worldRadEffects = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADWORLD_00_toggle", "Whether high radiation levels should perform changes in the world", true);
		worldRad = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "RADWORLD_01_amount", "How many block operations radiation can perform per tick", 10);
		worldRadThreshold = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "RADWORLD_02_minimum", "The least amount of RADs required for block modification to happen", 20);
		cleanupDeadDirt = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADWORLD_03_regrow", "Whether dead grass and mycelium should decay into dirt", false);

		enableContamination = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADIATION_00_enableContamination", "Toggles player contamination (and negative effects from radiation poisoning)", true);
		enableChunkRads = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADIATION_01_enableChunkRads", "Toggles the world radiation system (chunk radiation only, some blocks use an AoE!)", true);
		
		fogCh = CommonConfig.setDef(fogCh, 20);
	}
}
