package com.hbm.saveddata.satellites;

public class SatelliteScanner extends Satellite {
	
	public SatelliteScanner() {
		this.ifaceAcs.add(InterfaceActions.HAS_ORES);
		this.satIface = Interfaces.SAT_PANEL;
	}
}
