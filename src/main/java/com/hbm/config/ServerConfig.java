package com.hbm.config;

import java.io.File;
import java.util.HashMap;

import com.google.gson.Gson;

public class ServerConfig extends RunningConfig {

	public static final Gson gson = new Gson();
	public static HashMap<String, ConfigWrapper> configMap = new HashMap();

	public static ConfigWrapper<Boolean> DAMAGE_COMPATIBILITY_MODE =	new ConfigWrapper(false);
	public static ConfigWrapper<Float> MINE_AP_DAMAGE =					new ConfigWrapper(10F);
	public static ConfigWrapper<Float> MINE_HE_DAMAGE =					new ConfigWrapper(35F);
	public static ConfigWrapper<Float> MINE_SHRAP_DAMAGE =				new ConfigWrapper(7.5F);
	public static ConfigWrapper<Float> MINE_NUKE_DAMAGE =				new ConfigWrapper(100F);
	public static ConfigWrapper<Float> MINE_NAVAL_DAMAGE =				new ConfigWrapper(60F);
	public static ConfigWrapper<Boolean> TAINT_TRAILS =					new ConfigWrapper(false);
	public static ConfigWrapper<Boolean> CRATE_OPEN_HELD =				new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> CRATE_KEEP_CONTENTS =			new ConfigWrapper(true);
	public static ConfigWrapper<Integer> ITEM_HAZARD_DROP_TICKRATE =	new ConfigWrapper(2);

	private static void initDefaults() {
		configMap.put("DAMAGE_COMPATIBILITY_MODE", DAMAGE_COMPATIBILITY_MODE);
		configMap.put("MINE_AP_DAMAGE", MINE_AP_DAMAGE);
		configMap.put("MINE_HE_DAMAGE", MINE_HE_DAMAGE);
		configMap.put("MINE_SHRAP_DAMAGE", MINE_SHRAP_DAMAGE);
		configMap.put("MINE_NUKE_DAMAGE", MINE_NUKE_DAMAGE);
		configMap.put("MINE_NAVAL_DAMAGE", MINE_NAVAL_DAMAGE);
		configMap.put("TAINT_TRAILS", TAINT_TRAILS);
		configMap.put("CRATE_OPEN_HELD", CRATE_OPEN_HELD);
		configMap.put("CRATE_KEEP_CONTENTS", CRATE_KEEP_CONTENTS);
		configMap.put("ITEM_HAZARD_DROP_TICKRATE", ITEM_HAZARD_DROP_TICKRATE);
	}

	/** Initializes defaults, then reads the config file if it exists, then writes the config file. */
	public static void initConfig() {
		initDefaults();
		File config = getConfig("hbmServer.json");
		if(config.exists()) readConfig(config);
		refresh();
	}

	/** Writes over the config file using the running config. */
	public static void refresh() {
		File config = getConfig("hbmServer.json");
		writeConfig(config);
	}

	/** Writes over the running config using the config file. */
	public static void reload() {
		File config = getConfig("hbmServer.json");
		if(config.exists()) readConfig(config);
	}

	private static void readConfig(File config) {
		RunningConfig.readConfig(config, configMap);
	}

	private static void writeConfig(File config) {
		RunningConfig.writeConfig(config, configMap, "This file can be edited ingame using the /ntmserver command.");
	}
}
