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
	
	/// dupicate ids will cause wrong configs to be loaded ///
	public static final int TEST_CONFIG = 0x00;
	
	public static void loadConfigsForSync() {
		
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getTestConfig(), TEST_CONFIG));
	}
	
	public static BulletConfiguration pullConfig(int key) {
		
		for(int i = 0; i < configSet.size(); i++) {
			
			if(configSet.get(i).key == key)
				return configSet.get(i).config;
		}
		
		return null;//configSet.get(TEST_CONFIG).config;
	}

}
