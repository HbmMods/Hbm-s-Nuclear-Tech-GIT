package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class MachineConfig {
	public enum EnumSingGenFail
	{
		NONE,// Do not meltdown
		CAP_CUSTOM,// Custom cap, default 100
		NO_CAP;// No cap at all
	}
	public static int singGenFailRadius = 100;
	public static EnumSingGenFail singGenFailType = EnumSingGenFail.CAP_CUSTOM;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_MACHINE = "09_machines";
		int singGen = CommonConfig.createConfigInt(config, CATEGORY_MACHINE, "9.00_singGenFail", "When a Watz Super-Compressor fails, what is the radius cap of the potential fölkvangr field? 0 means no fail and -1 means no cap at all", 100);
		switch(singGen)
		{
		case 0:
			singGenFailRadius = 0;
			singGenFailType = EnumSingGenFail.NONE;
			break;
		case -1:
			singGenFailType = EnumSingGenFail.NO_CAP;
			break;
		default:
			singGenFailRadius = singGen;
			singGenFailType = EnumSingGenFail.CAP_CUSTOM;
			break;
		}
	}

}
