package com.hbm.saveddata.satellites;

public class SatelliteRadar extends Satellite {
	
	public SatelliteRadar() {
		this.ifaceAcs.add(InterfaceActions.HAS_MAP);
		this.ifaceAcs.add(InterfaceActions.HAS_RADAR);
		this.satIface = Interfaces.SAT_PANEL;
	}

	@Override
	public float[] getColor() {
		return new float[] { 0.134F, 1.0F, 0.134F };
	}
	
}
