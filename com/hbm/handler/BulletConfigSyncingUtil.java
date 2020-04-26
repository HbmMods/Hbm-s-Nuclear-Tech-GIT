package com.hbm.handler;

import java.util.ArrayList;
import java.util.List;
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
	
	static int i = 1;
	
	/// duplicate ids will cause wrong configs to be loaded ///
	public static int TEST_CONFIG = i++;
	public static int IRON_REVOLVER = i++;
	public static int STEEL_REVOLVER = i++;
	public static int LEAD_REVOLVER = i++;
	public static int GOLD_REVOLVER = i++;
	public static int CURSED_REVOLVER = i++;
	public static int SCHRABIDIUM_REVOLVER = i++;
	public static int NIGHT_REVOLVER = i++;
	public static int NIGHT2_REVOLVER = i++;
	public static int SATURNITE_REVOLVER = i++;
	public static int DESH_REVOLVER = i++;

	public static int G20_NORMAL = i++;
	public static int G20_SLUG = i++;
	public static int G20_FLECHETTE = i++;
	public static int G20_FIRE = i++;
	public static int G20_SHRAPNEL = i++;
	public static int G20_EXPLOSIVE = i++;
	public static int G20_CAUSTIC = i++;
	public static int G20_SHOCK = i++;
	public static int G20_WITHER = i++;

	public static int ROCKET_NORMAL = i++;
	public static int ROCKET_HE = i++;
	public static int ROCKET_INCENDIARY = i++;
	public static int ROCKET_SHRAPNEL = i++;
	public static int ROCKET_EMP = i++;
	public static int ROCKET_GLARE = i++;
	public static int ROCKET_SLEEK = i++;
	public static int ROCKET_NUKE = i++;
	public static int ROCKET_CHAINSAW = i++;
	public static int ROCKET_TOXIC = i++;

	public static int GRENADE_NORMAL = i++;
	public static int GRENADE_HE = i++;
	public static int GRENADE_INCENDIARY = i++;
	public static int GRENADE_CHEMICAL = i++;
	public static int GRENADE_SLEEK = i++;
	public static int GRENADE_CONCUSSION = i++;
	public static int GRENADE_FINNED = i++;
	public static int GRENADE_NUCLEAR = i++;

	public static int G12_NORMAL = i++;
	public static int G12_INCENDIARY = i++;
	public static int G12_SHRAPNEL = i++;
	public static int G12_DU = i++;

	public static int LR22_NORMAL = i++;
	public static int LR22_AP = i++;
	public static int LR22_NORMAL_FIRE = i++;
	public static int LR22_AP_FIRE = i++;

	public static int M44_NORMAL = i++;
	public static int M44_AP = i++;
	public static int M44_DU = i++;
	public static int M44_STAR = i++;
	public static int M44_PIP = i++;
	public static int M44_BJ = i++;
	public static int M44_SILVER = i++;
	public static int M44_ROCKET = i++;

	public static int P9_NORMAL = i++;
	public static int P9_AP = i++;
	public static int P9_DU = i++;
	public static int P9_ROCKET = i++;

	public static int BMG50_NORMAL = i++;
	public static int BMG50_INCENDIARY = i++;
	public static int BMG50_EXPLOSIVE = i++;
	public static int BMG50_DU = i++;
	public static int BMG50_STAR = i++;

	public static int R5_NORMAL = i++;
	public static int R5_EXPLOSIVE = i++;
	public static int R5_DU = i++;
	public static int R5_STAR = i++;
	public static int R5_NORMAL_BOLT = i++;
	public static int R5_EXPLOSIVE_BOLT = i++;
	public static int R5_DU_BOLT = i++;
	public static int R5_STAR_BOLT = i++;

	public static int AE50_NORMAL = i++;
	public static int AE50_AP = i++;
	public static int AE50_DU = i++;
	public static int AE50_STAR = i++;

	public static int SPECIAL_OSIPR = i++;
	public static int SPECIAL_GAUSS = i++;
	public static int SPECIAL_GAUSS_CHARGED = i++;

	public static int G20_NORMAL_FIRE = i++;
	public static int G20_SHRAPNEL_FIRE = i++;
	public static int G20_SLUG_FIRE = i++;
	public static int G20_FLECHETTE_FIRE = i++;
	public static int G20_EXPLOSIVE_FIRE = i++;
	public static int G20_CAUSTIC_FIRE = i++;
	public static int G20_SHOCK_FIRE = i++;
	public static int G20_WITHER_FIRE = i++;

	public static int NUKE_NORMAL = i++;
	public static int NUKE_MIRV = i++;
	public static int NUKE_AMAT = i++;
	public static int NUKE_PROTO = i++;
	
	public static int ENERGYPISTOL_B92 = i++;
	public static int ENERGYPISTOL_B93 = i++;
	
	public static int SPARK_PLUG = i++;
	
	public static int ZOMG_CANNON = i++;
	public static int ZOMG_SUPERUSER = i++;
	
	public static int BRIMSTONE = i++;
	
	public static int DEFABRICATOR = i++;
	
	public static int HP = i++;
		
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
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeShrapnelConfig(), G20_SHRAPNEL));
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
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketRPCConfig(), ROCKET_CHAINSAW));
		configSet.add(new ConfigKeyPair(GunRocketFactory.getRocketChlorineConfig(), ROCKET_TOXIC));

		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeConfig(), GRENADE_NORMAL));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeHEConfig(), GRENADE_HE));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeIncendirayConfig(), GRENADE_INCENDIARY));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeChlorineConfig(), GRENADE_CHEMICAL));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeSleekConfig(), GRENADE_SLEEK));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeConcussionConfig(), GRENADE_CONCUSSION));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeFinnedConfig(), GRENADE_FINNED));
		configSet.add(new ConfigKeyPair(GunGrenadeFactory.getGrenadeNuclearConfig(), GRENADE_NUCLEAR));

		configSet.add(new ConfigKeyPair(Gun12GaugeFactory.get12GaugeConfig(), G12_NORMAL));
		configSet.add(new ConfigKeyPair(Gun12GaugeFactory.get12GaugeFireConfig(), G12_INCENDIARY));
		configSet.add(new ConfigKeyPair(Gun12GaugeFactory.get12GaugeShrapnelConfig(), G12_SHRAPNEL));
		configSet.add(new ConfigKeyPair(Gun12GaugeFactory.get12GaugeDUConfig(), G12_DU));

		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRConfig(), LR22_NORMAL));
		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRAPConfig(), LR22_AP));
		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRConfig().setToFire(3), LR22_NORMAL_FIRE));
		configSet.add(new ConfigKeyPair(Gun22LRFactory.get22LRAPConfig().setToFire(3), LR22_AP_FIRE));

		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipConfig(), M44_NORMAL));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipAPConfig(), M44_AP));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipDUConfig(), M44_DU));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getNoPipStarConfig(), M44_STAR));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getPipConfig(), M44_PIP));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getBJConfig(), M44_BJ));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getSilverStormConfig(), M44_SILVER));
		configSet.add(new ConfigKeyPair(Gun44MagnumFactory.getRocketConfig(), M44_ROCKET));

		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmConfig(), P9_NORMAL));
		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmAPConfig(), P9_AP));
		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmDUConfig(), P9_DU));
		configSet.add(new ConfigKeyPair(Gun9mmFactory.get9mmRocketConfig(), P9_ROCKET));

		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGConfig(), BMG50_NORMAL));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGFireConfig(), BMG50_INCENDIARY));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGExplosiveConfig(), BMG50_EXPLOSIVE));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGDUConfig(), BMG50_DU));
		configSet.add(new ConfigKeyPair(Gun50BMGFactory.get50BMGStarConfig(), BMG50_STAR));

		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmConfig(), R5_NORMAL));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmExplosiveConfig(), R5_EXPLOSIVE));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmDUConfig(), R5_DU));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmStarConfig(), R5_STAR));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_NORMAL_BOLT));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmExplosiveConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_EXPLOSIVE_BOLT));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmDUConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_DU_BOLT));
		configSet.add(new ConfigKeyPair(Gun5mmFactory.get5mmStarConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE), R5_STAR_BOLT));

		configSet.add(new ConfigKeyPair(Gun50AEFactory.get50AEConfig(), AE50_NORMAL));
		configSet.add(new ConfigKeyPair(Gun50AEFactory.get50APConfig(), AE50_AP));
		configSet.add(new ConfigKeyPair(Gun50AEFactory.get50DUConfig(), AE50_DU));
		configSet.add(new ConfigKeyPair(Gun50AEFactory.get50StarConfig(), AE50_STAR));

		configSet.add(new ConfigKeyPair(GunOSIPRFactory.getPulseConfig(), SPECIAL_OSIPR));
		configSet.add(new ConfigKeyPair(GunGaussFactory.getGaussConfig(), SPECIAL_GAUSS));
		configSet.add(new ConfigKeyPair(GunGaussFactory.getAltConfig(), SPECIAL_GAUSS_CHARGED));

		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeConfig().setToFire(3), G20_NORMAL_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeShrapnelConfig().setToFire(3), G20_SHRAPNEL_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeSlugConfig().setToFire(3), G20_SLUG_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeFlechetteConfig().setToFire(3), G20_FLECHETTE_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeExplosiveConfig().setToFire(3), G20_EXPLOSIVE_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeCausticConfig().setToFire(3), G20_CAUSTIC_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeShockConfig().setToFire(3), G20_SHOCK_FIRE));
		configSet.add(new ConfigKeyPair(Gun20GaugeFactory.get20GaugeWitherConfig().setToFire(3), G20_WITHER_FIRE));

		configSet.add(new ConfigKeyPair(GunFatmanFactory.getNukeConfig(), NUKE_NORMAL));
		configSet.add(new ConfigKeyPair(GunFatmanFactory.getNukeProtoConfig(), NUKE_PROTO));
		configSet.add(new ConfigKeyPair(GunFatmanFactory.getBalefireConfig(), NUKE_AMAT));
		
		configSet.add(new ConfigKeyPair(GunEnergyPistolFactory.getLaserConfig(), ENERGYPISTOL_B92));
		configSet.add(new ConfigKeyPair(GunEnergyPistolFactory.getModConfig(), ENERGYPISTOL_B93));
		
		configSet.add(new ConfigKeyPair(GunSparkFactory.getElectroMagnetConfig(), SPARK_PLUG));
		
		configSet.add(new ConfigKeyPair(GunSparkFactory.getEnergyCellConfig(), DEFABRICATOR));
		
		configSet.add(new ConfigKeyPair(GunSparkFactory.getHPCartridgeConfig(), HP));
		
		configSet.add(new ConfigKeyPair(GunAnnihilationFactory.getZOMGShotConfig(), ZOMG_CANNON));
		configSet.add(new ConfigKeyPair(GunAnnihilationFactory.getAnnihilationLaserConfig(), ZOMG_SUPERUSER));
	
		configSet.add(new ConfigKeyPair(GunAnnihilationFactory.getBrimstoneLaserConfig(), BRIMSTONE));	
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
