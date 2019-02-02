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

	public static final int G20_NORMAL = 0x10;
	public static final int G20_SLUG = 0x11;
	public static final int G20_FLECHETTE = 0x12;
	public static final int G20_FIRE = 0x13;
	public static final int G20_EXPLOSIVE = 0x14;

	public static final int ROCKET_NORMAL = 0x20;
	public static final int ROCKET_HE = 0x21;
	public static final int ROCKET_INCENDIARY = 0x22;
	public static final int ROCKET_SHRAPNEL = 0x23;
	public static final int ROCKET_EMP = 0x24;
	public static final int ROCKET_GLARE = 0x25;
	public static final int ROCKET_SLEEK = 0x26;
	
	public static void loadConfigsForSync() {
		
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getTestConfig(), TEST_CONFIG));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRevIronConfig(), IRON_REVOLVER));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRevSteelConfig(), STEEL_REVOLVER));

		configSet.add(new ConfigKeyPair(BulletConfigFactory.get20GaugeConfig(), G20_NORMAL));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.get20GaugeSlugConfig(), G20_SLUG));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.get20GaugeFlechetteConfig(), G20_FLECHETTE));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.get20GaugeFireConfig(), G20_FIRE));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.get20GaugeExplosiveConfig(), G20_EXPLOSIVE));

		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketConfig(), ROCKET_NORMAL));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketHEConfig(), ROCKET_HE));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketIncendiaryConfig(), ROCKET_INCENDIARY));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketShrapnelConfig(), ROCKET_SHRAPNEL));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketEMPConfig(), ROCKET_EMP));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketGlareConfig(), ROCKET_GLARE));
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getRocketSleekConfig(), ROCKET_SLEEK));
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
