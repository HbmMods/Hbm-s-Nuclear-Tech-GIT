package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class MobConfig {

	public static boolean enableMaskman = true;
	public static int maskmanDelay = 60 * 60 * 60;
	public static int maskmanChance = 3;
	public static int maskmanMinRad = 50;
	public static boolean maskmanUnderground = true;
	
	public static boolean enableRaids = false;
	public static int raidDelay = 30 * 60 * 60;
	public static int raidChance = 3;
	public static int raidAmount = 15;
	public static int raidAttackDelay = 40;
	public static int raidAttackReach = 2;
	public static int raidAttackDistance = 32;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY = "12_mobs";

		enableMaskman = CommonConfig.createConfigBool(config, CATEGORY, "12.00_enableMaskman", "Whether mask man should spawn", true);
		maskmanDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.01_maskmanDelay", "How many world ticks need to pass for a check to be performed", 60 * 60 * 60);
		maskmanChance = CommonConfig.createConfigInt(config, CATEGORY, "12.02_maskmanChance", "1:x chance to spawn mask man, must be at least 1", 3);
		maskmanMinRad = CommonConfig.createConfigInt(config, CATEGORY, "12.03_maskmanMinRad", "The amount of radiation needed for mask man to spawn", 50);
		maskmanUnderground = CommonConfig.createConfigBool(config, CATEGORY, "12.04_maskmanUnderound", "Whether players need to be underground for mask man to spawn", true);

		enableRaids = CommonConfig.createConfigBool(config, CATEGORY, "12.05_enableFBIRaids", "Whether there should be FBI raids", false);
		raidDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.06_raidDelay", "How many world ticks need to pass for a check to be performed", 30 * 60 * 60);
		raidChance = CommonConfig.createConfigInt(config, CATEGORY, "12.07_raidChance", "1:x chance to spawn a raid, must be at least 1", 3);
		raidAmount = CommonConfig.createConfigInt(config, CATEGORY, "12.08_raidAmount", "How many FBI agents are spawned each raid", 15);
		raidAttackDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.09_raidAttackDelay", "Time between individual attempts to break machines", 40);
		raidAttackReach = CommonConfig.createConfigInt(config, CATEGORY, "12.10_raidAttackReach", "How far away machines can be broken", 2);
		raidAttackDistance = CommonConfig.createConfigInt(config, CATEGORY, "12.11_raidAttackDistance", "How far away agents will spawn from the targeted player", 32);

	}
}
