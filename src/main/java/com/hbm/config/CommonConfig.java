package com.hbm.config;

import java.util.Locale;

import com.hbm.main.MainRegistry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CommonConfig {
	
	public static final String CATEGORY_GENERAL = "01_general";
	public static final String CATEGORY_ORES = "02_ores";
	public static final String CATEGORY_NUKES = "03_nukes";
	public static final String CATEGORY_DUNGEONS = "04_dungeons";
	public static final String CATEGORY_METEORS = "05_meteors";
	public static final String CATEGORY_EXPLOSIONS = "06_explosions";
	public static final String CATEGORY_MISSILE = "07_missile_machines";
	public static final String CATEGORY_POTION = "08_potion_effects";
	public static final String CATEGORY_MACHINES = "09_machines";
	public static final String CATEGORY_DROPS = "10_dangerous_drops";
	public static final String CATEGORY_TOOLS = "11_tools";
	public static final String CATEGORY_MOBS = "12_mobs";
	public static final String CATEGORY_RADIATION = "13_radiation";
	public static final String CATEGORY_HAZARD = "14_hazard";
	public static final String CATEGORY_STRUCTURES = "15_structures";
	public static final String CATEGORY_BIOMES = "16_biomes";
	public static final String CATEGORY_DIMS = "17_dims";
	public static final String CATEGORY_POLLUTION = "18_pollution";
	public static final String CATEGORY_WEAPONS = "19_weapons";

	public static final String CATEGORY_528 = "528";
	public static final String CATEGORY_LBSM = "LESS BULLSHIT MODE";

	public static int setDefZero(int value, int def) {

		if(value < 0) {
			MainRegistry.logger.error("Fatal error config: Randomizer value has been below zero, despite bound having to be positive integer!");
			MainRegistry.logger.error(String.format(Locale.US, "Errored value will default back to %d, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", def));
			return def;
		}

		return value;
	}

	public static int setDef(int value, int def) {

		if(value <= 0) {
			MainRegistry.logger.error("Fatal error config: Randomizer value has been set to zero, despite bound having to be positive integer!");
			MainRegistry.logger.error(String.format(Locale.US, "Errored value will default back to %d, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", def));
			return def;
		}

		return value;
	}

	public static int createConfigInt(Configuration config, String category, String name, String comment, int def) {
		Property prop = config.get(category, name, def);
		prop.comment = comment;
		return prop.getInt();
	}

	public static double createConfigDouble(Configuration config, String category, String name, String comment, double def) {
		Property prop = config.get(category, name, def);
		prop.comment = comment;
		return prop.getDouble();
	}

	public static boolean createConfigBool(Configuration config, String category, String name, String comment, boolean def) {
		Property prop = config.get(category, name, def);
		prop.comment = comment;
		return prop.getBoolean();
	}

	public static String createConfigString(Configuration config, String category, String name, String comment, String def) {
		Property prop = config.get(category, name, def);
		prop.comment = comment;
		return prop.getString();
	}
    public static int[] createConfigIntList(Configuration config, String category, String name, String comment, int[] def){
		Property prop = config.get(category, name, def);
		prop.comment = comment;
		return prop.getIntList();
	}
	public static String[] createConfigStringList(Configuration config, String category, String name, String comment) {
		Property prop = config.get(category, name, new String[] { "PLACEHOLDER" });
		prop.comment = comment;
		return prop.getStringList();
	}

	public static int parseStructureFlag(String flag) {
		if(flag == null) flag = "";
		
		switch(flag.toLowerCase(Locale.US)) {
		case "true":
		case "on":
		case "yes":
			return 1;
		case "false":
		case "off":
		case "no":
			return 0;
		default:
			return 2;
		}
	}

}
