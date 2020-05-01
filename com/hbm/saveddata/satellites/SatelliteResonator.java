package com.hbm.saveddata.satellites;

public class SatelliteResonator extends Satellite {
	
	public SatelliteResonator() {
		this.coordAcs.add(CoordActions.HAS_Y);
		this.satIface = Interfaces.SAT_COORD;
	}
}
