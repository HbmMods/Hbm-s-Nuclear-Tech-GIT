package com.hbm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;

// https://youtube.com/shorts/XTHZWqZt_AI
public class ClientConfig {

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
	public static ConfigWrapper<Boolean> ITEM_TOOLTIP_SHOW_OREDICT =		new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> ITEM_TOOLTIP_SHOW_CUSTOM_NUKE =	new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> MAIN_MENU_WACKY_SPLASHES =			new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> DODD_RBMK_DIAGNOSTIC =				new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> RENDER_CABLE_HANG =				new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> NUKE_HUD_FLASH =					new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> NUKE_HUD_SHAKE =					new ConfigWrapper(true);
	public static ConfigWrapper<Boolean> RENDER_REEDS =						new ConfigWrapper(!Compat.isModLoaded(Compat.MOD_ANG));
	
	private static void initDefaults() {
		configMap.put("GEIGER_OFFSET_HORIZONTAL", GEIGER_OFFSET_HORIZONTAL);
		configMap.put("GEIGER_OFFSET_VERTICAL", GEIGER_OFFSET_VERTICAL);
		configMap.put("INFO_OFFSET_HORIZONTAL", INFO_OFFSET_HORIZONTAL);
		configMap.put("INFO_OFFSET_VERTICAL", INFO_OFFSET_VERTICAL);
		configMap.put("INFO_POSITION", INFO_POSITION);
		configMap.put("GUN_ANIMS_LEGACY", GUN_ANIMS_LEGACY);
		configMap.put("GUN_MODEL_FOV", GUN_MODEL_FOV);
		configMap.put("GUN_VISUAL_RECOIL", GUN_VISUAL_RECOIL);
		configMap.put("ITEM_TOOLTIP_SHOW_OREDICT", ITEM_TOOLTIP_SHOW_OREDICT);
		configMap.put("ITEM_TOOLTIP_SHOW_OREDICT", ITEM_TOOLTIP_SHOW_CUSTOM_NUKE);
		configMap.put("MAIN_MENU_WACKY_SPLASHES", MAIN_MENU_WACKY_SPLASHES);
		configMap.put("DODD_RBMK_DIAGNOSTIC", DODD_RBMK_DIAGNOSTIC);
		configMap.put("RENDER_CABLE_HANG", RENDER_CABLE_HANG);
		configMap.put("NUKE_HUD_FLASH", NUKE_HUD_FLASH);
		configMap.put("NUKE_HUD_SHAKE", NUKE_HUD_SHAKE);
		configMap.put("RENDER_REEDS", RENDER_REEDS);
	}
	
	/** Initializes defaults, then reads the config file if it exists, then writes the config file. */
	public static void initConfig() {
		initDefaults();
		File folder = MainRegistry.configHbmDir;
		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmClient.json");
		if(config.exists()) readConfig(config);
		refresh();
	}
	
	/** Writes over the config file using the running config. */
	public static void refresh() {
		File folder = MainRegistry.configHbmDir;
		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmClient.json");
		writeConfig(config);
	}
	
	/** Writes over the running config using the config file. */
	public static void reload() {
		File folder = MainRegistry.configHbmDir;
		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmClient.json");
		if(config.exists()) readConfig(config);
	}
	
	private static void readConfig(File config) {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(config), JsonObject.class);
			
			for(Entry<String, ConfigWrapper> line : configMap.entrySet()) {
				
				if(json.has(line.getKey())) {
					JsonElement value = json.get(line.getKey());
					
					try {

						//world's shittiest dynamic type parser
						if(configMap.containsKey(line.getKey())) {
							if(line.getValue().value instanceof String) configMap.get(line.getKey()).set(value.getAsString());
							if(line.getValue().value instanceof Float) configMap.get(line.getKey()).set(value.getAsFloat());
							if(line.getValue().value instanceof Double) configMap.get(line.getKey()).set(value.getAsDouble());
							if(line.getValue().value instanceof Integer) configMap.get(line.getKey()).set(value.getAsInt());
							if(line.getValue().value instanceof Boolean) configMap.get(line.getKey()).set(value.getAsBoolean());
						}
						
						//gson doesn't give me the option to read the raw value of a JsonPrimitive so we have to this shit effectively twice
						//once to make sure that the parsed data matches with what's determined by the default,
						//and a second time in the ConfigWrapper to add ease of reading the data without needing manual casts
						
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void writeConfig(File config) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(config));
			writer.setIndent("  ");
			writer.beginObject();
			
			writer.name("info").value("This file can be edited ingame using the /ntmclient command.");
			
			List<String> keys = new ArrayList();
			keys.addAll(configMap.keySet());
			Collections.sort(keys); //readability is cool
			
			for(String key : keys) {
				
				ConfigWrapper wrapper = configMap.get(key); 
				Object value = wrapper.value;
				//this sucks and i am too stupid to come up with something better
				if(value instanceof String) writer.name(key).value((String) value);
				if(value instanceof Float) writer.name(key).value((Float) value);
				if(value instanceof Double) writer.name(key).value((Double) value);
				if(value instanceof Integer) writer.name(key).value((Integer) value);
				if(value instanceof Boolean) writer.name(key).value((Boolean) value);
			}
			
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class ConfigWrapper<T> {
		public T value;
		
		public ConfigWrapper(T o) {
			this.value = o;
		}

		public T get() { return value; }
		public void set(T value) { this.value = value; }
		
		public void update(String param) {
			Object stupidBufferObject = null; // wahh wahh can't cast Float to T wahh wahh shut the fuck up
			if(value instanceof String) stupidBufferObject = param;
			if(value instanceof Float) stupidBufferObject = Float.parseFloat(param);
			if(value instanceof Double) stupidBufferObject = Double.parseDouble(param);
			if(value instanceof Integer) stupidBufferObject = Integer.parseInt(param);
			if(value instanceof Boolean) stupidBufferObject = Boolean.parseBoolean(param);
			if(stupidBufferObject != null) this.value = (T) stupidBufferObject;
		}
	}
}
