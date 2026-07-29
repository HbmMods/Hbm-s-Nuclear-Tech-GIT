package com.hbm.saveddata.satellites;

import com.hbm.itempool.ItemPoolsSatellite;

public class SatelliteLunarMiner extends SatelliteMiner {

	@Override public String getType() { return "LUNAR_MINER"; }
	
	static {
		SatelliteMiner.registerCargo(SatelliteLunarMiner.class, ItemPoolsSatellite.POOL_SAT_LUNAR);
	}
}