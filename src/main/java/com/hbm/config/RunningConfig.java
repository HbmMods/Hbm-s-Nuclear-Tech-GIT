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

public class RunningConfig {

	public static final Gson gson = new Gson();
	
	public static File getConfig(String name) {
		File folder = MainRegistry.configHbmDir;
		return new File(folder.getAbsolutePath() + File.separatorChar + name);
	}

	public static void readConfig(File config, HashMap<String, ConfigWrapper> configMap) {

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
	
	public static void writeConfig(File config, HashMap<String, ConfigWrapper> configMap, String info) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(config));
			writer.setIndent("  ");
			writer.beginObject();

			writer.name("info").value(info);

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
