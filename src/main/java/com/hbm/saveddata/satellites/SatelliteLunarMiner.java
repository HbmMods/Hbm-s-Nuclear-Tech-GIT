package com.hbm.saveddata.satellites;

import com.hbm.itempool.ItemPoolsSatellite;

public class SatelliteLunarMiner extends SatelliteMiner {
	
	static {
		registerCargo(SatelliteLunarMiner.class, ItemPoolsSatellite.POOL_SAT_LUNAR);
	}
}