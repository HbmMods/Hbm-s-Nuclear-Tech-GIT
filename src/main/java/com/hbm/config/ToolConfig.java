package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class ToolConfig {

	public static int recursionDepth = 500;
	public static boolean recursiveStone = true;
	public static boolean recursiveNetherrack = true;

	public static boolean abilityHammer = true;
	public static boolean abilityVein = true;
	public static boolean abilityLuck = true;
	public static boolean abilitySilk = true;
	public static boolean abilityFurnace = true;
	public static boolean abilityShredder = true;
	public static boolean abilityCentrifuge = true;
	public static boolean abilityCrystallizer = true;
	public static boolean abilityMercury = true;
	public static boolean abilityExplosion = true;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_TOOLS = CommonConfig.CATEGORY_TOOLS;
		recursionDepth = CommonConfig.createConfigInt(config, CATEGORY_TOOLS, "11.00_recursionDepth", "Limits veinminer's recursive function. Usually not an issue, unless you're using bukkit which is especially sensitive for some reason.", 1000);
		recursiveStone = CommonConfig.createConfigBool(config, CATEGORY_TOOLS, "11.01_recursionStone", "Determines whether veinminer can break stone", false);
		recursiveNetherrack = CommonConfig.createConfigBool(config, CATEGORY_TOOLS, "11.02_recursionNetherrack", "Determines whether veinminer can break netherrack", false);

		abilityHammer = config.get(CATEGORY_TOOLS, "11.03_hammerAbility", true).getBoolean(true);
		abilityVein = config.get(CATEGORY_TOOLS, "11.04_abilityVein", true).getBoolean(true);
		abilityLuck = config.get(CATEGORY_TOOLS, "11.05_abilityLuck", true).getBoolean(true);
		abilitySilk = config.get(CATEGORY_TOOLS, "11.06_abilitySilk", true).getBoolean(true);
		abilityFurnace = config.get(CATEGORY_TOOLS, "11.07_abilityFurnace", true).getBoolean(true);
		abilityShredder = config.get(CATEGORY_TOOLS, "11.08_abilityShredder", true).getBoolean(true);
		abilityCentrifuge = config.get(CATEGORY_TOOLS, "11.09_abilityCentrifuge", true).getBoolean(true);
		abilityCrystallizer = config.get(CATEGORY_TOOLS, "11.10_abilityCrystallizer", true).getBoolean(true);
		abilityMercury = config.get(CATEGORY_TOOLS, "11.11_abilityMercury", true).getBoolean(true);
		abilityExplosion = config.get(CATEGORY_TOOLS, "11.12_abilityExplosion", true).getBoolean(true);
	}
}
