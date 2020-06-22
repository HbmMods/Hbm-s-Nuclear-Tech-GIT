package com.hbm.saveddata.satellites;

public class SatelliteRadar extends Satellite {
	
	public SatelliteRadar() {
		this.ifaceAcs.add(InterfaceActions.HAS_MAP);
		this.ifaceAcs.add(InterfaceActions.HAS_RADAR);
		this.satIface = Interfaces.SAT_PANEL;
	}
}
