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
	public static int raidDrones = 5;
	public static int raidAttackDelay = 40;
	public static int raidAttackReach = 2;
	public static int raidAttackDistance = 32;

	public static boolean enableElementals = true;
	public static int elementalDelay = 30 * 60 * 60;
	public static int elementalChance = 2;
	public static int elementalAmount = 10;
	public static int elementalDistance = 32;

	public static boolean enableDucks = true;
	public static boolean enableMobGear = true;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY = CommonConfig.CATEGORY_MOBS;

		enableMaskman = CommonConfig.createConfigBool(config, CATEGORY, "12.M00_enableMaskman", "Whether mask man should spawn", true);
		maskmanDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.M01_maskmanDelay", "How many world ticks need to pass for a check to be performed", 60 * 60 * 60);
		maskmanChance = CommonConfig.createConfigInt(config, CATEGORY, "12.M02_maskmanChance", "1:x chance to spawn mask man, must be at least 1", 3);
		maskmanMinRad = CommonConfig.createConfigInt(config, CATEGORY, "12.M03_maskmanMinRad", "The amount of radiation needed for mask man to spawn", 50);
		maskmanUnderground = CommonConfig.createConfigBool(config, CATEGORY, "12.M04_maskmanUnderound", "Whether players need to be underground for mask man to spawn", true);

		enableRaids = CommonConfig.createConfigBool(config, CATEGORY, "12.F00_enableFBIRaids", "Whether there should be FBI raids", false);
		raidDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.F01_raidDelay", "How many world ticks need to pass for a check to be performed", 30 * 60 * 60);
		raidChance = CommonConfig.createConfigInt(config, CATEGORY, "12.F02_raidChance", "1:x chance to spawn a raid, must be at least 1", 3);
		raidAmount = CommonConfig.createConfigInt(config, CATEGORY, "12.F03_raidAmount", "How many FBI agents are spawned each raid", 15);
		raidAttackDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.F04_raidAttackDelay", "Time between individual attempts to break machines", 40);
		raidAttackReach = CommonConfig.createConfigInt(config, CATEGORY, "12.F05_raidAttackReach", "How far away machines can be broken", 2);
		raidAttackDistance = CommonConfig.createConfigInt(config, CATEGORY, "12.F06_raidAttackDistance", "How far away agents will spawn from the targeted player", 32);
		raidDrones = CommonConfig.createConfigInt(config, CATEGORY, "12.F07_raidDrones", "How many quadcopter drones are spawned each raid", 5);

		enableElementals = CommonConfig.createConfigBool(config, CATEGORY, "12.E00_enableMeltdownElementals", "Whether there should be radiation elementals", true);
		elementalDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.E01_elementalDelay", "How many world ticks need to pass for a check to be performed", 30 * 60 * 60);
		elementalChance = CommonConfig.createConfigInt(config, CATEGORY, "12.E02_elementalChance", "1:x chance to spawn elementals, must be at least 1", 2);
		elementalAmount = CommonConfig.createConfigInt(config, CATEGORY, "12.E03_elementalAmount", "How many elementals are spawned each raid", 10);
		elementalDistance = CommonConfig.createConfigInt(config, CATEGORY, "12.E04_elementalAttackDistance", "How far away elementals will spawn from the targeted player", 32);
		
		enableDucks = CommonConfig.createConfigBool(config, CATEGORY, "12.D00_enableDucks", "Whether pressing O should allow the player to duck", true);
		enableMobGear = CommonConfig.createConfigBool(config, CATEGORY, "12.D01_enableMobGear", "Whether zombies and skeletons should have additional gear when spawning", true);
	}
}
