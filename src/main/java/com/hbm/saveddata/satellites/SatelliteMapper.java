package com.hbm.saveddata.satellites;

public class SatelliteMapper extends Satellite {
	
	public SatelliteMapper() {
		this.ifaceAcs.add(InterfaceActions.HAS_MAP);
		this.satIface = Interfaces.SAT_PANEL;
	}

	@Override
	public float[] getColor() {
		return new float[] { 0.538F, 1.0F, 0.523F };
	}
	
}
