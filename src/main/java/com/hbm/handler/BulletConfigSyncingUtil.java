package com.hbm.handler;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.handler.guncfg.*;

public class BulletConfigSyncingUtil {

	private static HashMap<Integer, BulletConfiguration> configSet = new HashMap<Integer, BulletConfiguration>();

	static int i = 0;

	public static int TEST_CONFIG = i++;

	public static int FEXT_NORMAL = i++;
	public static int FEXT_FOAM = i++;
	public static int FEXT_SAND = i++;

	public static int TURBINE = i++;

	public static int MASKMAN_BULLET = i++;
	public static int MASKMAN_ORB = i++;
	public static int MASKMAN_BOLT = i++;
	public static int MASKMAN_ROCKET = i++;
	public static int MASKMAN_TRACER = i++;
	public static int MASKMAN_METEOR = i++;

	public static int WORM_BOLT = i++;
	public static int WORM_LASER = i++;
	
	public static int UFO_ROCKET = i++;

	public static void loadConfigsForSync() {

		configSet.put(FEXT_NORMAL, GunEnergyFactory.getFextConfig());
		configSet.put(FEXT_FOAM, GunEnergyFactory.getFextFoamConfig());
		configSet.put(FEXT_SAND, GunEnergyFactory.getFextSandConfig());

		configSet.put(TURBINE, GunEnergyFactory.getTurbineConfig());

		configSet.put(MASKMAN_BULLET, GunNPCFactory.getMaskmanBullet());
		configSet.put(MASKMAN_ORB, GunNPCFactory.getMaskmanOrb());
		configSet.put(MASKMAN_BOLT, GunNPCFactory.getMaskmanBolt());
		configSet.put(MASKMAN_ROCKET, GunNPCFactory.getMaskmanRocket());
		configSet.put(MASKMAN_TRACER, GunNPCFactory.getMaskmanTracer());
		configSet.put(MASKMAN_METEOR, GunNPCFactory.getMaskmanMeteor());
		configSet.put(WORM_BOLT, GunNPCFactory.getWormBolt());
		configSet.put(WORM_LASER, GunNPCFactory.getWormHeadBolt());
		
		configSet.put(UFO_ROCKET, GunNPCFactory.getRocketUFOConfig());
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
