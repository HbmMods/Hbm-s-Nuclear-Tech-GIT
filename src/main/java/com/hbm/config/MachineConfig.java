package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class MachineConfig {
	
	public static boolean scaleRTGPower = false;
	public static boolean doRTGsDecay = true;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_MACHINE = "09_machines";
		
		scaleRTGPower = CommonConfig.createConfigBool(config, CATEGORY_MACHINE, "9.00_scaleRTGPower", "Should RTG/Betavoltaic fuel power scale down as it decays?", false);
		doRTGsDecay = CommonConfig.createConfigBool(config, CATEGORY_MACHINE, "9.01_doRTGsDecay", "Should RTG/Betavoltaic fuel decay at all?", true);
		
		if(VersatileConfig.rtgDecay()) {
			scaleRTGPower = true;
			doRTGsDecay = true;
		}
	}

}
