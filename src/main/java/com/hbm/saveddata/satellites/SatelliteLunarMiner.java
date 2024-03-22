package com.hbm.saveddata.satellites;

import com.hbm.itempool.ItemPoolSatellite;

public class SatelliteLunarMiner extends SatelliteMiner {
	
	static {
		registerCargo(SatelliteLunarMiner.class, ItemPoolSatellite.POOL_SAT_LUNAR);
	}
}