package com.hbm.config;

import com.google.gson.Gson;
import com.hbm.util.Compat;

import java.io.File;
import java.util.HashMap;

// https://youtube.com/shorts/XTHZWqZt_AI
public class ClientConfig extends RunningConfig {

	public static final Gson gson = new Gson();
	public static HashMap<String, ConfigWrapper> configMap = new HashMap();

	//separate fields because they are a tad faster than using a hashmap and also because using them is less verbose
	public static ConfigWrapper<Integer> GEIGER_OFFSET_HORIZONTAL =			new ConfigWrapper(0);
	public static ConfigWrapper<Integer> GEIGER_OFFSET_VERTICAL =			new ConfigWrapper(0);
	public static ConfigWrapper<Integer> INFO_OFFSET_HORIZONTAL =			new ConfigWrapper(0);
	public static ConfigWrapper<Integer> INFO_OFFSET_VERTICAL =				new ConfigWrapper(0);
	public static ConfigWrapper<Integer> INFO_POSITION =					new ConfigWrapper(0);
	public static ConfigWrapper<Boolean> GUN_ANIMS_LEGACY =					new ConfigWrapper(false);
	public static ConfigWrapper<Boolean> GUN_MODEL_FOV =					new ConfigWrapper(false);
	public static ConfigWrapper<Boolean> GUN_VISUAL_RECOIL =				new ConfigWrapper(true);
	public static ConfigWrapper<Double> GUN_ANIMATION_SPEED =				new ConfigWrapper(1D);
	public static ConfigWrapper<Boolean> ITEM_TOOLTIP_SHOW_OREDICT =		new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> ITEM_TOOLTIP_SHOW_CUSTOM_NUKE =	new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> MAIN_MENU_WACKY_SPLASHES =			new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> DODD_RBMK_DIAGNOSTIC =				new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> RENDER_CABLE_HANG =				new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> NUKE_HUD_FLASH =					new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> NUKE_HUD_SHAKE =					new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> RENDER_REEDS =						new ConfigWrapper(!Compat.isModLoaded(Compat.MOD_ANG));
	public static ConfigWrapper<Boolean> NEI_HIDE_SECRETS =					new ConfigWrapper(true);

	private static void initDefaults() {
		configMap.put("GEIGER_OFFSET_HORIZONTAL", GEIGER_OFFSET_HORIZONTAL);
		configMap.put("GEIGER_OFFSET_VERTICAL", GEIGER_OFFSET_VERTICAL);
		configMap.put("INFO_OFFSET_HORIZONTAL", INFO_OFFSET_HORIZONTAL);
		configMap.put("INFO_OFFSET_VERTICAL", INFO_OFFSET_VERTICAL);
		configMap.put("INFO_POSITION", INFO_POSITION);
		configMap.put("GUN_ANIMS_LEGACY", GUN_ANIMS_LEGACY);
		configMap.put("GUN_MODEL_FOV", GUN_MODEL_FOV);
		configMap.put("GUN_VISUAL_RECOIL", GUN_VISUAL_RECOIL);
		configMap.put("GUN_ANIMATION_SPEED", GUN_ANIMATION_SPEED);
		configMap.put("ITEM_TOOLTIP_SHOW_OREDICT", ITEM_TOOLTIP_SHOW_OREDICT);
		configMap.put("ITEM_TOOLTIP_SHOW_CUSTOM_NUKE", ITEM_TOOLTIP_SHOW_CUSTOM_NUKE);
		configMap.put("MAIN_MENU_WACKY_SPLASHES", MAIN_MENU_WACKY_SPLASHES);
		configMap.put("DODD_RBMK_DIAGNOSTIC", DODD_RBMK_DIAGNOSTIC);
		configMap.put("RENDER_CABLE_HANG", RENDER_CABLE_HANG);
		configMap.put("NUKE_HUD_FLASH", NUKE_HUD_FLASH);
		configMap.put("NUKE_HUD_SHAKE", NUKE_HUD_SHAKE);
		configMap.put("RENDER_REEDS", RENDER_REEDS);
		configMap.put("NEI_HIDE_SECRETS", NEI_HIDE_SECRETS);
	}

	/** Initializes defaults, then reads the config file if it exists, then writes the config file. */
	public static void initConfig() {
		initDefaults();
		File config = getConfig("hbmClient.json");
		if(config.exists()) readConfig(config);
		refresh();
	}

	/** Writes over the config file using the running config. */
	public static void refresh() {
		File config = getConfig("hbmClient.json");
		writeConfig(config);
	}

	/** Writes over the running config using the config file. */
	public static void reload() {
		File config = getConfig("hbmClient.json");
		if(config.exists()) readConfig(config);
	}

	private static void readConfig(File config) {
		RunningConfig.readConfig(config, configMap);
	}

	private static void writeConfig(File config) {
		RunningConfig.writeConfig(config, configMap, "This file can be edited ingame using the /ntmclient command.");
	}
}
