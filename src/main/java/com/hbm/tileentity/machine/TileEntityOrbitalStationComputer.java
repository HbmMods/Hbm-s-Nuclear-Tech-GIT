package com.hbm.tileentity.machine;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.orbit.OrbitalStation;

import net.minecraft.tileentity.TileEntity;

public class TileEntityOrbitalStationComputer extends TileEntity {
	
	// debug
	public void travelTo(CelestialBody body) {
		OrbitalStation station = OrbitalStation.getStation(xCoord, zCoord);

		if(station.orbiting == body) return;

		station.travelTo(body);
	}

}
