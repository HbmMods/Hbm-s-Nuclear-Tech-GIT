package com.hbm.handler;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.handler.guncfg.*;

public class BulletConfigSyncingUtil {
	
	private static HashMap<Integer, BulletConfiguration> configSet = new HashMap();
	
	static int i = 0;
	
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
	public static int ROCKET_PHOSPHORUS = i++;

	public static int GRENADE_NORMAL = i++;
	public static int GRENADE_HE = i++;
	public static int GRENADE_INCENDIARY = i++;
	public static int GRENADE_CHEMICAL = i++;
	public static int GRENADE_SLEEK = i++;
	public static int GRENADE_CONCUSSION = i++;
	public static int GRENADE_FINNED = i++;
	public static int GRENADE_NUCLEAR = i++;
	public static int GRENADE_PHOSPHORUS = i++;

	public static int G12_NORMAL = i++;
	public static int G12_INCENDIARY = i++;
	public static int G12_SHRAPNEL = i++;
	public static int G12_DU = i++;
	public static int G12_AM = i++;

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
	public static int M44_PHOSPHORUS = i++;

	public static int P9_NORMAL = i++;
	public static int P9_AP = i++;
	public static int P9_DU = i++;
	public static int P9_ROCKET = i++;

	public static int BMG50_NORMAL = i++;
	public static int BMG50_INCENDIARY = i++;
	public static int BMG50_EXPLOSIVE = i++;
	public static int BMG50_DU = i++;
	public static int BMG50_STAR = i++;
	public static int BMG50_PHOSPHORUS = i++;

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

	public static int G4_NORMAL = i++;
	public static int G4_SLUG = i++;
	public static int G4_EXPLOSIVE = i++;

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
	
