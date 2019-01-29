package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BulletConfigSyncingUtil {
	
	private static List<ConfigKeyPair> configSet = new ArrayList();
	
	/// it's like a hashmap, but easier ///
	public static class ConfigKeyPair {
		
		BulletConfiguration config;
		int key;
		
		public ConfigKeyPair() { }
		
		public ConfigKeyPair(BulletConfiguration c, int i) {
			config = c;
			key = i;
		}
	}
	
	/// duplicate ids will cause wrong configs to be loaded ///
	public static final int TEST_CONFIG = 0x00;
	public static final int IRON_REVOLVER = 0x01;
	public static final int STEEL_REVOLVER = 0x02;
	
	public static void loadConfigsForSync() {
		
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getTestConfig(), TEST_CONFIG));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRevIronConfig(), IRON_REVOLVER));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRevSteelConfig(), STEEL_REVOLVER));
	}
	
	public static BulletConfiguration pullConfig(int key) {
		
		for(int i = 0; i < configSet.size(); i++) {
			
			if(configSet.get(i).key == key)
				return configSet.get(i).config;
		}
		
		return null;//configSet.get(TEST_CONFIG).config;
	}
	
	public static int getKey(BulletConfiguration config) {
		
		for(int i = 0; i < configSet.size(); i++) {
			
			if(configSet.get(i).config == config)
				return configSet.get(i).key;
		}
		
		return -1;
	}

}
