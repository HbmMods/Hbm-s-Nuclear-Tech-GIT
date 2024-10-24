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
	public static boolean enableMobWeapons = true;
	
	public static boolean enableHives = true;
	public static int hiveSpawn = 256;
	public static double scoutThreshold = 5;
	public static int scoutSwarmSpawnChance = 2;
	public static boolean waypointDebug = false;
	public static int largeHiveChance = 5;
	public static int largeHiveThreshold = 30;

	public static int swarmCooldown = 120 * 20;

	public static int baseSwarmSize = 5;
	public static double swarmScalingMult = 1.2;
	public static int sootStep = 50;

	public static int[] glyphidChance = {50, -40, 0};
	public static int[] brawlerChance = {5, 35, 1};
	public static int[] bombardierChance = {20, -15, 1};
	public static int[] blasterChance = {-15, 40, 5};
	public static int[] diggerChance = {-15, 25, 5};
	public static int[] behemothChance = {-30, 45, 10};
	public static int[] brendaChance = {-50, 60, 20};
	public static int[] johnsonChance = {-50, 60, 50};

	public static double spawnMax = 50;
	public static boolean enableInfestation = true;
	public static double baseInfestChance = 5;
	public static double targetingThreshold = 1;

	public static boolean rampantMode = false;
	public static boolean rampantNaturalScoutSpawn = false;
	public static double rampantScoutSpawnThresh = 14;
	public static int rampantScoutSpawnChance = 1400;
	public static boolean scoutInitialSpawn = false;
	public static boolean rampantExtendedTargetting = false;
	public static boolean rampantDig = false;
	public static boolean rampantGlyphidGuidance = false;
	public static double rampantSmokeStackOverride = 0.4;
	public static double pollutionMult = 3;
	
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
		enableMobWeapons = CommonConfig.createConfigBool(config, CATEGORY, "12.D02_enableMobWeapons", "Whether skeletons should have bows replaced with guns when spawning at higher soot levels", true);

		enableHives = CommonConfig.createConfigBool(config, CATEGORY, "12.G00_enableHives", "Whether glyphid hives should spawn", true);
		hiveSpawn = CommonConfig.createConfigInt(config, CATEGORY, "12.G01_hiveSpawn", "The average amount of chunks per hive", 256);
		scoutThreshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G02_scoutThreshold", "Minimum amount of soot for scouts to spawn", 1);
		spawnMax = CommonConfig.createConfigDouble(config, CATEGORY, "12.G07_spawnMax", "Maximum amount of glyphids being able to exist at once through natural spawning", 50);
		targetingThreshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G08_targetingThreshold", "Minimum amount of soot required for glyphids' extended targeting range to activate", 1D);

		scoutSwarmSpawnChance = CommonConfig.createConfigInt(config, CATEGORY,"12.G10_scoutSwarmSpawn", "How likely are scouts to spawn in swarms, 1 in x chance format", 3);

		largeHiveChance = CommonConfig.createConfigInt(config, CATEGORY,"12.G11_largeHiveChance", "The chance for a large hive to spawn, formula: 1/x", 5);
		largeHiveThreshold = CommonConfig.createConfigInt(config, CATEGORY,"12.G12_largeHiveThreshold", "The soot threshold for a large hive to spawn", 20);

		waypointDebug = CommonConfig.createConfigBool(config, CATEGORY,"12.G13_waypointDebug", "Allows glyphid waypoints to be seen, mainly used for debugging, also useful as an aid against them", false);

		//Infested structures
		enableInfestation= CommonConfig.createConfigBool(config, CATEGORY, "12.I01_enableInfestation", "Whether structures infested with glyphids should spawn", true);
		baseInfestChance = CommonConfig.createConfigDouble(config, CATEGORY, "12.I02_baseInfestChance", "The chance for infested structures to spawn", 5);

		//Glyphid spawn stuff
		config.addCustomCategoryComment(CATEGORY,
				"General Glyphid spawn logic configuration\n"
						+ "\n"
						+ "The first number is the base chance which applies at 0 soot,\n"
						+ "the second number is the modifier that applies with soot based on the formular below,\n"
						+ "the third number is a hard minimum of soot for this type to spawn.\n"
						+ "Negative base chances mean that glyphids won't spawn outright, negative modifiers mean that the type becomes less likely with higher soot.\n"
						+ "The formula for glyphid spawning chance is: (base chance + (modifier - modifier / max( (soot + 1)/3, 3 )))\n"
						+ "The formula for glyphid swarm scaling is: (baseSwarmSize * Math.max(swarmScalingMult * soot/sootStep, 1))");


		baseSwarmSize =  CommonConfig.createConfigInt(config, CATEGORY, "12.GS01_baseSwarmSize", "The basic, soot-less swarm size", 5);
		swarmScalingMult =  CommonConfig.createConfigDouble(config, CATEGORY, "12.GS02_swarmScalingMult", "By how much should swarm size scale by per soot amount determined below", 1.2);
		sootStep =  CommonConfig.createConfigInt(config, CATEGORY, "12.GS03_sootStep", "The soot amount the above multiplier applies to the swarm size", 50);
		swarmCooldown =  CommonConfig.createConfigInt(config, CATEGORY, "12.GS04_swarmCooldown", "How often do glyphid swarms spawn, in seconds", 120) * 20;

		glyphidChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC01_glyphidChance", "Base Spawn chance and soot modifier for a glyphid grunt", new int[]{50, -45, 0});
		brawlerChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC02_brawlerChance", "Base Spawn chance and soot modifier for a glyphid brawler", new int[]{10, 30, 1});
		bombardierChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC03_bombardierChance", "Base Spawn chance and soot modifier for a glyphid bombardier", new int[]{20, -15, 1});
		blasterChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC04_blasterChance", "Base Spawn chance and soot modifier for a glyphid blaster", new int[]{-5, 40, 5});
		diggerChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC05_diggerChance", "Base Spawn chance and soot modifier for a glyphid digger", new int[]{-15, 25, 5});
		behemothChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC06_behemothChance", "Base Spawn chance and soot modifier for a glyphid behemoth", new int[]{-30, 45, 10});
		brendaChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC07_brendaChance", "Base Spawn chance and soot modifier for a glyphid brenda", new int[]{-50, 60, 20});
		johnsonChance = CommonConfig.createConfigIntList(config, CATEGORY, "12.GC08_johnsonChance", "Base Spawn chance and soot modifier for Big Man Johnson", new int[]{-50, 60, 50});

		String rampantDesc = "Rampant Mode changes glyphid behavior and spawning to be more aggressive, changes include:\n"
				+ "\n"
				+ "Glyphid Scouts will naturally spawn alongside normal mobs if soot levels are above a certain threshold\n"
				+ "Glyphids will always have the extended targetting enabled\n"
				+ "Glyphids can dig to waypoints\n"
				+ "The Glyphids will expand always toward your base\n"
				+ "Scouts will spawn from the start, making glyphids start expanding off the bat\n"
				+ "Smokestacks have reduced efficiency, only reducing soot by 40%\n";

		config.addCustomCategoryComment(CATEGORY,rampantDesc);

		rampantMode = CommonConfig.createConfigBool(config, CATEGORY, "12.R01_rampantMode", "The main rampant mode toggle, enables all other features associated with it", false);

		config.addCustomCategoryComment(CATEGORY, "The individual features of rampant can be used regardless of whether the main rampant toggle is enabled or not");

		rampantNaturalScoutSpawn = CommonConfig.createConfigBool(config, CATEGORY,"12.R02_rampantScoutSpawn", "Whether scouts should spawn natually in highly polluted chunks", false);
		rampantScoutSpawnThresh = CommonConfig.createConfigDouble(config, CATEGORY, "12.R02.1_rampantScoutSpawnThresh", "How much soot is needed for scouts to naturally spawn", 13);
		rampantScoutSpawnChance = CommonConfig.createConfigInt(config, CATEGORY, "12.R02.2_rampantScoutSpawnChance", "How often scouts naturally spawn per mob population, 1/x format, the bigger the number, the more uncommon the scouts", 1400);
		rampantExtendedTargetting = CommonConfig.createConfigBool(config, CATEGORY,"12.R03_rampantExtendedTargeting", "Whether Glyphids should have the extended targetting always enabled", false);
		rampantDig = CommonConfig.createConfigBool(config, CATEGORY,"12.R04_rampantDig", "Whether Glyphids should be able to dig to waypoints", false);
		rampantGlyphidGuidance = CommonConfig.createConfigBool(config, CATEGORY,"12.R05_rampantGlyphidGuidance", "Whether Glyphids should always expand toward a player's spawnpoint", false);
		rampantSmokeStackOverride = CommonConfig.createConfigDouble(config, CATEGORY, "12.R06_rampantSmokeStackOverride", "How much should the smokestack multiply soot by when on rampant mode", 0.4);
		scoutInitialSpawn = CommonConfig.createConfigBool(config, CATEGORY,"12.R07_scoutInitialSpawn", "Whether glyphid scouts should be able to spawn on the first swarm of a hive, causes glyphids to expand significantly faster", false);
		pollutionMult = CommonConfig.createConfigDouble(config, CATEGORY, "12.R08_pollutionMult", "A multiplier for soot emitted, whether you want to increase or decrease it", 1);

		if(rampantMode){
			rampantNaturalScoutSpawn = true;
			rampantExtendedTargetting = true;
			rampantDig = true;
			rampantGlyphidGuidance = true;
			scoutSwarmSpawnChance = 1;
			scoutThreshold = 0.1;
			if(pollutionMult == 1) {
				pollutionMult = 3;
			}
			if (bombardierChance[2] == 1){
				bombardierChance[2] = 0;
			}
			RadiationConfig.sootFogThreshold *= pollutionMult;
		}
	}
}
