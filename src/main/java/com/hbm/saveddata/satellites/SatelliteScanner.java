package com.hbm.saveddata.satellites;

public class SatelliteScanner extends Satellite {
	
	public SatelliteScanner() {
		this.ifaceAcs.add(InterfaceActions.HAS_ORES);
		this.satIface = Interfaces.SAT_PANEL;
	}

	@Override
	public float[] getColor() {
		return new float[] { 0.544F, 0.680F, 1.0F };
	}
	
}
