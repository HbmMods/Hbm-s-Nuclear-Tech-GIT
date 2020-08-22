package com.hbm.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class RadiationConfig {

	public static int rain = 0;
	public static int cont = 0;
	public static int fogRad = 100;
	public static int fogCh = 20;
	public static float hellRad = 0.1F;
	public static int worldRad = 10;
	public static int worldRadThreshold = 20;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_NUKE = "06_explosions";

		Property radRain = config.get(CATEGORY_NUKE, "6.05_falloutRainDuration", 0);
		radRain.comment = "Duration of the thunderstorm after fallout in ticks (only large explosions)";
		rain = radRain.getInt();
		Property rainCont = config.get(CATEGORY_NUKE, "6.06_falloutRainRadiation", 0);
		rainCont.comment = "Radiation in 100th RADs created by fallout rain";
		cont = rainCont.getInt();
		Property fogThresh = config.get(CATEGORY_NUKE, "6.07_fogThreshold", 100);
		fogThresh.comment = "Radiation in RADs required for fog to spawn";
		fogRad = fogThresh.getInt();
		Property fogChance = config.get(CATEGORY_NUKE, "6.08_fogChance", 10);
		fogChance.comment = "1:n chance of fog spawning every second";
		fogCh = fogChance.getInt();
		Property netherRad = config.get(CATEGORY_NUKE, "6.09_netherRad", 10);
		netherRad.comment = "RAD/s in the nether in hundredths";
		hellRad = netherRad.getInt() * 0.01F;
		worldRad = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "6.10_worldRadCount", "How many block operations radiation can perform per tick", 10);
		worldRadThreshold = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "6.11_worldRadThreshold", "The least amount of RADs required for block modification to happen", 20);
		
		fogCh = CommonConfig.setDef(fogCh, 20);
	}
}
