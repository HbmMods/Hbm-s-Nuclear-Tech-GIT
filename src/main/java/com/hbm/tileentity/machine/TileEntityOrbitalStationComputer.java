package com.hbm.tileentity.machine;

import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;

import net.minecraft.tileentity.TileEntity;

public class TileEntityOrbitalStationComputer extends TileEntity {
    
    // debug
    public void toggleOrbiting() {
        OrbitalStation station = OrbitalStation.getStation(xCoord, zCoord);

        int target = station.orbiting.getEnum().ordinal();
        target++;
        if(target >= SolarSystem.Body.values().length) target = 1;

        station.travelTo(SolarSystem.Body.values()[target].getBody());
    }

}