	public static void loadConfigsForSync() {
		
		configSet.put(TEST_CONFIG, BulletConfigFactory.getTestConfig());
		
		configSet.put(IRON_REVOLVER, Gun357MagnumFactory.getRevIronConfig());
		configSet.put(STEEL_REVOLVER, Gun357MagnumFactory.getRevSteelConfig());
		configSet.put(LEAD_REVOLVER, Gun357MagnumFactory.getRevLeadConfig());
		configSet.put(GOLD_REVOLVER, Gun357MagnumFactory.getRevGoldConfig());
		configSet.put(CURSED_REVOLVER, Gun357MagnumFactory.getRevCursedConfig());
		configSet.put(SCHRABIDIUM_REVOLVER, Gun357MagnumFactory.getRevSchrabidiumConfig());
		configSet.put(NIGHT_REVOLVER, Gun357MagnumFactory.getRevNightmareConfig());
		configSet.put(NIGHT2_REVOLVER, Gun357MagnumFactory.getRevNightmare2Config());
		configSet.put(SATURNITE_REVOLVER, Gun357MagnumFactory.getRevSteelConfig().setToFire(3));
		configSet.put(DESH_REVOLVER, Gun357MagnumFactory.getRevDeshConfig());
        
		configSet.put(G20_NORMAL, Gun20GaugeFactory.get20GaugeConfig());
		configSet.put(G20_SLUG, Gun20GaugeFactory.get20GaugeSlugConfig());
		configSet.put(G20_FLECHETTE, Gun20GaugeFactory.get20GaugeFlechetteConfig());
		configSet.put(G20_FIRE, Gun20GaugeFactory.get20GaugeFireConfig());
		configSet.put(G20_SHRAPNEL, Gun20GaugeFactory.get20GaugeShrapnelConfig());
		configSet.put(G20_EXPLOSIVE, Gun20GaugeFactory.get20GaugeExplosiveConfig());
		configSet.put(G20_CAUSTIC, Gun20GaugeFactory.get20GaugeCausticConfig());
		configSet.put(G20_SHOCK, Gun20GaugeFactory.get20GaugeShockConfig());
		configSet.put(G20_WITHER, Gun20GaugeFactory.get20GaugeWitherConfig());
        
		configSet.put(ROCKET_NORMAL, GunRocketFactory.getRocketConfig());
		configSet.put(ROCKET_HE, GunRocketFactory.getRocketHEConfig());
		configSet.put(ROCKET_INCENDIARY, GunRocketFactory.getRocketIncendiaryConfig());
		configSet.put(ROCKET_PHOSPHORUS, GunRocketFactory.getRocketPhosphorusConfig());
		configSet.put(ROCKET_SHRAPNEL, GunRocketFactory.getRocketShrapnelConfig());
		configSet.put(ROCKET_EMP, GunRocketFactory.getRocketEMPConfig());
		configSet.put(ROCKET_GLARE, GunRocketFactory.getRocketGlareConfig());
		configSet.put(ROCKET_SLEEK, GunRocketFactory.getRocketSleekConfig());
		configSet.put(ROCKET_NUKE, GunRocketFactory.getRocketNukeConfig());
		configSet.put(ROCKET_CHAINSAW, GunRocketFactory.getRocketRPCConfig());
		configSet.put(ROCKET_TOXIC, GunRocketFactory.getRocketChlorineConfig());
        
		configSet.put(GRENADE_NORMAL, GunGrenadeFactory.getGrenadeConfig());
		configSet.put(GRENADE_HE, GunGrenadeFactory.getGrenadeHEConfig());
		configSet.put(GRENADE_INCENDIARY, GunGrenadeFactory.getGrenadeIncendirayConfig());
		configSet.put(GRENADE_PHOSPHORUS, GunGrenadeFactory.getGrenadePhosphorusConfig());
		configSet.put(GRENADE_CHEMICAL, GunGrenadeFactory.getGrenadeChlorineConfig());
		configSet.put(GRENADE_SLEEK, GunGrenadeFactory.getGrenadeSleekConfig());
		configSet.put(GRENADE_CONCUSSION, GunGrenadeFactory.getGrenadeConcussionConfig());
		configSet.put(GRENADE_FINNED, GunGrenadeFactory.getGrenadeFinnedConfig());
		configSet.put(GRENADE_NUCLEAR, GunGrenadeFactory.getGrenadeNuclearConfig());
        
		configSet.put(G12_NORMAL, Gun12GaugeFactory.get12GaugeConfig());
		configSet.put(G12_INCENDIARY, Gun12GaugeFactory.get12GaugeFireConfig());
		configSet.put(G12_SHRAPNEL, Gun12GaugeFactory.get12GaugeShrapnelConfig());
		configSet.put(G12_DU, Gun12GaugeFactory.get12GaugeDUConfig());
		configSet.put(G12_AM, Gun12GaugeFactory.get12GaugeAMConfig());
        
		configSet.put(LR22_NORMAL, Gun22LRFactory.get22LRConfig());
		configSet.put(LR22_AP, Gun22LRFactory.get22LRAPConfig());
		configSet.put(LR22_NORMAL_FIRE, Gun22LRFactory.get22LRConfig().setToFire(3));
		configSet.put(LR22_AP_FIRE, Gun22LRFactory.get22LRAPConfig().setToFire(3));
        
		configSet.put(M44_NORMAL, Gun44MagnumFactory.getNoPipConfig());
		configSet.put(M44_AP, Gun44MagnumFactory.getNoPipAPConfig());
		configSet.put(M44_DU, Gun44MagnumFactory.getNoPipDUConfig());
		configSet.put(M44_PHOSPHORUS, Gun44MagnumFactory.getPhosphorusConfig());
		configSet.put(M44_STAR, Gun44MagnumFactory.getNoPipStarConfig());
		configSet.put(M44_PIP, Gun44MagnumFactory.getPipConfig());
		configSet.put(M44_BJ, Gun44MagnumFactory.getBJConfig());
		configSet.put(M44_SILVER, Gun44MagnumFactory.getSilverStormConfig());
		configSet.put(M44_ROCKET, Gun44MagnumFactory.getRocketConfig());
        
		configSet.put(P9_NORMAL, Gun9mmFactory.get9mmConfig());
		configSet.put(P9_AP, Gun9mmFactory.get9mmAPConfig());
		configSet.put(P9_DU, Gun9mmFactory.get9mmDUConfig());
		configSet.put(P9_ROCKET, Gun9mmFactory.get9mmRocketConfig());
        
		configSet.put(BMG50_NORMAL, Gun50BMGFactory.get50BMGConfig());
		configSet.put(BMG50_INCENDIARY, Gun50BMGFactory.get50BMGFireConfig());
		configSet.put(BMG50_PHOSPHORUS, Gun50BMGFactory.get50BMGPhosphorusConfig());
		configSet.put(BMG50_EXPLOSIVE, Gun50BMGFactory.get50BMGExplosiveConfig());
		configSet.put(BMG50_DU, Gun50BMGFactory.get50BMGDUConfig());
		configSet.put(BMG50_STAR, Gun50BMGFactory.get50BMGStarConfig());
        
		configSet.put(R5_NORMAL, Gun5mmFactory.get5mmConfig());
		configSet.put(R5_EXPLOSIVE, Gun5mmFactory.get5mmExplosiveConfig());
		configSet.put(R5_DU, Gun5mmFactory.get5mmDUConfig());
		configSet.put(R5_STAR, Gun5mmFactory.get5mmStarConfig());
		configSet.put(R5_NORMAL_BOLT, Gun5mmFactory.get5mmConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
		configSet.put(R5_EXPLOSIVE_BOLT, Gun5mmFactory.get5mmExplosiveConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
		configSet.put(R5_DU_BOLT, Gun5mmFactory.get5mmDUConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
		configSet.put(R5_STAR_BOLT, Gun5mmFactory.get5mmStarConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
        
		configSet.put(AE50_NORMAL, Gun50AEFactory.get50AEConfig());
		configSet.put(AE50_AP, Gun50AEFactory.get50APConfig());
		configSet.put(AE50_DU, Gun50AEFactory.get50DUConfig());
		configSet.put(AE50_STAR, Gun50AEFactory.get50StarConfig());
        
		configSet.put(G4_NORMAL, Gun4GaugeFactory.get4GaugeConfig());
		configSet.put(G4_SLUG, Gun4GaugeFactory.get4GaugeSlugConfig());
		configSet.put(G4_EXPLOSIVE, Gun4GaugeFactory.get4GaugeExplosiveConfig());
        
		configSet.put(SPECIAL_OSIPR, GunOSIPRFactory.getPulseConfig());
		configSet.put(SPECIAL_GAUSS, GunGaussFactory.getGaussConfig());
		configSet.put(SPECIAL_GAUSS_CHARGED, GunGaussFactory.getAltConfig());
        
		configSet.put(G20_NORMAL_FIRE, Gun20GaugeFactory.get20GaugeConfig().setToFire(3));
		configSet.put(G20_SHRAPNEL_FIRE, Gun20GaugeFactory.get20GaugeShrapnelConfig().setToFire(3));
		configSet.put(G20_SLUG_FIRE, Gun20GaugeFactory.get20GaugeSlugConfig().setToFire(3));
		configSet.put(G20_FLECHETTE_FIRE, Gun20GaugeFactory.get20GaugeFlechetteConfig().setToFire(3));
		configSet.put(G20_EXPLOSIVE_FIRE, Gun20GaugeFactory.get20GaugeExplosiveConfig().setToFire(3));
		configSet.put(G20_CAUSTIC_FIRE, Gun20GaugeFactory.get20GaugeCausticConfig().setToFire(3));
		configSet.put(G20_SHOCK_FIRE, Gun20GaugeFactory.get20GaugeShockConfig().setToFire(3));
		configSet.put(G20_WITHER_FIRE, Gun20GaugeFactory.get20GaugeWitherConfig().setToFire(3));
		
		configSet.put(NUKE_NORMAL, GunFatmanFactory.getNukeConfig());
		configSet.put(NUKE_PROTO, GunFatmanFactory.getNukeProtoConfig());
		configSet.put(NUKE_AMAT, GunFatmanFactory.getBalefireConfig());
	}
	
	public static BulletConfiguration pullConfig(int key) {
		
		return configSet.get(key);
	}
	
	public static int getKey(BulletConfiguration config) {
		
		for(Entry<Integer, BulletConfiguration> e : configSet.entrySet()) {
			
			if(e.getValue() == config)
				return e.getKey();
		}
		
		return -1;
	}

}
