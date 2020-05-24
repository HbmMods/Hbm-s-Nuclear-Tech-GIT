package com.hbm.saveddata.satellites;

public class SatelliteMapper extends Satellite {
	
	public SatelliteMapper() {
		this.ifaceAcs.add(InterfaceActions.HAS_MAP);
		this.satIface = Interfaces.SAT_PANEL;
	}
}
