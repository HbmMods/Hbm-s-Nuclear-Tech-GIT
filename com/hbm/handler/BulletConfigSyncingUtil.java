package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.handler.guncfg.*;

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
	public static final int LEAD_REVOLVER = 0x03;
	public static final int GOLD_REVOLVER = 0x04;
	public static final int CURSED_REVOLVER = 0x05;
	public static final int SCHRABIDIUM_REVOLVER = 0x06;
	public static final int NIGHT_REVOLVER = 0x07;
	public static final int NIGHT2_REVOLVER = 0x08;
	public static final int SATURNITE_REVOLVER = 0x09;
	public static final int DESH_REVOLVER = 0x0A;

	public static final int G20_NORMAL = 0x10;
	public static final int G20_SLUG = 0x11;
	public static final int G20_FLECHETTE = 0x12;
	public static final int G20_FIRE = 0x13;
	public static final int G20_EXPLOSIVE = 0x14;
	public static final int G20_CAUSTIC = 0x15;
	public static final int G20_SHOCK = 0x16;
	public static final int G20_WITHER = 0x17;

	public static final int ROCKET_NORMAL = 0x20;
	public static final int ROCKET_HE = 0x21;
	public static final int ROCKET_INCENDIARY = 0x22;
	public static final int ROCKET_SHRAPNEL = 0x23;
	public static final int ROCKET_EMP = 0x24;
	public static final int ROCKET_GLARE = 0x25;
	public static final int ROCKET_SLEEK = 0x26;
	public static final int ROCKET_NUKE = 0x27;

	public static final int GRENADE_NORMAL = 0x30;
	public static final int GRENADE_HE = 0x31;
	public static final int GRENADE_INCENDIARY = 0x32;
	public static final int GRENADE_CHEMICAL = 0x33;
	public static final int GRENADE_SLEEK = 0x34;

	public static final int G12_NORMAL = 0x40;
	public static final int G12_INCENDIARY = 0x41;

	public static final int LR22_NORMAL = 0x50;
	public static final int LR22_AP = 0x51;
	public static final int LR22_NORMAL_FIRE = 0x52;
	public static final int LR22_AP_FIRE = 0x53;

	public static final int M44_NORMAL = 0x60;
	public static final int M44_AP = 0x61;
	public static final int M44_DU = 0x62;
	public static final int M44_PIP = 0x63;
	public static final int M44_BJ = 0x64;

	public static final int P9_NORMAL = 0x70;
	public static final int P9_AP = 0x71;
	public static final int P9_DU = 0x72;

	public static final int BMG50_NORMAL = 0x80;
	public static final int BMG50_INCENDIARY = 0x81;
	public static final int BMG50_EXPLOSIVE = 0x82;
	public static final int BMG50_DU = 0x83;

	public static final int R5_NORMAL = 0x90;
	public static final int R5_EXPLOSIVE = 0x91;
	public static final int R5_DU = 0x92;
	public static final int R5_NORMAL_BOLT = 0x93;
	public static final int R5_EXPLOSIVE_BOLT = 0x94;
	public static final int R5_DU_BOLT = 0x95;
	
	public static void loadConfigsForSync() {
		
		configSet.add(new ConfigKeyPair(BulletConfigFactory.getTestConfig(), TEST_CONFIG));
		
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevIronConfig(), IRON_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevSteelConfig(), STEEL_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevLeadConfig(), LEAD_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevGoldConfig(), GOLD_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevCursedConfig(), CURSED_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevSchrabidiumConfig(), SCHRABIDIUM_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevNightmareConfig(), NIGHT_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevNightmare2Config(), NIGHT2_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevSteelConfig().setToFire(3), SATURNITE_REVOLVER));
		configSet.add(new ConfigKeyPair(Gun357MagnumFactory.getRevDeshConfig(), DESH_REVOLVER));

		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeConfig(), G20_NORMAL));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeSlugConfig(), G20_SLUG));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeFlechetteConfig(), G20_FLECHETTE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeFireConfig(), G20_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeExplosiveConfig(), G20_EXPLOSIVE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeCausticConfig(), G20_CAUSTIC));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeShockConfig(), G20_SHOCK));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeWitherConfig(), G20_WITHER));

		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketConfig(), ROCKET_NORMAL));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketHEConfig(), ROCKET_HE));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketIncendiaryConfig(), ROCKET_INCENDIARY));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketShrapnelConfig(), ROCKET_SHRAPNEL));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketEMPConfig(), ROCKET_EMP));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketGlareConfig(), ROCKET_GLARE));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketSleekConfig(), ROCKET_SLEEK));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketNukeConfig(), ROCKET_NUKE));

		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeConfig(), GRENADE_NORMAL));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeHEConfig(), GRENADE_HE));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeIncendirayConfig(), GRENADE_INCENDIARY));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeChlorineConfig(), GRENADE_CHEMICAL));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeSleekConfig(), GRENADE_SLEEK));

		configSet.add(new ConfigKeyPair(Gun12GaugeFactory.get12GaugeConfig(), G12_NORMAL));
		configSet.add(new ConfigKeyPair(Gun12GaugeFactory.get12GaugeFireConfig(), G12_INCENDIARY));

		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRConfig(), LR22_NORMAL));
		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRAPConfig(), LR22_AP));
		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRConfig().setToFire(3), LR22_NORMAL_FIRE));
		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRAPConfig().setToFire(3), LR22_AP_FIRE));

		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipConfig(), M44_NORMAL));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipAPConfig(), M44_AP));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipDUConfig(), M44_DU));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getPipConfig(), M44_PIP));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getBJConfig(), M44_BJ));

		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmConfig(), P9_NORMAL));
		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmAPConfig(), P9_AP));
		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmDUConfig(), P9_DU));

		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGConfig(), BMG50_NORMAL));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGFireConfig(), BMG50_INCENDIARY));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGExplosiveConfig(), BMG50_EXPLOSIVE));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGDUConfig(), BMG50_DU));

		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmConfig(), R5_NORMAL));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmExplosiveConfig(), R5_EXPLOSIVE));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmDUConfig(), R5_DU));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_NORMAL_BOLT));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmExplosiveConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_EXPLOSIVE_BOLT));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmDUConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_DU_BOLT));
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
