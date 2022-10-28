package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class MachineConfig {
	
	protected static boolean scaleRTGPower = false;
	protected static boolean doRTGsDecay = true;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_MACHINE = CommonConfig.CATEGORY_MACHINES;
		
		scaleRTGPower = CommonConfig.createConfigBool(config, CATEGORY_MACHINE, "9.00_scaleRTGPower", "Should RTG/Betavoltaic fuel power scale down as it decays?", false);
		doRTGsDecay = CommonConfig.createConfigBool(config, CATEGORY_MACHINE, "9.01_doRTGsDecay", "Should RTG/Betavoltaic fuel decay at all?", true);
	}

}
